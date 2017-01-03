package root.mybatis.mapper.dao;

import org.apache.ibatis.annotations.Mapper;
import root.mybatis.mapper.bean.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}