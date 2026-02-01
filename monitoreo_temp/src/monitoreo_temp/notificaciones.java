package monitoreo_temp;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class notificaciones {

    public static void enviarAlerta(String destinatario, double temperatura) {
        final String remitente = "maria.sanchez243459@potros.itson.edu.mx"; 
        final String password = "lpmg yrxa icsn csyd"; 

        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

       
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject("Alerta de Temperatura Alta");
            mensaje.setText("La temperatura ha superado el rango permitido.\n\nValor actual: " + temperatura + " °C");

            Transport.send(mensaje);
            System.out.println("Correo de alerta enviado correctamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println(" Error al enviar el correo: " + e.getMessage());
        }
    }
}
