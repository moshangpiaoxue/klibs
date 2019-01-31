package mo.klib.utils.bengUtil;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SmsManager;

import java.util.List;


import mo.klib.k;
import mo.klib.utils.appUtils.PermissionUtil;
import mo.klib.utils.dataUtil.StringUtil;
import mo.klib.utils.logUtils.LogUtil;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * @ author：mo
 * @ data：2018/12/11
 * @ 功能：
 */
public class NextOtherActivityUtil {
    /**
     * 打开 App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (StringUtil.isSpace(packageName)) {
            return;
        }
        k.app().startActivity(IntentUtil.getLaunchAppIntent(packageName, true));
    }

    /**
     * 打开 App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(final Activity activity, final String packageName, final int requestCode) {
        if (StringUtil.isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtil.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 跳至拨号界面
     *
     * @param phoneNumber 电话号码
     */
    public static void toDial(final String phoneNumber) {
        k.app().startActivity(IntentUtil.getDialIntent(phoneNumber, true));
    }

    /**
     * 拨打电话
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static void toCall(final String phoneNumber) {
        if (PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.CALL_PHONE)) {
            return;
        }
        k.app().startActivity(IntentUtil.getCallIntent(phoneNumber, true));
    }

    /**
     * 跳至发送短信界面
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static void toSendSms(final String phoneNumber, final String content) {
        k.app().startActivity(IntentUtil.getSendSmsIntent(phoneNumber, content, true));
    }

    /**
     * 发送短信
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SEND_SMS" />}</p>
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static void toSendSms2(final String phoneNumber, final String content) {
        if (StringUtil.isEmpty(content)) {
            return;
        }
        if (!PermissionUtil.INSTANCE.checkSelfPermission(Manifest.permission.SEND_SMS)) {
            LogUtil.i("没权限");
            return;
        }
        PendingIntent sentIntent = PendingIntent.getBroadcast(k.app(), 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }

    /**
     * 跳app系统设置
     */
    public static void toSettingApp() {
        toSettingApp(k.app().getPackageName());
    }

    /**
     * 跳app系统设置
     *
     * @param packageName 包名
     */
    public static void toSettingApp(final String packageName) {
        if (StringUtil.isSpace(packageName)) {
            return;
        }
        k.app().startActivity(IntentUtil.getSettingAppIntent(packageName, true));
    }

    /**
     * 跳网络设置
     */
    public static void toSettingNet() {
        k.app().startActivity(IntentUtil.getSettingNetIntent(true));
    }

    /**
     * 调起浏览器
     *
     * @param url 浏览器地址
     */
    public static void toBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        k.app().startActivity(intent);

//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        context.startActivity(intent);
    }

    /**
     * 设置通知栏 权限
     */
    public static void toSettingNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", k.app().getPackageName());
            intent.putExtra("app_uid", k.app().getApplicationInfo().uid);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            k.app().startActivity(intent);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + k.app().getPackageName()));
            k.app().startActivity(intent);
        } else {
            Intent localIntent = new Intent();
            localIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", k.app().getPackageName(), null));
            k.app().startActivity(localIntent);

        }
    }
}
