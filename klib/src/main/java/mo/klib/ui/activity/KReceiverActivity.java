package mo.klib.ui.activity;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import mo.klib.modle.broadcastreceivers.GPSBroadcastReceiver;
import mo.klib.modle.broadcastreceivers.HomeBroadcastReceiver;
import mo.klib.modle.broadcastreceivers.LockScreenBroadcastReceiver;
import mo.klib.modle.broadcastreceivers.NetChangeBroadcastReceiver;
import mo.klib.modle.listener.receiverListener.KOnGpsChangeListener;
import mo.klib.modle.listener.receiverListener.KOnHomeListener;
import mo.klib.modle.listener.receiverListener.KOnLockScreenListener;
import mo.klib.modle.listener.receiverListener.KOnNetChangeListener;
import mo.klib.modle.manager.KLocationManager;
import mo.klib.utils.logUtils.LogUtil;
import mo.klib.utils.tipsUtil.ToastUtil;
import mo.klib.view.dialog.IosAlertDialog;


/**
 * @ author：mo
 * @ data：2018/12/14
 * @ 功能：
 */
public class KReceiverActivity extends KRxJavaActivity implements KOnNetChangeListener,
        KOnGpsChangeListener, KOnHomeListener, KOnLockScreenListener {
    private GPSBroadcastReceiver gpsBroadcastReceiver;
    private NetChangeBroadcastReceiver netBroadcastReceiver;
    private HomeBroadcastReceiver homeBroadcastReceiver;
    private LockScreenBroadcastReceiver lockScreenBroadcastReceiver;

    /**
     * 网络类型
     */
    protected int netType;
    /**
     * Gps开启状态
     */
    protected Boolean isOpenGps;
    private IosAlertDialog GpsDialog;

    /**
     * 锁屏监听
     */
    protected void actionScreenListener() {
        lockScreenBroadcastReceiver = new LockScreenBroadcastReceiver(this);
    }

    /**
     * 开home键监听
     */
    protected void actionHomeListener() {
        homeBroadcastReceiver = new HomeBroadcastReceiver(this);
    }

    /**
     * 开网络监听
     */
    protected void actionNetListener() {
        netBroadcastReceiver = new NetChangeBroadcastReceiver(this);
    }

    /**
     * 开GPS监听
     */
    protected void actionGpsListener() {
        gpsBroadcastReceiver = new GPSBroadcastReceiver(this);
        onGpsStatusChange(KLocationManager.INSTANCE.isOpen());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gpsBroadcastReceiver == null) {
            onGpsStatusChange(KLocationManager.INSTANCE.isOpen());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gpsBroadcastReceiver != null) {
            gpsBroadcastReceiver.onDestroy();
        }
        if (netBroadcastReceiver != null) {
            netBroadcastReceiver.onDestroy();
        }
        if (homeBroadcastReceiver != null) {
            homeBroadcastReceiver.onDestroy();
        }
        if (lockScreenBroadcastReceiver != null) {
            lockScreenBroadcastReceiver.onDestroy();
        }
    }

    /**
     * 网络变化
     */
    @Override
    public void onNetChange(int netType) {
        this.netType = netType;
        if (netType == -1) {
            ToastUtil.showToast("网络异常，请检查网络连接");
        }
    }

    /**
     * GPS开关
     */
    @Override
    public void onGpsStatusChange(Boolean isOpen) {
        isOpenGps = isOpen;
        LogUtil.i("定位功能开启状态==" + isOpen);
        if (!isOpen) {
            GpsDialog = new IosAlertDialog(mActivity).builder()
                    .setCancelable(false)
                    .setTitle("定位功能未开启")
                    .setMsg("请先打开GPS定位功能!")
                    .setPositiveButton("设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            // 设置完成后返回到原来的界面
                            mActivity.startActivityForResult(intent, 0);
                        }
                    });
            GpsDialog.show();
        } else {
            if (GpsDialog != null) {
                GpsDialog.dismiss();
            }

        }
    }

    /**
     * 点击home键
     *
     * @param status 1=短按Home键 只有这个是最靠谱的，
     *               2=长按Home键 或者 activity切换键
     *               3=锁屏
     *               4=samsung 长按Home键
     */
    @Override
    public void onHomeTouch(int status) {
    }

    /**
     * status;true=开屏
     * false=锁屏
     */
    @Override
    public void onScreenChange(Boolean status) {

    }
}
