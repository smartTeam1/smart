package com.cqyw.smart.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.loopj.android.http.RequestParams;

import android.text.TextUtils;

/**
 * 字符串操作工具包
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");
    /**
     * 月份数组
     */
    public static String[] month = {"一","二","三","四","五","六","七","八","九","十","十一","十二"};
    public static String[] chNum = {"一","二","三","四","五","六","七","八","九","十","十一","十二"};

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 数字返回中文
     * @param num
     * @return
     */
    public static String num2ch(int num) {
        return chNum[num];
    }
    public static String num2ch(String num){
        
        return num2ch(Integer.valueOf(num));
    }
    /**
     * 将字符串转位日期类型
     * 
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }
    
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }
    public static String getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return month[c.get(Calendar.MONTH)];
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }


    public static String friendly_time2(String sdate) {
        String res = "";
        if (isEmpty(sdate))
            return "";

        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        String currentData = StringUtils.getDataTime("MM-dd");
        int currentDay = toInt(currentData.substring(3));
        int currentMoth = toInt(currentData.substring(0, 2));

        int sMoth = toInt(sdate.substring(5, 7));
        int sDay = toInt(sdate.substring(8, 10));
        int sYear = toInt(sdate.substring(0, 4));
        Date dt = new Date(sYear, sMoth - 1, sDay - 1);

        if (sDay == currentDay && sMoth == currentMoth) {
            res = "今天 / " + weekDays[getWeekOfDate(new Date())];
        } else if (sDay == currentDay + 1 && sMoth == currentMoth) {
            res = "昨天 / " + weekDays[(getWeekOfDate(new Date()) + 6) % 7];
        } else {
            if (sMoth < 10) {
                res = "0";
            }
            res += sMoth + "/";
            if (sDay < 10) {
                res += "0";
            }
            res += sDay + " / " + weekDays[getWeekOfDate(dt)];
        }

        return res;
    }

    /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    /**
     * 判断给定字符串时间是否为今日
     * 
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期
     * 
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /***
     * 计算两个时间差，返回的是的秒s
     * 
     * @author 火蚁 2015-2-9 下午4:50:06
     * 
     * @return long
     * @param dete1
     * @param date2
     * @return
     */
    public static long calDateDifferent(String dete1, String date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormater.get().parse(dete1);
            d2 = dateFormater.get().parse(date2);

            // 毫秒ms
            diff = d2.getTime() - d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diff / 1000;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * 
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断一个url是否为图片url
     * 
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 判断是否为一个合法的url地址
     * 
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }

    /**
     * 字符串转整数
     * 
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     * 
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     * 
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     * 
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     * 
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /***
     * 截取字符串
     * 
     * @param start
     *            从那里开始，0算起
     * @param num
     *            截取多少个
     * @param str
     *            截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     * 
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     * 
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
    /**
     * 根据数据返回字符串 只能是单数
     * @param num 传入的数据
     * @return 转换后的字符串数字
     */
    public static String getStrNum(int num) {
        StringBuilder strBuilder = new StringBuilder();
        String str = String.valueOf(num);
        for(int i = 0; i < str.length(); i++ ){
            char c = str.charAt(i);
            switch (c) {
            case '0':
                strBuilder.append("零");
                break;
            case '1':
                strBuilder.append("一");
                break;
            case '2':
                strBuilder.append("二");
                break;
            case '3':
                strBuilder.append("三");
                break;
            case '4':
                strBuilder.append("四");
                break;
            case '5':
                strBuilder.append("五");
                break;
            case '6':
                strBuilder.append("六");
                break;
            case '7':
                strBuilder.append("七");
                break;
            case '8':
                strBuilder.append("八");
                break;
            case '9':
                strBuilder.append("九");
                break;
            default:
                break;
            }
        }
        return strBuilder.toString();
    }

    /**
     * 解析网络参数
     * 
     * @param str
     * @return
     */
    public static RequestParams parseUploadFileCallback(String str) {
        if (TextUtils.isEmpty(str))
            return null;

        String[] array = str.split("&");

        if (array == null || !(array.length > 0))
            return null;
        RequestParams params = new RequestParams();

        for (String temp : array) {
            params.put(temp.substring(0, temp.indexOf("=")),
                    temp.substring((temp.indexOf("=") + 1), temp.length()));
        }
        return params;
    }
}
