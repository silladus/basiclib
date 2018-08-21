package silladus.basic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by silladus on 2018/5/31/0031.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class ActivityLifecycleCallbacksImp extends SimpleActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // Set Toolbar
        // if an Activity want to set special toolbar style should also
        // implement the IToolbar interface
        if (activity instanceof IToolbar) {
            ((IToolbar) activity).initToolbar();
        } //else if (activity instanceof AppCompatActivity) {
            // initToolbar((AppCompatActivity) activity);
        //}
    }

//    private void initToolbar(@NonNull AppCompatActivity activity) {
//        // Set up the toolbar.
//        Toolbar toolbar = activity.findViewById(R.id.toolbar);
//        // Set the Toolbar as an ActionBar.
//        activity.setSupportActionBar(toolbar);
//        ActionBar ab = activity.getSupportActionBar();
//        if (ab != null) {
//            // Title layout in the Toolbar's center.
//            TextView tv = new AppCompatTextView(activity);
//            tv.setText(activity.getTitle());
//            tv.setSingleLine();
//            tv.setEllipsize(TextUtils.TruncateAt.END);
//            tv.setTextSize(20);
//            tv.setTextColor(Color.WHITE);
//            toolbar.addView(tv, new Toolbar.LayoutParams(Gravity.CENTER));
//            // Hide the default title.
//            ab.setDisplayShowTitleEnabled(false);
//            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
//            ab.setDisplayHomeAsUpEnabled(true);
//        }
//    }
}
