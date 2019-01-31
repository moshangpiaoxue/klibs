package mo.klib.utils.dataUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mo.klib.modle.constants.ConstUtil;
import mo.klib.utils.tipsUtil.ToastUtil;

import static mo.klib.modle.constants.KConstans.DAY;
import static mo.klib.modle.constants.KConstans.HOUR;
import static mo.klib.modle.constants.KConstans.MIN;
import static mo.klib.modle.constants.KConstans.MSEC;
import static mo.klib.modle.constants.KConstans.SEC;


/**
 * description: 时间格式的转换工具类
 * autour: mo
 * date: 2017/11/15 0015 11:08
 * format 格式 如 "yyyy-MM-dd HH:mm:ss"   或   yyyy年MM月dd日   yyyy是完整的公元年，MM是月份，dd是日期
 * 备注:获取12小时制的时间要把HH换成小写hh，默认是大写的24小时制的
 * G：年代标识，表示是公元前还是公元后
 * y：年份
 * M：月份
 * d：日
 * h：小时，从1到12，分上下午
 * H：小时，从0到23
 * m：分钟
 * s：秒
 * S：毫秒
 * E：一周中的第几天，对应星期几，第一天为星期日，于此类推
 * z：时区
 * D：一年中的第几天
 * F：这一天所对应的星期几在该月中是第几次出现
 * w：一年中的第几个星期
 * W：一个月中的第几个星期
 * a：上午/下午标识
 * k：小时，从1到24
 * K：小时，从0到11，区分上下午
 * 输入的个数就是现实的位数
 */
