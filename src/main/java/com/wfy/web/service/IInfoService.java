package com.wfy.web.service;

import com.wfy.web.model.Info;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15, good luck.
 */
public interface IInfoService {
    List<Info> getInfos(String position);

    Info addInfo(Info info) throws Exception;

    void updateInfo(Info info);

    Info getInfoById(String id);

    void delete(String id);
}
