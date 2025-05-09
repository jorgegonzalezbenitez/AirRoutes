package org.example.CreacionDatabase;

import org.neo4j.driver.*;

public class Neo4jConnection {
    public static void main(String[] args) {
        String URI = "bolt://localhost:7687";  // Conectar a Docker
        String USER = "neo4j";
        String PASSWORD = "Hippy_2004";  // Contraseña que configuraste

        try (Driver driver = GraphDatabase.driver(URI, AuthTokens.basic(USER, PASSWORD));
             Session session = driver.session()) {
            System.out.println("✅ Conectado a Neo4j!");
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}


