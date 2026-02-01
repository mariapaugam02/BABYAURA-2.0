package monitoreo_temp;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord;
import java.time.Instant;
import java.util.List;

public class influxDB {

    private static final String url = "https://us-east-1-1.aws.cloud2.influxdata.com";
    private static final String token = "KBgAR95hc6jIoFICdlt9c62KVBaS_u2MDJl6xd8GzDuA9gMyDlfnu_cE38VmsAlxpq5B-iRd6xA0SGQVuoAf1w==";
    private static final String org = "8b3329c2fe09326f";
    private static final String bucket = "monitoreoTempBebe";

    private static InfluxDBClient client = null;

    public static InfluxDBClient getClient() {
        if (client == null) {
          
            client = InfluxDBClientFactory.create(url, token.toCharArray());
        }
        return client;
    }

    public static void write(double sensorReal, double sensorSimulado) {
        try {
            InfluxDBClient client = getClient();
            WriteApiBlocking writeApi = client.getWriteApiBlocking();

            Point point = Point.measurement("temperatura")
                    .addField("sensor_real", sensorReal)
                    .addField("sensor_simulado", sensorSimulado)
                    .time(Instant.now(), WritePrecision.MS);

            
            writeApi.writePoint(bucket, org, point);

            System.out.println("Dato válido registrado en InfluxDB.");
        } catch (Exception e) {
            System.err.println("Error al guardar en InfluxDB: " + e.getMessage());
        }
    }
    
    public static void alerta(String tipo, double valor, String mensaje) {
        try {
            InfluxDBClient client = getClient();
            WriteApiBlocking writeApi = client.getWriteApiBlocking();

            Point point = Point.measurement("alertas")
                    .addTag("tipo", tipo)
                    .addField("valor", valor)
                    .addField("mensaje", mensaje)
                    .time(Instant.now(), WritePrecision.MS);

            writeApi.writePoint(bucket, org, point);
            System.out.println("Alerta registrada en InfluxDB.");
        } catch (Exception e) {
            System.err.println("Error al guardar la alerta: " + e.getMessage());
        }
    }
    
   
    public static temperatura_datos obtenerUltimaTempReal() {

    String flux =
        "from(bucket: \"monitoreoTempBebe\")"
      + " |> range(start: -10m)"
      + " |> filter(fn: (r) => r._measurement == \"temperatura\")"
      + " |> filter(fn: (r) => r._field == \"sensor_real\")"
      + " |> last()";

    QueryApi queryApi = getClient().getQueryApi();
    List<FluxTable> tables = queryApi.query(flux, org);

    for (FluxTable table : tables) {
        for (FluxRecord record : table.getRecords()) {
            double valor = ((Number) record.getValue()).doubleValue();
            String fecha = record.getTime().toString();
            return new temperatura_datos(valor);
        }
    }
    return null;
}
}
    
    
    
