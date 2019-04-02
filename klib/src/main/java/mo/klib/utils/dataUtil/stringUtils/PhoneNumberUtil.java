package mo.klib.utils.dataUtil.stringUtils;

import mo.klib.modle.constants.RegexConstants;

/**
 * @ author：mo
 * @ data：2019/4/2:13:57
 * @ 功能：
 */
public class PhoneNumberUtil {
    /**
     * 是否手机号
     */
    public static boolean isPhone(String mobile_phone) {
        return RegexConstants.isRegex(mobile_phone, RegexConstants.RegexTel);
    }

    /**
     * 隐藏手机号中间几位
     *
     * @param phone   完整手机号
     * @param postion 从第几位开始隐藏
     * @param num     一共隐藏几位
     * @return 130****0000
     */
    public static String hidePhone(String phone, int postion, int num) {
        if (!isPhone(phone)) {
            return "手机号码不正确";
        }
        return phone.substring(0, postion - 1) + "****" + phone.substring(postion - 1 + num, phone.length());
    }

}
