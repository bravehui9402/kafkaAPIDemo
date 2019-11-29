package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerFactory {
    //服务器地址
    private String serversAddr;
    //重试次数
    private Integer retries;
    //应答模式
    private String acks;
    public Producer buildProducer(){
        Properties props = new Properties();
        /*默认配置*/
        // 设置key序列化器 "org.apache.kafka.common.serialization.StringSerializer"
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置值序列化器 "org.apache.kafka.common.serialization.StringSerializer"
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        /*配置文件配置*/
        // 设置集群地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serversAddr);
        // 设置重试次数
        props.put(ProducerConfig.RETRIES_CONFIG,retries);
        /*可为空配置*/


        // KafkaProducer 线程安全
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        return producer;
    }






















    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }
    public String getServersAddr() {
        return serversAddr;
    }

    public void setServersAddr(String serversAddr) {
        this.serversAddr = serversAddr;
    }

}
