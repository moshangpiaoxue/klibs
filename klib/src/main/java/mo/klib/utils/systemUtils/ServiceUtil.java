package mo.klib.utils.systemUtils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.text.TextUtils;

import java.util.List;

import mo.klib.k;
import mo.klib.modle.manager.KActivityManager;

/**
 * @ author：mo
 * @ data：2019/2/13:16:25
 * @ 功能：
 */
public class ServiceUtil {
    /**
     * 判断服务是否正在运行
     *
     * @param service 服务类
     * @return 是否正在运行
     */
    public static boolean isRuning(Class<? extends Service> service) {
        // 获取运行中服务
        List<ActivityManager.RunningServiceInfo> services = KActivityManager.INSTANCE.getRunningServiceInfo();
        String serviceName = service.getName();
        for (ActivityManager.RunningServiceInfo info : services) {
            // 获取每一条运行中的服务的类名并判断
            String name = info.service.getClassName();
            if (TextUtils.equals(serviceName, name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 启动指定的服务
     *
     * @param clazz 服务类
     */
    public static void startService(Class<?> clazz) {
        k.app().startService(new Intent(k.app(), clazz));
    }

    /**
     * 停止指定的服务
     *
     * @param clazz 服务类
     */
    public static boolean stopService(Class<?> clazz) {
        return k.app().stopService(new Intent(k.app(), clazz));
    }
}
