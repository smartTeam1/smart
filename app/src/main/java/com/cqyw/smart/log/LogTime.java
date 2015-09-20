package com.cqyw.smart.log;

import android.os.SystemClock;
import android.util.Log;

/**
 * 带时间的日志
 * @author HongYu
 *
 * 2015年7月29日
 */
public class LogTime {
    private static long mLogTime = 0;

    /** Reset time value to elapsed real time. */
    public static void resetTime() {
        mLogTime = SystemClock.elapsedRealtime();
    }

    /** Add time value to TAG */
    private static String addTimeMsg(String msg) {
        return msg + " T:" + (SystemClock.elapsedRealtime() - mLogTime);
    }

    public static void v(String tag, String msg) {
        if (LogUtils.mLoggingEnabled) {

            Log.v(tag, addTimeMsg(msg));
        }
    }

    public static void d(String tag, String msg) {
        if (LogUtils.mLoggingEnabled) {

            Log.d(tag, addTimeMsg(msg));
        }
    }

    public static void i(String tag, String msg) {
        if (LogUtils.mLoggingEnabled) {

            Log.i(tag, addTimeMsg(msg));
        }
    }

    public static void w(String tag, String msg) {
        if (LogUtils.mLoggingEnabled) {

            Log.w(tag, addTimeMsg(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (LogUtils.mLoggingEnabled) {

            Log.e(tag, addTimeMsg(msg));
        }
    }

    public static int v(String tag, String msg, Throwable tr) {
        int result = 0;
        if (LogUtils.mLoggingEnabled) {
            result = Log.v(tag, msg, tr);
        }
        return result;
    }
    public static int d(String tag, String msg, Throwable tr) {
        int result = 0;
        if (LogUtils.mLoggingEnabled) {
            result = Log.d(tag, msg, tr);
        }
        return result;
    }
    public static int i(String tag, String msg, Throwable tr) {
        int result = 0;
        if (LogUtils.mLoggingEnabled) {
            result = Log.i(tag, msg, tr);
        }
        return result;
    }
    public static int w(String tag, String msg, Throwable tr) {
        int result = 0;
        if (LogUtils.mLoggingEnabled) {
            result = Log.w(tag, msg, tr);
        }
        return result;
    }
    public static int e(String tag, String msg, Throwable tr) {
        int result = 0;
        if (LogUtils.mLoggingEnabled) {
            result = Log.e(tag, msg, tr);
        }
        return result;
    } 
}
