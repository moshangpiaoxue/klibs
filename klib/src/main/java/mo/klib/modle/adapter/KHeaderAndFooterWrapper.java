package mo.klib.modle.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

import mo.klib.modle.viewHolder.KRecycleViewHolder;
import mo.klib.utils.viewUtil.ViewUtil;

import static android.R.attr.key;

/**
 * @ author：mo
 * @ data：2018/6/29：14:27
 * @ 功能：可以添加RecycleView头部及脚部视图的适配器
 */
public class KHeaderAndFooterWrapper<T> extends RecyclerView.Adapter<KRecycleViewHolder> {
    /**
     * 头部type
     */
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    /**
     * 空数据type
     */
    private static final int BASE_ITEM_TYPE_NOT_DATA = 200000;
    /**
     * 底部type
     */
    private static final int BASE_ITEM_TYPE_FOOTER = 300000;
    /**
     * 存储列表头布局HeaderView实例
     */
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    /**
     * 存储列表底部布局FooterView实例
     */
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();
    /**
     * 列表（mInnerAdapter）无数据时显示的视图布局
     */
    private SparseArrayCompat<View> mInnerAdapterNotData = new SparseArrayCompat<>();
    /**
     * 这个适配器就是展示你具体数据的
     */
    private KRecycleViewAdapter<T> mInnerAdapter;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 空数据布局的父布局，用来动态测量列表的高度，出入当前的ewcycleview
     */
    private ViewGroup viewParent;
    /**
     * RecyclerView的高度
     */
    private int viewGroupMeasureHeight;
    /**
     * RecyclerView的宽度
     */
    private int viewGroupMeasureWidth;
    /**
     * 空数据view标识，id
     */
    private int emptyNum = 0;
    /**
     * 空数据view点击监听 后期优化删掉，因为创建的时候传进来的是一个view  点击事件可以提前添加
     */
    private View.OnClickListener listener;
    /**
     * 空数据view
     */
    private View emptyView;
    /**
     * 顶部开始索引
     */
    private int headerStartIndex;

    public KHeaderAndFooterWrapper(KRecycleViewAdapter<T> mInnerAdapter, Context mContext) {
        this.mInnerAdapter = mInnerAdapter;
        this.mContext = mContext;
    }

    /**
     * 构造方法
     *
     * @param context    上下文
     * @param adapter    主体数据适配器
     * @param emptyView  空数据view
     * @param viewParent 空数据view父类，可直传recycleview
     */
    public KHeaderAndFooterWrapper(Context context, KRecycleViewAdapter<T> adapter, View emptyView, final ViewGroup viewParent) {
        this.mContext = context;
        this.mInnerAdapter = adapter;
        this.viewParent = viewParent;
        this.emptyView = emptyView;

    }

    public KHeaderAndFooterWrapper(Context context, KRecycleViewAdapter<T> adapter, final int layoutId, final ViewGroup viewParent) {
        mContext = context;
        mInnerAdapter = adapter;
        this.viewParent = viewParent;
        this.emptyView = ViewUtil.getView(context, layoutId, viewParent);

    }

    /**
     * 获取主体数据适配器
     */
    public KRecycleViewAdapter<T> getInnerAdapter() {
        return mInnerAdapter;
    }

