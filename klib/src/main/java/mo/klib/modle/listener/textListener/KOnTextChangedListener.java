package mo.klib.modle.listener.textListener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * author：mo
 * data：2017/11/13 0013
 * 功能：文本变化监听
 */

public class KOnTextChangedListener implements TextWatcher {
    /**
     * @param charSequence 当前TextView内部的mText成员变量，实际上就是当前显示的文本
     * @param start        需要改变的文字区域的起点，即选中的文本区域的起始点
     * @param count        需要改变的文字的字符数目，即选中的文本区域的字符的数目
     * @param after        替换的文字的字符数目
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    /**
     * @param charSequence 当前TextView内部的mText成员变量，此时的mText已经被修改过了，但此时mText所表示的文本还没有被显示到UI组件上
     * @param start        改变的文字区域的起点;
     * @param before       改变的文字区域在改变前的旧的文本长度，即选中文字区域的文本长度
     * @param count        改变的文字区域在修改后的新的文本长度
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    /**
     * @param editable mText
     */
    @Override
    public void afterTextChanged(Editable editable) {

    }
}
