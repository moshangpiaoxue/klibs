package mo.klib.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import mo.klib.R;
import mo.klib.modle.adapter.KHeaderAndFooterWrapper;
import mo.klib.modle.adapter.KRecycleViewAdapter;
import mo.klib.modle.listener.scrollingListener.KOnScrollingListener;
import mo.klib.modle.viewHolder.ViewHolder;
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
    public View statusBar;
    public LinearLayout ll_base_list_addlayout;
    public KRecycleView kRecycleview;
    private KPullToRefreshLayout kPullLayout;
    private int page = 1;
    private KHeaderAndFooterWrapper<T> adapter;
    protected List<T> mData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list_layout;
    }

    @Override
    protected void initView(ViewHolder mViewHolder, View rootView) {
        statusBar = findView(R.id.view_status_bar);
        ll_base_list_addlayout = findView(R.id.ll_base_list_addlayout);
        kPullLayout = findView(R.id.k_pull_layout);
        kRecycleview = findView(R.id.k_recycleview);

        kPullLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                page = 1;
                getList(page);
            }

            @Override
            public void loadMore() {
                page++;
                getMore(page);
            }
        });
//空数据布局
        View emptyView = ViewUtil.getView(mActivity, R.layout.base_empty, kRecycleview);
        adapter = new KHeaderAndFooterWrapper<>(mActivity, getAdapter(), emptyView, kRecycleview);

        kRecycleview.setAdapter(adapter);
        dealView(mViewHolder, ll_base_list_addlayout, kPullLayout, kRecycleview, adapter);
    }

    protected void getMore(int page) {
    }

    protected void getList(int page) {
    }


    protected abstract KRecycleViewAdapter<T> getAdapter();

    /**
     * 设置当前布局是否可以下拉或上拉
     *
     * @param mViewHolder
     * @param addlayout
     * @param kPullLayout
     * @param kRecycleview
     * @param adapter
     */
    protected abstract void dealView(ViewHolder mViewHolder, LinearLayout addlayout, KPullToRefreshLayout kPullLayout, KRecycleView kRecycleview, KHeaderAndFooterWrapper<T> adapter);

    /**
     * 开启滑动到底部自动加载更多
     */
    public void actionOnScrollingListener() {
        adapter.getInnerAdapter().setOnScrollingListener(new KOnScrollingListener() {
            @Override
            public void onScrollingListener(int shoePosition, int count) {
                if (adapter.getInnerAdapter().getmItemPosition() == adapter.getItemCount() - 1) {
                    page++;
                    getMore(page);

                }
            }
        });
    }

    /**
     * 关闭滑动监听
     */
    public void closeScollingListener() {
        adapter.getInnerAdapter().setOnScrollingListener(null);
    }

    /**
     * 刷新数据
     *
     */
    public void refeshAdapter() {
        if (mData.size() != 0) {
            adapter.refresh(mData);
            loadSuccess();
        }
        kPullLayout.finishRefresh();
    }

    /**
     * 加载更多
     *
     * @param list
     */
    public void loardMoreAdapter(List<T> list) {
        if (list.size() != 0) {
            adapter.loadMore(list);
        } else {
            page -= 1;
            showToast("没有更多数据");
        }
        kPullLayout.finishLoadMore();
    }

}
