package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Comment;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.service.ICommentService;
import com.wfy.web.service.IUserService;
import com.wfy.web.utils.RefCount;
import com.wfy.web.utils.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@RestController
@RequestMapping("/comment/")
public class CommentController {

    @Resource
    private ICommentService iCommentService;

    @Resource
    private IUserService iUserService;

    @RequestMapping(value = "get_comments.do", method = RequestMethod.POST)
    public ServerResponse<List<Comment>> getComments(@RequestBody Map<String, Object> map,
                                                     HttpServletRequest request) {
        boolean allowSecret = false;
        String userId = Token.parse(request.getHeader(Const.AUTHORIZATION)).getUserId();
        if (iUserService.isSuperAdmin(userId)) {
            allowSecret = true;
        }
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");

        RefCount refCount = new RefCount(0);
        List<Comment> comments;
        comments = iCommentService.getComments(refCount, userId, allowSecret, pageIndex, pageSize);
        if (comments != null) {
            ServerResponse<List<Comment>> response = ServerResponse.createBySuccess(comments);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取留言失败");
        }
    }

    @RequestMapping(value = "get_comment.do", method = RequestMethod.GET)
    public ServerResponse<Comment> getComment(String id) {
        Comment comment = iCommentService.getCommentById(id);
        if (comment != null) {
            return ServerResponse.createBySuccess(comment);
        } else {
            return ServerResponse.createByErrorMessage("获取留言失败");
        }
    }

    @RequestMapping(value = "add_comment.do", method = RequestMethod.POST)
    public ServerResponse<String> addComment(@RequestBody Comment comment, HttpServletRequest request) {
        Token token = TokenUtil.getTokenfromRequest(request);
        if (token != null) {
            comment.setUser(new User(token.getUserId()));
        }
        comment.setCreateDate(new Date(System.currentTimeMillis()));
        comment.setIp(request.getRemoteAddr());
        try {
            iCommentService.addComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "update_comment.do", method = RequestMethod.POST)
    public ServerResponse<String> updateComment(@RequestBody Comment comment) {
        try {
            System.out.println(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_comment.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        try {
            iCommentService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccess();
    }
}
