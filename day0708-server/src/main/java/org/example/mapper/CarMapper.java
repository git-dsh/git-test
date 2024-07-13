package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.TbCar;

/**
 * @program: day0708_week3
 * @author: dsh
 * @description:
 * @create: 2024-07-08 10:11
 */
@Mapper
public interface CarMapper extends BaseMapper<TbCar> {
}
