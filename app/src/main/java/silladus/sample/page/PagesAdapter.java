package silladus.sample.page;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import silladus.basic.adapter.CommonAdapter;
import silladus.basic.adapter.page.BasePage;
import silladus.basic.adapter.page.IData;
import silladus.basic.adapter.page.ListPagerAdapter;
import silladus.sample.page.SinglePage;

/**
 * @author silladus
 * @date 2018/5/13/0013
 * GitHub: https://github.com/silladus
 * Description:
 */
public class PagesAdapter extends ListPagerAdapter<String, ListView, CommonAdapter<String>> {

    @NonNull
    @Override
    protected BasePage<String, ListView, CommonAdapter<String>> initPageLayout(int index, Context context) {
        final SinglePage page = new SinglePage(getReqCode()[index], index, context);
        page.setIData(new IData() {
            private int hadSize;

            @Override
            public void initData(final int reqCode, final int partIndex, int pagerIndex) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (partIndex == BasePage.PART_START) {
                            hadSize = 0;
                        }
                        List<String> list = new ArrayList<>();
                        for (int i = hadSize; i < 5 + hadSize; i++) {
                            list.add("item " + i + ", reqCode:" + reqCode + ", part: "
                                    + partIndex + ", ViewType:ListView");
                        }
                        page.receiveData(list, "Have no more data!");

                        hadSize = page.getData().size();
                    }
                }, 2000);
            }
        });
        return page;
    }
}
