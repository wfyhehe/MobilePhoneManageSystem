package com.wfy.web.service.impl;

import com.wfy.web.dao.DeptDao;
import com.wfy.web.model.Dept;
import com.wfy.web.service.IDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iDeptService")
@Transactional
public class DeptServiceImpl implements IDeptService {

    @Resource
    DeptDao deptDao;

    @Override
    public List<Dept> getDepts() {
        return deptDao.getAll();
    }

    @Override
    public List<Dept> getDeletedDepts() {
        return deptDao.getDeleted();
    }

    @Override
    public Dept addDept(String name, String address) {
        Dept dept = new Dept(name, address);
        String id = deptDao.save(dept);
        dept.setId(id);
        return dept;
    }

    @Override
    public boolean recover(String id) {
        return deptDao.recover(id);
    }

    @Override
    public void updateDept(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public Dept getDeptById(String id) {
        return deptDao.getDeptById(id);
    }

    @Override
    public Dept getDeptByName(String name) {
        return deptDao.getDeptByName(name);
    }

    @Override
    public boolean delete(String id) {
        Dept dept = deptDao.getDeptById(id);
        if (dept == null) {
            return false;
        }
        dept.setDeleted(true);
        deptDao.update(dept);
        return true;
    }


}
