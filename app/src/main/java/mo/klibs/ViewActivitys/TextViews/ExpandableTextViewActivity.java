package mo.klibs.ViewActivitys.TextViews;

import android.view.View;

import mo.klib.ui.activity.KBaseLayoutActivity;
import mo.klibs.R;

/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：展开/收缩TextView
 */
public class ExpandableTextViewActivity extends KBaseLayoutActivity {

    @Override
    protected int getMainLayoutId() {
        return R.layout.act_view_textview_corner;
    }


    @Override
    protected void getData() {

    }


    @Override
    protected void initViews(View mainView) {
        title.setMidleText("展开/收缩TextView");
        loadSuccess();
    }
}
