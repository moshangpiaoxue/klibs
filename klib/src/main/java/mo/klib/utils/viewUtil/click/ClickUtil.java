package mo.klib.utils.viewUtil.click;

/**
 * @ author：mo
 * @ data：2018/12/28
 * @ 功能：
 */
public class ClickUtil {
    /**
    *  间隔时间
    */
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    /**
    * 上次点击时间
    */
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
