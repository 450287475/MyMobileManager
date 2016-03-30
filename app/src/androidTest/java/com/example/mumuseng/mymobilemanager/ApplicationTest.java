package com.example.mumuseng.mymobilemanager;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.example.mumuseng.dao.NumberLocationDao;
import com.example.mumuseng.utils.ContactUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public void testL(){
        String location = NumberLocationDao.getLocation("18608901832");
        System.out.println(location);
    }

}