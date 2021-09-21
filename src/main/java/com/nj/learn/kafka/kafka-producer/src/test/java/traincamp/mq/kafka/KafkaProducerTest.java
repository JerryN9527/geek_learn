package traincamp.mq.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaProducerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * 模拟生产者发送消息
     */
    @Test
    public void kafkaProducer(){
        this.kafkaProducer.send();
    }

    //@Test
    public void contextLoads() {
    }

}
