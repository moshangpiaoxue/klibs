package mo.klib.utils.bengUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;


import java.util.ArrayList;
import java.util.List;

import mo.klib.k;
import mo.klib.utils.appUtils.AppInfoUtil;
import mo.klib.utils.dataUtil.StringUtil;
import mo.klib.utils.logUtils.LogUtil;
import mo.klib.utils.tipsUtil.ToastUtil;


/**
 * @ author：mo
 * @ data：2018/10/12
 * @ 功能：跳应用市场工具类
 */
public class ToMarketUtil {
    /**
     * 检查已安装的应用商店
     * 但是小米商店目前检测不出，先定义为bug
     *
     * @return 返回包名列表
     */
    public static List<String> checkMarket() {
        List<String> packageNames = new ArrayList<>();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = k.app().getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            ActivityInfo activityInfo = infos.get(i).activityInfo;
            String packageName = activityInfo.packageName;
            LogUtil.i("packageName : " + packageName);
            packageNames.add(packageName);
        }
        return packageNames;
    }

    /**
     * 跳转应用商店
     *
     * @param marketPkg 应用商店包名  null==跳默认市场，如果装有多个市场，会弹列表选择，但是 实测装应用宝和华为市场时，默认跳转了应用宝
     * @return
     */
    public static void toMarket(String marketPkg) {
        Uri uri = Uri.parse("market://details?id=" + AppInfoUtil.getAppPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 如果没给市场的包名，则系统会弹出市场的列表让你进行选择。
        if (!StringUtil.isEmpty(marketPkg)) {
            intent.setPackage(marketPkg);
        }
        try {
            k.app().startActivity(intent);
        } catch (Exception ex) {
            LogUtil.i("提示没有安装应用市场");
            ToastUtil.showToast("提示没有安装应用市场");
            ex.printStackTrace();
        }
    }

    /**
     * 跳转三星应用商店
     *
     * @return
     */
    public static void goToSamsungMarket() {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + AppInfoUtil.getAppPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.sec.android.app.samsungapps");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            k.app().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接跳转至应用宝
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toQQDownload() {
        toMarket("com.tencent.android.qqdownloader");
    }

    /**
     * 直接跳转至360手机助手
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void to360Download() {

        toMarket("com.qihoo.appstore");
    }

    /**
     * 直接跳转至豌豆荚
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toWandoujia() {
        toMarket("com.wandoujia.phoenix2");
    }

    /**
     * 直接跳转至小米应用商店
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toXiaoMi() {
        toMarket("com.xiaomi.market");
    }

    /**
     * 直接跳转至魅族应用商店
     *
     * @return {@code true} 跳转成功 <br> {@code false} 跳转失败
     */
    public static void toMeizu() {
        toMarket("com.meizu.mstore");
    }
}
