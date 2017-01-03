package root.module.service;

/**
 * Created by m on 2016/12/21.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.mybatis.mapper.bean.User;
import root.mybatis.mapper.dao.UserMapper;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("simpleQueue")
    private Queue simpleQueue;

    @Transactional
    public void insertUser(){
        User insert = new User();
        insert.setAge(17);
        insert.setUserName("insert");
        insert.setCreateTime(new Date());
        insert.setPassword("321312");
        insert.setStatus(1);
        userMapper.insert(insert);
    }

    public void jmsProduce(String msg){
        jmsTemplate.convertAndSend(simpleQueue, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setJMSPriority(9);
                return message;
            }
        });
    }
}
