package root.system.config.jms.service;

import com.gistandard.transport.giifiCalc.external.bean.ICalcForPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.mybatis.mapper.bean.User;
import root.mybatis.mapper.dao.UserMapper;

import java.util.Date;

/**
 * Created by m on 2016/12/23.
 */
@Service
public class JmsConsumeService {
    private Logger logger = LoggerFactory.getLogger(JmsConsumeService.class);

    @Autowired
    private UserMapper userMapper;

    @JmsListener(destination = "calc_forprice_queue_mq")
    public void consumerCalcForPrice2Message(ICalcForPrice iCalcForPrice){
        logger.info(iCalcForPrice.getOrderNo() + " --> " + iCalcForPrice.getOrderId());
    }

    @JmsListener(destination = "simple.queue")
    @Transactional
    public void receiveQueue(String text) {
        System.out.println(text);
        User insert = new User();
        insert.setAge(17);
        insert.setUserName(text);
        insert.setCreateTime(new Date());
        insert.setPassword("321312");
        insert.setStatus(1);
        userMapper.insert(insert);
//        throw new RuntimeException("jms error");
    }
}
