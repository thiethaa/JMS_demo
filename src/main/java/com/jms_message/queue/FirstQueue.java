package com.jms_message.queue;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = factory.createConnection();
            Session session = connection.createSession();
            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
            MessageProducer producer = session.createProducer(queue);
            TextMessage messageSend = session.createTextMessage("omar mommy doing JMS");
            producer.send(messageSend);
            System.out.println("Message sent "+ messageSend.getText());

            MessageConsumer consumer = session.createConsumer(queue);
            //to consume we need to call start () methode
            connection.start();
            TextMessage messageReceive = (TextMessage) consumer.receive();
            System.out.println("Message receive " + messageReceive.getText());
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
