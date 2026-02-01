/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo_temp;
import static spark.Spark.*;
/**
 *
 * @author Paola Gamboa
 */
public class Monitoreo_temp {

    /**
     * @param args the command line arguments
//     */
    public static void main(String[] args) {
    
   Temperatura_API.iniciar();
        
 
    String broker = "tcp://test.mosquitto.org:1883";
    String topic = "sensor/temperatura";
    
    mqtt llamado = new mqtt(broker, topic);
    llamado.subscripcion();
    

        try {
            while (true) {
                Thread.sleep(1000); 
            }
        } catch (InterruptedException e) {
            System.out.println("Programa interrumpido.");
        }
        
        
    }
}