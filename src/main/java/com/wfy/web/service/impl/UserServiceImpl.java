package com.wfy.web.service.impl;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.UserDao;
import com.wfy.web.model.TokenModel;
import com.wfy.web.model.User;
import com.wfy.web.model.UserStatus;
import com.wfy.web.service.ITokenService;
import com.wfy.web.service.IUserService;
import com.wfy.web.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/14.
 */
@Service("iUserService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Resource
    private ITokenService iTokenService;

    public ServerResponse<String> checkUsername(String username) {
        if (userDao.exists(username)) {
            return ServerResponse.createByErrorMessage("用户名已存在");
        } else {
            return ServerResponse.createBySuccess();
        }
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
            userDao.updateLoginTime(user, new Date(System.currentTimeMillis()));
            userDao.updateStatus(user, UserStatus.ONLINE);
            return ServerResponse.createBySuccess("登陆成功",
                    iTokenService.createToken(user.getId()));
        }
    }

    @Override
    public void logout(String userId) {
        User user = userDao.getUser(userId);
        userDao.updateStatus(user, UserStatus.OFFLINE);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setPassword((MD5Util.getMD5(user.getPassword())));
        user.setCreateTime(new Date(System.currentTimeMillis()));
        user.setStatus(UserStatus.ONLINE);
        try {
            userDao.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
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

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, String
            id) {
        int resultCount = userDao.checkPassword(MD5Util.getMD5(passwordOld), id);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        if (userDao.updatePassword(id, MD5Util.getMD5(passwordNew))) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public User getUser(String id) {
        User user = userDao.getUser(id);
        System.out.println(user);
        return user;
    }


}
