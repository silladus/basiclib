package silladus.sample.adapter.page;

/**
 * Created by silladus on 2018/3/24/0024.
 * GitHub: https://github.com/silladus
 * Description:
 */

public interface IData {
    /**
     * Request data.
     *
     * @param reqCode   请求码，一般用于不同分类的数据
     * @param partIndex 分页加载第几页
     * @param pagerIndex ViewPager中的第几页
     */
    void initData(int reqCode, int partIndex, int pagerIndex);
}
