package com.nj.learn.kmq.demo;


import com.nj.learn.kmq.core.*;
import lombok.SneakyThrows;

public class KmqDemo {

    @SneakyThrows
    public static void main(String[] args) {

        String topic = "kk.test";
        KmqBroker broker = new KmqBroker();
        broker.createTopic(topic);

        KmqConsumer consumer = broker.createConsumer();
        consumer.subscribe(topic);
        final boolean[] flag = new boolean[1];
        flag[0] = true;
        new Thread(() -> {
            while (flag[0]) {
                KmqMessage<Order> message = consumer.poll(100);
                if(null != message) {
                    System.out.println(message.getBody());
                }
            }
            System.out.println("程序退出。");
        }).start();

        KmqProducer producer = broker.createProducer();
        for (int i = 0; i < 1000; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            producer.send(topic, new KmqMessage(null, order));
        }
        Thread.sleep(500);
        System.out.println("点击任何键，发送两条消息；点击q或e，退出程序。");
        producer.setAutoCommit(false);
        while (true) {
            char c = (char) System.in.read();
            if(c > 20) {
                System.out.println(c);
                //producer.send(topic, new KmqMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
                producer.beginTransaction(topic);
                try {
                    producer.send(topic, new KmqMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
                    producer.send(topic, new KmqMessage(null, new Order(100000L + c + 1, System.currentTimeMillis(), "USD2CNY", 6.52d)));
                    producer.commitTransaction(topic);
                } catch (Exception e){
                    producer.abortTransaction(topic);
                    e.printStackTrace();
                }
            }

            if( c == 'q' || c == 'e') break;
        }

        flag[0] = false;

    }
}
