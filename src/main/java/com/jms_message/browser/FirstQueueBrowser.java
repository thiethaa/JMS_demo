package com.jms_message.browser;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Enumeration;

public class FirstQueueBrowser {
        public static void main(String[] args) throws Exception {

            InitialContext initialContext = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            Connection connection = factory.createConnection();
            Session session = connection.createSession();
            Queue queue= (Queue) initialContext.lookup("queue/myQueue");
            MessageProducer producer = session.createProducer(queue);
            TextMessage messageSend = session.createTextMessage("omar mommy doing JMS queuebrowser1");
            TextMessage messageSend1 = session.createTextMessage("omar mommy doing JMS queuebrowser2");
            producer.send(messageSend);
            producer.send(messageSend1);

            QueueBrowser browser=session.createBrowser(queue);
            Enumeration enumeration= browser.getEnumeration();

            while(enumeration.hasMoreElements()){
                TextMessage eachMsg= (TextMessage)enumeration.nextElement();
                System.out.println("browsing "+ eachMsg.getText());
            //to consume we need to call start () methode
            connection.start();
            //multiple consumers
            MessageConsumer consumer = session.createConsumer(queue);

            TextMessage messageReceive = (TextMessage) consumer.receive();

            System.out.println("Message receive " + messageReceive.getText());

            initialContext.close();
            connection.close();

        }
    }

}
