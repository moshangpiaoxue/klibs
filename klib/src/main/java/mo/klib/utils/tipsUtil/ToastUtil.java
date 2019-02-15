package mo.klib.utils.tipsUtil;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mo.klib.R;
import mo.klib.k;
import mo.klib.utils.viewUtil.ViewUtil;


/**
 * @ author：mo
 * @ data：2018/10/12
 * @ 功能：
 */
public class ToastUtil {

    public static void showToast(final String msg) {
        //如果是主线程，直接弹出toast
        if (TextUtils.equals("main",Thread.currentThread().getName())) {
            Toast.makeText(k.app(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(final Activity ctx, final String msg) {
        if (ctx != null) {
            //如果是主线程，直接弹出toast
            if ("main".equals(Thread.currentThread().getName())) {
                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
            } else {
                //如果不是主线程，则调用context中 runOnUIThread方法弹出toast
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    public static void showToastIos(String msg) {
        Toast toast = new Toast(k.app());
        View layout = ViewUtil.getView(k.app(), R.layout.tost_ios, null);
        layout.setAlpha(0.7f);
        toast.setView(layout);
        TextView tv_ios_toast = layout.findViewById(R.id.tv_ios_toast);
        tv_ios_toast.setText("  " + msg + "  ");
        toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }


}
