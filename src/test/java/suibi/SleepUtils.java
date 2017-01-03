package suibi;

import java.util.concurrent.TimeUnit;

/**
 * Created by m on 2016/12/29.
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
