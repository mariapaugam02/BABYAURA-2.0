package monitoreo_temp;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.simple.JSONObject;

public class mqtt {

    private String broker;
    private String topic;
    private MqttClient client;

    // Umbral para enviar alerta por correo
    private static final double UMBRAL_TEMPERATURA = 37.0;

    public mqtt(String broker, String topic) {
        this.broker = broker;
        this.topic = topic;
    }

    public void subscripcion() {
        try {
            client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.setConnectionTimeout(20);
            options.setKeepAliveInterval(60);

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println(" Conexión perdida con el broker MQTT: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String payload = new String(message.getPayload());
                    System.out.println("Mensaje recibido: " + payload);

                    JSONObject dato = ParserJSON.parse(payload);

                    if (dato != null && aseg_calidad.datoValido(dato)) {
                        double tempReal = Double.parseDouble(dato.get("sensor_real").toString());
                        double tempSim = Double.parseDouble(dato.get("sensor_simulado").toString());

                        
                        new Thread(() -> {
                            try {
                                influxDB.write(tempReal, tempSim);
                                System.out.println("Dato válido registrado en InfluxDB.");
                            } catch (Exception ex) {
                                System.err.println("Error al escribir en InfluxDB: " + ex.getMessage());
                            }
                        }).start();

                       
                        if (tempReal > UMBRAL_TEMPERATURA) {
                            new Thread(() -> {
                                try {
                                    notificaciones.enviarAlerta("destinatario@correo.com", tempReal);
                                    
                                   influxDB.alerta("temperatura_alta_real", tempReal, "Temperatura real supero el umbral");
                                    
                                } catch (Exception e) {
                                    System.err.println("Error al enviar notificación tempReal: " + e.getMessage());
                                }
                            }).start();
                        }

                       
                        if (tempSim > UMBRAL_TEMPERATURA) {
                            new Thread(() -> {
                                try {
                                    notificaciones.enviarAlerta("destinatario@correo.com", tempSim);
                                    
                               influxDB.alerta("Temperatura_alta_simulada", tempSim, "Temperatura simulada supero el umbral");
                                    
                                } catch (Exception e) {
                                    System.err.println("Error al enviar notificación tempSim: " + e.getMessage());
                                }
                            }).start();
                        }
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    
                }
            });

            System.out.println("Conectando al broker MQTT...");
            client.connect(options);
            client.subscribe(topic, 1);
            System.out.println("Suscrito al topic: " + topic);
            System.out.println("Esperando mensajes...\n");

        } catch (Exception e) {
            System.err.println("Error al conectar o suscribirse al broker MQTT:");
            e.printStackTrace();
        }
    }
}
