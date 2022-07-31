package com.login.page.config;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GetCalendar {
    public String getCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        return calendar.getTime().toString();
    }
}
