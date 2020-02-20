package silladus.sample.page;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import silladus.sample.adapter.CommonAdapter;
import silladus.sample.adapter.ViewHolder;
import silladus.sample.adapter.page.IData;
import silladus.sample.impl.PageLayout;

/**
 * @author silladus
 * @date 2018/5/9/0009
 * GitHub: https://github.com/silladus
 * Description:
 */
public class SinglePage extends PageLayout<String> implements AdapterView.OnItemClickListener {

    public SinglePage(int reqCode, int pagerIndex, Context mContext) {
        super(reqCode, pagerIndex, mContext);
    }

    @Override
    public CommonAdapter<String> createListAdapter(ListView listView) {
        //listView.setDivider(null);
        listView.setOnItemClickListener(this);
        CommonAdapter<String> adapter = new CommonAdapter<String>(android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, String item, int position) {
                holder.<TextView>getView(android.R.id.text1).setText(item);
            }
        };
        listView.setAdapter(adapter);
        return adapter;
    }

    @Override
    public void initData(int reqCode, int partIndex, int pagerIndex) {
        if (mIData != null) {
            mIData.initData(reqCode, partIndex, pagerIndex);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(
                view.getContext().getApplicationContext(),
                getData().get(position),
                Toast.LENGTH_SHORT
        ).show();
    }

    private IData mIData;

    public void setIData(IData mIData) {
        this.mIData = mIData;
    }

}
