package mo.klib.utils.dataUtil.dealUtil;

import android.util.TypedValue;

import mo.klib.utils.systemUtils.ScreenUtil;


/**
 * 单位转换工具类
 * Created by OSen on 2016/4/25 15:00.
 */
public class DensityUtil {


    private DensityUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("DensityUtil cannot be instantiated");
    }


    /**
     * dp转px
     *
     * @param dpVal
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, ScreenUtil.getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal, ScreenUtil.getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal
     * @return
     */
    public static float px2dp(float pxVal) {
        return (pxVal / ScreenUtil.getDisplayMetrics().density);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / ScreenUtil.getDisplayMetrics().scaledDensity);
    }
}
