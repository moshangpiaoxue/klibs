package mo.klib.ui.fragment;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mo.klib.R;
import mo.klib.modle.adapter.KHeaderAndFooterWrapper;
import mo.klib.modle.adapter.KRecycleViewAdapter;
import mo.klib.view.PullToRefresh.BaseRefreshListener;
import mo.klib.view.PullToRefresh.PullToRefreshLayout;
import mo.klib.view.recyclerView.KRecycleView;


/**
 * @ author：mo
 * @ data：2018/12/18
 * @ 功能：基础列表碎片
 */
public abstract class KBaseListFragment<T> extends KBaseLayoutFragment {
    protected PullToRefreshLayout mPullToRefreshLayout;
    protected KRecycleView mKRecycleView;
    protected List<T> mData = new ArrayList<T>();
    protected KHeaderAndFooterWrapper<T> mWrapper;
    protected KRecycleViewAdapter<T> mAdapter;

    @Override
    protected void initLayoutViews(View mainView) {
        mPullToRefreshLayout = mainView.findViewById(R.id.ptrl_base_list);
        mKRecycleView = mainView.findViewById(R.id.krv_base_list);
        mPullToRefreshLayout.setCanRefresh(true);
        mPullToRefreshLayout.setCanLoadMore(false);
        mPullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                mPage = 1;
                getList(mPage);
            }

            @Override
            public void loadMore() {
                getMore(mPage);
            }
        });
        mAdapter = getAdapter();
        if (mAdapter != null) {
            mWrapper = new KHeaderAndFooterWrapper<T>(mAdapter);
            mKRecycleView.setAdapter(mWrapper);
        }
        loading();
        initListView();
    }

    protected abstract void initListView();

    public void loadList(List<T> list) {
        if (list == null || list.size() == 0) {
            loadErrorNoData();
        } else {
            loadSuccess();
            mData.clear();
            mData.addAll(list);
            mWrapper.notifyDataSetChanged();
            mPage++;
        }
        mPullToRefreshLayout.finishRefresh();
    }

    public void loadMore(List<T> list) {
        if (list == null || list.size() == 0) {
            noMoreData();
            mPage--;
        } else {
            mData.addAll(list);
            mWrapper.notifyDataSetChanged();
            mPage++;
        }
        mPullToRefreshLayout.finishLoadMore();
    }

    private void noMoreData() {
        showToast("没有更多数据");
        mPullToRefreshLayout.setCanLoadMore(false);
    }

    protected abstract KRecycleViewAdapter<T> getAdapter();

    protected abstract void getMore(int mPage);

    @Override
    protected int getMainLayoutId() {
        return R.layout.base_list;
    }


}
