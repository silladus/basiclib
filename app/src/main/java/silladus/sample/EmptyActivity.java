package silladus.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author silladus
 */
public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(new BackgroundFragment(), "BackgroundFragment")
//                .add(android.R.id.content, new MainFragment(), "MainFragment")
//                .commit();


    }

}