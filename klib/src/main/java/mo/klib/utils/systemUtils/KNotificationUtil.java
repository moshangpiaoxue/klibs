package mo.klib.utils.systemUtils;

import android.support.v4.app.NotificationManagerCompat;

import mo.klib.k;

/**
 * @ author：mo
 * @ data：2019/6/12:16:51
 * @ 功能：
 */
public class KNotificationUtil {
    /**
     * 是否可以接收通知
     */
    public static boolean isNotificationEnabled() {
        return NotificationManagerCompat.from(k.app()).areNotificationsEnabled();
    }
}
