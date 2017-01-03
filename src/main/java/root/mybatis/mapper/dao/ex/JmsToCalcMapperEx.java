package root.mybatis.mapper.dao.ex;

import org.apache.ibatis.annotations.Mapper;
import root.mybatis.mapper.bean.JmsToCalc;

import java.util.List;

/**
 * Created by m on 2016/12/23.
 */
@Mapper
public interface JmsToCalcMapperEx {
    List<JmsToCalc> selectlist(JmsToCalc jmsToCalc);
}
