package silladus.sample.page;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;

import silladus.basic.ActivityInitConfig;
import silladus.basic.IActivity;
import silladus.sample.adapter.recyclerview.RecyclerViewAdapter;
import silladus.sample.adapter.recyclerview.RecyclerViewHolder;
import silladus.sample.R;

/**
 * @author silladus
 * @date 2018/5/9/0009
 * GitHub: https://github.com/silladus
 * Description:
 */
public class RecyclerPagerActivity extends AppCompatActivity implements IActivity {

    TabLayout tabLayout;
    RecyclerView mRecyclerView;
    ViewPager mViewPager;

    String[] title = new String[]{"P1", "P2", "P3", "P4"};

    @Override
    public void onConfigInit(@NonNull ActivityInitConfig config) {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_recycler_pager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTitle("Pager");
        super.onCreate(savedInstanceState);
        ViewCompat.setTransitionName(findViewById(android.R.id.content), "anim");

        tabLayout = findViewById(android.R.id.tabs);
        mViewPager = findViewById(R.id.mViewPager);
        mRecyclerView = findViewById(R.id.mRecyclerView);

        for (int i = 0; i < 4; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(title[i]);
            tabLayout.addTab(tab);
        }

        // ViewPager
        tabLayout.setupWithViewPager(mViewPager);
        PagesAdapter pagesAdapter = new PagesAdapter();
        pagesAdapter.setTitles(title);
        pagesAdapter.setReqCode(0, 1, 2, 3);
        mViewPager.setAdapter(pagesAdapter);

        // RecyclerView
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(R.layout.page_item_recyclerview, 0) {
            @Override
            public void onBindViewHolder(RecyclerViewHolder holder, Object item, int i) {
                holder.getView(R.id.tips)
                        .setVisibility(View.VISIBLE);
                holder.getView(R.id.mProgressBar)
                        .setVisibility(View.GONE);
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setData(Arrays.asList(title));
        SetupWithRecyclerViewHelper.setup(tabLayout, mRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasks_fragment_menu, menu);
        menu.findItem(R.id.menu_filter).setVisible(false);
        menu.findItem(R.id.menu_clear).setTitle("RecyclerView");
        menu.findItem(R.id.menu_refresh).setTitle("ViewPager");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 返回键
            case android.R.id.home:
                finish();
                return true;
        }
        switch (item.getItemId()) {
            case R.id.menu_clear:
                mRecyclerView.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.GONE);
                break;
            case R.id.menu_refresh:
                mRecyclerView.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
