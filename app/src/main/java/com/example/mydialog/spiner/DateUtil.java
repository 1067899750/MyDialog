package com.example.mydialog.spiner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @description
 * @author puyantao
 * @date 2020/7/7 16:07
 */
public class DateUtil {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat BaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat ymdHmsFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final long DAY_MILLISECONDS = 24 * 60 * 60 * 1000;

    private static DatePickerDialog datePickerDialog;


    /**
     *    时间蹉  -->  日期
     * @param dateLong  时间蹉
     * @param type  类型
     * @return  日期
     */
    public static String getStringDateByLong(long dateLong, int type) {
        String template = null;
        switch (type) {
            case 1:
                template = "yyyy-MM-dd HH:mm";
                break;
            case 2:
                template = "yyyy/MM/dd";
                break;
            case 3:
                template = "yyyy年MM月dd日";
                break;
            case 4:
                template = "yyyy-MM-dd";
                break;
            case 5:
                template = "yyyy-MM-dd HH:mm:ss";
                break;
            case 6:
                template = "MM月dd号";
                break;
            case 7:
                template = "yyyy/MM/dd HH:mm:ss";
                break;
            case 8:
                template = "HH:mm";
                break;
            case 9:
                template = "MM/dd";
                break;
            case 10:
                template = "yyyy/MM";
                break;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        // 1000L
        Date date = new Date(dateLong );
        String dateString = sdf.format(date);
        return dateString;
    }



    /**
     *  日期 --> 时间蹉
     * @param s  日期
     * @param type 类型
     * @return 时间蹉
     */
    public static long dateToStamp(String s, int type) {
        String template = null;
        switch (type) {
            case 1:
                template = "yyyy-MM-dd HH:mm";
                break;
            case 2:
                template = "yyyy/MM/dd";
                break;
            case 3:
                template = "yyyy年MM月dd日";
                break;
            case 4:
                template = "yyyy-MM-dd";
                break;
            case 5:
                template = "yyyy-MM-dd HH:mm:ss";
                break;
            case 6:
                template = "MM月dd号";
                break;
            case 7:
                template = "yyyy/MM/dd HH:mm:ss";
                break;
            case 8:
                template = "HH:mm";
                break;
            case 9:
                template = "MM/dd";
                break;
            case 10:
                template = "yyyy/MM";
                break;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    /**
     * 用于时间选择器
     */
    public interface OnDateSelectInterface {
        public void onSelect(String time);
    }

    //-------------------------------------- 转换类型   -----------------------------------------------


    /**
     * 时间转换
     *
     * @param str
     * @param format
     * @return
     */
    public static Date str2Date(String str, SimpleDateFormat format) {
        if (str == null || str.length() == 0) {
            return new Date();
        }
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转时间
     *
     * @param str
     * @param format
     * @return
     */
    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return new Date();
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Calendar str2Calendar(String str, SimpleDateFormat format) {
        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;

    }

    public static Calendar str2Calendar(String str, String format) {
        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;

    }

    public static String calendar2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return "";
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }


    /**
     * timeMill转fromat
     *
     * @param time
     * @param fromat
     * @return
     */
    public static String timeMilli2Str(long time, String fromat) {
        SimpleDateFormat dateformat = new SimpleDateFormat(fromat);
        String brith_StrTime = dateformat.format(time);
        return brith_StrTime;
    }


    /**
     * 转换类型
     *
     * @param expiryDate
     * @param dateType
     * @param toDateType
     * @return
     */
    public static String str2str(String expiryDate, String dateType, String toDateType) {
        try {
            Date date = str2Date(expiryDate, dateType);
            SimpleDateFormat dateFormat = new SimpleDateFormat(toDateType);
            return dateFormat.format(date);
        } catch (Exception e) {
            return expiryDate;
        }
    }


    //-----------------------------------获取时间 ----------------------------------

    /**
     * 格式到天
     *
     * @param time
     * @return
     */
    public static String getDay(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);

    }

    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + "-"
                + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
    }

    /**
     * 获得当前日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentDatetime() {
        return datetimeFormat.format(now());
    }

    /**
     * 获得当前时间的<code>java.util.Date</code>对象
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获得当前Chinese月份
     *
     * @return
     */
    public static int month() {
        return calendar().get(Calendar.MONTH) + 1;
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    public static int getDayOfWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得周五日期
     * <p/>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date friday() {
        return weekDay(Calendar.FRIDAY);
    }

    /**
     * 获得周六日期
     * <p/>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date saturday() {
        return weekDay(Calendar.SATURDAY);
    }

    /**
     * 获得周日日期 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date sunday() {
        return weekDay(Calendar.SUNDAY);
    }


    /**
     * 用于获取星期几
     *
     * @param week
     * @return
     */
    private static Date weekDay(int week) {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_WEEK, week);
        return cal.getTime();
    }

    /**
     * 获得年份
     *
     * @param date
     * @return
     */
    public int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获得月份
     *
     * @param date
     * @return
     */
    public int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得星期几
     *
     * @param date
     * @return
     */
    public int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 获得月份中的第几天
     *
     * @return
     */
    public static int dayOfMonth() {
        return calendar().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 今天是年中的第几天
     *
     * @return
     */
    public static int dayOfYear() {
        return calendar().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得当前月的最后一天
     * <p/>
     * HH:mm:ss为0，毫秒为999
     *
     * @return
     */
    public static Date lastDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
        cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
        return cal.getTime();
    }

    /**
     * 获得当前月的第一天
     * <p/>
     * HH:mm:ss SS为零
     *
     * @return
     */
    public static Date firstDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }


    /**
     * 获得日期
     *
     * @param date
     * @return
     */
    public int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    public static String getMondayOfThisWeek() {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return df2.format(c.getTime());
    }

    public static String getSundayOfThisWeek() {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return df2.format(c.getTime());
    }


    public static String getDistanceTime(long time2) {
        Date now = new Date();
        long day = 0;// 天数
        long hour = 0;// 小时
        long min = 0;// 分钟
        long sec = 0;// 秒
        try {
            long time1 = now.getTime();
            System.out.println("当前时间：" + time1);
            time2 = time2 * 1000l;
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000));
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rs = "";
        if (hour == 0) {
            rs = min + "分钟前";
            System.out.println(rs);
            return rs;
        }
        if (day == 0 && hour <= 4) {
            rs = hour + "小时前";
            System.out.println(rs);
            return rs;
        }
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String d = format.format(time2);
        Date date = null;
        try {
            date = format.parse(d);// 把字符类型的转换成日期类型的
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (now.getDate() - date.getDate() == 0) {// 当前时间和时间戳转换来的时间的天数对比
            DateFormat df2 = new SimpleDateFormat("HH:mm");
            rs = "今天  " + df2.format(time2);
            System.out.println(rs);
            return rs;
        } else if (now.getDate() - date.getDate() == 1) {
            DateFormat df2 = new SimpleDateFormat("HH:mm");
            rs = "昨天  " + df2.format(time2);
            System.out.println(rs);
            return rs;
        } else {
            DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
            rs = df2.format(time2);
            System.out.println(rs);
            return rs;
        }
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }


    //-----------------------------------计算-----------------------------------------

    /**
     * 计算2个date之间差值
     *
     * @param fromDate
     * @param toDate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date fromDate, Date toDate) throws ParseException {
        long from = fromDate.getTime();
        long to = toDate.getTime();
        return (int) Math.abs(((to - from) / (1000 * 60 * 60 * 24)));
    }


    /**
     * 转化时间输入时间与当前时间的间隔
     *
     * @param timestamp
     * @return
     */
    public static String converTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
        String timeStr = null;
        timeStr = timeGap / (24 * 60 * 60) + "";
        return timeStr;
    }

