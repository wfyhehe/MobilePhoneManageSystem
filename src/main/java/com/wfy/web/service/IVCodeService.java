package com.wfy.web.service;

import com.wfy.web.model.VCode;

/**
 * Created by Administrator on 2017/9/12, good luck.
 */
public interface IVCodeService {
    VCode createOrUpdateVCode(String userId, String credentials);

    boolean checkVCode(VCode vCode);

}
