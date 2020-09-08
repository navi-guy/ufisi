package procesamientoOrdenes;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MessageSender {

    PropertiesReader propertiesReader = new PropertiesReader();

    public void enviar(String mensaje) {
        Properties properties = this.propertiesReader.getProperties();
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(properties, new StringSerializer(), new ByteArraySerializer());
        byte[] flujo = mensaje.getBytes();
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(properties.getProperty("kafka.topic.name"), flujo);
        producer.send(record);
        producer.close();
    }

}