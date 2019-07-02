package mo.klibs.modle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mo.klib.modle.adapter.KRecycleViewAdapter;
import mo.klib.modle.listener.clickListener.KOnItemClickListenerImpl;
import mo.klib.modle.viewHolder.KRecycleViewHolder;
import mo.klib.utils.bengUtil.BengUtil;
import mo.klibs.R;
import mo.klibs.bean.MainBean;

/**
 * @ author：mo
 * @ data：2019/6/12:10:19
 * @ 功能：
 */
public class AdapterModle {
    public static KRecycleViewAdapter<MainBean> getMainAdapter(final Activity mActivity, List<MainBean> list) {
        return new KRecycleViewAdapter<MainBean>(mActivity, list) {
            @Override
            protected int getItemLayout(int position) {
                return R.layout.item_main;
            }

            @Override
            public void doWhat(KRecycleViewHolder holder, final MainBean bean, int position, int itemViewType, RecyclerView parent) {
                ImageView iv_item_main = holder.getView(R.id.iv_item_main);
                TextView tv_item_main = holder.getView(R.id.tv_item_main);
                tv_item_main.setText(bean.getTitle());
                iv_item_main.setImageResource(bean.getDwrableId());
                holder.setItemClick(new KOnItemClickListenerImpl() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        BengUtil.getBuilder(mActivity, bean.getActivity(), false).action();
                    }
                });
            }

        };

    }

    public static KRecycleViewAdapter<String> getAdapter(Activity mActivity, List<String> list) {
        return new KRecycleViewAdapter<String>(mActivity, list) {
            @Override
            protected int getItemLayout(int position) {
                return R.layout.iten;
            }

            @Override
            public void doWhat(KRecycleViewHolder holder, final String bean, int position, int itemViewType, RecyclerView parent) {
                TextView item_1 = holder.getView(R.id.item_1);
                item_1.setText(bean);
            }
        };
    }
}
