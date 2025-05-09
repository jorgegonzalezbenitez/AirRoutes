package org.example.CreacionDatabase;

import org.neo4j.driver.*;

public class InsertarVuelos{
    public static void main(String[] args) {
        String URI = "bolt://localhost:7687";  // Reemplaza con la IP de tu PC
        String USER = "neo4j";
        String PASSWORD = "Hippy_2004";

        try (Driver driver = GraphDatabase.driver(URI, AuthTokens.basic(USER, PASSWORD));
             Session session = driver.session()) {

            String query = """
                MERGE (o:Ciudad {nombre: $origen})
                MERGE (d:Ciudad {nombre: $destino})
                MERGE (o)-[:VUELA_A {compania: $compania, precio: $precio, tiempo: $tiempo}]->(d)
            """;
            Object[][] vuelosLPA = {
                    // Vuelos África
                    {"Las Palmas de Gran Canaria (LPA)", "Casablanca (CMN)", "Royal Air Maroc", 120, 160},
                    {"Las Palmas de Gran Canaria (LPA)", "Casablanca (CMN)", "Air Europa", 125, 165},
                    {"Las Palmas de Gran Canaria (LPA)", "Marrakech (RAK)", "Binter Canarias", 130, 90},
                    {"Las Palmas de Gran Canaria (LPA)", "Marrakech (RAK)", "Ryanair", 135, 95},
                    {"Las Palmas de Gran Canaria (LPA)", "Agadir (AGA)", "Binter Canarias", 110, 95},
                    {"Las Palmas de Gran Canaria (LPA)", "Agadir (AGA)", "Air Europa", 115, 100},
                    {"Las Palmas de Gran Canaria (LPA)", "Dakhla (VIL)", "Binter Canarias", 90, 90},
                    {"Las Palmas de Gran Canaria (LPA)", "Dakhla (VIL)", "Ryanair", 95, 95},
                    {"Las Palmas de Gran Canaria (LPA)", "El Aaiún (EUN)", "Binter Canarias", 60, 45},
                    {"Las Palmas de Gran Canaria (LPA)", "El Aaiún (EUN)", "Air Europa", 65, 50},
                    {"Las Palmas de Gran Canaria (LPA)", "Nuakchot (NKC)", "Binter Canarias", 140, 115},
                    {"Las Palmas de Gran Canaria (LPA)", "Nuakchot (NKC)", "Ryanair", 145, 120},
                    {"Las Palmas de Gran Canaria (LPA)", "Dakar (DSS)", "Binter Canarias", 170, 145},
                    {"Las Palmas de Gran Canaria (LPA)", "Dakar (DSS)", "Air Europa", 175, 150},
                    {"Las Palmas de Gran Canaria (LPA)", "Nuadibú (NDB)", "Mauritania Airlines", 100, 80},
                    {"Las Palmas de Gran Canaria (LPA)", "Nuadibú (NDB)", "Binter Canarias", 105, 85},
                    {"Las Palmas de Gran Canaria (LPA)", "Isla de Sal (SID)", "Binter Canarias", 190, 145},
                    {"Las Palmas de Gran Canaria (LPA)", "Isla de Sal (SID)", "Ryanair", 195, 150},
                    {"Las Palmas de Gran Canaria (LPA)", "Guelmin (GLN)", "Binter Canarias", 170, 85},
                    {"Las Palmas de Gran Canaria (LPA)", "Guelmin (GLN)", "Air Europa", 175, 90},

                    // Vuelos Canarias
                    {"Las Palmas de Gran Canaria (LPA)", "Tenerife Norte (TFN)", "Binter Canarias", 21, 30},
                    {"Las Palmas de Gran Canaria (LPA)", "Tenerife Norte (TFN)", "Ryanair", 23, 32},
                    {"Las Palmas de Gran Canaria (LPA)", "Tenerife Sur (TFS)", "Binter Canarias", 21.75, 40},
                    {"Las Palmas de Gran Canaria (LPA)", "Tenerife Sur (TFS)", "Air Europa", 22, 42},
                    {"Las Palmas de Gran Canaria (LPA)", "Fuerteventura (FUE)", "Binter Canarias", 25, 35},
                    {"Las Palmas de Gran Canaria (LPA)", "Fuerteventura (FUE)", "Ryanair", 27, 37},
                    {"Las Palmas de Gran Canaria (LPA)", "Lanzarote (ACE)", "Binter Canarias", 30, 45},
                    {"Las Palmas de Gran Canaria (LPA)", "Lanzarote (ACE)", "Air Europa", 32, 47},
                    {"Las Palmas de Gran Canaria (LPA)", "La Palma (SPC)", "Binter Canarias", 30, 50},
                    {"Las Palmas de Gran Canaria (LPA)", "La Palma (SPC)", "Ryanair", 32, 52},
                    {"Las Palmas de Gran Canaria (LPA)", "El Hierro (VDE)", "Binter Canarias", 45, 55},
                    {"Las Palmas de Gran Canaria (LPA)", "El Hierro (VDE)", "Air Europa", 47, 57},
                    {"Las Palmas de Gran Canaria (LPA)", "La Gomera (GMZ)", "Binter Canarias", 50, 50},
                    {"Las Palmas de Gran Canaria (LPA)", "La Gomera (GMZ)", "Ryanair", 52, 52},

                    // Vuelos España
                    {"Las Palmas de Gran Canaria (LPA)", "Madrid (MAD)", "Ryanair", 150, 15},
                    {"Las Palmas de Gran Canaria (LPA)", "Madrid (MAD)", "Air Europa", 155, 20},
                    {"Las Palmas de Gran Canaria (LPA)", "Barcelona (BCN)", "Vueling", 180, 19},
                    {"Las Palmas de Gran Canaria (LPA)", "Barcelona (BCN)", "Ryanair", 185, 22},
                    {"Las Palmas de Gran Canaria (LPA)", "Málaga (AGP)", "Vueling", 150, 12},
                    {"Las Palmas de Gran Canaria (LPA)", "Málaga (AGP)", "Air Europa", 155, 14},
                    {"Las Palmas de Gran Canaria (LPA)", "Sevilla (SVQ)", "Vueling", 150, 12},
                    {"Las Palmas de Gran Canaria (LPA)", "Sevilla (SVQ)", "Ryanair", 155, 14},
                    {"Las Palmas de Gran Canaria (LPA)", "Valencia (VLC)", "Ryanair", 180, 15},
                    {"Las Palmas de Gran Canaria (LPA)", "Valencia (VLC)", "Air Europa", 185, 18},
                    {"Las Palmas de Gran Canaria (LPA)", "Santiago de Compostela (SCQ)", "Ryanair", 180, 15},
                    {"Las Palmas de Gran Canaria (LPA)", "Santiago de Compostela (SCQ)", "Vueling", 185, 17},
                    {"Las Palmas de Gran Canaria (LPA)", "Bilbao (BIO)", "Vueling", 180, 21},
                    {"Las Palmas de Gran Canaria (LPA)", "Bilbao (BIO)", "Air Europa", 185, 23},
                    {"Las Palmas de Gran Canaria (LPA)", "Granada (GRX)", "Vueling", 150, 22},
                    {"Las Palmas de Gran Canaria (LPA)", "Granada (GRX)", "Ryanair", 155, 24}
            };


            Object[][] vuelosTFS = {
                    // Vuelos interinsulares
                    {"Tenerife Sur (TFS)", "Gran Canaria (LPA)", "Binter Canarias", 45, 40},
                    {"Tenerife Sur (TFS)", "Gran Canaria (LPA)", "Ryanair", 47, 42},
                    {"Tenerife Sur (TFS)", "Fuerteventura (FUE)", "Binter Canarias", 50, 50},
                    {"Tenerife Sur (TFS)", "Fuerteventura (FUE)", "Air Europa", 52, 52},
                    {"Tenerife Sur (TFS)", "Lanzarote (ACE)", "Binter Canarias", 55, 55},
                    {"Tenerife Sur (TFS)", "Lanzarote (ACE)", "Ryanair", 57, 57},
                    {"Tenerife Sur (TFS)", "La Palma (SPC)", "Binter Canarias", 50, 50},
                    {"Tenerife Sur (TFS)", "La Palma (SPC)", "Air Europa", 52, 52},
                    {"Tenerife Sur (TFS)", "El Hierro (VDE)", "Binter Canarias", 60, 60},
                    {"Tenerife Sur (TFS)", "El Hierro (VDE)", "Ryanair", 62, 62},
                    {"Tenerife Sur (TFS)", "La Gomera (GMZ)", "Binter Canarias", 40, 30},
                    {"Tenerife Sur (TFS)", "La Gomera (GMZ)", "Air Europa", 42, 32},

                    // Vuelos nacionales
                    {"Tenerife Sur (TFS)", "Madrid (MAD)", "Iberia Express", 100, 180},
                    {"Tenerife Sur (TFS)", "Madrid (MAD)", "Ryanair", 105, 185},
                    {"Tenerife Sur (TFS)", "Barcelona (BCN)", "Vueling", 110, 190},
                    {"Tenerife Sur (TFS)", "Barcelona (BCN)", "Air Europa", 115, 195},
                    {"Tenerife Sur (TFS)", "Málaga (AGP)", "Vueling", 95, 160},
                    {"Tenerife Sur (TFS)", "Málaga (AGP)", "Ryanair", 100, 165},
                    {"Tenerife Sur (TFS)", "Sevilla (SVQ)", "Ryanair", 90, 150},
                    {"Tenerife Sur (TFS)", "Sevilla (SVQ)", "Air Europa", 95, 155},
                    {"Tenerife Sur (TFS)", "Bilbao (BIO)", "Vueling", 105, 190},
                    {"Tenerife Sur (TFS)", "Bilbao (BIO)", "Ryanair", 110, 195},
                    {"Tenerife Sur (TFS)", "Valencia (VLC)", "Ryanair", 100, 180},
                    {"Tenerife Sur (TFS)", "Valencia (VLC)", "Air Europa", 105, 185},
                    {"Tenerife Sur (TFS)", "Santiago de Compostela (SCQ)", "Ryanair", 95, 190},
                    {"Tenerife Sur (TFS)", "Santiago de Compostela (SCQ)", "Vueling", 100, 195},
                    {"Tenerife Sur (TFS)", "Alicante (ALC)", "Vueling", 110, 180},
                    {"Tenerife Sur (TFS)", "Alicante (ALC)", "Ryanair", 115, 185},

                    // Vuelos internacionales
                    {"Tenerife Sur (TFS)", "Londres (LGW)", "easyJet", 120, 240},
                    {"Tenerife Sur (TFS)", "Londres (LGW)", "Ryanair", 125, 245},
                    {"Tenerife Sur (TFS)", "París (ORY)", "Transavia France", 130, 240},
                    {"Tenerife Sur (TFS)", "París (ORY)", "Air Europa", 135, 245},
                    {"Tenerife Sur (TFS)", "Bruselas (BRU)", "Brussels Airlines", 140, 250},
                    {"Tenerife Sur (TFS)", "Bruselas (BRU)", "Ryanair", 145, 255},
                    {"Tenerife Sur (TFS)", "Roma (FCO)", "Ryanair", 115, 250},
                    {"Tenerife Sur (TFS)", "Roma (FCO)", "Air Europa", 120, 255},
                    {"Tenerife Sur (TFS)", "Lisboa (LIS)", "TAP Air Portugal", 95, 180},
                    {"Tenerife Sur (TFS)", "Lisboa (LIS)", "Ryanair", 100, 185},
                    {"Tenerife Sur (TFS)", "Marrakech (RAK)", "Binter Canarias", 80, 160},
                    {"Tenerife Sur (TFS)", "Marrakech (RAK)", "Air Europa", 85, 165},
                    {"Tenerife Sur (TFS)", "Casablanca (CMN)", "Royal Air Maroc", 150, 190},
                    {"Tenerife Sur (TFS)", "Casablanca (CMN)", "Ryanair", 155, 195}
            };


            Object[][] vuelosTFN = {
                    // Vuelos interinsulares
                    {"Tenerife Norte (TFN)", "Las Palmas de Gran Canaria (LPA)", "Binter Canarias", 30, 30},
                    {"Tenerife Norte (TFN)", "Las Palmas de Gran Canaria (LPA)", "Ryanair", 32, 32},
                    {"Tenerife Norte (TFN)", "Fuerteventura (FUE)", "Binter Canarias", 35, 45},
                    {"Tenerife Norte (TFN)", "Fuerteventura (FUE)", "Air Europa", 37, 47},
                    {"Tenerife Norte (TFN)", "Lanzarote (ACE)", "Binter Canarias", 40, 50},
                    {"Tenerife Norte (TFN)", "Lanzarote (ACE)", "Ryanair", 42, 52},
                    {"Tenerife Norte (TFN)", "La Palma (SPC)", "Binter Canarias", 25, 30},
                    {"Tenerife Norte (TFN)", "La Palma (SPC)", "Air Europa", 27, 32},
                    {"Tenerife Norte (TFN)", "El Hierro (VDE)", "Binter Canarias", 50, 55},
                    {"Tenerife Norte (TFN)", "El Hierro (VDE)", "Ryanair", 52, 57},
                    {"Tenerife Norte (TFN)", "La Gomera (GMZ)", "Binter Canarias", 45, 45},
                    {"Tenerife Norte (TFN)", "La Gomera (GMZ)", "Air Europa", 47, 47},

                    // Vuelos nacionales
                    {"Tenerife Norte (TFN)", "Madrid (MAD)", "Iberia Express", 70, 150},
                    {"Tenerife Norte (TFN)", "Madrid (MAD)", "Ryanair", 75, 155},
                    {"Tenerife Norte (TFN)", "Barcelona (BCN)", "Vueling", 75, 180},
                    {"Tenerife Norte (TFN)", "Barcelona (BCN)", "Air Europa", 80, 185},
                    {"Tenerife Norte (TFN)", "Bilbao (BIO)", "Vueling", 80, 180},
                    {"Tenerife Norte (TFN)", "Bilbao (BIO)", "Ryanair", 85, 185},
                    {"Tenerife Norte (TFN)", "Málaga (AGP)", "Vueling", 65, 150},
                    {"Tenerife Norte (TFN)", "Málaga (AGP)", "Air Europa", 70, 155},
                    {"Tenerife Norte (TFN)", "Sevilla (SVQ)", "Ryanair", 55, 120},
                    {"Tenerife Norte (TFN)", "Sevilla (SVQ)", "Vueling", 60, 125},
                    {"Tenerife Norte (TFN)", "Valencia (VLC)", "Vueling", 70, 180},
                    {"Tenerife Norte (TFN)", "Valencia (VLC)", "Ryanair", 75, 185},
                    {"Tenerife Norte (TFN)", "Santiago de Compostela (SCQ)", "Ryanair", 60, 180},
                    {"Tenerife Norte (TFN)", "Santiago de Compostela (SCQ)", "Vueling", 65, 185},

                    // Vuelos internacionales
                    {"Tenerife Norte (TFN)", "Londres (LGW)", "easyJet", 85, 240},
                    {"Tenerife Norte (TFN)", "Londres (LGW)", "Ryanair", 90, 245},
                    {"Tenerife Norte (TFN)", "París (ORY)", "Transavia France", 90, 240},
                    {"Tenerife Norte (TFN)", "París (ORY)", "Air Europa", 95, 245},
                    {"Tenerife Norte (TFN)", "Ámsterdam (AMS)", "Transavia France", 95, 240},
                    {"Tenerife Norte (TFN)", "Ámsterdam (AMS)", "KLM", 100, 245},
                    {"Tenerife Norte (TFN)", "Bruselas (BRU)", "Brussels Airlines", 100, 240},
                    {"Tenerife Norte (TFN)", "Bruselas (BRU)", "Ryanair", 105, 245},
                    {"Tenerife Norte (TFN)", "Milán (MXP)", "Ryanair", 80, 240},
                    {"Tenerife Norte (TFN)", "Milán (MXP)", "Air Europa", 85, 245},
                    {"Tenerife Norte (TFN)", "Marrakech (RAK)", "Ryanair", 50, 120},
                    {"Tenerife Norte (TFN)", "Marrakech (RAK)", "Binter Canarias", 55, 125},
                    {"Tenerife Norte (TFN)", "Dakar (DSS)", "Binter Canarias", 140, 180},
                    {"Tenerife Norte (TFN)", "Dakar (DSS)", "Air Europa", 145, 185},
                    {"Tenerife Norte (TFN)", "Casablanca (CMN)", "Royal Air Maroc", 110, 160},
                    {"Tenerife Norte (TFN)", "Casablanca (CMN)", "Ryanair", 115, 165}
            };


            Object[][] vuelosFUE = {
                    // Vuelos interinsulares
                    {"Fuerteventura (FUE)", "Las Palmas de Gran Canaria (LPA)", "Binter Canarias", 25, 45},
                    {"Fuerteventura (FUE)", "Las Palmas de Gran Canaria (LPA)", "Air Europa", 28, 50},
                    {"Fuerteventura (FUE)", "Tenerife Norte (TFN)", "Binter Canarias", 35, 45},
                    {"Fuerteventura (FUE)", "Tenerife Norte (TFN)", "Air Europa", 37, 47},
                    {"Fuerteventura (FUE)", "Tenerife Sur (TFS)", "Binter Canarias", 40, 50},
                    {"Fuerteventura (FUE)", "Tenerife Sur (TFS)", "Ryanair", 42, 55},
                    {"Fuerteventura (FUE)", "Lanzarote (ACE)", "Binter Canarias", 30, 40},
                    {"Fuerteventura (FUE)", "Lanzarote (ACE)", "Ryanair", 32, 45},
                    {"Fuerteventura (FUE)", "La Palma (SPC)", "Binter Canarias", 45, 60},
                    {"Fuerteventura (FUE)", "La Palma (SPC)", "Air Europa", 48, 63},

                    // Vuelos nacionales
                    {"Fuerteventura (FUE)", "Madrid (MAD)", "Iberia Express", 75, 150},
                    {"Fuerteventura (FUE)", "Madrid (MAD)", "Air Europa", 78, 155},
                    {"Fuerteventura (FUE)", "Barcelona (BCN)", "Vueling", 80, 180},
                    {"Fuerteventura (FUE)", "Barcelona (BCN)", "Iberia", 85, 185},
                    {"Fuerteventura (FUE)", "Bilbao (BIO)", "Vueling", 85, 180},
                    {"Fuerteventura (FUE)", "Bilbao (BIO)", "Iberia", 88, 185},
                    {"Fuerteventura (FUE)", "Sevilla (SVQ)", "Ryanair", 60, 120},
                    {"Fuerteventura (FUE)", "Sevilla (SVQ)", "Vueling", 62, 125},
                    {"Fuerteventura (FUE)", "Málaga (AGP)", "Vueling", 70, 150},
                    {"Fuerteventura (FUE)", "Málaga (AGP)", "Air Europa", 73, 155},
                    {"Fuerteventura (FUE)", "Valencia (VLC)", "Ryanair", 75, 180},
                    {"Fuerteventura (FUE)", "Valencia (VLC)", "Vueling", 77, 182},
                    {"Fuerteventura (FUE)", "Santiago de Compostela (SCQ)", "Ryanair", 65, 180},
                    {"Fuerteventura (FUE)", "Santiago de Compostela (SCQ)", "Vueling", 68, 185},

                    // Vuelos internacionales
                    {"Fuerteventura (FUE)", "Londres (LGW)", "easyJet", 90, 240},
                    {"Fuerteventura (FUE)", "Londres (LGW)", "British Airways", 95, 245},
                    {"Fuerteventura (FUE)", "París (ORY)", "Transavia France", 95, 240},
                    {"Fuerteventura (FUE)", "París (ORY)", "Air France", 100, 245},
                    {"Fuerteventura (FUE)", "Ámsterdam (AMS)", "Transavia France", 100, 240},
                    {"Fuerteventura (FUE)", "Ámsterdam (AMS)", "KLM", 105, 250},
                    {"Fuerteventura (FUE)", "Bruselas (BRU)", "Brussels Airlines", 105, 240},
                    {"Fuerteventura (FUE)", "Bruselas (BRU)", "Ryanair", 110, 250},
                    {"Fuerteventura (FUE)", "Milán (MXP)", "Ryanair", 85, 240},
                    {"Fuerteventura (FUE)", "Milán (MXP)", "easyJet", 90, 240},
                    {"Fuerteventura (FUE)", "Ginebra (GVA)", "easyJet", 80, 240},
                    {"Fuerteventura (FUE)", "Ginebra (GVA)", "Swiss", 85, 245},
                    {"Fuerteventura (FUE)", "Düsseldorf (DUS)", "Eurowings", 95, 240},
                    {"Fuerteventura (FUE)", "Düsseldorf (DUS)", "Ryanair", 98, 245},
                    {"Fuerteventura (FUE)", "Marrakech (RAK)", "Ryanair", 55, 120},
                    {"Fuerteventura (FUE)", "Marrakech (RAK)", "Royal Air Maroc", 60, 125},
                    {"Fuerteventura (FUE)", "Casablanca (CMN)", "Royal Air Maroc", 120, 160},
                    {"Fuerteventura (FUE)", "Casablanca (CMN)", "Air Europa", 125, 165},
                    {"Fuerteventura (FUE)", "Dakar (DSS)", "Binter Canarias", 150, 180},
                    {"Fuerteventura (FUE)", "Dakar (DSS)", "Air Europa", 155, 185}
            };


            Object[][] vuelosACE = {
                    // Vuelos interinsulares
                    {"Lanzarote (ACE)", "Las Palmas de Gran Canaria (LPA)", "Ryanair", 30, 45},
                    {"Lanzarote (ACE)", "Tenerife Norte (TFN)", "Air Europa", 35, 50},
                    {"Lanzarote (ACE)", "Tenerife Sur (TFS)", "Vueling", 40, 55},
                    {"Lanzarote (ACE)", "Fuerteventura (FUE)", "Binter Canarias", 30, 40},
                    {"Lanzarote (ACE)", "La Palma (SPC)", "Ryanair", 50, 60},

                    // Vuelos nacionales
                    {"Lanzarote (ACE)", "Madrid (MAD)", "Ryanair", 70, 150},
                    {"Lanzarote (ACE)", "Barcelona (BCN)", "Air Europa", 75, 180},
                    {"Lanzarote (ACE)", "Bilbao (BIO)", "Binter Canarias", 80, 180},
                    {"Lanzarote (ACE)", "Sevilla (SVQ)", "Vueling", 65, 120},
                    {"Lanzarote (ACE)", "Málaga (AGP)", "Ryanair", 70, 150},
                    {"Lanzarote (ACE)", "Valencia (VLC)", "Air Europa", 75, 180},
                    {"Lanzarote (ACE)", "Santiago de Compostela (SCQ)", "Vueling", 70, 180},

                    // Vuelos internacionales
                    {"Lanzarote (ACE)", "Londres (LGW)", "easyJet", 95, 240},
                    {"Lanzarote (ACE)", "París (ORY)", "Air Europa", 100, 240},
                    {"Lanzarote (ACE)", "Ámsterdam (AMS)", "Transavia France", 105, 240},
                    {"Lanzarote (ACE)", "Bruselas (BRU)", "easyJet", 110, 240},
                    {"Lanzarote (ACE)", "Milán (MXP)", "Ryanair", 90, 240},
                    {"Lanzarote (ACE)", "Ginebra (GVA)", "Air Europa", 85, 240},
                    {"Lanzarote (ACE)", "Düsseldorf (DUS)", "Eurowings", 100, 240},
                    {"Lanzarote (ACE)", "Marrakech (RAK)", "Vueling", 60, 120},
                    {"Lanzarote (ACE)", "Casablanca (CMN)", "Royal Air Maroc", 130, 160},
                    {"Lanzarote (ACE)", "Dakar (DSS)", "Air Europa", 160, 180},

                    // Vuelos adicionales con otras aerolíneas
                    {"Lanzarote (ACE)", "Las Palmas de Gran Canaria (LPA)", "Air Europa", 30, 45},
                    {"Lanzarote (ACE)", "Tenerife Norte (TFN)", "Ryanair", 35, 50},
                    {"Lanzarote (ACE)", "Tenerife Sur (TFS)", "Binter Canarias", 40, 55},
                    {"Lanzarote (ACE)", "Fuerteventura (FUE)", "Vueling", 30, 40},
                    {"Lanzarote (ACE)", "La Palma (SPC)", "Vueling", 50, 60},

                    {"Lanzarote (ACE)", "Madrid (MAD)", "Binter Canarias", 70, 150},
                    {"Lanzarote (ACE)", "Barcelona (BCN)", "Vueling", 75, 180},
                    {"Lanzarote (ACE)", "Bilbao (BIO)", "Ryanair", 80, 180},
                    {"Lanzarote (ACE)", "Sevilla (SVQ)", "Air Europa", 65, 120},
                    {"Lanzarote (ACE)", "Málaga (AGP)", "Vueling", 70, 150},
                    {"Lanzarote (ACE)", "Valencia (VLC)", "Binter Canarias", 75, 180},
                    {"Lanzarote (ACE)", "Santiago de Compostela (SCQ)", "Ryanair", 70, 180},

                    {"Lanzarote (ACE)", "Londres (LGW)", "Ryanair", 95, 240},
                    {"Lanzarote (ACE)", "París (ORY)", "Transavia", 100, 240},
                    {"Lanzarote (ACE)", "Ámsterdam (AMS)", "easyJet", 105, 240},
                    {"Lanzarote (ACE)", "Bruselas (BRU)", "Air Europa", 110, 240},
                    {"Lanzarote (ACE)", "Milán (MXP)", "Binter Canarias", 90, 240},
                    {"Lanzarote (ACE)", "Ginebra (GVA)", "Ryanair", 85, 240},
                    {"Lanzarote (ACE)", "Düsseldorf (DUS)", "Eurowings", 100, 240},
                    {"Lanzarote (ACE)", "Marrakech (RAK)", "Air Europa", 60, 120},
                    {"Lanzarote (ACE)", "Casablanca (CMN)", "easyJet", 130, 160},
                    {"Lanzarote (ACE)", "Dakar (DSS)", "Vueling", 160, 180}
            };


            Object[][] vuelosSPC = {
                    // Vuelos interinsulares
                    {"La Palma (SPC)", "Las Palmas de Gran Canaria (LPA)", "Ryanair", 50, 60},
                    {"La Palma (SPC)", "Las Palmas de Gran Canaria (LPA)", "Binter Canarias", 50, 60},
                    {"La Palma (SPC)", "Las Palmas de Gran Canaria (LPA)", "Air Europa", 50, 60},
                    {"La Palma (SPC)", "Tenerife Norte (TFN)", "Air Europa", 40, 30},
                    {"La Palma (SPC)", "Tenerife Norte (TFN)", "Binter Canarias", 40, 30},
                    {"La Palma (SPC)", "Tenerife Norte (TFN)", "Vueling", 40, 30},
                    {"La Palma (SPC)", "Tenerife Sur (TFS)", "Vueling", 45, 35},
                    {"La Palma (SPC)", "Tenerife Sur (TFS)", "Binter Canarias", 45, 35},
                    {"La Palma (SPC)", "Tenerife Sur (TFS)", "Air Europa", 45, 35},
                    {"La Palma (SPC)", "Fuerteventura (FUE)", "Binter Canarias", 55, 70},
                    {"La Palma (SPC)", "Fuerteventura (FUE)", "Vueling", 55, 70},
                    {"La Palma (SPC)", "Fuerteventura (FUE)", "Ryanair", 55, 70},
                    {"La Palma (SPC)", "Lanzarote (ACE)", "Ryanair", 60, 80},
                    {"La Palma (SPC)", "Lanzarote (ACE)", "Air Europa", 60, 80},
                    {"La Palma (SPC)", "Lanzarote (ACE)", "Binter Canarias", 60, 80},
                    {"La Palma (SPC)", "El Hierro (VDE)", "Binter Canarias", 65, 85},
                    {"La Palma (SPC)", "El Hierro (VDE)", "Vueling", 65, 85},
                    {"La Palma (SPC)", "El Hierro (VDE)", "Air Europa", 65, 85},

                    // Vuelos nacionales
                    {"La Palma (SPC)", "Madrid (MAD)", "Ryanair", 90, 150},
                    {"La Palma (SPC)", "Madrid (MAD)", "Air Europa", 90, 150},
                    {"La Palma (SPC)", "Madrid (MAD)", "Vueling", 90, 150},
                    {"La Palma (SPC)", "Barcelona (BCN)", "Air Europa", 95, 180},
                    {"La Palma (SPC)", "Barcelona (BCN)", "Ryanair", 95, 180},
                    {"La Palma (SPC)", "Barcelona (BCN)", "Vueling", 95, 180},
                    {"La Palma (SPC)", "Sevilla (SVQ)", "Vueling", 85, 160},
                    {"La Palma (SPC)", "Sevilla (SVQ)", "Air Europa", 85, 160},
                    {"La Palma (SPC)", "Sevilla (SVQ)", "Ryanair", 85, 160},
                    {"La Palma (SPC)", "Málaga (AGP)", "Air Europa", 90, 150},
                    {"La Palma (SPC)", "Málaga (AGP)", "Vueling", 90, 150},
                    {"La Palma (SPC)", "Málaga (AGP)", "Ryanair", 90, 150},
                    {"La Palma (SPC)", "Bilbao (BIO)", "Vueling", 100, 180},
                    {"La Palma (SPC)", "Bilbao (BIO)", "Air Europa", 100, 180},
                    {"La Palma (SPC)", "Bilbao (BIO)", "Ryanair", 100, 180},
                    {"La Palma (SPC)", "Valencia (VLC)", "Ryanair", 95, 170},
                    {"La Palma (SPC)", "Valencia (VLC)", "Air Europa", 95, 170},
                    {"La Palma (SPC)", "Valencia (VLC)", "Vueling", 95, 170},
                    {"La Palma (SPC)", "Santiago de Compostela (SCQ)", "Ryanair", 85, 180},
                    {"La Palma (SPC)", "Santiago de Compostela (SCQ)", "Air Europa", 85, 180},
                    {"La Palma (SPC)", "Santiago de Compostela (SCQ)", "Vueling", 85, 180},

                    // Vuelos internacionales
                    {"La Palma (SPC)", "Londres (LGW)", "easyJet", 110, 240},
                    {"La Palma (SPC)", "Londres (LGW)", "Ryanair", 110, 240},
                    {"La Palma (SPC)", "Londres (LGW)", "Air Europa", 110, 240},
                    {"La Palma (SPC)", "París (ORY)", "Transavia France", 120, 240},
                    {"La Palma (SPC)", "París (ORY)", "Ryanair", 120, 240},
                    {"La Palma (SPC)", "París (ORY)", "Air Europa", 120, 240},
                    {"La Palma (SPC)", "Ámsterdam (AMS)", "Transavia", 125, 240},
                    {"La Palma (SPC)", "Ámsterdam (AMS)", "easyJet", 125, 240},
                    {"La Palma (SPC)", "Ámsterdam (AMS)", "Air Europa", 125, 240},
                    {"La Palma (SPC)", "Bruselas (BRU)", "easyJet", 130, 240},
                    {"La Palma (SPC)", "Bruselas (BRU)", "Air Europa", 130, 240},
                    {"La Palma (SPC)", "Bruselas (BRU)", "Ryanair", 130, 240},
                    {"La Palma (SPC)", "Zúrich (ZRH)", "Edelweiss Air", 115, 240},
                    {"La Palma (SPC)", "Zúrich (ZRH)", "Swiss", 115, 240},
                    {"La Palma (SPC)", "Zúrich (ZRH)", "Air Europa", 115, 240},
                    {"La Palma (SPC)", "Düsseldorf (DUS)", "Eurowings", 120, 240},
                    {"La Palma (SPC)", "Düsseldorf (DUS)", "Ryanair", 120, 240},
                    {"La Palma (SPC)", "Düsseldorf (DUS)", "Air Europa", 120, 240},
                    {"La Palma (SPC)", "Múnich (MUC)", "Lufthansa", 130, 240},
                    {"La Palma (SPC)", "Múnich (MUC)", "Air Europa", 130, 240},
                    {"La Palma (SPC)", "Múnich (MUC)", "Ryanair", 130, 240},
                    {"La Palma (SPC)", "Frankfurt (FRA)", "Condor", 125, 240},
                    {"La Palma (SPC)", "Frankfurt (FRA)", "Lufthansa", 125, 240},
                    {"La Palma (SPC)", "Frankfurt (FRA)", "Air Europa", 125, 240},
                    {"La Palma (SPC)", "Marrakech (RAK)", "Air Europa", 75, 150},
                    {"La Palma (SPC)", "Marrakech (RAK)", "Ryanair", 75, 150},
                    {"La Palma (SPC)", "Marrakech (RAK)", "Vueling", 75, 150},
                    {"La Palma (SPC)", "Casablanca (CMN)", "easyJet", 140, 180},
                    {"La Palma (SPC)", "Casablanca (CMN)", "Royal Air Maroc", 140, 180},
                    {"La Palma (SPC)", "Casablanca (CMN)", "Ryanair", 140, 180}
            };


            Object[][] vuelosGMZ = {
                    // Vuelos interinsulares
                    {"La Gomera (GMZ)", "Tenerife Norte (TFN)", "Ryanair", 40, 30},
                    {"La Gomera (GMZ)", "Tenerife Norte (TFN)", "Air Europa", 40, 30},
                    {"La Gomera (GMZ)", "Tenerife Norte (TFN)", "Vueling", 40, 30},
                    {"La Gomera (GMZ)", "Gran Canaria (LPA)", "Binter Canarias", 55, 60},
                    {"La Gomera (GMZ)", "Gran Canaria (LPA)", "Ryanair", 55, 60},
                    {"La Gomera (GMZ)", "Gran Canaria (LPA)", "Vueling", 55, 60},
                    {"La Gomera (GMZ)", "La Palma (SPC)", "Binter Canarias", 50, 45},
                    {"La Gomera (GMZ)", "La Palma (SPC)", "Vueling", 50, 45},
                    {"La Gomera (GMZ)", "La Palma (SPC)", "Air Europa", 50, 45},
                    {"La Gomera (GMZ)", "El Hierro (VDE)", "Binter Canarias", 60, 55},
                    {"La Gomera (GMZ)", "El Hierro (VDE)", "Vueling", 60, 55},
                    {"La Gomera (GMZ)", "El Hierro (VDE)", "Ryanair", 60, 55},
                    {"La Gomera (GMZ)", "Fuerteventura (FUE)", "Binter Canarias", 65, 75},
                    {"La Gomera (GMZ)", "Fuerteventura (FUE)", "Vueling", 65, 75},
                    {"La Gomera (GMZ)", "Fuerteventura (FUE)", "Air Europa", 65, 75},
                    {"La Gomera (GMZ)", "Lanzarote (ACE)", "Binter Canarias", 70, 85},
                    {"La Gomera (GMZ)", "Lanzarote (ACE)", "Ryanair", 70, 85},
                    {"La Gomera (GMZ)", "Lanzarote (ACE)", "Vueling", 70, 85},

                    // Vuelos nacionales
                    {"La Gomera (GMZ)", "Madrid (MAD)", "Ryanair", 95, 160},
                    {"La Gomera (GMZ)", "Madrid (MAD)", "Air Europa", 95, 160},
                    {"La Gomera (GMZ)", "Madrid (MAD)", "Vueling", 95, 160},
                    {"La Gomera (GMZ)", "Barcelona (BCN)", "Vueling", 100, 180},
                    {"La Gomera (GMZ)", "Barcelona (BCN)", "Air Europa", 100, 180},
                    {"La Gomera (GMZ)", "Barcelona (BCN)", "Ryanair", 100, 180},
                    {"La Gomera (GMZ)", "Sevilla (SVQ)", "Vueling", 85, 150},
                    {"La Gomera (GMZ)", "Sevilla (SVQ)", "Ryanair", 85, 150},
                    {"La Gomera (GMZ)", "Sevilla (SVQ)", "Air Europa", 85, 150},
                    {"La Gomera (GMZ)", "Málaga (AGP)", "Vueling", 90, 150},
                    {"La Gomera (GMZ)", "Málaga (AGP)", "Ryanair", 90, 150},
                    {"La Gomera (GMZ)", "Málaga (AGP)", "Air Europa", 90, 150},
                    {"La Gomera (GMZ)", "Bilbao (BIO)", "Vueling", 105, 180},
                    {"La Gomera (GMZ)", "Bilbao (BIO)", "Ryanair", 105, 180},
                    {"La Gomera (GMZ)", "Bilbao (BIO)", "Air Europa", 105, 180},
                    {"La Gomera (GMZ)", "Valencia (VLC)", "Ryanair", 95, 170},
                    {"La Gomera (GMZ)", "Valencia (VLC)", "Air Europa", 95, 170},
                    {"La Gomera (GMZ)", "Valencia (VLC)", "Vueling", 95, 170},
                    {"La Gomera (GMZ)", "Santiago de Compostela (SCQ)", "Air Europa", 85, 180},
                    {"La Gomera (GMZ)", "Santiago de Compostela (SCQ)", "Ryanair", 85, 180},
                    {"La Gomera (GMZ)", "Santiago de Compostela (SCQ)", "Vueling", 85, 180},

                    // Vuelos internacionales
                    {"La Gomera (GMZ)", "Londres (LGW)", "easyJet", 110, 240},
                    {"La Gomera (GMZ)", "Londres (LGW)", "Ryanair", 110, 240},
                    {"La Gomera (GMZ)", "Londres (LGW)", "Air Europa", 110, 240},
                    {"La Gomera (GMZ)", "París (ORY)", "Transavia", 120, 240},
                    {"La Gomera (GMZ)", "París (ORY)", "Ryanair", 120, 240},
                    {"La Gomera (GMZ)", "París (ORY)", "Air Europa", 120, 240},
                    {"La Gomera (GMZ)", "Ámsterdam (AMS)", "Transavia", 125, 240},
                    {"La Gomera (GMZ)", "Ámsterdam (AMS)", "easyJet", 125, 240},
                    {"La Gomera (GMZ)", "Ámsterdam (AMS)", "Air Europa", 125, 240},
                    {"La Gomera (GMZ)", "Bruselas (BRU)", "easyJet", 130, 240},
                    {"La Gomera (GMZ)", "Bruselas (BRU)", "Air Europa", 130, 240},
                    {"La Gomera (GMZ)", "Bruselas (BRU)", "Ryanair", 130, 240},
                    {"La Gomera (GMZ)", "Zúrich (ZRH)", "Edelweiss Air", 115, 240},
                    {"La Gomera (GMZ)", "Zúrich (ZRH)", "Swiss", 115, 240},
                    {"La Gomera (GMZ)", "Zúrich (ZRH)", "Air Europa", 115, 240},
                    {"La Gomera (GMZ)", "Düsseldorf (DUS)", "Eurowings", 120, 240},
                    {"La Gomera (GMZ)", "Düsseldorf (DUS)", "Ryanair", 120, 240},
                    {"La Gomera (GMZ)", "Düsseldorf (DUS)", "Air Europa", 120, 240},
                    {"La Gomera (GMZ)", "Múnich (MUC)", "Lufthansa", 130, 240},
                    {"La Gomera (GMZ)", "Múnich (MUC)", "Air Europa", 130, 240},
                    {"La Gomera (GMZ)", "Múnich (MUC)", "Ryanair", 130, 240},
                    {"La Gomera (GMZ)", "Frankfurt (FRA)", "Condor", 125, 240},
                    {"La Gomera (GMZ)", "Frankfurt (FRA)", "Lufthansa", 125, 240},
                    {"La Gomera (GMZ)", "Frankfurt (FRA)", "Air Europa", 125, 240},
                    {"La Gomera (GMZ)", "Marrakech (RAK)", "Air Europa", 75, 150},
                    {"La Gomera (GMZ)", "Marrakech (RAK)", "Ryanair", 75, 150},
                    {"La Gomera (GMZ)", "Marrakech (RAK)", "Vueling", 75, 150},
                    {"La Gomera (GMZ)", "Casablanca (CMN)", "easyJet", 140, 180},
                    {"La Gomera (GMZ)", "Casablanca (CMN)", "Royal Air Maroc", 140, 180},
                    {"La Gomera (GMZ)", "Casablanca (CMN)", "Ryanair", 140, 180}
            };


            Object[][] vuelosVDE = {
                    // Vuelos interinsulares
                    {"El Hierro (VDE)", "Tenerife Norte (TFN)", "Air Europa", 50, 40},
                    {"El Hierro (VDE)", "Tenerife Norte (TFN)", "Ryanair", 50, 40},
                    {"El Hierro (VDE)", "Tenerife Norte (TFN)", "Vueling", 50, 40},
                    {"El Hierro (VDE)", "Gran Canaria (LPA)", "Binter Canarias", 65, 70},
                    {"El Hierro (VDE)", "Gran Canaria (LPA)", "Vueling", 65, 70},
                    {"El Hierro (VDE)", "Gran Canaria (LPA)", "Ryanair", 65, 70},
                    {"El Hierro (VDE)", "La Palma (SPC)", "Binter Canarias", 55, 50},
                    {"El Hierro (VDE)", "La Palma (SPC)", "Ryanair", 55, 50},
                    {"El Hierro (VDE)", "La Palma (SPC)", "Vueling", 55, 50},
                    {"El Hierro (VDE)", "La Gomera (GMZ)", "Binter Canarias", 60, 55},
                    {"El Hierro (VDE)", "La Gomera (GMZ)", "Vueling", 60, 55},
                    {"El Hierro (VDE)", "La Gomera (GMZ)", "Ryanair", 60, 55},
                    {"El Hierro (VDE)", "Fuerteventura (FUE)", "Binter Canarias", 75, 80},
                    {"El Hierro (VDE)", "Fuerteventura (FUE)", "Vueling", 75, 80},
                    {"El Hierro (VDE)", "Fuerteventura (FUE)", "Air Europa", 75, 80},
                    {"El Hierro (VDE)", "Lanzarote (ACE)", "Binter Canarias", 80, 90},
                    {"El Hierro (VDE)", "Lanzarote (ACE)", "Vueling", 80, 90},
                    {"El Hierro (VDE)", "Lanzarote (ACE)", "Ryanair", 80, 90},

                    // Vuelos nacionales
                    {"El Hierro (VDE)", "Madrid (MAD)", "Ryanair", 110, 170},
                    {"El Hierro (VDE)", "Madrid (MAD)", "Air Europa", 110, 170},
                    {"El Hierro (VDE)", "Madrid (MAD)", "Vueling", 110, 170},
                    {"El Hierro (VDE)", "Barcelona (BCN)", "Vueling", 115, 190},
                    {"El Hierro (VDE)", "Barcelona (BCN)", "Ryanair", 115, 190},
                    {"El Hierro (VDE)", "Barcelona (BCN)", "Air Europa", 115, 190},
                    {"El Hierro (VDE)", "Sevilla (SVQ)", "Vueling", 95, 160},
                    {"El Hierro (VDE)", "Sevilla (SVQ)", "Ryanair", 95, 160},
                    {"El Hierro (VDE)", "Sevilla (SVQ)", "Air Europa", 95, 160},
                    {"El Hierro (VDE)", "Málaga (AGP)", "Vueling", 100, 160},
                    {"El Hierro (VDE)", "Málaga (AGP)", "Ryanair", 100, 160},
                    {"El Hierro (VDE)", "Málaga (AGP)", "Air Europa", 100, 160},
                    {"El Hierro (VDE)", "Bilbao (BIO)", "Vueling", 120, 190},
                    {"El Hierro (VDE)", "Bilbao (BIO)", "Ryanair", 120, 190},
                    {"El Hierro (VDE)", "Bilbao (BIO)", "Air Europa", 120, 190},
                    {"El Hierro (VDE)", "Valencia (VLC)", "Ryanair", 110, 180},
                    {"El Hierro (VDE)", "Valencia (VLC)", "Vueling", 110, 180},
                    {"El Hierro (VDE)", "Valencia (VLC)", "Air Europa", 110, 180},
                    {"El Hierro (VDE)", "Santiago de Compostela (SCQ)", "Ryanair", 100, 190},
                    {"El Hierro (VDE)", "Santiago de Compostela (SCQ)", "Vueling", 100, 190},
                    {"El Hierro (VDE)", "Santiago de Compostela (SCQ)", "Air Europa", 100, 190},

                    // Vuelos internacionales
                    {"El Hierro (VDE)", "Londres (LGW)", "easyJet", 130, 250},
                    {"El Hierro (VDE)", "Londres (LGW)", "Ryanair", 130, 250},
                    {"El Hierro (VDE)", "Londres (LGW)", "Air Europa", 130, 250},
                    {"El Hierro (VDE)", "París (ORY)", "Transavia France", 140, 250},
                    {"El Hierro (VDE)", "París (ORY)", "Ryanair", 140, 250},
                    {"El Hierro (VDE)", "París (ORY)", "Air Europa", 140, 250},
                    {"El Hierro (VDE)", "Ámsterdam (AMS)", "Transavia France", 145, 250},
                    {"El Hierro (VDE)", "Ámsterdam (AMS)", "easyJet", 145, 250},
                    {"El Hierro (VDE)", "Ámsterdam (AMS)", "Air Europa", 145, 250},
                    {"El Hierro (VDE)", "Bruselas (BRU)", "easyJet", 150, 250},
                    {"El Hierro (VDE)", "Bruselas (BRU)", "Ryanair", 150, 250},
                    {"El Hierro (VDE)", "Bruselas (BRU)", "Air Europa", 150, 250},
                    {"El Hierro (VDE)", "Zúrich (ZRH)", "Edelweiss Air", 135, 250},
                    {"El Hierro (VDE)", "Zúrich (ZRH)", "Swiss", 135, 250},
                    {"El Hierro (VDE)", "Zúrich (ZRH)", "Air Europa", 135, 250},
                    {"El Hierro (VDE)", "Düsseldorf (DUS)", "Eurowings", 140, 250},
                    {"El Hierro (VDE)", "Düsseldorf (DUS)", "Ryanair", 140, 250},
                    {"El Hierro (VDE)", "Düsseldorf (DUS)", "Air Europa", 140, 250},
                    {"El Hierro (VDE)", "Múnich (MUC)", "Lufthansa", 150, 250},
                    {"El Hierro (VDE)", "Múnich (MUC)", "Air Europa", 150, 250},
                    {"El Hierro (VDE)", "Múnich (MUC)", "Ryanair", 150, 250},
                    {"El Hierro (VDE)", "Frankfurt (FRA)", "Condor", 145, 250},
                    {"El Hierro (VDE)", "Frankfurt (FRA)", "Lufthansa", 145, 250},
                    {"El Hierro (VDE)", "Frankfurt (FRA)", "Air Europa", 145, 250},
                    {"El Hierro (VDE)", "Marrakech (RAK)", "Binter Canarias", 90, 160},
                    {"El Hierro (VDE)", "Marrakech (RAK)", "Ryanair", 90, 160},
                    {"El Hierro (VDE)", "Marrakech (RAK)", "Air Europa", 90, 160},
                    {"El Hierro (VDE)", "Casablanca (CMN)", "easyJet", 160, 190},
                    {"El Hierro (VDE)", "Casablanca (CMN)", "Royal Air Maroc", 160, 190},
                    {"El Hierro (VDE)", "Casablanca (CMN)", "Ryanair", 160, 190}
            };

            for (Object[] vuelo : vuelosLPA) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }

            for (Object[] vuelo : vuelosACE) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }

            for (Object[] vuelo : vuelosFUE) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }

            for (Object[] vuelo : vuelosTFN) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }

            for (Object[] vuelo : vuelosTFS) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }

            for (Object[] vuelo : vuelosVDE) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }
            for (Object[] vuelo : vuelosGMZ) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }

            for (Object[] vuelo : vuelosSPC) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters(
                            "origen", vuelo[0],
                            "destino", vuelo[1],
                            "compania", vuelo[2],
                            "precio", vuelo[3],
                            "tiempo", vuelo[4]
                    ));
                    return null;
                });
            }

            System.out.println("✈️ Vuelos insertados correctamente desde el equipo remoto!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}