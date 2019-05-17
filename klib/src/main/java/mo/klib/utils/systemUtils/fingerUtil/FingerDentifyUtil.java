package mo.klib.utils.systemUtils.fingerUtil;

import android.os.Build;

/**
 * @ author：mo
 * @ data：2019/5/17:13:50
 * @ 功能：
 */
public enum FingerDentifyUtil {
    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 判断版本9.0
     */
    private boolean isAboveApi28() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 判断版本6.0
     */
    private boolean isAboveApi23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


}
