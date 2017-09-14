package com.wfy.web.dto;

import com.wfy.web.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/9/9, good luck.
 */
@Component
public class UserSignUpDto {
    private User user;
    private String vCode;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    @Override
    public String toString() {
        return "UserSignUpDto{" +
                "user=" + user +
                ", vCode='" + vCode + '\'' +
                '}';
    }
}
