package procesamientoOrdenes;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.swing.*;
import java.util.Arrays;
import java.util.Properties;

public class MessageReceiver implements Runnable{

    public void recibir() {
        Properties props = new PropertiesReader().getProperties();
        Message message;
        props.put("group.id", "01");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(props.getProperty("kafka.topic.consumer")));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000L);
                for (ConsumerRecord<String, String> record : records) {
                    message = new Gson().fromJson(record.value(), Message.class);
                    JOptionPane.showMessageDialog(null, message.getMensaje() ,"Mensaje", message.getEstado());
                }
            }
        }
    }

    @Override
    public void run() {
        recibir();
    }
}
