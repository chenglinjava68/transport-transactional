package root.system.config.scheduled.quartz.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by m on 2016/12/21.
 */
@Configuration
public class SchedulerConfiguration {
    @Autowired
    private MyJobFactory myJobFactory;

    @Bean(name = "scheduler0")
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("druidDataSource") DruidDataSource druidDataSource){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(druidDataSource);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactoryBean.setJobFactory(myJobFactory);
        return schedulerFactoryBean;
    }

    /**
     * 加载quartz数据源配置
     *
     * @return
     * @throws IOException
     */
//    @Bean
//    public Properties quartzProperties() throws IOException {
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//        return propertiesFactoryBean.getObject();
//    }

    @Component
    public class MyJobFactory extends SpringBeanJobFactory {
        @Autowired
        private AutowireCapableBeanFactory capableBeanFactory;

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            //调用父类的方法
            Object jobInstance = super.createJobInstance(bundle);
            capableBeanFactory.autowireBean(jobInstance);
            return jobInstance;
        }
    }
}
