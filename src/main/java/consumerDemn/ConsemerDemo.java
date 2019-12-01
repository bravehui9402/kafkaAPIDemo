package consumerDemn;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;
import producerDemo.producerDemo;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ConsemerDemo {
    private static Logger logger = Logger.getLogger(producerDemo.class);

    public static void main(String[] args) {
        KafkaConsumer consumer = getConsumer();

        getMessageByTopic(consumer);
        getMessageByPartition(consumer);
    }
    //���ݷ���������Ϣ
    private static void getMessageByPartition(KafkaConsumer consumer) {
        try{
            consumer.assign(Arrays.asList(new TopicPartition("topic",0)));
            ConsumerRecords<String,String> records = consumer.poll(500);
            for(ConsumerRecord record:records){
                System.out.println(record.topic());
                System.out.println(record.partition());
                System.out.println(record.key());
                System.out.println(record.value());
                System.out.println(record.offset());}
            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets,
                                       Exception exception) {
                    if (exception == null) {
                        System.out.println(offsets);
                    }else {
                        logger.error("fail to commit offsets", exception);
                    }
                }
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            consumer.close();
        }

    }

    //�������ⶩ����Ϣ
    private static void getMessageByTopic(KafkaConsumer consumer) {
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


    public static KafkaConsumer getConsumer(){
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"");//��Ⱥ��ַ
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"groupId");
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"true");
        prop.put("auto.offset.reset","earliest"); // �������offset��ʼ��ȡ��latest:�������offset��ʼ����
        KafkaConsumer consumer = new KafkaConsumer(prop);
        return consumer;
    }
}
