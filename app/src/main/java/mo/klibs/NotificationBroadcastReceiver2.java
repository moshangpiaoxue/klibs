package mo.klibs;

import android.content.Intent;

import mo.klib.k;
import mo.klib.modle.broadcastreceivers.NotificationBroadcastReceiver;
import mo.klib.utils.logUtils.LogUtil;

/**
 * @ author：mo
 * @ data：2019/4/8:18:59
 * @ 功能：
 */
public class NotificationBroadcastReceiver2 extends NotificationBroadcastReceiver {
    @Override
    protected void onCanaell() {
        Intent intent1 = new Intent(k.app(), ListActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        k.app().stopService(intent1);
    }

    @Override
    protected void onClick() {
        Intent intent2 = new Intent(k.app(), ListActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        k.app().startActivity(intent2);
    }
}
