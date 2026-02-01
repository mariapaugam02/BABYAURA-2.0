/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo_temp;

import com.google.gson.Gson;
import static spark.Spark.*;


public class Temperatura_API {

    public static void iniciar(){
        
      
        port(Integer.parseInt(System.getenv("PORT")));

        

        // 🔹 CORS (OBLIGATORIO PARA FLUTTER WEB)
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            res.header("Access-Control-Allow-Headers", "*");
            res.header("Access-Control-Allow-Credentials", "true");
        });

        options("/*", (req, res) -> "OK");

        Gson gson = new Gson();

        // 🔹 ENDPOINT
        get("/api/temperaturas/real/ultima", (req, res) -> {
            res.type("application/json");

            temperatura_datos dato = influxDB.obtenerUltimaTempReal();

            if (dato == null) {
                res.status(404);
                return "{}";
            }

            return gson.toJson(dato);
        });
    }
}
