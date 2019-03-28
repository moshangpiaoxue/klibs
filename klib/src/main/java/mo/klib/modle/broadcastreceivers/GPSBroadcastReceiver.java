package mo.klib.modle.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

import mo.klib.k;
import mo.klib.modle.listener.receiverListener.KOnGpsChangeListener;
import mo.klib.modle.manager.KLocationManager;
import mo.klib.utils.logUtils.LogUtil;


/**
 * @ author：mo
 * @ data：2019/1/29：14:37
 * @ 功能：
 */
public class GPSBroadcastReceiver {
    private Receiver receiver;
    private KOnGpsChangeListener mInterface;

    private String GPS_ACTION = LocationManager.PROVIDERS_CHANGED_ACTION;

    public GPSBroadcastReceiver(KOnGpsChangeListener mInterface) {
        this.mInterface = mInterface;
        onCreate();
    }

    private void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GPS_ACTION);
        receiver = new Receiver();
        k.app().registerReceiver(receiver, filter);
    }

    /**
     * 释放资源
     */
    public void onDestroy() {
        if (receiver != null) {
            k.app().unregisterReceiver(receiver);
        }
    }

    class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.i("intent.getAction()====" + intent.getAction());
            if (intent.getAction().equals(GPS_ACTION)) {
                if (mInterface != null) {
                    mInterface.onGpsStatusChange(KLocationManager.INSTANCE.isOpen());
                }
            }
        }
    }


}
