package mo.klibs.ViewActivitys.TextViews;

import android.graphics.Color;
import android.view.View;

import mo.klib.ui.activity.KBaseLayoutActivity;
import mo.klib.view.textView.KSlantedTextView;
import mo.klibs.R;

/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：旋转TextView
 */
public class SlantedTextViewActivity extends KBaseLayoutActivity {

    @Override
    protected int getMainLayoutId() {
        return R.layout.act_view_textview_slantedtv;
    }


    @Override
    protected void getData() {

    }


    @Override
    protected void initViews(View mainView) {
        title.setMidleText("旋转TextView");
        loadSuccess();
    }
}
