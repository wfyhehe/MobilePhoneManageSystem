package com.wfy.web.service.impl;

import com.wfy.web.dao.CommentDao;
import com.wfy.web.model.Comment;
import com.wfy.web.service.ICommentService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iCommentService")
@Transactional
public class CommentServiceImpl implements ICommentService {

    @Resource
    private CommentDao commentDao;

    @Override
    public List<Comment> getComments(RefCount refCount, String userId, boolean allowSecret,
                                     Integer pageIndex, Integer pageSize) {
        return commentDao.search(refCount, userId, allowSecret, pageIndex, pageSize);
    }

    @Override
    public Comment addComment(Comment comment) throws Exception {
        String id = commentDao.save(comment);
        comment.setId(id);
        return comment;
    }

    @Override
    public void updateComment(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public Comment getCommentById(String id) {
        return commentDao.getCommentById(id);
    }

    @Override
    public void delete(String id) {
        Comment comment = commentDao.getCommentById(id);
        comment.setDeleted(true);
        commentDao.update(comment);
    }
}
