package silladus.basic.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by silladus on 2020/2/21.
 * e-mail:silladus@163.com
 */
@SuppressLint("PrivateApi")
public final class AppContext {
    private AppContext() {
    }

    private static Application app;

    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     *
     * @param context
     */
    public static void init(Application context) {
        app = context;
    }

    public static Application get() {
        if (app == null) {
            try {
                Application application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null);
                if (application != null) {
                    app = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication").invoke(null);
                if (application != null) {
                    app = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException(AppContext.class.getSimpleName() + " is not initialed, it is recommend to init with application context.");
        }
        return app;
    }
}