    /**
     * 判断原日期是否在目标日期之前
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }

    /**
     * 判断原日期是否在目标日期之后
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    /**
     * 判断两日期是否相同
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断某个日期是否在某个日期范围
     *
     * @param beginDate 日期范围开始
     * @param endDate   日期范围结束
     * @param src       需要判断的日期
     * @return
     */
    public static boolean between(Date beginDate, Date endDate, Date src) {
        return beginDate.before(src) && endDate.after(src);
    }

    /**
     * 比较时间大小
     *
     * @param begin
     * @param end
     * @return
     */
    public static int compareDate(String begin, String end) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date beginDate = df.parse(begin);
            Date endDate = df.parse(end);
            if (beginDate.getTime() < endDate.getTime()) {
                return 1;
            } else if (beginDate.getTime() > endDate.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    /**
     * 获得天数差
     *
     * @param begin
     * @param end
     * @return
     */
    public long getDayDiff(Date begin, Date end) {
        long day = 1;
        if (end.getTime() < begin.getTime()) {
            day = -1;
        } else if (end.getTime() == begin.getTime()) {
            day = 1;
        } else {
            day += (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000);
        }
        return day;
    }


    public static boolean isAfterToday(Calendar calendar) {
        Calendar calendar2 = Calendar.getInstance();
        if (calendar2.before(calendar)) {
            return true;
        }
        return false;
    }

    public static int calendarBetween(Calendar time1c, Calendar time2c) {

        long sec1 = time1c.getTimeInMillis();
        long sec2 = time2c.getTimeInMillis();
        int i = Integer.parseInt(String.valueOf((sec1 - sec2) / 1000 / 60 / 60 / 24));
        return i;
    }

    /**
     * 根据年月获得 这个月总共有几天
     *
     * @return
     */
    public static int getDayCountOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        long firstDayOfMonth = calendar.getTimeInMillis();

        if (month == Calendar.DECEMBER) {
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
        } else {
            calendar.add(Calendar.MONTH, 1);
        }
        long firstDayOfNextMonth = calendar.getTimeInMillis();
        return (int) ((firstDayOfNextMonth - firstDayOfMonth) / DAY_MILLISECONDS);
    }


