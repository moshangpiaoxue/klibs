package mo.klib.utils.viewUtil;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

/**
 * @author：mo
 * @data：2017/11/17 0017
 * @功能：EditText工具类
 */

public class EditTextUtil {
    public interface InputListener {
        void onInputAfter(Boolean allComplete);
    }

    /**
     * 密码模式切换
     *
     * @param mEditText
     * @param isShow
     */
    public static void changPwStatue(EditText mEditText, Boolean isShow) {
        //第一种方法
        if (isShow) {
            //选择状态 显示明文--设置为可见的密码
            mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        //第二种方法
        if (isShow) {
            mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * 监听若干个输入框，是否全部输入了数据
     * @param inputListener 监听接口回调
     * @param mEditTexts    edittext数组
     */
    public static void setInpListener(final InputListener inputListener, final EditText... mEditTexts) {
        for (int i = 0; i < mEditTexts.length; i++) {
            mEditTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) {
                        inputListener.onInputAfter(false);
                        return;
                    }

                    boolean tag = false;

                    for (int i = 0; i < mEditTexts.length; i++) {
                        if (mEditTexts[i].getText().length() != 0) {
                            tag = true;
                        } else {
                            tag = false;
                            break;
                        }
                    }
                    inputListener.onInputAfter(tag);

                }
            });
        }

    }

    /**
     * Edittext 输入时保留小数点后位数
     *
     * @param editText
     * @param count    位数 count=0表示不输入小数类型
     */
    public static void setEdDecimal(EditText editText, int count) {
        if (count < 1) {
            //整数型
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            //小数点型、整数型
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);

            //设置字符过滤
            final int finalCount = count + 1;
            editText.setFilters(new InputFilter[]{new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (source.equals(".") && dest.toString().length() == 0) {
                        return "0.";
                    }
                    if (dest.toString().contains(".")) {
                        int index = dest.toString().indexOf(".");
                        int mlength = dest.toString().substring(index).length();
                        if (mlength == finalCount) {
                            return "";
                        }
                    }
                    return null;
                }
            }});
        }
    }

    public static void initEditNumberPrefix(final EditText edSerialNumber, final int number) {
        edSerialNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String s = edSerialNumber.getText().toString();
                    String temp = "";
                    for (int i = s.length(); i < number; i++) {
                        s = "0" + s;
                    }

                    for (int i = 0; i < number; i++) {
                        temp += "0";
                    }
                    if (s.equals(temp)) {
                        s = temp.substring(1) + "1";
                    }
                    edSerialNumber.setText(s);
                }
            }
        });
    }
}
