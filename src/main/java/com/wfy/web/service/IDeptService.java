package com.wfy.web.service;

import com.wfy.web.model.Dept;
import com.wfy.web.model.Dept;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public interface IDeptService {

    List<Dept> getDepts();

    Dept getDeptByName(String name);

    void updateDept(Dept dept);

    Dept getDeptById(String id);

    boolean delete(String id);

    List<Dept> getDeletedDepts();

    Dept addDept(String name,String address);

    boolean recover(String id);
}
