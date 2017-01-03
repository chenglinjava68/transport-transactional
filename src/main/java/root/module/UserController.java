package root.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import root.mybatis.mapper.bean.User;
import root.mybatis.mapper.dao.UserMapper;

/**
 * Created by m on 2016/12/20.
 */
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/findUserById")
    public User findUserById(@RequestParam(value="id") Integer id) {
        User user = null;
        try {
            user = userMapper.selectByPrimaryKey(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
