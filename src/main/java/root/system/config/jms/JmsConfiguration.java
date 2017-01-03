package root.system.config.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

/**
 * Created by m on 2016/12/21.
 */
@Configuration
public class JmsConfiguration {
    @Bean(name = "simpleQueue")
    public Queue simpleQueue(){
        return new ActiveMQQueue("simple.queue");
    }

    @Bean(name = "calcForPrice2QueueMq")
    public Queue calcForPrice2QueueMq(){
        return new ActiveMQQueue("calc_forprice_queue_mq");
    }

    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate(CachingConnectionFactory pooledConnectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(pooledConnectionFactory);
        return jmsTemplate;
    }

    @Bean(name = "jmsTemplateCalc")
    public JmsTemplate jmsTemplateCalc(CachingConnectionFactory pooledConnectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(pooledConnectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    @Bean(name = "pooledConnectionFactory")
    public CachingConnectionFactory pooledConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory());
        cachingConnectionFactory.setSessionCacheSize(100);
        return cachingConnectionFactory;
    }

//    @Bean(name = "targetConnectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://172.16.2.177:61616");
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }
}
