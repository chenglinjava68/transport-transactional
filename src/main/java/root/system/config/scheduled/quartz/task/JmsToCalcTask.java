package root.system.config.scheduled.quartz.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import root.module.service.JmsToCalcService;
import root.system.config.scheduled.quartz.bean.BaseJob;

/**
 * Created by m on 2016/12/23.
 */
public class JmsToCalcTask extends BaseJob{
    private Logger logger = LoggerFactory.getLogger(JmsToCalcTask.class);

    @Autowired
    private JmsToCalcService jmsToCalcService;

    @Override
    protected void run(JobExecutionContext context) throws JobExecutionException {
        logger.info("start" + context.getJobDetail().getJobClass());
        jmsToCalcService.dealJmsToCalc();
    }
}
