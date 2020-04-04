package silladus.basic.util;

import android.util.Log;

import java.util.Locale;

/**
 * Created by luxianyue on 2018/3/15.
 */

public class LogUtils {
    private final static short LOG_V = 1;
    private final static short LOG_D = 2;
    private final static short LOG_I = 3;
    private final static short LOG_W = 4;
    private final static short LOG_E = 5;
    private final static short LOG_OUT = 6;

    private static final String TAG = "LogUtils";
    private static String mPrefix;
    private static String mPackageName;
    private static boolean isDebug;

    public static void out(String log) {
        doLog(LOG_OUT, log);
    }

    public static void v(String log) {
        doLog(LOG_V, log);
    }

    public static void d(String log) {
        doLog(LOG_D, log);
    }

    public static void i(String log) {
        doLog(LOG_I, log);
    }

    public static void w(String log) {
        doLog(LOG_W, log);
    }

    public static void e(String log) {
        doLog(LOG_E, log);
    }

    private static void doLog(short flag, String log) {
        if (isDebug) {
            initPrefix();
            switch (flag) {
                case LOG_V:
                    Log.v(TAG, mPrefix + log);
                    break;
                case LOG_D:
                    Log.d(TAG, mPrefix + log);
                    break;
                case LOG_I:
                    Log.i(TAG, mPrefix + log);
                    break;
                case LOG_W:
                    Log.w(TAG, mPrefix + log);
                    break;
                case LOG_E:
                    Log.e(TAG, mPrefix + log);
                    break;
                case LOG_OUT:
                    System.out.println(TAG + "：\n" + mPrefix  + log);
                    break;
            }
        }
    }

    private static void initPrefix() {
        StackTraceElement element = Thread.currentThread().getStackTrace()[5];
        if (mPackageName == null) {
            mPackageName = AppContext.get().getPackageName();
        }
        String classSimplePath = element.getClassName().substring(mPackageName.length());
        mPrefix = String.format(Locale.getDefault(), "%s.%s(%s:%d):",
                classSimplePath, element.getMethodName(), element.getFileName(), element.getLineNumber());
    }

    public static void setDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    public static boolean isDebug() {
        return isDebug;
    }
}
