package procesamientoOrdenes;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class MessageReceiver {

    public String recibir() {
        String mensaje = "";
        Properties props = new PropertiesReader().getProperties();
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(props.getProperty("kafka.topic.consumer")));
            ConsumerRecords<String, String> records = consumer.poll(1000L);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }
        }
        return mensaje;
    }

    public static void main(String[] args) {
        MessageReceiver messageReceiver = new MessageReceiver();
        messageReceiver.recibir();
    }
}
