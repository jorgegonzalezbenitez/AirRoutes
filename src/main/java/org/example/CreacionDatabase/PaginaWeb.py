import streamlit as st
from neo4j import GraphDatabase
import networkx as nx
import pandas as pd
import folium
from streamlit_folium import st_folium
import os
import base64
from io import BytesIO
import qrcode
from geopy.geocoders import Nominatim

# ——— CONFIGURACIÓN DE PÁGINA ———
st.set_page_config(page_title="Air Routes ✈️", page_icon="✈️", layout="wide")

st.markdown("""
    <style>
        .stApp {
            background: linear-gradient(120deg, #e0f7fa, #ffffff);
            font-family: 'Arial', sans-serif;
        }
        h1 {
            color: #0d47a1;
            text-align: center;
            font-size: 3rem;
        }
        .stButton>button {
            background-color: #0d47a1;
            color: white;
            border-radius: 0.5rem;
        }
    </style>
""", unsafe_allow_html=True)

st.title("Air Routes ✈️")

# ——— CONSTANTES Y ESTÁTICOS ———
NEO4J_URI      = "bolt://192.168.1.141:7687"
NEO4J_USER     = "neo4j"
NEO4J_PASSWORD = "Hippy_2004"

AIRLINE_URLS = {
    "Vueling": "https://www.vueling.com",
    "Air Europa": "https://www.aireuropa.com",
    "Ryanair": "https://www.ryanair.com",
    "Binter Canarias": "https://www.bintercanarias.com",
    "Royal Air Maroc": "https://www.royalairmaroc.com",
    "Mauritania Airlines": "https://mauritaniaairlines.mr",
    "Iberia Express": "https://www.iberiaexpress.com",
    "easyJet": "https://www.easyjet.com",
    "Transavia France": "https://www.transavia.com",
    "Brussels Airlines": "https://www.brusselsairlines.com",
    "Tap Air Portugal": "https://www.flytap.com",
    "KLM": "https://www.klm.com",
    "British Airways": "https://www.britishairways.com",
    "Swiss": "https://www.swiss.com",
    "Eurowings": "https://www.eurowings.com",
    "Edelweiss Air": "https://www.flyedelweiss.com",
    "Lufthansa": "https://www.lufthansa.com",
    "Condor": "https://www.condor.com"
}

# Lista de aeropuertos canarios (nombres tal cual como aparecen en Neo4j)
CANARY_AIRPORTS = [
    "Las Palmas de Gran Canaria (LPA)",
    "Tenerife Norte (TFN)",
    "Tenerife Sur (TFS)",
    "Fuerteventura (FUE)",
    "Lanzarote (ACE)",
    "La Palma (SPC)",
    "El Hierro (VDE)",
    "La Gomera (GMZ)"
]

# ——— GEOCODIFICACIÓN ———
@st.cache_resource
def get_geocoder():
    return Nominatim(user_agent="air_routes_app")

@st.cache_data(show_spinner=False)
def geocode_place(place_name: str):
    geocoder = get_geocoder()
    loc = geocoder.geocode(place_name)
    if loc:
        return loc.latitude, loc.longitude
    return None, None

# ——— FUNCIONES DE DATOS ———
def load_graph():
    """Carga el grafo de vuelos desde Neo4j."""
    driver = GraphDatabase.driver(NEO4J_URI, auth=(NEO4J_USER, NEO4J_PASSWORD))
    G = nx.Graph()
    with driver.session() as session:
        result = session.run("""
            MATCH (o)-[r:VUELA_A]->(d)
            RETURN o.nombre AS origen,
                   d.nombre AS destino,
                   r.precio AS precio,
                   r.tiempo AS tiempo,
                   r.compania AS compania
        """)
        for rec in result:
            G.add_edge(
                rec["origen"], rec["destino"],
                weight=rec["precio"],
                tiempo=rec["tiempo"],
                compania=rec["compania"]
            )
    driver.close()
    return G

def find_cheapest_route(G, origin: str, dest: str):
    """Calcula la ruta más barata con A* e
       informa DataFrame, lista de nodos, precio y tiempo total."""
    try:
        path = nx.astar_path(G, origin, dest, weight="weight")
    except Exception:
        return None, None, None, None, None

    rows = []
    airlines = set()
    total_price = 0
    total_time = 0

    for a, b in zip(path, path[1:]):
        edge = G[a][b]
        price = edge["weight"]
        time_ = edge["tiempo"]
        airline = edge["compania"]

        rows.append({
            "Origen": a,
            "Destino": b,
            "Aerolínea": airline,
            "Precio (€)": price,
            "Tiempo (min)": time_
        })
        airlines.add(airline)
        total_price += price
        total_time += time_

    df = pd.DataFrame(rows)
    links = [f"[{c}]({AIRLINE_URLS.get(c,'#')})" for c in airlines]
    return df, path, total_price, links, total_time

