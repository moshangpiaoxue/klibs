package mo.klib.utils.logUtils;

import android.os.Build;
import android.util.Log;

import mo.klib.k;
import mo.klib.utils.appUtils.AppInfoUtil;


/**
 * @ author：mo
 * @ data：2017/11/22 0022
 * @ 功能：LogUtil辅助类
 */

public class LogUtilHelp {
    private static String customTagPrefix = "mo";
    private static final int MAX_LENGTH = 4000;
    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    public static void printDefault(int type, String msg) {

        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                printSub(type, sub);
                index += MAX_LENGTH;
            }
            printSub(type, msg.substring(index, length));
        } else {
            printSub(type, msg);
        }
    }

    private static void printSub(int type, String sub) {
        switch (type) {
            case V:
                Log.v(customTagPrefix, "║" + sub);
                break;
            case D:
                Log.d(customTagPrefix, "║" + sub);
                break;
            case I:
                Log.i(customTagPrefix, "║" + sub);
                break;
            case W:
                Log.w(customTagPrefix, "║" + sub);
                break;
            case E:
                Log.e(customTagPrefix, "║" + sub);
                break;
            case A:
                Log.wtf(customTagPrefix, "║" + sub);
                break;
            default:
                break;
        }
    }

    public static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        return "手机型号：" + Build.BRAND +
                "\n║app名称：" + AppInfoUtil.getAppName() +
                "\n║当前版本：" + AppInfoUtil.getAppVersionName() +
                "\n║输出类名：" + callerClazzName +
                "\n║方 法 名：" + caller.getMethodName() +
                "\n║行    号：" + caller.getLineNumber();
    }

    public static void log(int type, String str, String tag) {
        if (!k.isDebug()) {
            return;
        }
        Log.i(customTagPrefix, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        printDefault(type, "                                                   飞哥告诉你，这是log！不谢 ^_^ ");
        printDefault(type, tag);
        printDefault(type, "输出内容：" + str);
        Log.i(customTagPrefix, "╚═══════════════════════════════════════════════════════════════════════════════════════");
    }

}
