package root.system.config.scheduled.quartz.manager;

import org.apache.commons.lang3.StringUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import root.system.config.scheduled.quartz.bean.MyQuartzJobBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by m on 2016/12/22.
 */
public class TaskUtils {
    private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     */
    public static void invokeMethod(MyQuartzJobBean scheduleJob){
        final String jobClass = scheduleJob.getJobClass();
        Object obj = null;
        if (StringUtils.isNotBlank(jobClass)){
            try {
                final Class<?> aClazz = Class.forName(jobClass);
                obj = aClazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (obj == null)
        {
            logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查执行类是否配置正确！！！");
            return;
        }
        try {
            final Method method = obj.getClass().getDeclaredMethod(scheduleJob.getMethodName());
            method.invoke(obj);
        } catch (NoSuchMethodException e) {
            logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查执行类的方法名是否设置错误！！！");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断cron时间表达式正确性
     * @param cronExpression
     * @return
     */
    public static boolean isValidExpression(String cronExpression){
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            final Date date = trigger.computeFirstFireTime(null);
            return date != null && date.after(new Date());
        } catch (ParseException e) {
            logger.error("时间表达式:" + cronExpression + "错误");
        }
        return false;
    }

    public static void main(String[] args)
    {
        System.out.println(isValidExpression("0 0/1 * * * ?"));
    } /*       * 任务运行状态        */

    public enum TASK_STATE
    {
        NONE("NONE", "未知"),
        NORMAL("NORMAL", "正常运行"),
        PAUSED("PAUSED", "暂停状态"),
        COMPLETE("COMPLETE", ""),
        ERROR("ERROR", "错误状态"),
        BLOCKED("BLOCKED", "锁定状态");

        TASK_STATE(String index, String name)
        {
            this.name = name;
            this.index = index;

        }

        private String index;

        private String name;

        public String getName()
        {
            return name;
        }

        public String getIndex()
        {
            return index;
        }
    }
}
