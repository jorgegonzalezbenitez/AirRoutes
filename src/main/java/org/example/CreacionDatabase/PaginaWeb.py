from neo4j import GraphDatabase
import networkx as nx

class RutaFinder:
    def __init__(self, uri, user, password):  # Corregido _init a __init_
        self.driver = GraphDatabase.driver(uri, auth=(user, password))
        self.graph = nx.Graph()

    def close(self):
        self.driver.close()

    def cargar_rutas(self):
        # Modificación del query para incluir la aerolínea
        query = "MATCH (o)-[r:VUELA_A]->(d) RETURN o.nombre AS origen, d.nombre AS destino, r.precio AS precio, r.tiempo AS tiempo, r.compania AS compania"
        with self.driver.session() as session:
            result = session.run(query)
            for record in result:
                origen, destino, precio, tiempo, compania = record["origen"], record["destino"], record["precio"], record["tiempo"], record["compania"]
                self.graph.add_edge(origen, destino, weight=precio, tiempo=tiempo, compania=compania)

    def a_star(self, origen, destino, criterio="precio"):
        weight = "weight" if criterio == "precio" else "tiempo"
        try:
            path = nx.astar_path(self.graph, origen, destino, weight=weight)
            cost = nx.astar_path_length(self.graph, origen, destino, weight=weight)
            compania = []
            tiempos = []
            for i in range(len(path) - 1):
                compania.append(self.graph[path[i]][path[i + 1]]['compania'])
                tiempos.append(self.graph[path[i]][path[i + 1]]['tiempo'])
            return path, cost, compania, tiempos
        except nx.NetworkXNoPath:
            return None, float('inf'), [], []

def main():
    # Crear una instancia de RutaFinder para interactuar con la base de datos y el grafo
    finder = RutaFinder("bolt://localhost:7687", "neo4j", "Hippy_2004")
    
    # Cargar las rutas desde la base de datos
    finder.cargar_rutas()

    # Leer la entrada del usuario
    origen = input("Ingrese el origen: ")
    destino = input("Ingrese el destino: ")

    # Seleccionar el criterio (precio o tiempo)
    criterio = input("Seleccione el criterio de búsqueda (precio/tiempo): ").lower()
    while criterio not in ["precio", "tiempo"]:
        criterio = input("Criterio no válido. Ingrese 'precio' o 'tiempo': ").lower()
    
    # Ejecutar el algoritmo de búsqueda
    ruta, costo, aerolineas, tiempos = finder.a_star(origen, destino, criterio)

    # Mostrar los resultados al usuario
    if ruta is None:
        print(f"La ruta desde {origen} hasta {destino} no está disponible utilizando el criterio {criterio}.")
    elif criterio == "precio":
        print(f"Ruta: {ruta}")
        print(f"Costo: {costo}€")
        print("Aerolineas y tiempos:")
        for i in range(len(ruta) - 1):
            print(f"De {ruta[i]} a {ruta[i + 1]}: Aerolínea {aerolineas[i]}, Tiempo: {tiempos[i]} minutos")
    else:
        print(f"Ruta: {ruta}")
        print(f"Tiempo: {tiempos} minutos")
        print("Aerolineas y tiempos:")
        for i in range(len(ruta) - 1):
            print(f"De {ruta[i]} a {ruta[i + 1]}: Aerolínea {aerolineas[i]}, Costo: {costo}€")

    # Cerrar la conexión con Neo4j
    finder.close()

if __name__ == "__main__":
    main()
