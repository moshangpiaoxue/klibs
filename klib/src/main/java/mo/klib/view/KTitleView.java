package mo.klib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mo.klib.R;
import mo.klib.modle.listener.clickListener.KNoDoubleClickListener;
import mo.klib.utils.dataUtil.StringUtil;


/**
 * @ author：mo
 * @ data：2018/12/18
 * @ 功能：
 */
public class KTitleView extends FrameLayout {
    private TitleBarClickListener listener;
    private RelativeLayout title;
    private LinearLayout left;
    private TextView midle;
    private TextView right;

    public void setListener(TitleBarClickListener listener) {
        this.listener = listener;
    }

    public KTitleView(@NonNull Context context) {
        super(context, null);
    }

    public KTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this, true);
        title = (RelativeLayout) findViewById(R.id.rl_title);
        left = (LinearLayout) findViewById(R.id.ll_title_left);
        midle = (TextView) findViewById(R.id.tv_title_midle);
        right = (TextView) findViewById(R.id.tv_title_right);
        //左侧监听
        left.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.leftClick(v);
                }

            }
        });
        //中间监听
        midle.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.midleClick(v);
                }

            }
        });
        //右侧监听
        right.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.rightClick(v);
                }

            }
        });
    }

    public void setHindView() {
        title.setVisibility(GONE);
    }

    public void setShowView() {
        title.setVisibility(VISIBLE);
    }

    public void setLeftHind() {
        left.setVisibility(GONE);
    }

    /**
     * 设置中间文字 String
     *
     * @param midleText
     */
    public void setMidleText(String midleText) {
        midle.setText(midleText);
    }

    public void setRightText(String text) {
        if (StringUtil.isEmpty(text)) {
            right.setText("");
            right.setVisibility(INVISIBLE);
        } else {
            right.setText(text);
            right.setVisibility(VISIBLE);
        }
    }

    /**
     * 监听接口
     */
    private interface TitleBarClickListener {
        void leftClick(View v);

        void midleClick(View v);

        void rightClick(View v);
    }

    /**
     * 抽象监听方法
     */
    public static abstract class KTitleBarClickListenerImpl implements TitleBarClickListener {
        @Override
        public void leftClick(View v) {

        }

        @Override
        public void midleClick(View v) {

        }

        @Override
        public void rightClick(View v) {

        }
    }
}
