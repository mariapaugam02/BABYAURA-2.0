/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo_temp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ParserJSON {
    
public static JSONObject parse(String jsonStr) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(jsonStr);
        } catch (Exception e) {
            System.out.println(" Error al parsear JSON: " + jsonStr);
            return null;
        }
    }
}