def generate_map(path):
    m = folium.Map(location=[28.5, -15.5], zoom_start=7)
    coords = []

    for i, node in enumerate(path):
        latlon = geocode_place(node)
        if latlon and None not in latlon:
            coords.append(latlon)

            if i == 0:  # origen
                folium.Marker(
                    location=latlon,
                    popup=node,
                    icon=folium.Icon(color="green", icon="glyphicon glyphicon-map-marker")
                ).add_to(m)
            elif i == len(path) - 1:  # destino
                folium.Marker(
                    location=latlon,
                    popup=node,
                    icon=folium.Icon(color="red", icon="glyphicon glyphicon-map-marker")
                ).add_to(m)
            else:  # puntos intermedios
                folium.Marker(
                    location=latlon,
                    popup=node,
                    icon=folium.Icon(color="blue", icon="glyphicon glyphicon-map-marker")
                ).add_to(m)

    if coords:
        folium.PolyLine(coords, color="blue", weight=2.5, opacity=1).add_to(m)

    legend_html = f"""
 <div style='
     position: fixed; 
     bottom: 40px; left: 40px; width: auto; 
     background-color: white;
     border:2px solid grey; z-index:9999; 
     font-size:13px; padding: 10px; 
     border-radius: 10px; line-height: 1.4;
     box-shadow: 2px 2px 6px rgba(0,0,0,0.2);
 '>
     <b>Leyenda</b><br>
     <span style="color:green; font-weight:bold;">●</span> Origen: {path[0]}<br>
     {"<br>".join([
         f'<span style="color:blue; font-weight:bold;">●</span> Escala: {node}' 
         for node in path[1:-1]
     ])}
     <br><span style="color:red; font-weight:bold;">●</span> Destino: {path[-1]}
 </div>
"""
    m.get_root().html.add_child(folium.Element(legend_html))


    return m


def make_qr(data_str: str) -> str:
    """Genera código QR y lo devuelve codificado en base64."""
    img = qrcode.make(data_str)
    buf = BytesIO()
    img.save(buf, format="PNG")
    return base64.b64encode(buf.getvalue()).decode()

# ——— CARGA INICIAL DE DATOS ———
graph = load_graph()

# ——— WIDGETS DE SELECCIÓN ———
origin = st.selectbox("🛫 Origen (Islas Canarias)", CANARY_AIRPORTS)
dest   = st.selectbox("🛬 Destino", sorted(graph.nodes))

# ——— INICIALIZAR SESSION STATE ———
for key in ("df", "path", "price", "links", "time"):
    if key not in st.session_state:
        st.session_state[key] = None

col1, col2 = st.columns([3, 1])

# ——— BLOQUE DE BÚSQUEDA Y MAPA ———
with col1:
    if st.button("🔍 Buscar ruta más económica"):
        if origin == dest:
            st.warning("El origen y destino no pueden ser iguales.")
        else:
            df, path, price, links, time_tot = find_cheapest_route(graph, origin, dest)
            if df is None:
                st.error("No existe ruta posible entre ambos aeropuertos.")
            else:
                st.session_state.df    = df
                st.session_state.path  = path
                st.session_state.price = price
                st.session_state.links = links
                st.session_state.time  = time_tot

    if st.session_state.df is not None:
        st.success("✅ Ruta encontrada")
        st.dataframe(st.session_state.df, use_container_width=True)

        route_map = generate_map(st.session_state.path)
        st_folium(route_map, width=700, height=450)

# ——— DETALLES Y QR ———
with col2:
    if st.session_state.df is not None:
        st.markdown("### 🎫 Detalles del vuelo")
        st.markdown(f"*Origen:* {origin}")
        st.markdown(f"*Destino:* {dest}")
        st.markdown(f"*Precio total:* {st.session_state.price} €")
        st.markdown(f"*Tiempo total:* {st.session_state.time} min")
        st.markdown("*Aerolínea(s):*<br>" + "<br>".join(st.session_state.links),
                    unsafe_allow_html=True)

        qr_payload = f"{origin} → {dest} | {st.session_state.price}€ | {st.session_state.time}min"
        qr_b64      = make_qr(qr_payload)
        st.image(f"data:image/png;base64,{qr_b64}", width=200)

# ——— BOTÓN DE CIERRE ———
if st.button("❌ Cerrar aplicación"):
    os._exit(0)
