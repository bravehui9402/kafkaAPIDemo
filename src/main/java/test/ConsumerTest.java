package test;

import consumer.KafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;

public class ConsumerTest {

    public static void main(String[] args) {
        KafkaConsumer consumer = new KafkaConsumerFactory().getConsumer();

        consumer.subscribe(Arrays.asList("topic1"));

        ConsumerRecords records = consumer.poll(1000);

        records.records()




    }
}
