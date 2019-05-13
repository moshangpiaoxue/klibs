package mo.klib.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import mo.klib.R;
import mo.klib.utils.dataUtil.StringUtil;

/**
 * @ author：mo
 * @ data：2019/5/13:9:05
 * @ 功能：
 */
public enum HttpDialogLoading {
    /**
     * 单例
     */
    INSTANCE;

    private BaseDialog loadingDialog;

    public void show(Activity mActivity, final String tips) {
        if (loadingDialog == null) {
            loadingDialog = new BaseDialog(mActivity) {
                @Override
                protected int getLayoutId() {
                    return R.layout.dialog_loading;
                }

                @Override
                protected void doWhat(Dialog dialog, View view) {

                    TextView tv_dialog_loading = view.findViewById(R.id.tv_dialog_loading);
                    if (StringUtil.isEmpty(tips)) {
                        tv_dialog_loading.setVisibility(View.GONE);
                    } else {
                        tv_dialog_loading.setVisibility(View.VISIBLE);
                        tv_dialog_loading.setText(tips);
                    }
                }
            };
        }
        if (!mActivity.isFinishing()) {
            loadingDialog.show();
        }
    }

    public void dismiss() {
        loadingDialog.dismiss();
        loadingDialog = null;
    }
}
