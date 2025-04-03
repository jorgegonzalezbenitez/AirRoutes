package org.example;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

public class ConsultarVuelos {
    public static void main(String[] args) {
        String URI = "bolt://localhost:7687";  // Conectar a Docker
        String USER = "neo4j";
        String PASSWORD = "Hippy_2004";  // Contraseña que configuraste

        // Conexión a Neo4j
        try (Driver driver = GraphDatabase.driver(URI, AuthTokens.basic(USER, PASSWORD));
             Session session = driver.session()) {

            String query = """
                MATCH (o:Ciudad)-[v:VUELA_A]->(d:Ciudad)
                RETURN o.nombre AS Origen, d.nombre AS Destino, v.compania AS Compañia, v.precio($) AS Precio, v.tiempo(min) AS Tiempo
            """;

            // Ejecutar la consulta y mostrar los resultados
            Result result = session.run(query);
            while (result.hasNext()) {
                Record record = result.next();
                System.out.println("🛫 Origen: " + record.get("Origen").asString() +
                        ", Destino: " + record.get("Destino").asString() +
                        ", Compañía: " + record.get("Compañia").asString() +
                        ", Precio: " + record.get("Precio").asInt() +
                        ", Tiempo: " + record.get("Tiempo").asInt() + " min");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

