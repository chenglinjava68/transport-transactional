package root.module.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.mybatis.mapper.bean.JmsToCalc;
import root.mybatis.mapper.dao.ex.JmsToCalcMapperEx;
import root.system.config.jms.service.JmsProduceService;

import java.util.List;

/**
 * Created by m on 2016/12/23.
 */
@Service
public class JmsToCalcService {
    private Logger logger = LoggerFactory.getLogger(JmsToCalcService.class);

    @Autowired
    private JmsToCalcMapperEx jmsToCalcMapperEx;
    @Autowired
    private JmsProduceService jmsProduceService;

    private JmsToCalc jmsToCalc;

    {
        jmsToCalc = new JmsToCalc();
        jmsToCalc.setStatus(1);
    }

    @Transactional
    public void dealJmsToCalc(){
        List<JmsToCalc> jmsToCalcs = jmsToCalcMapperEx.selectlist(jmsToCalc);
        for (JmsToCalc jmsToCalc : jmsToCalcs){
            jmsProduceService.sendCalcForPrice2Message(jmsToCalc);
        }
    }
}
