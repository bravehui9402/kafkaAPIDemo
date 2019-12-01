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
        //���ͼ������ͷ�ʽ
        sendMessage(producer,record);
        //ͬ�����ͷ�ʽ
        sendMessageSync(producer,record);
        //�첽����
        sendMenssageAsync(producer,record);
        //�첽���ʹ�����
        sendMenssageAsyncWithTranstion(producer,record);

        producer.close();

    }

    //�첽���ʹ�����
    private static void sendMenssageAsyncWithTranstion(KafkaProducer producer, ProducerRecord record) {
        // ��ʼ������
        producer.initTransactions();
        try {
            Thread.sleep(2000);
            // ��������
            producer.beginTransaction();
            // ������Ϣ
            producer.send(record);
            // �ύ����
            producer.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            // ��ֹ����
            producer.abortTransaction();
        }
    }

    //�첽����
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

    //ͬ�����ͷ�ʽ
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

    //���ͼ����ķ��ͷ�ʽ
    private static void sendMessage(KafkaProducer producer, ProducerRecord record) {
        try{
            producer.send(record);
        }catch (Exception e){
            logger.error("���ͼ�����ʽ����ʧ��",e);
        }
    }


    public static KafkaProducer getProducer(){
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,""); //��Ⱥ��ַ
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());//key���л�
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());//value���л�
        prop.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"demo1");//����id
        prop.put(ProducerConfig.ACKS_CONFIG,"-1"); //ack������ȫ����������
        prop.put(ProducerConfig.RETRIES_CONFIG,"3"); //���Դ���
        prop.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,"1000"); //����ʱ����
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true"); //�Ƿ����ݵ���
        prop.put("buffer.memory", 33554432); // �����С�����ݱ����ڴ��С����
        prop.put("linger.ms", 1000); // ����Ƶ�ʣ���������һ����������
        KafkaProducer producer = new KafkaProducer(prop);
        return producer;
    }
}
