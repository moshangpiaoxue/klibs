package mo.klib.utils.dataUtil.dealUtil;

import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.util.TypedValue;

import mo.klib.R;
import mo.klib.k;


/**
 * @ author：mo
 * @ data：2018/12/12
 * @ 功能：
 */
public class ColorUtils {
    /**
     * 根据透明度计算颜色值
     *
     * @param color color值
     * @param alpha alpha值 -1=半透明
     * @return
     */
    public static int getColor(@ColorInt int color, @IntRange(from = 0,to = 255) int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
    /**
     * 获取主题颜色
     * @return
     */
    public static int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        k.app().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

}
