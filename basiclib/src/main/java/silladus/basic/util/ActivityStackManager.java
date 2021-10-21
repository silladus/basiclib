package silladus.basic.util;

import android.app.Activity;
import android.content.Intent;

import java.util.Stack;

import androidx.annotation.Nullable;

public class ActivityStackManager {

    private Stack<Activity> activities;

    private ActivityStackManager() {
        activities = new Stack<>();
    }

    private static class Holder {
        private static final ActivityStackManager INSTANCE = new ActivityStackManager();
    }

    public static ActivityStackManager getInstance() {
        return Holder.INSTANCE;
    }

    public Activity getCurrentActivity() {
        return activities.peek();
    }

    public boolean isNotEmpty() {
        return !activities.empty();
    }

    /**
     * Activity入栈
     *
     * @param activity
     */
    public void add(Activity activity) {
        if (activities.search(activity) == -1) {
            activities.push(activity);
        }
    }

    public void remove(Activity activity) {
        if (isNotEmpty()) {
            activities.remove(activity);
        }
    }

    /**
     * Activity栈中是否存在对应的Activity
     */
    public boolean isExistActivity(Class<?> clz) {
        return getActivity(clz) != null;
    }

    public void startNewActivity(Class<? extends Activity> newActivity) {
        Activity activity = getCurrentActivity();
        activity.startActivity(new Intent(activity, newActivity));
        activity.finish();
    }

    public void toggleActivity(Class<? extends Activity> newActivity) {
        if (isExistActivity(newActivity)) {
            popToTopActivity(newActivity);
        } else {
            startNewActivity(newActivity);
        }
    }

    public void setTopActivity(Activity activity) {
        if (isNotEmpty()) {
            int location = activities.search(activity);

            if (location == -1) {
                activities.push(activity);
            } else if (location != 1) {
                activities.remove(activity);
                activities.push(activity);
            }
        }
    }

    public boolean isTopActivity(Activity activity) {
        return activity.equals(activities.peek());
    }

    public void finishTopActivity() {
        if (isNotEmpty()) {
            Activity activity = activities.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 所有Activity逐个出栈，直到clz Activity
     *
     * @param clz
     */
    public void popToTopActivity(Class<?> clz) {
        while (isNotEmpty()) {
            Activity activity = activities.pop();
            if (activity.getClass().equals(clz)) {
                //到达目标activity
                activities.push(activity);
                break;
            } else {
                //未到达目标activity
                activity.finish();
            }
        }
    }

    /**
     * 获取指定Activity
     *
     * @param clz
     */
    @Nullable
    public Activity getActivity(Class<?> clz) {
        if (isNotEmpty()) {
            for (Activity tmp : activities) {
                if (tmp != null && tmp.getClass().equals(clz)) {
                    return tmp;
                }
            }
        }
        return null;
    }

    public void startSingleActivity(Class<? extends Activity> clazz) {
        if (isNotEmpty()) {
            @SuppressWarnings("unchecked")
            Stack<Activity> temp = (Stack<Activity>) activities.clone();

            Activity top = temp.pop();
            if (!clazz.equals(top.getClass())){
                top.startActivity(new Intent(top, clazz));
                top.finish();
            }

            // 销毁其他Activity
            while (!temp.empty()) {
                top = temp.pop();
                top.finish();
            }
        }
    }
}