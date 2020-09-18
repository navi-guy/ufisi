package procesamientoOrdenes;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MessageSender {

    Properties prop = new PropertiesReader().getProperties();

    public void enviar(String topico, String mensaje) {
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(this.prop, new StringSerializer(), new ByteArraySerializer());
        byte[] flujo = mensaje.getBytes();
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(prop.getProperty("kafka.topic.name"), flujo);
        producer.send(record);
        producer.close();
    }

}