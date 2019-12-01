package consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class KafkaConsumerFactory {
    //��δ�����̰߳�ȫ���⣬����֤
    private KafkaConsumer consumer;
    //broker��Ⱥ��ַ
    private String bootstrapservers;
    //��������id
    private String groupId;
    //���Ѷ˳�ʼ������ �� ���� ʱ ����λ�Ƶ�λ�ò��� earliest
    private String autoOffectRest;

    public KafkaConsumer getConsumer(){
        Properties prop = new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapservers);
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoOffectRest);
        return consumer;
    }



    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

    public String getBootstrapservers() {
        return bootstrapservers;
    }

    public void setBootstrapservers(String bootstrapservers) {
        this.bootstrapservers = bootstrapservers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAutoOffectRest() {
        return autoOffectRest;
    }

    public void setAutoOffectRest(String autoOffectRest) {
        this.autoOffectRest = autoOffectRest;
    }
}
