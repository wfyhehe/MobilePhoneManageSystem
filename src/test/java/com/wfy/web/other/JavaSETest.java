package com.wfy.web.other;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public class JavaSETest {

    @Test
    public void test() {
        System.out.println(new Date(System.currentTimeMillis()));
    }

    @Test
    public void testNull() {
        System.out.println(null == null);
    }

    public void changeRef(Long a) {
        a = new Long(5);
    }

    @Test
    public void testRef() {
        long refA = 0;
        System.out.println(refA);
        changeRef(refA);
        System.out.println(refA);
    }

    @Test
    public void testIterator() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Iterator iterator = list.iterator();
        System.out.println(list);
        while (iterator.hasNext()) {
        }
        System.out.println(list);
    }
}
