package mo.klib.view;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import java.util.List;
import mo.klib.R;
import mo.klib.k;
import mo.klib.modle.manager.KInputMethodManager;

/**
 * @ author：mo
 * @ data：2019/3/13:15:27
 * @ 功能：自定义车牌输入键盘
 * 使用：
 * keyboard_view1.setEditText(mActivity, et_input_charge_number);//必须先写
 * keyboard_view1.showKeyboard();
 */
public class CarNumKeyboardView extends RelativeLayout {
    private Activity activity;
    private KeyboardView keyboardView;
    private Keyboard province_keyboard;// 字母键盘
    private Keyboard number_keyboar;// 数字键盘
    public boolean isnun = true;// 是否数据键盘
    public boolean isupper = false;// 是否大写
    /**
     * 判定是否是中文的正则表达式 [\\u4e00-\\u9fa5]判断一个中文 [\\u4e00-\\u9fa5]+多个中文
     */
    private String reg = "[\\u4e00-\\u9fa5]";
    private EditText editText;

    public CarNumKeyboardView(Context context) {
        super(context, null);
    }


    public CarNumKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.car_num_keyboard_view, this, true);
        province_keyboard = new Keyboard(k.app(), R.xml.province_abbreviation);
        number_keyboar = new Keyboard(k.app(), R.xml.number_or_letters);

    }

    public void setEditText(Activity activity, EditText editText) {
        this.activity = activity;
        this.editText = editText;
        keyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(province_keyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(listener);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                keyboardView.setKeyboard(province_keyboard);

            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换
                if (!isnun) {
                    isnun = false;
                    keyboardView.setKeyboard(province_keyboard);
                } else {
                    isnun = true;
                    keyboardView.setKeyboard(number_keyboar);
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
                // 判断第一个字符是否是中文,是，则自动切换到数字软键盘
                if (editText.getText().toString().matches(reg)) {
                    changeKeyboard(true);
                }
            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Keyboard.Key> keylist = province_keyboard.getKeys();
        if (isupper) {//大写切换小写
            isupper = false;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {//小写切换大写
            isupper = true;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
        KInputMethodManager.INSTANCE.hideSoftInputMethod(activity, editText);
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }

    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }

    /**
     * 指定切换软键盘 isnumber false表示要切换为省份简称软键盘 true表示要切换为数字软键盘
     */
    public void changeKeyboard(boolean isnumber) {
        if (isnumber) {
            keyboardView.setKeyboard(number_keyboar);
        } else {
            keyboardView.setKeyboard(province_keyboard);
        }
    }

}
