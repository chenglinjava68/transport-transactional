package root.system.config.scheduled.quartz.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import root.module.service.UserService;
import root.system.config.scheduled.quartz.bean.BaseJob;

/**
 * Created by m on 2016/12/22.
 */
public class TestTask extends BaseJob {
    @Autowired
    private UserService userService;

    @Override
    protected void run(JobExecutionContext context) throws JobExecutionException {
        userService.jmsProduce("aaa");
    }
}
