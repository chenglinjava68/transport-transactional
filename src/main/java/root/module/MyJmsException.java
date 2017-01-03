package root.module;

import javax.jms.JMSException;

/**
 * Created by m on 2016/12/23.
 */
public class MyJmsException extends JMSException {
    public MyJmsException(String reason, String errorCode) {
        super(reason, errorCode);
    }
}
