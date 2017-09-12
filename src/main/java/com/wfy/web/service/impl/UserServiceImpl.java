package com.wfy.web.service.impl;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.UserDao;
import com.wfy.web.model.User;
import com.wfy.web.model.enums.UserStatus;
import com.wfy.web.service.ITokenService;
import com.wfy.web.service.IUserService;
import com.wfy.web.utils.MD5Util;
import com.wfy.web.utils.RefCount;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

/*    @Override
    public ServerResponse<String> login(String username, String password) {
        boolean exists = userDao.exists(username);
        if (!exists) {
            throw new UnknownAccountException("用户名不存在");
//            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String md5Password = MD5Util.getMD5(password);
        User user = userDao.selectLogin(username, md5Password);
        if (user == null) {
            throw new IncorrectCredentialsException("密码错误");
//            return ServerResponse.createByErrorMessage("密码错误");
        } else {
            user.setLastLoginTime(new Date(System.currentTimeMillis()));
            user.setStatus(UserStatus.ONLINE);
            userDao.update(user);
            return ServerResponse.createBySuccess("登陆成功",
                    iTokenService.createToken(user.getId()));
        }
    }*/

    @Override
    public void logout(String userId) {
        User user = userDao.getUser(userId);
        user.setStatus(UserStatus.OFFLINE);
        userDao.update(user);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public List<User> getUsers(RefCount refCount, String username, String empName
            , Integer pageIndex, Integer pageSize) {
        return userDao.search(refCount, username, empName, pageIndex, pageSize);
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
        if (!userDao.checkPassword(MD5Util.getMD5(passwordOld), id)) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        User user;
        try {
            user = userDao.getUser(id);
            user.setPassword(MD5Util.getMD5(passwordNew));
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("密码更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public User getUser(String id) {
        return userDao.getUser(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public List<User> getDeletedUsers() {
        return userDao.getDeleted();
    }

    @Override
    public boolean recover(String id) {
        return userDao.recover(id);
    }

    @Override
    public void updateUser(User user) {
        User oldUser = userDao.getUser(user.getId());
        if (user.getUsername() == null) {
            user.setUsername(oldUser.getUsername());
        }
        if (user.getEmployee() == null) {
            user.setEmployee(oldUser.getEmployee());
        }
        if (user.getRoles() == null) {
            user.setRoles(oldUser.getRoles());
        }
        if (user.getCreateTime() == null) {
            user.setCreateTime(oldUser.getCreateTime());
        }
        if (user.getStatus() == null) {
            user.setStatus(oldUser.getStatus());
        }
        if (user.getLastLoginTime() == null) {
            user.setLastLoginTime(oldUser.getLastLoginTime());
        }
        if (user.getRemark() == null) {
            user.setRemark(oldUser.getRemark());
        }
        if (user.getRoles() == null) {
            user.setRoles(oldUser.getRoles());
        }
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        userDao.update(user);
    }

    @Override
    public boolean delete(String id) {
        User user = userDao.getUser(id);
        if (user == null) {
            return false;
        }
        user.setStatus(UserStatus.DELETED);
        userDao.update(user);
        return true;
    }

    @Override
    public long countUser() {
        return userDao.count();
    }
}
