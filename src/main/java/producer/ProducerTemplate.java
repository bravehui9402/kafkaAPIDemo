package producer;



import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.Future;

public class ProducerTemplate {
    private Producer producer;
    private ProducerFactory producerFactory;
    public ProducerTemplate(ProducerFactory producerFactory) {
        this.producer = producerFactory.buildProducer();
    }
    /*异步发送*/
    public Future sendByAsync(String topicName, String message, Callback callback){
        ProducerRecord producerRecord = new ProducerRecord(topicName,message);
        return producer.send(producerRecord, callback);
    }
    public Future sendByAsync(String topicName,String key,String message,Callback callback){
        ProducerRecord producerRecord = new ProducerRecord(topicName,message);
        return producer.send(producerRecord, callback);
    }
    public Future sendByAsync(String topicName,Integer partition,String key,String message,Callback callback){

        ProducerRecord producerRecord = new ProducerRecord(topicName,message);
        return producer.send(producerRecord, callback);
    }



















    public ProducerFactory getProducerFactory() {
        return producerFactory;
    }

    public void setProducerFactory(ProducerFactory producerFactory) {
        this.producerFactory = producerFactory;
    }




}
