package prueba;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class MessageSender {

    Properties prop = new Properties();
    //Producto producto = new Producto("123","jabon",20.5,10);

    public void enviar(String topico, String mensaje){
        prop.setProperty("bootstrap.servers","localhost:9092");
        prop.setProperty("kafka.topic.name",topico);
        KafkaProducer<String,byte[]> producer = new KafkaProducer<String,byte[]>(this.prop, new StringSerializer(), new ByteArraySerializer());
        byte[] flujo = mensaje.getBytes();
        ProducerRecord<String,byte[]> record = new ProducerRecord<String,byte[]>(prop.getProperty("kafka.topic.name"),flujo);
        producer.send(record);
        producer.close();
    }

    /*public static void main(String[] args) throws InterruptedException{
        MessageSender sender = new MessageSender();
        sender.enviar();
    }*/
}
