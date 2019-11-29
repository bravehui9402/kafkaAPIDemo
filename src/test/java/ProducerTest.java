import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import producer.ProducerFactory;
import producer.ProducerTemplate;

public class ProducerTest {
    @Test
    public void send1(){
        Logger logger = Logger.getLogger(ProducerTest.class);
        ApplicationContext app= new ClassPathXmlApplicationContext("spring-producer.xml");
        ProducerTemplate producerTemplate = (ProducerTemplate) app.getBean("producerTemplate");
        producerTemplate.sendByAsync("topic2", "message", new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    logger.info("message send success! topic:"+recordMetadata.topic()+" partition : "+recordMetadata.partition());
                }else {
                    logger.error("message send fail");
                }
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
