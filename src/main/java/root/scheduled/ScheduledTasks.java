package root.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by m on 2016/12/21.
 */
@Configuration
@EnableScheduling
public class ScheduledTasks {
    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date ()));
    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron(){
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }
}
