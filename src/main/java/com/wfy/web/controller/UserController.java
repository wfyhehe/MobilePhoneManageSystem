package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Role;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.service.IRoleService;
import com.wfy.web.service.ITokenService;
import com.wfy.web.service.IUserService;
import com.wfy.web.utils.RefCount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/8/7.
 */
//@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService iUserService;

    @Resource
    private ITokenService iTokenService;

    @Resource
    private IRoleService iRoleService;

    @RequestMapping(value = "check_username.do", method = RequestMethod.GET)
    public ServerResponse<String> checkUsername(String username) {
        return iUserService.usernameExists(username)
                ? ServerResponse.createByErrorMessage("用户名已存在")
                : ServerResponse.createBySuccess();
    }

//    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
//    public ServerResponse<User> getUserInfo(String token) {
//        if (iTokenService.checkToken(token)) {
//            return ServerResponse.createBySuccess(iUserService.getUser(Token.parseUserId(token)));
//        }
//        return ServerResponse.createBySuccessMessage("用户未登录，无法获取当前用户信息");
//    }


    @RequestMapping(value = "update_information.do", method = RequestMethod.GET)
    public ServerResponse<User> updateInformation(Token token, User user) {
        if (iTokenService.checkToken(token)) {
            if (user != null) {
                //TODO
                return null;
            }
        }
        return ServerResponse.createByErrorMessage("用户未登录");
    }

    @RequestMapping(value = "get_users.do", method = RequestMethod.POST)
    public ServerResponse<List<User>> getUsers(@RequestBody Map<String, Object> map) {
        String empName = (String) map.get("name");
        String username = (String) map.get("username");
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");
        RefCount refCount = new RefCount(0);
        List<User> users = iUserService.getUsers(refCount, username, empName, pageIndex, pageSize);
        if (users != null) {
            ServerResponse response = ServerResponse.createBySuccess(users);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取用户失败");
        }
    }

    @RequestMapping(value = "get_deleted_users.do", method = RequestMethod.GET)
    public ServerResponse<List<User>> getDeletedUsers() {
        List<User> users = iUserService.getDeletedUsers();
        if (users != null) {
            return ServerResponse.createBySuccess(users);
        } else {
            return ServerResponse.createByErrorMessage("获取用户失败");
        }
    }

    @RequestMapping(value = "recover_user.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverUser(String id) {
        if (iUserService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_user.do", method = RequestMethod.GET)
    public ServerResponse<User> getUser(String id) {
        User user = iUserService.getUser(id);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        } else {
            return ServerResponse.createByErrorMessage("获取用户失败");
        }
    }

    @RequestMapping(value = "get_me.do", method = RequestMethod.GET)
    public ServerResponse<User> getMe(HttpServletRequest request) {
        String tokenStr = request.getHeader(Const.AUTHORIZATION);
        System.out.println("tokenStr at get_me.do: " + tokenStr);
        if (StringUtils.isNotBlank(tokenStr)) {
            String id = Token.parse(tokenStr).getUserId();
            User user = iUserService.getUser(id);
            if (user != null) {
                return ServerResponse.createBySuccess(user);
            }
        }
        return ServerResponse.createByErrorMessage("获取用户失败");
    }

    @RequestMapping(value = "update_user.do", method = RequestMethod.POST)
    public ServerResponse<String> updateUser(@RequestBody User user) {
        System.out.println(user);
        try {
            iUserService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_user.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iUserService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @RequestMapping(value = "count_user.do", method = RequestMethod.GET)
    public ServerResponse<Long> getCount() {
        long count;
        try {
            count = iUserService.countUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("获取数量失败");
        }
        return ServerResponse.createBySuccess(count);
    }
}
