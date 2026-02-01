
package monitoreo_temp;

import org.json.simple.JSONObject;

/**
 *
 * @author Maria Paula Gamboa
 */
public class aseg_calidad {
    
     public static boolean datoValido(JSONObject dato) {
        
        double tempReal = Double.parseDouble(dato.get("sensor_real").toString());
        double tempSim = Double.parseDouble(dato.get("sensor_simulado").toString());
        

        // Reglas de aseguramiento de calidad
        if (tempReal < 31 || tempReal > 41) {
            System.out.println("Temperatura real fuera de rango: " + tempReal);
            return false;
        }
        if (tempSim < 31 || tempSim > 41) {
            System.out.println("Temperatura simulada fuera de rango: " + tempSim);
            return false;
        }

        return true;
    }
}