package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerFactory {
    private KafkaProducer producer;
    //broker集群地址
    private String bootstrapservers;
    //事务id
    private String transactionalId;
    //acks
    private Integer acks;
    //retries 重试次数
    private String retries;
    //重试时间间隔
    private String retriesBackOff;
    //幂等性开启
    private String idempotence;



    public KafkaProducer getProducer(){
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapservers);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        prop.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,transactionalId);
        prop.put(ProducerConfig.ACKS_CONFIG,"-1");
        prop.put(ProducerConfig.RETRIES_CONFIG,"3");
        prop.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,"1000");
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,idempotence);
        KafkaProducer producer = new KafkaProducer(prop);
        return producer;
    }



    public void setProducer(KafkaProducer producer) {
        this.producer = producer;
    }

    public String getBootstrapservers() {
        return bootstrapservers;
    }

    public void setBootstrapservers(String bootstrapservers) {
        this.bootstrapservers = bootstrapservers;
    }

    public String getTransactionalId() {
        return transactionalId;
    }

    public void setTransactionalId(String transactionalId) {
        this.transactionalId = transactionalId;
    }

    public Integer getAcks() {
        return acks;
    }

    public void setAcks(Integer acks) {
        this.acks = acks;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public String getRetriesBackOff() {
        return retriesBackOff;
    }

    public void setRetriesBackOff(String retriesBackOff) {
        this.retriesBackOff = retriesBackOff;
    }

    public String getIdempotence() {
        return idempotence;
    }

    public void setIdempotence(String idempotence) {
        this.idempotence = idempotence;
    }
}
