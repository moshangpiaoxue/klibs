package mo.klib.utils.systemUtils.fingerUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import mo.klib.R;


/**
 * @ author：mo
 * @ data：2019/5/16：13:51
 * @ 功能：指纹验证dialog
 */
public class FingerDentifyDialog extends DialogFragment {

    public static final int STATE_NORMAL = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_SUCCEED = 4;
    private TextView mStateTv;
    private TextView mCancelBtn;
    private Activity mActivity;
    private OnBiometricPromptDialogActionCallback mDialogActionCallback;

    public interface OnBiometricPromptDialogActionCallback {
        void onDialogDismiss();
        void onCancel();
    }

    public static FingerDentifyDialog newInstance() {
        FingerDentifyDialog dialog = new FingerDentifyDialog();
        return dialog;
    }

    public void setOnBiometricPromptDialogActionCallback(OnBiometricPromptDialogActionCallback callback) {
        mDialogActionCallback = callback;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupWindow(getDialog().getWindow());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_biometric_prompt_dialog, container);
        mStateTv = view.findViewById(R.id.state_tv);
        mCancelBtn = view.findViewById(R.id.cancel_btn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialogActionCallback != null) {
                    mDialogActionCallback.onCancel();
                }
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
        }
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogActionCallback != null) {
            mDialogActionCallback.onDialogDismiss();
        }
    }

    private void setupWindow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.dimAmount = 0;
            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public void setState(int state) {
        switch (state) {
            case STATE_NORMAL:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity, R.color.text_quaternary));
                mStateTv.setText("请验证指纹");
                mCancelBtn.setVisibility(View.VISIBLE);
                break;
            case STATE_FAILED:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity, R.color.text_red));
                mStateTv.setText("验证失败,请重新验证");
                mCancelBtn.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity, R.color.text_red));
                mStateTv.setText("验证失败,请稍后重试");
                mCancelBtn.setVisibility(View.GONE);
                break;
            case STATE_SUCCEED:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity, R.color.text_green));
                mStateTv.setText("验证成功");
                mCancelBtn.setVisibility(View.VISIBLE);
                mStateTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 500);
                break;
            default:
                break;
        }
    }

}
