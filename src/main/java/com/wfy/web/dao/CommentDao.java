package com.wfy.web.dao;

import com.wfy.web.model.Comment;
import com.wfy.web.model.User;
import com.wfy.web.utils.CloneUtil;
import com.wfy.web.utils.RefCount;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Repository
public class CommentDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private Comment extractAndNormalizeFirstComment(List<Comment> list) {
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<Comment> search(RefCount refCount, String userId, boolean allowSecret, Integer
            pageIndex, Integer
                                        pageSize) {
        List<Comment> comments;
        DetachedCriteria criteria = DetachedCriteria.forClass(Comment.class, "c")
                .setFetchMode("user", FetchMode.SELECT)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("c.createDate"))
                .add(Restrictions.ne("c.deleted", true));
        if (!allowSecret) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.ne("c.secret", true)); // 不能看secret
            disjunction.add(Restrictions.eq("c.user", new User(userId))); // 除非是他自己的
            criteria.add(disjunction);
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            comments = (List<Comment>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            comments = (List<Comment>) hibernateTemplate.findByCriteria(criteria);
        }
//        for (Comment comment : comments) {
//            Hibernate.initialize(comment.getMobiles());
//        }
        return comments;
    }

    public String save(Comment comment) throws Exception {
        return (String) hibernateTemplate.save(comment);
    }

    public Comment getCommentById(String id) {
        String hql = "from Comment m where m.id = ?";
        List<Comment> list = (List<Comment>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstComment(list);
    }

    public void update(Comment comment) {
        hibernateTemplate.update(comment);
    }

    public void delete(Comment comment) {
        hibernateTemplate.delete(comment);
    }

    public void merge(Comment comment) {
        hibernateTemplate.merge(comment);
    }
}