    /**
     * 添加数据
     *
     * @param data 数据
     */
    public void refresh(List<T> data) {
        if (data == null || data.isEmpty()) {
            mInnerAdapter.refreshView(data);
            setNotDataLayoutView(emptyView, viewParent);
        } else {
            //隐藏列表无数据时显示的视图
            mInnerAdapterNotData.clear();
            //隐藏占位视图，占位的原因-item数量少时，筛选布局无法顶到顶部，使用占位布局可让筛选布局顶到顶部
            mFootViews.clear();
            mInnerAdapter.refreshView(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加mInnerAdapter无数据时的视图
     */
    private void setNotDataLayoutView(View emptyView, ViewGroup viewGroup) {
        if (emptyView != null && viewGroup != null) {
            clearInnerAdapterNotData();
//        if (mInnerAdapterNotData.size() == 0) {
            //隐藏占位视图布局，占位的原因，列表item数量少时，筛选布局无法顶到顶部，使用占位布局可把筛选布局顶到顶部
            mFootViews.clear();
            viewGroupMeasureHeight = viewGroup.getMeasuredHeight();
            viewGroupMeasureWidth = viewGroup.getMeasuredWidth();

            if (listener != null) {
                emptyView.setOnClickListener(listener);
            }
            emptyView.setVisibility(View.VISIBLE);
            addNotDataView(emptyView);
//        }
        }
    }

    /**
     * 清空mInnerAdapter无数据时的视图
     */
    public void clearInnerAdapterNotData() {
        mInnerAdapterNotData.clear();
    }

    /**
     * 添加mInnerAdapter无数据时的视图
     *
     * @param view 列表无数据展示的视图
     */
    private void addNotDataView(View view) {
        int key = mInnerAdapterNotData.size() + BASE_ITEM_TYPE_NOT_DATA;
        mInnerAdapterNotData.put(key, view);
    }

    /**
     * 添加数据
     *
     * @param data 列表数据
     */
    public void loadMore(List<T> data) {
        if (data != null && !data.isEmpty()) {
            mInnerAdapterNotData.clear();
            mInnerAdapter.loadMore(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置空数据view点击
     *
     * @param listener
     */
    public void setNoDairyClick(View.OnClickListener listener) {
        this.listener = listener;
    }


    /**
     * 添加头部视图
     *
     * @param view 头部视图
     * @return int 返回key值
     */
    public int addHeaderView(View view) {
        int key = mHeaderViews.size() + BASE_ITEM_TYPE_HEADER;
        mHeaderViews.put(key, view);
        return key;
    }

    /**
     * 添加头部视图
     *
     * @param view 头部视图
     * @return int 返回key值
     */
    public int addHeaderView(int key, View view) {
        View localKeyView = mHeaderViews.get(key);
        if (localKeyView == null) {
            mHeaderViews.put(key, view);
        }
        return key;
    }

    /**
     * 依据key值删除存储的对象
     *
     * @param key key值
     */
    public void removeHeaderView(int key) {
        mHeaderViews.remove(key);
    }

    public void addEmptyView(View view) {
        if (emptyNum == 0) {
            emptyNum = 1000099;
        }
        mHeaderViews.put(key, view);
    }

    public void removeEmptyView() {
        if (emptyNum == 1000099) {
            mHeaderViews.remove(key);
            emptyNum = 0;
        }
    }

    /**
     * 添加底部视图，添加完成后直接刷新布局
     *
     * @param view 底部视图
     */
    public void addFootView(View view) {
        int key = mFootViews.size() + BASE_ITEM_TYPE_FOOTER;
        mFootViews.put(key, view);
        notifyDataSetChanged();
    }

    /**
     * 添加底部视图， 添加完成后直接刷新布局
     *
     * @param viewId 底部视图资源ID
     */
    public void addFootView(int viewId) {
        int key = mFootViews.size() + BASE_ITEM_TYPE_FOOTER;
        View footView = LayoutInflater.from(mContext).inflate(viewId, viewParent, false);
        mFootViews.put(key, footView);
        notifyDataSetChanged();
    }

    @Override
    public KRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        //查看第viewType位置的View
        //头部布局
        View headerView = mHeaderViews.get(viewType);
        //底部布局
        View footView = mFootViews.get(viewType);
        //列表中的数据布局
        View notDataView = mInnerAdapterNotData.get(viewType);
        if (headerView != null) {
            return KRecycleViewHolder.getViewHolder(context, headerView);

        } else if (footView != null) {
            return KRecycleViewHolder.getViewHolder(context, footView);
        } else if (notDataView != null) {
            return KRecycleViewHolder.getViewHolder(context, notDataView);
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(KRecycleViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        if (mInnerAdapter.getItemCount() == 0) {
//            View view = holder.getView(R.id.ll_common_no_data);
//            setNotDataView(view, headerStartIndex);
            return;
        }
        int mInnerAdapterPosition = position - getHeadersCount();
        mInnerAdapter.onBindViewHolder(holder, mInnerAdapterPosition);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            //查看第position个位置的键
            int viewType = mHeaderViews.keyAt(position);
            return viewType;
        } else if (isFooterViewPos(position)) {
            int viewType = mFootViews.keyAt(position - getHeadersCount() - getRealItemCount() - getNotDaoViewCount());
            return viewType;
        } else if (isNotDataViewPos(position) && mInnerAdapter.getItemCount() == 0) {//列表（mInnerAdapter）无数据
            //显示无数据布局视图
            int viewType = mInnerAdapterNotData.keyAt(position - getHeadersCount());
            return viewType;
        } else {
            int viewType = mInnerAdapter.getItemViewType(position - getHeadersCount());
            return viewType;
        }
    }

    private boolean isNotDataViewPos(int position) {
        return position >= getHeadersCount();
    }

    /**
     * 适配器中显示的全部Item数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        int items = getHeadersCount() + getFootersCount() + getRealItemCount() + getNotDaoViewCount();
        return items;
    }

    private int getFootersCount() {
        int footersCount = mFootViews.size();
        return footersCount;
    }

    /**
     * 判断position位置放置HeaderView布局视图
     *
     * @param position 位置索引
     * @return false 该位置不能放列表头部布局；true 该位置放列表头部布局；
     */
    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    /**
     * 判断position位置放置FooterView布局视图
     *
     * @param position 位置索引
     * @return 该位置不能放列表底部布局；true 该位置放列表底部布局；
     */
    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount() + getNotDaoViewCount();
    }

    /**
     * 重新计算无数据布局的高度
     *
     * @param ll_common_no_data_match 无数据布局对象
     */
    private void setNotDataView(View ll_common_no_data_match, int paramMix) {
        int notDataViewHeight = viewGroupMeasureHeight;
        if (mHeaderViews.size() > 0) {
            //这段代码为了计算出无数据时的布局高度
            int lastIndex = mHeaderViews.size() - 1;
            for (int i = lastIndex; i >= paramMix; i--) {
                int key = mHeaderViews.keyAt(i);
                View view = mHeaderViews.get(key);
                //RecyclerView的高度减去头部布局的高度，
                notDataViewHeight = viewGroupMeasureHeight - view.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams layoutParams = ll_common_no_data_match.getLayoutParams();
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams layoutParamsR = (RelativeLayout.LayoutParams) layoutParams;
            layoutParamsR.width = viewGroupMeasureWidth; //viewGroupMeasureWidth, viewGroupMeasureHeight));
            layoutParamsR.height = notDataViewHeight;
            ll_common_no_data_match.setLayoutParams(layoutParamsR);
        } else if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParamsL = (LinearLayout.LayoutParams) layoutParams;
            layoutParamsL.width = viewGroupMeasureWidth; //viewGroupMeasureWidth, viewGroupMeasureHeight));
            layoutParamsL.height = notDataViewHeight;
            ll_common_no_data_match.setLayoutParams(layoutParamsL);
        } else if (layoutParams instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParamsF = (FrameLayout.LayoutParams) layoutParams;
            layoutParamsF.width = viewGroupMeasureWidth; //viewGroupMeasureWidth, viewGroupMeasureHeight));
            layoutParamsF.height = notDataViewHeight;
            ll_common_no_data_match.setLayoutParams(layoutParamsF);
        } else if (layoutParams instanceof RecyclerView.LayoutParams) {
            RecyclerView.LayoutParams layoutParamsR = (RecyclerView.LayoutParams) layoutParams;
            layoutParamsR.width = viewGroupMeasureWidth; //viewGroupMeasureWidth, viewGroupMeasureHeight));
            layoutParamsR.height = notDataViewHeight;
            ll_common_no_data_match.setLayoutParams(layoutParamsR);
        }
    }

    /**
     * 获取头部布局的数量
     *
     * @return 头部布局的数量
     */
    public int getHeadersCount() {
        int headersCount = mHeaderViews.size();
        return headersCount;
    }

    /**
     * 展示你具体数据适配器中的item数量
     *
     * @return
     */
    public int getRealItemCount() {
        int realItemCount = mInnerAdapter.getItemCount();
        return realItemCount;
    }

    /**
     * mInnerAdapter列表无数据时展示的视图数--正常情况数量为1
     *
     * @return
     */
    private int getNotDaoViewCount() {
        int notDaoaViewCount = mInnerAdapterNotData.size();
        return notDaoaViewCount;
    }

    public void setHeaderStartIndex(int headerStartIndex) {
        this.headerStartIndex = headerStartIndex;
    }

}

