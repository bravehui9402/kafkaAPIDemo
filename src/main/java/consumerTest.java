import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class consumerTest {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.25.10:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"test1");
        KafkaConsumer consumer = new KafkaConsumer(prop);
        try{
            consumer.subscribe(Arrays.asList("topic1"));
            ConsumerRecords<String,String> records = consumer.poll(500);
            for(ConsumerRecord record:records){
                System.out.println(record.topic());
                System.out.println(record.partition());
                System.out.println(record.key());
                System.out.println(record.value());
                System.out.println(record.offset());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            consumer.close();
        }



    }
}
