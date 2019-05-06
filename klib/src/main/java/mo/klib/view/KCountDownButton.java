package mo.klib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import mo.klib.utils.dataUtil.date.KCountDownTimer;
import mo.klib.utils.viewUtil.click.OnMultiClickListener;

/**
 * @ author：mo
 * @ data：2019/5/5：16:15
 * @ 功能：倒计时button
 */
@SuppressLint("AppCompatCustomView")
public class KCountDownButton extends Button {
    /**
     * 倒计时
     */
    private long mCountTime = 60 * 1000;
    /**
     * 间隔时间
     */
    private long mDelayTime = 1000;
    /**
     * 默认初始化时的文本
     */
    private String mStartText = "获取验证码";
    /**
     * 默认倒计时结束时的文本
     */
    private String mFinishText = "重新" + mStartText;
    private KCountDownTimer kCountDownTimer;

    public KCountDownButton(Context context) {
        super(context, null);
        initView();
    }

    public KCountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initView();
    }

    public KCountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(getText())) {
            mStartText = getText().toString().trim();
        }
        this.mFinishText = "重新" + mStartText;
        this.setText(mStartText);
        this.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                actionCountDrwon();
            }
        });
    }

    /**
     * 开启倒计时
     */
    private void actionCountDrwon() {
        if (kCountDownTimer == null) {
            kCountDownTimer = new KCountDownTimer(mCountTime, mDelayTime) {
                @Override
                protected void onTicks(long millisUntilFinished, int second) {
                    KCountDownButton.this.setText(second + "秒");
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    KCountDownButton.this.setEnabled(true);
                    KCountDownButton.this.setText(mFinishText);
                }
            };
        }
        kCountDownTimer.start();
        this.setEnabled(false);
    }

    /**
     * 载体也就是activity或fragment销毁的时候停止
     */
    @Override
    protected void onDetachedFromWindow() {
        kCountDownTimer.cancel();
        super.onDetachedFromWindow();
    }

    /**
     * 主动停止
     */
    public void stop() {
        kCountDownTimer.cancel();
    }

    /**
     * 设置开始文本
     */
    public void setStartText(String mStartText) {
        this.mStartText = mStartText;
        this.mFinishText = "重新" + mStartText;
        this.setText(mStartText);
    }

    /**
     * 设置结束文本 （如果调用此方法，在setStartText（）之后，因为默认结束文本是在开始文本前加上“重新”）
     */
    public void setFinishText(String mFinishText) {
        this.mFinishText = mFinishText;
    }

    /**
     * 设置倒计时时间
     */
    public void setCountTimeLong(long mCountTime) {
        this.mCountTime = mCountTime;
    }

    /**
     * 设置间隔时间
     */
    public void setDelayTimeLong(long mDelayTime) {
        this.mDelayTime = mDelayTime;
    }

    public void setCountTimeInt(int mCountTime) {
        this.mCountTime = mCountTime * 1000;
    }

    public void setDelayTimeInt(int mDelayTime) {
        this.mDelayTime = mDelayTime * 1000;
    }
}