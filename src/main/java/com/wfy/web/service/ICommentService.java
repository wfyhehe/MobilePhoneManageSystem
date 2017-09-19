package com.wfy.web.service;

import com.wfy.web.model.Comment;
import com.wfy.web.utils.RefCount;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19, good luck.
 */
public interface ICommentService {
    List<Comment> getComments(RefCount refCount, String userId, boolean allowSecret, Integer
            pageIndex, Integer pageSize);

    Comment addComment(Comment comment) throws Exception;

    void updateComment(Comment comment);

    Comment getCommentById(String id);

    void delete(String id);
}
