package root.system.config.jms.service;

import com.alibaba.fastjson.JSON;
import com.gistandard.transport.giifiCalc.external.bean.ICalcForPrice;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.mybatis.mapper.bean.JmsToCalc;
import root.mybatis.mapper.bean.User;
import root.mybatis.mapper.dao.JmsToCalcMapper;
import root.mybatis.mapper.dao.UserMapper;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by m on 2016/12/23.
 */
@Service
public class JmsProduceService {
    private Logger logger = LoggerFactory.getLogger(JmsProduceService.class);

    @Autowired
    @Qualifier("calcForPrice2QueueMq")
    private Queue calcForPrice2QueueMq;
    @Autowired
    @Qualifier("jmsTemplateCalc")
    private JmsTemplate jmsTemplateCalc;
    @Autowired
    private JmsToCalcMapper jmsToCalcMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void sendCalcForPrice2Message(JmsToCalc jmsToCalc) {
        try {
            final ICalcForPrice iCalcForPrice = new ICalcForPrice();
            BeanUtils.copyProperties(iCalcForPrice, jmsToCalc);
            jmsTemplateCalc.send(calcForPrice2QueueMq, new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(iCalcForPrice);
                }
            });
            logger.info("发送消息 orderNo --> " + jmsToCalc.getOrderNo());
        } catch (JmsException e) {
            e.printStackTrace();
            logger.error("I单结算发送消息失败! ---> 发送参数： " + JSON.toJSONString(jmsToCalc));
            jmsToCalcMapper.insert(jmsToCalc);
            User insert = new User();
            insert.setAge(17);
            insert.setUserName(jmsToCalc.getOrderNo());
            insert.setCreateTime(new Date());
            insert.setPassword("321312");
            insert.setStatus(1);
            userMapper.insert(insert);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
