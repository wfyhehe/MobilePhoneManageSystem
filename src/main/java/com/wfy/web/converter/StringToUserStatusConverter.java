package com.wfy.web.converter;

import com.wfy.web.model.UserStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Administrator on 2017/8/10.
 */
public class StringToUserStatusConverter implements Converter<String, UserStatus> {
    @Override
    public UserStatus convert(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        return UserStatus.get(s.trim().toUpperCase());
    }
}
