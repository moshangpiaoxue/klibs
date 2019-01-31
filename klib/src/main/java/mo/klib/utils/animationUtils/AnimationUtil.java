package mo.klib.utils.animationUtils;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

import mo.klib.R;
import mo.klib.k;


/**
 * description: 动画工具类
 * ValueAnimator： 数值动画
 * ObjectAnimator：继承ValueAnimator，可对view设置
 * autour: mo
 * date: 2017/9/20 0020 11:34
*/
public class AnimationUtil {
    /**
     * 默认动画持续时间
     */
    public static final long DEFAULT_ANIMATION_DURATION = 400;

    /**
     * 闪屏页渐变动画
     */
    public static Animation splashAlpha = AnimationUtils.loadAnimation(k.app(), R.anim.alpha_splash);
    /**
     * 点击渐变动画
     */
    public static Animation clickAlpha = AnimationUtils.loadAnimation(k.app(), R.anim.alpha_click);
    /**
     * 设置点击动画
     *
     * @param view
     */
    public static void setClickAnimation(View view) {
        setAnimation(view, clickAlpha, null);
    }

    /**
     * 设置动画
     *
     * @param mView      view
     * @param mAnimation 动画
     * @param mListener  监听
     */
    public static void setAnimation(View mView, Animation mAnimation, Animation.AnimationListener mListener) {
        if (mView == null) {
            return;
        }
        if (mAnimation == null) {
            return;
        }
        if (mListener != null) {
            mAnimation.setAnimationListener(mListener);
        }
        mView.startAnimation(mAnimation);
    }

    /**
     * 数据变化监听接口
     */
    public interface onUpdateListener {
        void onUpdate(int intValue);
    }
    /**
     * 颜色渐变动画
     *
     * @param beforeColor 变化之前的颜色
     * @param afterColor  变化之后的颜色
     * @param listener    变化事件监听，数据处理在回调监听里做： textView.setTextColor(intValue); 回调值为两个颜色中间的颜色值
     */
    public static void getColorGradient(int beforeColor, int afterColor, final onUpdateListener listener) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), beforeColor, afterColor).setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                listener.onUpdate((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }
    /**
     * 缩小动画
     * @param view      view对象
     * @param scale     缩小值
     * @param dist      竖直方向上的移动距离；正值为向上，负值为向下
     */
    public static void getZoomIn(final View view, float scale, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f,
                dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(DEFAULT_ANIMATION_DURATION);
        mAnimatorSet.start();
    }
    /**
     * f放大
     *
     * @param view
     */
    public static void getZoomOut(final View view, float scale, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(DEFAULT_ANIMATION_DURATION);
        mAnimatorSet.start();
    }
    /**
     * 改变view高度动画
     *
     * @param start 起始高度
     * @param end   结束高度
     * @param view  view
     */
    public static void getChangHeight(int start, int end, final View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();//根据时间因子的变化系数进行设置高度
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);//设置高度
            }
        });
        valueAnimator.start();
    }
    /**
     * 卡片翻转动画
     *
     * @param beforeView
     * @param AfterView
     */
    public static void cardFilpAnimation(final View beforeView, final View AfterView) {
        ScaleAnimation sato0 = new ScaleAnimation(1, 0, 1, 1,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);

        final ScaleAnimation sato1 = new ScaleAnimation(0, 1, 1, 1,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        sato0.setDuration(500);
        sato1.setDuration(500);


        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (beforeView.getVisibility() == View.VISIBLE) {
                    beforeView.setAnimation(null);
                    beforeView.setVisibility(View.GONE);
                    AfterView.setVisibility(View.VISIBLE);
                    AfterView.startAnimation(sato1);
                } else {
                    AfterView.setAnimation(null);
                    beforeView.setVisibility(View.VISIBLE);
                    AfterView.setVisibility(View.GONE);
                    beforeView.startAnimation(sato1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (beforeView.getVisibility() == View.VISIBLE) {
            beforeView.startAnimation(sato0);
        } else {
            beforeView.startAnimation(sato0);
        }


//        Interpolator accelerator = new AccelerateInterpolator();
//        Interpolator decelerator = new DecelerateInterpolator();
//        if (beforeView.getVisibility() == View.GONE) {
//            // 局部layout可达到字体翻转 背景不翻转
//            invisToVis = ObjectAnimator.ofFloat(beforeView,
//                    "rotationY", -90f, 0f);
//            visToInvis = ObjectAnimator.ofFloat(AfterView,
//                    "rotationY", 0f, 90f);
//        } else if (AfterView.getVisibility() == View.GONE) {
//            invisToVis = ObjectAnimator.ofFloat(AfterView,
//                    "rotationY", -90f, 0f);
//            visToInvis = ObjectAnimator.ofFloat(beforeView,
//                    "rotationY", 0f, 90f);
//        }
//
//        visToInvis.setDuration(250);// 翻转速度
//        visToInvis.setInterpolator(accelerator);// 在动画开始的地方速率改变比较慢，然后开始加速
//        invisToVis.setDuration(250);
//        invisToVis.setInterpolator(decelerator);
//        visToInvis.addListener(new Animator.AnimatorListener() {
//
//            @Override
//            public void onAnimationEnd(Animator arg0) {
//                if (beforeView.getVisibility() == View.GONE) {
//                    AfterView.setVisibility(View.GONE);
//                    invisToVis.start();
//                    beforeView.setVisibility(View.VISIBLE);
//                } else {
//                    AfterView.setVisibility(View.GONE);
//                    visToInvis.start();
//                    beforeView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onAnimationCancel(Animator arg0) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator arg0) {
//
//            }
//
//            @Override
//            public void onAnimationStart(Animator arg0) {
//
//            }
//        });
//        visToInvis.start();
    }


    public static void FlipAnimatorXViewShow(final View oldView, final View newView, final int time) {

        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(oldView, "rotationX", 0, 90);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(newView, "rotationX", -90, 0);
        animator2.setInterpolator(new OvershootInterpolator(2.0f));

        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldView.setVisibility(View.GONE);
                animator2.setDuration(time).start();
                newView.setVisibility(View.VISIBLE);
//                if (newView.getVisibility() == View.GONE) {
//                    newView.setVisibility(View.GONE);
//                    animator1.setDuration(time).start();
//                    oldView.setVisibility(View.VISIBLE);
//                } else {
//                    newView.setVisibility(View.GONE);
//                    animator2.setDuration(time).start();
//                    oldView.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator1.setDuration(time).start();
    }


}