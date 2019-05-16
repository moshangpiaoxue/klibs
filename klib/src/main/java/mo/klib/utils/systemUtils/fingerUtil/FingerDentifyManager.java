package mo.klib.utils.systemUtils.fingerUtil;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;

/**
 * @ author：mo
 * @ data：2019/5/15：16:22
 * @ 功能：指纹识别管理器
 */
public class FingerDentifyManager {

    private FingerImpl mImpl;
    private Activity mActivity;

    /**
     * 单例，一会换枚举
     */
    public static FingerDentifyManager from(Activity activity) {
        return new FingerDentifyManager(activity);
    }

    /**
     * 构造器
     */
    public FingerDentifyManager(Activity activity) {
        mActivity = activity;
        if (isAboveApi28()) {
            mImpl = new FingerPromptApi28(activity);
        } else if (isAboveApi23()) {
            mImpl = new FingerPromptApi23(activity);
        }
    }

    /**
     * 判断版本8.0
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

    /**
     * 开始验证指纹
     */
    public void authenticate(@NonNull OnBiometricIdentifyCallback callback) {
        mImpl.authenticate(new CancellationSignal(), callback);
    }

    /**
     * 开始验证指纹
     */
    public void authenticate(@NonNull CancellationSignal cancel,
                             @NonNull OnBiometricIdentifyCallback callback) {
        mImpl.authenticate(cancel, callback);
    }


    /**
     * 判断用户是否设置了指纹
     */
    public boolean hasEnrolledFingerprints() {
        if (isAboveApi28()) {
            //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
            final FingerprintManager manager = mActivity.getSystemService(FingerprintManager.class);
            return manager != null && manager.hasEnrolledFingerprints();
        } else if (isAboveApi23()) {
            return ((FingerPromptApi23) mImpl).hasEnrolledFingerprints();
        } else {
            return false;
        }
    }

    /**
     * 当前设备是否支持指纹
     */
    public boolean isHardwareDetected() {
        if (isAboveApi28()) {
            //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
            final FingerprintManager fm = mActivity.getSystemService(FingerprintManager.class);
            return fm != null && fm.isHardwareDetected();
        } else if (isAboveApi23()) {
            return ((FingerPromptApi23) mImpl).isHardwareDetected();
        } else {
            return false;
        }
    }

    /**
     * 有没有锁屏密码
     */
    public boolean isKeyguardSecure() {
        KeyguardManager keyguardManager = (KeyguardManager) mActivity.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            return true;
        }
        return false;
    }

    /**
     * 是否可以开启指纹验证 （版本大于6.0+设备支持指纹+用户设置过指纹+用户设置了锁屏密码）
     */
    public boolean isBiometricPromptEnable() {
        return isAboveApi23()
                && isHardwareDetected()
                && hasEnrolledFingerprints()
                && isKeyguardSecure();
    }

}
