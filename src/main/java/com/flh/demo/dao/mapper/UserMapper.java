package com.flh.demo.dao.mapper;

import com.flh.demo.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by fulinhua on 2018/3/22.
 */
@Mapper
public interface UserMapper {
    public List<User> listAll();
}