public class DateUtil {
    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link #}: 毫秒</li>
     *                     <li>{@link # }: 秒</li>
     *                     <li>{@link # }: 分</li>
     *                     <li>{@link #}: 小时</li>
     *                     <li>{@link # }: 天</li>
     *                     </ul>
     * @return unit时间戳
     */
    private static long MS2Unit(long milliseconds, ConstUtil.TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
            default:
                break;
        }
        return -1;
    }

    /**
     * unit时间戳转换毫秒时间戳（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link #}: 毫秒</li>
     *                     <li>{@link # }: 秒</li>
     *                     <li>{@link # }: 分</li>
     *                     <li>{@link #}: 小时</li>
     *                     <li>{@link # }: 天</li>
     *                     </ul>
     * @return unit时间戳
     */
    private static long Unit2MS(long milliseconds, ConstUtil.TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds * MSEC;
            case SEC:
                return milliseconds * SEC;
            case MIN:
                return milliseconds * MIN;
            case HOUR:
                return milliseconds * HOUR;
            case DAY:
                return milliseconds * DAY;
            default:
                break;
        }
        return -1;
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取当前秒级时间戳
     *
     * @return
     */
    public static long getNowM() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间默认时间格式
     *
     * @return
     */
    public static String getNowString() {
        return MS2String(getNowMS(), getSDF());
    }

    /**
     * 时间戳-->>时间字符串
     *
     * @param milliseconds 时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String MS2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(getMS(milliseconds)));
    }

/////////////////////////////////////////////////// get方法//////////////////////////////////////////////////////////////////

    /**
     * 获取当前毫秒级时间戳
     *
     * @return
     */
    public static long getNowMS() {
        return System.currentTimeMillis();
    }

    /**
     * 默认时间格式
     */
    public static SimpleDateFormat getSDF() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    /**
     * 检验时间戳，默认输出毫秒级时间戳，如果格式不对，输出当前毫秒级时间戳
     *
     * @param time
     * @return
     */
    public static long getMS(long time) {
        int size = LongUtil.getLongSize(time);
        if (size == 10) {
            return time * 1000;
        } else if (size == 13) {
            return time;
        } else {
            return getNowMS();
        }
    }

    /**
     * 获取当前时间自定义时间格式
     *
     * @param formatStr 自定义时间格式
     * @return
     */
    public static String getNowString(String formatStr) {
        return MS2String(getNowMS(), getSDF(formatStr));
    }

    /**
     * 自定义时间格式
     *
     * @param formatStr format 格式 如 " yyyy-MM-dd HH:mm:ss "   或   yyyy年MM月dd日   yyyy是完整的公元年，MM是月份，dd是日期， 备注:获取12小时制的时间要把HH换成小写hh，默认是大写的24小时制的
     * @return
     */
    public static SimpleDateFormat getSDF(String formatStr) {
        if (isDateFormat(formatStr)) {
            return new SimpleDateFormat(formatStr, Locale.getDefault());
        } else {
            return getSDF();
        }
    }

    /**
     * 判断时间格式是否正确
     *
     * @param formatStr
     * @return
     */
    public static Boolean isDateFormat(String formatStr) {
        Boolean boo = false;
        try {
            new SimpleDateFormat(formatStr);
            boo = true;
        } catch (Exception e) {
            boo = false;
            ToastUtil.showToast("格式不正确，使用默认格式！");
        }
        return boo;
    }

    /**
     * 获取当前时间
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getNowData() {
        return new Date();
    }


    /**
     * 根据时间戳获取 Calendar类
     *
     * @param time 时间戳 null为获取当前时间
     * @return
     */
    public static Calendar getCalendar(long time) {
        return getCalendar(MS2Date(getMS(time)));
    }

    /**
     * data-->>星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        return getWeek(date2MS(date));
    }

    /**
     * 时间戳-->>星期几
     *
     * @param date
     * @return
     */
    public static String getWeek(long date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = getCalendar(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 获取两个时间戳中间的天数
     *
     * @param ms1
     * @param ms2
     * @return
     */
    public static int getCountDays(long ms1, long ms2) {
        return Math.abs((int) (getMS(ms1) / 1000 - getMS(ms2) / 1000) / (60 * 60 * 24));
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * 毫秒时间戳-->>时间字符串
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String MS2String(long milliseconds) {
        return MS2String(milliseconds, getSDF());
    }

    /**
     * 毫秒时间戳-->>时间字符串
     *
     * @param milliseconds 毫秒时间戳
     * @param formatStr    自定义时间格式
     * @return
     */
    public static String MS2String(long milliseconds, String formatStr) {
        return MS2String(milliseconds, getSDF(formatStr));
    }


///////////////////////////////////////////////////数据转换//////////////////////////////////////////////////////////////////

    /**
     * 时间戳-->>Date类型
     *
     * @param milliseconds 时间戳
     * @return Date类型时间
     */
    public static Date MS2Date(long milliseconds) {
        return new Date(getMS(milliseconds));
    }

    /**
     * 时间字符串-->>毫秒时间戳
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2MS(String time) {
        return string2MS(time, getSDF());
    }

    /**
     * 时间字符串-->>毫秒时间戳
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2MS(String time, SimpleDateFormat format) {
        try {
            String re_time = null;
            Date d;
            d = format.parse(time);
            long l = d.getTime();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 时间字符串-->>毫秒时间戳
     *
     * @param time      时间字符串
     * @param formatStr 时间格式
     * @return
     */
    public static long string2MS(String time, String formatStr) {
        return string2MS(time, getSDF(formatStr));
    }

    /**
     * 时间字符串-->>Date类型
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, getSDF());
    }

    /**
     * 时间字符串-->>Date类型
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2MS(time, format));
    }

    /**
     * 时间字符串-->>Date类型
     *
     * @param time      时间字符串
     * @param formatStr 时间格式
     * @return
     */
    public static Date string2Date(String time, String formatStr) {
        return string2Date(time, getSDF(formatStr));
    }

    /**
     * Date类型-->>时间字符串
     * <p>格式为yyyy-MM-dd</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, getSDF());
    }

    /**
     * Date类型-->>时间字符串
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * Date类型-->>时间字符串
     *
     * @param time      Date类型时间
     * @param formatStr 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, String formatStr) {
        return date2String(time, getSDF(formatStr));
    }

    /**
     * Date类型-->>时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2MS(Date time) {
        return time.getTime();
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @return true今天 false不是
     */
    public static boolean isToday(long day) {
        return isOneDay(0, day);
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param time 传入的 时间戳 秒级 毫秒级都可以
     * @return true今天 false不是
     */
    public static boolean isYesterday(long time) {
        Calendar pre = getCalendar(0);
        Calendar cal = getCalendar(time);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为今年
     *
     * @param time 传入的 时间戳 秒级 毫秒级都可以
     * @return true今年 false不是
     */
    public static boolean isThisYear(long time) {
        return isOneYear(0, time);
    }

////////////////////////////////////////////////////  true\false判断  /////////////////////////////////////////////////////////////////////////////////

    /**
     * 判断是否同一天
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isOneDay(long time, long time2) {
        Calendar one = getCalendar(time);
        Calendar two = getCalendar(time2);
        if (one.get(Calendar.YEAR) == (two.get(Calendar.YEAR))) {
            if (two.get(Calendar.DAY_OF_YEAR) == one.get(Calendar.DAY_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 判断两个日期是否在同一周
     *
     * @param time
     * @param time2
     * @return
     */
//    public static boolean isOneWeek(Long time, Long time2) {
//       final int aa=11;
//        Calendar cal1 = getCalendar(time);
//        Calendar cal2 = getCalendar(time2);
//        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
//        if (0 == subYear) {
//            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
//                return true;
//            }
//        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
//            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
//            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
//                return true;
//            }
//        } else if ((-1 == subYear) && (cal1.get(Calendar.MONTH) == aa)) {
//            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * 判断是否同一月
     * 不能跨年
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isOneMonth(Long time, Long time2) {
        Calendar one = getCalendar(time);
        Calendar two = getCalendar(time2);
        if (one.get(Calendar.YEAR) == (two.get(Calendar.YEAR))) {
            if (two.get(Calendar.MONTH) == one.get(Calendar.MONTH)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否同一年
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isOneYear(long time, long time2) {
        Calendar one = getCalendar(time);
        Calendar two = getCalendar(time2);
        if (one.get(Calendar.YEAR) == (two.get(Calendar.YEAR))) {
            return true;
        }
        return false;
    }

    /**
     * 判断两个日期是否相连
     * 不能跨年
     *
     * @param time  时间1  时间戳 秒级 毫秒级都可以
     * @param time2 时间2 时间戳 秒级 毫秒级都可以
     * @return
     */
    public static boolean isAdiacentDay(Long time, Long time2) {
        Calendar one = getCalendar(time);
        Calendar two = getCalendar(time2);
        if (one.get(Calendar.YEAR) == (two.get(Calendar.YEAR)) && one.get(Calendar.MONTH) == (two.get(Calendar.MONTH))) {
            return two.get(Calendar.DAY_OF_YEAR) - one.get(Calendar.DAY_OF_YEAR) == 1 || two.get(Calendar.DAY_OF_YEAR) - one.get(Calendar.DAY_OF_YEAR) == -1 ? true : false;
        }
        return false;
    }

    /**
     * 判断两个日期是否相连
     *
     * @param time1 小日期 1986-11-01
     * @param time2 大日期1986-10-01
     * @return
     */
    public static Boolean intervalOneDay(String time1, String time2) {
        if (Integer.parseInt(time2.replaceAll("-", "")) - (Integer.parseInt(time1.replaceAll("-", ""))) == 1) {
            return true;
        }
        return false;
    }

    public static String getTime(long time) {
        String returnSre = "";
        if (DateUtil.isThisYear(time)) {
            if (DateUtil.isToday(time)) {
                returnSre = "今天 " + DateUtil.MS2String(time, "HH:mm");
            } else if (DateUtil.isYesterday(time)) {
                returnSre = "昨天 " + DateUtil.MS2String(time, "HH:mm");
            } else {
                returnSre = DateUtil.MS2String(time, "MM-dd HH:mm");
            }
        } else {
            returnSre = DateUtil.MS2String(time, "yyyy-MM-dd HH:mm");
        }
        return returnSre;
    }

    /**
     * 距离现在多长时间
     *
     * @param timeStr 传入的毫秒数
     * @return 返回类型有多少月前，多少天前，多少小时前，多少分钟前
     */
    public static String getDateAgo(String timeStr) {
        String temp = "";
        try {
            long now = System.currentTimeMillis() / 1000;
            long publish = Long.parseLong(timeStr);
            long diff = now - publish;
            long months = diff / (60 * 60 * 24 * 30);
            long days = diff / (60 * 60 * 24);
            long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
            long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;
            if (months > 0) {
                temp = months + "月前";
            } else if (days > 0) {
                temp = days + "天前";
            } else if (hours > 0) {
                temp = hours + "小时前";
            } else if (minutes > 0) {
                temp = minutes + "分钟前";
            } else {
                temp = "刚刚";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 毫秒时间戳---》》秒数
     *
     * @param ms
     * @return
     */
    public static long MS2Sec(long ms) {
//        long seconds = new BigDecimal(((float) ms / (float) 1000)).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
        return M2Sec(ms / 1000);
    }

    /**
     * 秒时间戳---》》秒数
     *
     * @param m
     * @return
     */
    public static long M2Sec(long m) {
        long seconds = new BigDecimal((float) m).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        return seconds;
    }

    /**
     * 得到月日对应的星座
     *
     * @param month 月份
     * @param day   天
     * @return 返回星座信息
     */
    public static String getConstellation(int month, int day) {
        String[] astro = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
                "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
        int[] arr = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};// 两个星座分割日
        if (month <= 0 || day <= 0) {
            return "猴年马月座";
        } else if (month > 12 || day > 31) {
            return "猴年马月座";
        }
        int index = month;
        // 所查询日期在分割日之前，索引-1，否则不变
        if (day < arr[month - 1]) {
            index = index - 1;
        }
        // 返回索引指向的星座string
        return astro[index];
    }

    /**
     * 时分秒 --》》 秒数
     *
     * @param time "hh:mm:ss"或"mm:ss" 只能是以：冒号格式的时间
     * @return 秒数
     */
    public static long formatSeconds(String time) {
        String s = time;
        int hh = 0;
        int mi = 0;
        int ss = 0;
        //首次出现：的位置
        int index1 = s.indexOf(":");
        if (index1 == -1) {
            ss = Integer.parseInt(time);
        } else {
            //第二次出现：的位置
            int index2 = s.indexOf(":", index1 + 1);
            //如果第二次的位置为-1，说明这个时间是 分：秒 mm:ss 格式  没有小时
            if (index2 == -1) {
                mi = Integer.parseInt(s.substring(0, index1));

                ss = Integer.parseInt(s.substring(index1 + 1));
                //如果第二次的位置为正值，说明这个时间是 时：分：秒 hh：mm:ss 格式  有小时
            } else {
                hh = Integer.parseInt(s.substring(0, index1));

                mi = Integer.parseInt(s.substring(index1 + 1, index2));

                ss = Integer.parseInt(s.substring(index2 + 1));
            }

        }


        return hh * 60 * 60 + mi * 60 + ss;
    }

    /**
     * 秒数 --》》 时分秒
     *
     * @param seconds 秒数
     * @return 1小时20分30秒
     */
    public static String getDurationFromSecond(int seconds) {
        return getDurationFromSecond(seconds, "小时", "分", "秒");
    }

    /**
     * 秒数 --》》 时分秒
     *
     * @param seconds       秒数
     * @param hourSuffix    小时后面的后缀
     * @param minuteSuffix  分后面的后缀
     * @param hsecondSuffix 秒后面的后缀
     * @return 1小时20分30秒 或 1：20：30
     */
    public static String getDurationFromSecond(int seconds, String hourSuffix, String minuteSuffix, String hsecondSuffix) {
        StringBuilder sb = new StringBuilder();
        if (seconds < 1) {
            seconds = 0;
        }
        //几小时
        int hour = seconds / (60 * 60);
        //几分
        int minute = (seconds - 60 * 60 * hour) / 60;
        //当小时不为0，时分都显示
        if (hour != 0) {
            sb.append(hour).append(hourSuffix);
            sb.append(minute).append(minuteSuffix);
            //当小时为0
        } else {
            //当分不等于0，展示分，否则不展示
            if (minute != 0) {
                sb.append(minute).append(minuteSuffix);
            }
        }
        //几秒
        int second = (seconds - 60 * 60 * hour - 60 * minute);
        sb.append(second).append(hsecondSuffix);
        return sb.toString();
    }
    public static String S2Time(int timeInMillis) {
        long hour = timeInMillis / (60 * 60 * 1000);
        long minute = (timeInMillis - hour * 60 * 60 * 1000) / (60 * 1000);
        long second = (timeInMillis - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        if (second >= 60) {
            second = second % 60;
            minute += second / 60;
        }
        if (minute >= 60) {
            minute = minute % 60;
            hour += minute / 60;
        }
        String sh = "";
        String sm = "";
        String ss = "";
        if (hour < 10) {
            sh = "0" + String.valueOf(hour);
        } else {
            sh = String.valueOf(hour);
        }
        if (minute < 10) {
            sm = "0" + String.valueOf(minute);
        } else {
            sm = String.valueOf(minute);
        }
        if (second < 10) {
            ss = "0" + String.valueOf(second);
        } else {
            ss = String.valueOf(second);
        }
        return sm+":"+ss;
    }
    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = getCalendar(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeformatter1to12.format(date);
        }
        return "";
    }

    /**
     * 根据日期获取 Calendar类
     *
     * @param time 时间戳 null为获取当前时间
     * @return
     */
    public static Calendar getCalendar(Date time) {
        Calendar pre = Calendar.getInstance();
        pre.setTime(time);
        return pre;
    }


}
