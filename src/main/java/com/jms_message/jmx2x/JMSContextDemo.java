package com.jms_message.jmx2x;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class JMSContextDemo {
    public static void main(String[] args) throws  Exception {
        InitialContext initialContext=new InitialContext();
        Queue queue= (Queue)initialContext.lookup("queue/myQueue");
        try{
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
            JMSContext context =factory.createContext();
            context.createProducer().send(queue,"mommy using jms2x");
            String msg = context.createConsumer(queue).receiveBody(String.class);
            System.out.println(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
