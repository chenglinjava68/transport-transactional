package root.system.config.scheduled.quartz.bean;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Created by m on 2016/12/22.
 */
@Component
public abstract class BaseJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(BaseJob.class);

    /**
     * 供实现类运行
     *
     * @param context 任务执行上下文
     * @throws JobExecutionException 任务执行时异常
     */
    protected abstract void run(JobExecutionContext context) throws JobExecutionException;

    /**
     * @param context 任务执行上下文
     * @throws JobExecutionException 任务执行时异常
     */
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            run(context);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {

        }
    }
}