    //获取上个月
    public static String getLastMonth(String strDate, int last) {
        if (null == strDate) {
            return null;
        }
        try {

            DateFormat fmt = new SimpleDateFormat("yyyy-MM");
            Calendar c = Calendar.getInstance();
            if (!"".equals(strDate)) {
                c.setTime(fmt.parse(strDate));
            }
            c.add(Calendar.MONTH, last);
            return fmt.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTime(String time) {
        if (time.contains("-")) {
            return time;
        }
        String timevalue = "";
        if (time.length() == 8) {
            timevalue = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
        } else if (time.length() == 6) {
            timevalue = time.substring(0, 4) + "-" + time.substring(4, 6);
        }
        return timevalue;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateTwoBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

      /**
     * 日期转换
     *
     * @param time
     * @return
     */
    public static String getDate2String(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            re_StrTime = sdf.format(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_StrTime;
    }


    /**
     * 日期转换
     *
     * @param time
     * @return
     */
    public static String getDate5String(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            re_StrTime = sdf.format(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_StrTime;
    }


    /**
     * 日期转换
     *
     * @param time
     * @return
     */
    public static String getDate4String(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            re_StrTime = sdf.format(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_StrTime;
    }


    /**
     * 日期转换
     *
     * @param time
     * @return
     */
    public static String getTimeString(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            re_StrTime = sdf.format(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_StrTime;
    }



    /**
     * 日期转换
     *
     * @param time
     * @return
     */
    public static String getDate3String(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        try {
            re_StrTime = sdf.format(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_StrTime;
    }


    /****
     * 传入具体日期 ，返回具体日期增加或减少一个月。
     * @param date 日期(yyyy年MM月
     * @param  month 1 表示加一个月， -1 表示减少一个月
     * @return yyyy年MM月
     * @throws ParseException
     */
    public static String addOrSubMonth(String date, int month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, month);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    /****
     * 传入具体日期 ，返回具体日期增加或减少一个年。
     * @param date 日期(yyyy年)
     * @param  year 1 表示加一个年， -1 表示减少一个年
     * @return yyyy年
     * @throws ParseException
     */
    public static String addOrSubYear(String date, int year) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR, year);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }



    /**
     *
     * @param date 天数
     * @param day 1 表示加一个天， -1 表示减少一个天
     * @return
     */
    public static String addOrSubDay(String date, int day) throws ParseException{
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        Date endDate = sf.parse(date);
        c.setTime(endDate);
        c.add(Calendar.DAY_OF_MONTH, day);
        return sf.format(c.getTime());
    }

    //---------------------------时间选择弹框 -------------------------------------------

    public static DatePickerDialog initDatePickerDialog(Context context, final TextView textView, Calendar calendar, int year, int month, int day, final OnDateSelectInterface onDateSelectInterface) {
        try {
            Date date = dateFormat.parse(textView.getText().toString());
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
        } catch (NullPointerException e) {
        }
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String timeValue = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                onDateSelectInterface.onSelect(timeValue);
                datePickerDialog.dismiss();
            }
        }, year, month, day);
        return datePickerDialog;
    }


}
