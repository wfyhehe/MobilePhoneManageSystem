package com.wfy.web.service.impl;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.TokenManager;
import com.wfy.web.dao.UserDao;
import com.wfy.web.model.TokenModel;
import com.wfy.web.model.User;
import com.wfy.web.service.IUserService;
import com.wfy.web.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/7/14.
 */
@Service("iUserService")
@Transactional
public class UserServiceImpl implements IUserService {
    private UserDao userDao;
    @Resource
    private TokenManager tokenManager;

    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean exists(User user) {
        return userDao.exists(user);
    }

    @Override
    public ServerResponse<TokenModel> login(String username, String password) {
        boolean exists = userDao.exists(username);
        if (!exists) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //TODO 密码登陆MD5
        String md5Password = MD5Util.getMD5(password);
        User user = userDao.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        } else {
            return ServerResponse.createBySuccess("登陆成功",
                    tokenManager.createToken(user.getId()));
        }
    }

    public ServerResponse<String> register(User user) {
        System.out.println(user);
        ServerResponse validResponse = this.checkValid(user.getUsername(),
                Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setPassword((MD5Util.getMD5(user.getPassword())));
        try {
            userDao.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
            if (Const.USERNAME.equals(type)) {
                if (userDao.exists(str)) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    /*   public ServerResponse<String> forgetResetPassword(String username, String
               passwordNew, String forgetToken) {
           if (StringUtils.isBlank(forgetToken)) {
               return ServerResponse.createByErrorMessage("参数错误，token需要传递");
           }
           ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
           if (validResponse.isSuccess()) {
               return ServerResponse.createByErrorMessage("用户名不存在");
           }

           String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);

           if (StringUtils.isBlank(token)) {
               return ServerResponse.createByErrorMessage("token无效或过期");
           }

           if (StringUtils.equals(forgetToken, token)) {
               String md5Password = MD5Util.getMD5(passwordNew);
               int rowCount = userDao.updatePasswordByUsername(username,
                       passwordNew);
               if (rowCount > 0) {
                   return ServerResponse.createBySuccessMessage("修改密码成功");
               }
           } else {
               return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
           }
           return ServerResponse.createByErrorMessage("修改密码失败");
       }
   */
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        int resultCount = userDao.checkPassword(MD5Util.getMD5(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.getMD5(passwordNew));
        int updateCount = userDao.updatePasswordByUsername(user.getUsername(), passwordNew);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }
}
