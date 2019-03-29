package mo.klib.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import mo.klib.R;
import mo.klib.modle.adapter.KHeaderAndFooterWrapper;
import mo.klib.modle.adapter.KRecycleViewAdapter;
import mo.klib.modle.listener.scrollingListener.KOnScrollingListener;
import mo.klib.utils.viewUtil.ViewUtil;
import mo.klib.view.PullToRefresh.BaseRefreshListener;
import mo.klib.view.PullToRefresh.KPullToRefreshLayout;
import mo.klib.view.recyclerView.KRecycleView;


/**
 * @ author：mo
 * @ data：2017/11/24 0024
 * @ 功能：
 */

public abstract class KBaseListActivity<T> extends KBaseLayoutActivity {
    protected LinearLayout ll_base_list_addlayout;
    protected KRecycleView kRecycleview;
    protected KPullToRefreshLayout kPullLayout;
    protected int mPage = 1;
    protected KHeaderAndFooterWrapper<T> mWrapper;
    protected KRecycleViewAdapter<T> mAdapter;
    protected List<T> mData = new ArrayList<>();


    @Override
    protected int getMainLayoutId() {
        return R.layout.base_list_layout;
    }

    @Override
    protected void initViews(View mainView) {
        ll_base_list_addlayout = findView(R.id.ll_base_list_addlayout);
        kPullLayout = findView(R.id.k_pull_layout);
        kRecycleview = findView(R.id.k_recycleview);

        kPullLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                getList(mPage = 1);
            }

            @Override
            public void loadMore() {
                getMore(mPage++);
            }
        });
        mAdapter = getAdapter();
        if (mAdapter != null) {
            mWrapper = new KHeaderAndFooterWrapper<>(mAdapter);
            kRecycleview.setAdapter(mWrapper);
        }

        layoutError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList(mPage = 1);
            }
        });
        initListView();

        kPullLayout.autoRefresh();
        loading();
    }

    protected abstract void initListView();

    @Override
    protected void getData() {
    }

    protected abstract void getMore(int page);

    protected abstract void getList(int page);


    protected abstract KRecycleViewAdapter<T> getAdapter();


    /**
     * 开启滑动到底部自动加载更多
     */
    public void actionOnScrollingListener() {
        mWrapper.getInnerAdapter().setOnScrollingListener(new KOnScrollingListener() {
            @Override
            public void onScrollingListener(int shoePosition, int count) {
                if (mWrapper.getInnerAdapter().getmItemPosition() == mWrapper.getItemCount() - 1) {
                    getMore(mPage++);

                }
            }
        });
    }

    /**
     * 关闭滑动监听
     */
    public void closeScollingListener() {
        mWrapper.getInnerAdapter().setOnScrollingListener(null);
    }

    /**
     * 刷新数据
     */
    public void refeshAdapter(List<T> list) {
        mData.clear();
        if (list != null && list.size() != 0) {
            mData = list;
            mWrapper.removeEmptyView();
            mWrapper.refresh(mData);
            mPage++;
        } else {
            loadErrorNoData();
            mWrapper.addEmptyView(ViewUtil.getView(mActivity, R.layout.base_empty, kRecycleview));
        }
        loadSuccess();
        kPullLayout.finishRefresh();
    }

    /**
     * 加载更多
     *
     * @param list
     */
    public void loardMoreAdapter(List<T> list) {
        if (list != null && list.size() != 0) {
            mWrapper.loadMore(list);
        } else {
            mPage -= 1;
            showToast("没有更多数据");
        }
        kPullLayout.finishLoadMore();
    }

}
