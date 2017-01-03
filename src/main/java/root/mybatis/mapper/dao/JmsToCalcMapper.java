package root.mybatis.mapper.dao;

import org.apache.ibatis.annotations.Mapper;
import root.mybatis.mapper.bean.JmsToCalc;

@Mapper
public interface JmsToCalcMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JmsToCalc record);

    int insertSelective(JmsToCalc record);

    JmsToCalc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JmsToCalc record);

    int updateByPrimaryKey(JmsToCalc record);
}