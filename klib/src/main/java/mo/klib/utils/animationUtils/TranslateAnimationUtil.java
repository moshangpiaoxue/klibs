package mo.klib.utils.animationUtils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import mo.klib.modle.listener.KAnimatorListener;


/**
 * description: 位移动画工具类
 * autour: mo
 * date: 2017/9/20 0020 14:46
 */
public class TranslateAnimationUtil {
    /**
     * 默认动画持续时间
     */
    public static int defultDuration = 500;
    /**
     * 从右到左出现
     */
    public static TranslateAnimation showAnim = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);
    /**
     * 从左到右隐藏
     */
    public static TranslateAnimation hideAnim = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);
    /**
     * 以自身为基点，从自身的正常位置移动到顶点,向上移动一个身位
     */
    public static TranslateAnimation self2Top = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f);
    /**
     * 以自身为基点，从自身的顶点移动到正常位置
     */
    public static TranslateAnimation top2Self = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);
    /**
     * 以自身为基点，从自身的正常位置移动到底部,向下移动一个身位
     */
    public static TranslateAnimation self2Bottom = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f);
    /**
     * 以自身为基点，从自身的底部移动到正常位置
     */
    public static TranslateAnimation bottom2Self = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);

    /**
     * 抛物线动画-同时旋转
     *
     * @param view       view
     * @param starX      起始X轴坐标
     * @param endX       结束X轴坐标
     * @param starY      起始Y轴坐标
     * @param endY       结束Y轴坐标
     * @param viewWidthX view的宽度，X轴的偏移量
     * @param viewhightY view的高度 Y轴的偏移量
     */
    public static void get(View view, int starX, int endX, int starY, int endY, int viewWidthX, int viewhightY) {
        //抛物线动画
        ObjectAnimator translateAnimationX = ObjectAnimator.ofFloat(view, "translationX", 0, -(starX - endX) - viewWidthX);
        translateAnimationX.setDuration(1500);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        ObjectAnimator translateAnimationY = ObjectAnimator.ofFloat(view, "translationY", 0, endY - starY + viewhightY);
        translateAnimationY.setDuration(1500);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 720f);

        // 动画的持续时间，执行多久？
        rotation.setDuration(1500);
        //缩小动画
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0);
//        scaleX.setDuration(200);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0);
//        scaleY.setDuration(200);
//        scaleY.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                anim_mask_layout.removeView(readingIV);
////                setLvTouch(activity, false);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateAnimationX).with(translateAnimationY).with(rotation);
//        animatorSet.play(scaleX).with(scaleY).after(translateAnimationX);
        animatorSet.start();
    }

    public static TranslateAnimation getTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(defultDuration);
        return translateAnimation;
    }

    /**
     * 给位移动画添加监听和持续时间
     *
     * @param animation 原始动画
     * @param callBack  监听回调
     * @param duration  持续时间
     * @return
     */
    public static TranslateAnimation getTranslateAnimation(TranslateAnimation animation,
                                                           int duration, final KAnimatorListener callBack) {
        if (callBack != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    callBack.onAnimationStart(animation);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    callBack.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    callBack.onAnimationRepeat(animation);
                }
            });
        }
        animation.setDuration(duration);
        return animation;
    }


    /**
     * 从控件所在位置移动到控件顶部
     *
     * @return
     */
    public static TranslateAnimation moveToViewSelfToTop(TranslateAnimation animation) {
        return getTranslateAnimation(animation, defultDuration, null);
    }

    public static TranslateAnimation moveToViewSelfToTop(TranslateAnimation animation, int duration) {
        return getTranslateAnimation(animation, duration, null);
    }

    public static TranslateAnimation moveToViewSelfToTop(TranslateAnimation animation, final KAnimatorListener callBack) {
        return getTranslateAnimation(animation, defultDuration, callBack);
    }

    public static TranslateAnimation moveToViewSelfToTop(TranslateAnimation animation, int duration, final KAnimatorListener callBack) {
        return getTranslateAnimation(animation, duration, callBack);
    }


}
