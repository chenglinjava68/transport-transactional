package root.mybatis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import root.module.service.UserService;
import root.mybatis.mapper.dao.UserMapper;
import root.system.config.scheduled.quartz.bean.MyQuartzJobBean;
import root.system.config.scheduled.quartz.manager.QuartzJobManage;
import root.system.config.scheduled.quartz.task.JmsToCalcTask;
import root.system.config.scheduled.quartz.task.TestTask;

/**
 * Created by m on 2016/12/20.
 */
@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private QuartzJobManage quartzJobManage;

    @Override
    public void run(String... args) throws Exception {
        try {
            for (int i = 0; i < 10; i++){
                System.out.println("start run --> " + i);
            }

//            User user = userMapper.selectByPrimaryKey(1L);
//            System.out.println(user.getUserName());
//            userService.insertUser();
//            userService.jmsProduce("this is my jms");
//            operateJob();
            operateJmsToCalcTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void operateJob() throws Exception {
        MyQuartzJobBean job = quartzJobManage.findJob("testTimer", "testTimer");
        if (job == null){
            //不存在则新增一个
            job = new MyQuartzJobBean();
            job.setJobName("testTimer");
            job.setJobGroup("testTimer");
            job.setJobStatus(MyQuartzJobBean.STATUS_NORMAL);
            job.setCronExpression("0/30 * * * * ?");
            job.setJobClass(TestTask.class.getName());
            job.setDescription("处理通用账户快捷支付未处理完订单");
            quartzJobManage.addJob(job);
        }else if (job.getJobStatus().equals(MyQuartzJobBean.STATUS_PAUSED)){
            //如果任务处于暂停状态，则恢复运行
            quartzJobManage.resumeJob(job);
        }
    }

    private void operateJmsToCalcTask() throws Exception {
        MyQuartzJobBean job = quartzJobManage.findJob("JmsToCalcTaskTimer", "JmsToCalcTaskTimer");
        if (job == null){
            //不存在则新增一个
            job = new MyQuartzJobBean();
            job.setJobName("JmsToCalcTaskTimer");
            job.setJobGroup("JmsToCalcTaskTimer");
            job.setJobStatus(MyQuartzJobBean.STATUS_NORMAL);
            job.setCronExpression("0/10 * * * * ?");
            job.setJobClass(JmsToCalcTask.class.getName());
            job.setDescription("处理通用账户快捷支付未处理完订单");
            quartzJobManage.addJob(job);
        }else if (job.getJobStatus().equals(MyQuartzJobBean.STATUS_PAUSED)){
            //如果任务处于暂停状态，则恢复运行
            quartzJobManage.resumeJob(job);
        }
    }
}
