package mo.klib.utils.animationUtils;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import static mo.klib.utils.animationUtils.AnimationUtil.DEFAULT_ANIMATION_DURATION;


/**
 * description: 缩放动画工具类
 * autour: mo
 * date: 2017/9/20 0020 14:41
 */
public class ScaleAnimationUtil {
//////////////////放大动画////////////////////////////////////

    /**
     * 获取一个放大动画
     *
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation() {
        return getAmplificationAnimation(DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * 获取一个放大动画
     *
     * @param animationListener 监听
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(Animation.AnimationListener animationListener) {
        return getAmplificationAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个放大动画
     *
     * @param durationMillis 时间
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis) {
        return getAmplificationAnimation(durationMillis, null);
    }

    /**
     * 获取一个放大动画
     *
     * @param durationMillis    时间
     * @param animationListener 监听
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
                1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }


    //////////////////缩小动画////////////////////////////////////

    /**
     * 获取一个缩小动画
     *
     * @return 返回一个缩小的动画
     */
    public static ScaleAnimation getLessenScaleAnimation() {
        return getLessenScaleAnimation(DEFAULT_ANIMATION_DURATION, null);
    }

    /**
     * 获取一个缩小动画
     *
     * @param animationListener 监听
     * @return 返回一个缩小的动画
     */
    public static ScaleAnimation getLessenScaleAnimation(Animation.AnimationListener animationListener) {
        return getLessenScaleAnimation(DEFAULT_ANIMATION_DURATION, animationListener);
    }

    /**
     * 获取一个缩小动画
     *
     * @param durationMillis 时间
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis) {
        return getLessenScaleAnimation(durationMillis, null);
    }

    /**
     * 获取一个缩小动画
     *
     * @param durationMillis    时间
     * @param animationListener 监听
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                0.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }


}
