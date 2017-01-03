package root.system.config.scheduled.quartz.manager;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import root.system.config.scheduled.quartz.bean.MyQuartzJobBean;
import root.system.utils.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by m on 2016/12/22.
 */
@Component
public class QuartzJobManage {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJobManage.class);
    @Qualifier("scheduler0")
    @Autowired
    private SchedulerFactoryBean scheduler0;

    /**
     * 获取单个任务
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public MyQuartzJobBean findJob(String jobName, String jobGroup)
    {
        try
        {
            MyQuartzJobBean job = null;
            Scheduler scheduler = scheduler0.getScheduler();
//            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            if (trigger != null && jobDetail != null)
            {
                MyQuartzJobBean jobExist = (MyQuartzJobBean) jobDetail.getJobDataMap().get("scheduleJob");
                job = new MyQuartzJobBean();
                job.setJobName(jobName);
                job.setJobGroup(jobGroup);
                job.setDescription("触发器:" + trigger.getKey());
                job.setNextTime(trigger.getNextFireTime()); //下次触发时间
                job.setPreviousTime(trigger.getPreviousFireTime());//上次触发时间
                job.setDescription(jobExist.getDescription());
                job.setJobClass(jobExist.getJobClass());
                job.setMethodName(jobExist.getMethodName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger)
                {
                    CronTrigger cronTrigger = trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
            }
            return job;
        }
        catch (Exception e)
        {
            logger.error("任务获取失败：", e);
        }
        return null;
    }

    /**
     * 查询任务列表
     * @return
     * @throws SchedulerException
     */
    public List<MyQuartzJobBean> findAllJobList()
    {
        try
        {
            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            List<MyQuartzJobBean> jobList = new ArrayList<MyQuartzJobBean>();
            for (JobKey jobKey : jobKeys)
            {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                MyQuartzJobBean jobExist = (MyQuartzJobBean)jobDetail.getJobDataMap().get("scheduleJob");

                for (Trigger trigger : triggers)
                {
                    MyQuartzJobBean job = new MyQuartzJobBean();
                    job.setJobName(jobKey.getName());
                    job.setJobGroup(jobKey.getGroup());
                    job.setDescription(jobExist.getDescription());
                    job.setJobClass(jobExist.getJobClass());
                    job.setMethodName(jobExist.getMethodName());
                    job.setStartTime(trigger.getStartTime());
                    job.setPreviousTime(trigger.getPreviousFireTime());
                    job.setNextTime(trigger.getNextFireTime());

                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setJobStatus(triggerState.name());
                    if (trigger instanceof CronTrigger)
                    {
                        CronTrigger cronTrigger = (CronTrigger)trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    jobList.add(job);
                }
            }
            return jobList;
        }
        catch (Exception e)
        {
            logger.error("任务获取失败：", e);
        }
        return null;
    }

    /**
     * 所有正在运行的job
     *
     *
     * @return
     * @throws SchedulerException
     * */
    public List<MyQuartzJobBean> findAllRunningJob()
    {
        try
        {
            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<MyQuartzJobBean> jobList = new ArrayList<MyQuartzJobBean>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs)
            {
                MyQuartzJobBean job = new MyQuartzJobBean();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger)
                {
                    CronTrigger cronTrigger = (CronTrigger)trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
            return jobList;
        }
        catch (Exception e)
        {
            logger.error("任务获取失败：", e);
        }
        return null;
    }

    /**
     * 添加任务
     *
     * @param scheduleJob
     */
    public void addJob(MyQuartzJobBean scheduleJob) throws Exception {
        try {
            if (scheduleJob == null || !MyQuartzJobBean.STATUS_NORMAL.equals(scheduleJob.getJobStatus()))
                throw new Exception("添加的任务不能为null并且任务的初始状态必须是[STATUS_NORMAL]");
            if (!TaskUtils.isValidExpression(scheduleJob.getCronExpression()))
                throw new Exception("任务名称[" + scheduleJob.getJobName() + "] 任务分组[" + scheduleJob.getJobGroup() + "]的时间表达式错误：" + scheduleJob.getCronExpression());
//            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            Scheduler scheduler = scheduler0.getScheduler();
            final TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (null == trigger){
                Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(scheduleJob.getJobClass());
                final JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
                jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);
                final CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // trigger已存在，则更新相应的定时设置
                final CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
                trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** 暂停任务
     * @param scheduleJob
     * @return
     */
    public void pauseJob(MyQuartzJobBean scheduleJob){
        try {
            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            final JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("任务暂停失败：", e);
        }
    }

    /**
     * 恢复任务
     * @param scheduleJob
     * @return
     */
    public void resumeJob(MyQuartzJobBean scheduleJob) {
        try {
            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            final JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("任务恢复失败：", e);
        }
    }

    /**
     * 删除任务
     */
    public void deleteJob(MyQuartzJobBean scheduleJob){
        try {
            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            final JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("任务名称[" + scheduleJob.getJobName() + "] 分组[" + scheduleJob.getJobGroup() + "] 删除失败!", e);
        }
    }

    /**
     * 重启任务
     */
    public void rescheduleJob(MyQuartzJobBean scheduleJob){
        try {
            Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler0");
            final TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            logger.error("任务名称[" + scheduleJob.getJobName() + "] 分组[" + scheduleJob.getJobGroup() + "] 删除失败!", e);
        }
    }
}
