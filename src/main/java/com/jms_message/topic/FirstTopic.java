package com.jms_message.topic;

import javax.jms.*;
import javax.naming.InitialContext;

public class FirstTopic {
    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        Session session = connection.createSession();
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
        MessageProducer producer = session.createProducer(topic);
        TextMessage messageSend = session.createTextMessage("omar mommy doing JMS topic");
        producer.send(messageSend);
        System.out.println("Message sent "+ messageSend.getText());

        //to consume we need to call start () methode
        connection.start();
        //multiple consumers
        MessageConsumer consumer = session.createConsumer(topic);
        MessageConsumer consumer1 = session.createConsumer(topic);

        TextMessage messageReceive = (TextMessage) consumer.receive();
        TextMessage messageReceive1 = (TextMessage) consumer1.receive();

        System.out.println("Message receive " + messageReceive.getText());
        System.out.println("Message receive1 " + messageReceive1.getText());

        initialContext.close();
        connection.close();

    }
}
