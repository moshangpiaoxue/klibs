package mo.klib.utils.animationUtils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @ author：mo
 * @ data：2019/4/19:17:43
 * @ 功能：属性动画
 * https://blog.csdn.net/qq_40881680/article/details/82318363
 */
public class ObjectAnimatorUtil {
    /**
     * 渐变
     *
     * @param view       view
     * @param alphaStart 开始时的透明状态( 1f 为不透明，0f 为完全透明，取值 0f ~ 1f )
     * @param alphaEnd   结束时的透明状态( 1f 为不透明，0f 为完全透明，取值 0f ~ 1f )
     * @param duration   设置动画持续时间，单位:毫秒ms
     */
    public static void alpah(View view, float alphaStart, float alphaEnd, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", alphaStart, alphaEnd);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
    public static void set(View view){
        ObjectAnimator    objectAnimator1 = ObjectAnimator.ofFloat(view,"scaleY",0f);
        ObjectAnimator  objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",0f);
        ObjectAnimator  objectAnimator3 = ObjectAnimator.ofFloat(view,"alpha",1f,0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }
    public static void set2(View view){
        ObjectAnimator    objectAnimator1 = ObjectAnimator.ofFloat(view,"scaleY",1f);
        ObjectAnimator  objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",1f);
        ObjectAnimator  objectAnimator3 = ObjectAnimator.ofFloat(view,"alpha",0f,1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }
}
