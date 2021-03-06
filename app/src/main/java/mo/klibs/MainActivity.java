package mo.klibs;

import java.util.ArrayList;
import java.util.List;

import mo.klib.modle.adapter.KRecycleViewAdapter;
import mo.klib.ui.activity.KBaseListActivity;
import mo.klibs.UtilsActivtys.aUtilActivity;
import mo.klibs.ViewActivitys.aViewActivity;
import mo.klibs.bean.MainBean;
import mo.klibs.modle.AdapterModle;


public class MainActivity extends KBaseListActivity<MainBean> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("首页");
        kRecycleview.setLayoutGrid(4);
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("View相关", aViewActivity.class));
        list.add(new MainBean("Utils相关", aUtilActivity.class));
        refeshAdapter(list);
    }

    @Override
    protected void getMore(int page) {

    }

    @Override
    protected void getList(int page) {

    }

    @Override
    protected KRecycleViewAdapter<MainBean> getAdapter() {
        return AdapterModle.getMainAdapter(mActivity, mData);
    }

}
