package producerDemo;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class producerDemo {
    private static Logger logger = Logger.getLogger(producerDemo.class);
    public static void main(String[] args) {

        ProducerRecord record = new ProducerRecord("topic1","value");

        KafkaProducer producer = getProducer();
        //发送既忘发送方式
        sendMessage(producer,record);
        //同步发送方式
        sendMessageSync(producer,record);
        //异步发送
        sendMenssageAsync(producer,record);
        //异步发送带事务
        sendMenssageAsyncWithTranstion(producer,record);

        producer.close();

    }

    //异步发送带事务
    private static void sendMenssageAsyncWithTranstion(KafkaProducer producer, ProducerRecord record) {
        // 初始化事务
        producer.initTransactions();
        try {
            Thread.sleep(2000);
            // 开启事务
            producer.beginTransaction();
            // 发送消息
            producer.send(record);
            // 提交事务
            producer.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            // 终止事务
            producer.abortTransaction();
        }
    }

    //异步发送
    private static void sendMenssageAsync(KafkaProducer producer, ProducerRecord record) {
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e == null){
                    System.out.println("send success");
                    recordMetadata.topic();
                }else{
                    System.out.println("send fail"+e.getMessage());
                }
            }
        });
    }

    //同步发送方式
    private static void sendMessageSync(KafkaProducer producer, ProducerRecord record) {

        try {
            Future<RecordMetadata> future = (Future<RecordMetadata>)producer.send(record).get();
            RecordMetadata recordMetadata = future.get();
            recordMetadata.topic();
            recordMetadata.partition();
            recordMetadata.offset();
            recordMetadata.timestamp();
            recordMetadata.serializedKeySize();
            recordMetadata.serializedValueSize();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    //发送既往的发送方式
    private static void sendMessage(KafkaProducer producer, ProducerRecord record) {
        try{
            producer.send(record);
        }catch (Exception e){
            logger.error("发送既往方式发送失败",e);
        }
    }


    public static KafkaProducer getProducer(){
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,""); //集群地址
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());//key序列化
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());//value序列化
        prop.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"demo1");//事物id
        prop.put(ProducerConfig.ACKS_CONFIG,"-1"); //ack，副本全部进行落盘
        prop.put(ProducerConfig.RETRIES_CONFIG,"3"); //重试次数
        prop.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,"1000"); //重试时间间隔
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true"); //是否开启幂等性
        prop.put("buffer.memory", 33554432); // 缓存大小，根据本机内存大小配置
        prop.put("linger.ms", 1000); // 发送频率，满足任务一个条件发送
        KafkaProducer producer = new KafkaProducer(prop);
        return producer;
    }
}
