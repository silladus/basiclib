//package silladus.sample;
//
//import android.app.Activity;
//import android.graphics.Color;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatTextView;
//import androidx.appcompat.widget.Toolbar;
//import silladus.basic.IToolbar;
//import silladus.basic.SimpleActivityLifecycleCallbacks;
//
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.widget.TextView;
//
///**
// * Created by silladus on 2018/5/31/0031.
// * GitHub: https://github.com/silladus
// * Description:
// */
//public class ActivityLifecycleCallbacksImp extends SimpleActivityLifecycleCallbacks {
//
//    @Override
//    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        // Set Toolbar
//        // if an Activity want to set special toolbar style should also
//        // implement the IToolbar interface
//        if (activity instanceof IToolbar) {
//            ((IToolbar) activity).initToolbar();
//        } else if (activity instanceof AppCompatActivity) {
//             initToolbar((AppCompatActivity) activity);
//        }
//    }
//
//    private void initToolbar(@NonNull AppCompatActivity activity) {
////        View view = View.inflate(activity, R.layout.toolbar, null);
//        // Set up the toolbar.
//        Toolbar toolbar = activity.findViewById(R.id.toolbar);
//        // Set the Toolbar as an ActionBar.
//        activity.setSupportActionBar(toolbar);
//        ActionBar ab = activity.getSupportActionBar();
//        if (ab != null) {
//            for (int i = 0; i < 3; i++) {
//                // Title layout in the Toolbar's center.
//                TextView tv = new AppCompatTextView(activity);
//                tv.setText(activity.getTitle());
//                tv.setSingleLine();
//                tv.setEllipsize(TextUtils.TruncateAt.END);
//                tv.setTextSize(20);
//                tv.setTextColor(Color.WHITE);
//                //tv.setClickable(true);
//                //tv.setPointerIcon(PointerIcon.getSystemIcon(activity, PointerIcon.TYPE_HAND));
//                Toolbar.LayoutParams lp;
//                if (i == 1) {
//                    lp = new Toolbar.LayoutParams(Gravity.CENTER);
//                    lp.setMargins(0, 0, 120, 0);
//                } else {
//                    if (i == 0) {
//                        tv.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                                activity.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp),
//                                null,
//                                null,
//                                null
//                        );
//                        tv.setBackgroundColor(Color.GREEN);
//                        lp = new Toolbar.LayoutParams(Gravity.CENTER_VERTICAL);
//                    } else {
//                        lp = new Toolbar.LayoutParams(Gravity.CENTER_VERTICAL|Gravity.END);
//                    }
//                    lp.width = 120;
//                }
////                toolbar.addView(tv, lp);
//            }
//            // Hide the default title.
//            ab.setDisplayShowTitleEnabled(false);
//            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
//            ab.setDisplayHomeAsUpEnabled(false);
//        }
//    }
//}
