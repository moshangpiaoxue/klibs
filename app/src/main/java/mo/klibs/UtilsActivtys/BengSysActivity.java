package mo.klibs.UtilsActivtys;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mo.klib.modle.adapter.KRecycleViewAdapter;
import mo.klib.modle.listener.clickListener.KOnItemClickListenerImpl;
import mo.klib.modle.viewHolder.KRecycleViewHolder;
import mo.klib.ui.activity.KBaseListActivity;
import mo.klib.utils.bengUtil.NextOtherActivityUtil;
import mo.klib.utils.bengUtil.ToMarketUtil;
import mo.klib.utils.systemUtils.KNotificationUtil;
import mo.klibs.R;

/**
 * @ author：mo
 * @ data：2019/6/12：10:31
 * @ 功能：跳系统界面
 */
public class BengSysActivity extends KBaseListActivity<String> {
    @Override
    protected boolean isCanAutoRefresh() {
        return false;
    }

    @Override
    protected void initListView() {
        title.setMidleText("跳系统界面");

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> list = new ArrayList<>();
        list.add("跳权限1" + KNotificationUtil.isNotificationEnabled());
        list.add("跳权限2" + KNotificationUtil.isNotificationEnabled());
        list.add("跳拨号界面");
        list.add("跳拨打电话");
        list.add("跳至发送短信界面");
        list.add("跳app系统设置");
        list.add("跳网络设置");
        list.add("调起浏览器");
        list.add("调应用市场");
        refeshAdapter(list);
    }

    private void click(int position, String bean) {
        if (bean.contains("跳权限1")) {
            NextOtherActivityUtil.toSettingNotification();
        } else if (bean.contains("跳权限2")) {
            NextOtherActivityUtil.toSettingNotification2(mActivity);
        } else if (bean.contains("跳拨号界面")) {
            NextOtherActivityUtil.toDial("15100299848");
        } else if (bean.contains("跳拨打电话")) {
            NextOtherActivityUtil.toCall("15100299848");
        } else if (bean.contains("跳至发送短信界面")) {
            NextOtherActivityUtil.toSendSms("15100299848", "111111");
        } else if (bean.contains("跳app系统设置")) {
            NextOtherActivityUtil.toSettingApp();
        } else if (bean.contains("跳网络设置")) {
            NextOtherActivityUtil.toSettingNet();
        } else if (bean.contains("调起浏览器")) {
            NextOtherActivityUtil.toBrowser("www.baidu.com");
        }
        else if (bean.contains("调应用市场")) {
            ToMarketUtil.toMarket("com.huawei.appmarket");
//            try {
//                String mAddress = "market://details?id=" + "mo.klibs";
//                Intent marketIntent = new Intent("android.intent.action.VIEW");
//                marketIntent.setData(Uri.parse(mAddress));
//                mActivity.startActivity(marketIntent);
//            } catch (Exception e) {
////                AlertUtil.showDeftToast(mActivity, "垃圾手机，请砸了");
//            }
        }
    }

    @Override
    protected void getMore(int page) {

    }

    @Override
    protected void getList(int page) {

    }

    @Override
    protected KRecycleViewAdapter<String> getAdapter() {
        return new KRecycleViewAdapter<String>(mActivity, mData) {
            @Override
            public void doWhat(KRecycleViewHolder holder, final String bean, int position, int itemViewType, RecyclerView parent) {
                TextView item_1 = holder.getView(R.id.item_1);
                item_1.setText(bean);
                holder.setItemClick(new KOnItemClickListenerImpl() {
                    @Override
                    public void onItemClick(View view, int position) {
                        super.onItemClick(view, position);
                        click(position, bean);
                    }
                });
            }

            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.iten;
            }
        };
    }


}
