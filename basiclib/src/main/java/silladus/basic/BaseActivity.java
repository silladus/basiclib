//package silladus.basic;
//
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import silladus.basic.view.GrayFrameLayout;
//
///**
// * Created by silladus on 2020/4/5.
// * GitHub: https://github.com/silladus
// * Description:
// */
//public class BaseActivity extends AppCompatActivity {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        if("FrameLayout".equals(name)){
//            int count = attrs.getAttributeCount();
//            for (int i = 0; i < count; i++) {
//                String attributeName = attrs.getAttributeName(i);
//                String attributeValue = attrs.getAttributeValue(i);
//                if (attributeName.equals("id")) {
//                    int id = Integer.parseInt(attributeValue.substring(1));
//                    String idVal = getResources().getResourceName(id);
//                    if ("android:id/content".equals(idVal)) {
//                        GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context, attrs);
//
//                        //针对设置了windowBackground的情况
//                        Drawable backgroundDrawable;
//                        TypedValue a = new TypedValue();
//                        getTheme().resolveAttribute(android.R.attr.windowBackground, a, true);
//                        if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
//                            // windowBackground is a color
//                            int color = a.data;
//                            backgroundDrawable = new ColorDrawable(color);
//                        } else {
//                            // windowBackground is not a color, probably a drawable
//                            backgroundDrawable = getResources().getDrawable(a.resourceId);
//                        }
//
//                        grayFrameLayout.setBackground(backgroundDrawable);
//
//                        return grayFrameLayout;
//                    }
//                }
//            }
//        }
//        return super.onCreateView(name, context, attrs);
//    }
//}
