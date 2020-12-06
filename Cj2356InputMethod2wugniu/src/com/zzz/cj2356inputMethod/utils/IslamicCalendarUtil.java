package com.zzz.cj2356inputMethod.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 回曆日期計算
 */
public class IslamicCalendarUtil {
    static SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    static String[] jyutMingzi = { "穆哈蘭姆月聖月", "色法爾月旅月", "賴比爾·敖外魯月第一春月", "賴比爾·阿色尼月第二春月", "主馬達·敖外魯月第一乾月", "主馬達·阿色尼月第二乾月",
            "赖哲卜月問候月", "舍爾邦月分配月", "賴買丹月齋戒月", "閃瓦魯月獵月", "都爾喀爾德月休息月", "都爾黑哲月朝聖月" };

    static String[] jyutMingziSim = { "穆哈兰姆月圣月", "色法尔月旅月", "赖比尔·敖外鲁月第一春月", "赖比尔·阿色尼月第二春月", "主马达·敖外鲁月第一干月",
            "主马达·阿色尼月第二干月", "赖哲卜月问候月", "舍尔邦月分配月", "赖买丹月斋戒月", "闪瓦鲁月猎月", "都尔喀尔德月休息月", "都尔黑哲月朝圣月" };

    /**
     * 計算回曆
     * 
     * @param dayRL
     *            2000.0起算儒略日
     * @param date
     *            只用於塡返回結果
     */
//    public static void getHuiLi(int dayRL, LunarDate date) {
//        String huili = getHuiLiByJD(dayRL);
//        String[] parts = huili.split(" ");
//        date.sethYear(Integer.parseInt(parts[0]));
//        date.sethMonth(Integer.parseInt(parts[1]));
//        date.sethDay(Integer.parseInt(parts[2]));
//    }

    /**
     * 按儒略日算出回曆年月日字符串
     * 
     * @param jd
     *            2000.0起算儒略日
     * 
     * @return 回曆年月日，格式如“(-)1420 09 24”
     */
    public static String getHuiLiByJD(int jd) {
        // 以下算法使用Excel测试得到,测试时主要关心年临界与月临界
        int z, y, m, d;
        d = jd + 503105;
        z = (int) Math.floor(d / 10631); // 10631为一周期(30年)
        d -= z * 10631;
        y = (int) Math.floor((d + 0.5) / 354.366); // 加0.5的作用是保证闰年正确(一周中的闰年是第2,5,7,10,13,16,18,21,24,26,29年)
        d -= (int) Math.floor(y * 354.366 + 0.5);
        m = (int) Math.floor((d + 0.11) / 29.51); // 分子加0.11,分母加0.01的作用是第354或355天的的月分保持为12月(m=11)
        d -= (int) Math.floor(m * 29.5 + 0.5);
        int year = z * 30 + y + 1;
        int month = m + 1;
        int day = d + 1;
        return fomatHuili2Str(year, month, day);
    }

    /**
     * 按日期對象算出回曆年月日字符串
     * 
     * @param date
     * @return 回曆年月日，格式如“(-)1420 09 24”
     */
    public static String getHuiLiByDate(Date date) {
        // 公元2000年1月1日，設儒略日相對值爲0，回曆1420-9-24
        // 實際值參見Common.J2000=2451545
        int theJd = 0;
        String res = null;
        try {
            Date date0 = sdf_yyyyMMdd.parse("2000-01-01");
            Long days = DateGanzhiTest.daysBetween(date0, date);
            theJd = days.intValue();
            res = getHuiLiByJD(theJd);
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 按日期對象算出回曆時間戳
     * 
     * @param date
     * @param isSimple
     *            是否簡化字
     * @param withNames
     *            是否帶各月的名字
     * @param withTime
     *            是否帶時分秒
     * @return 字符串，如回曆1439年01月穆哈蘭姆月01日
     */
    public static String getHuiLiStrByDate(Date date, boolean isSimple, boolean withNames, boolean withTime) {
        String huili = getHuiLiByDate(date);
        String[] parts = huili.split(" ");
        String res = "回曆";
        res += parts[0].replaceAll("-", "前") + "年";
        res += parts[1] + "月";
        if (withNames) {
            res += jyutMingzi[Integer.parseInt(parts[1]) - 1];
        }
        res += parts[2] + "日";

        if (withTime) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            res += (hour < 10 ? "0" : "") + hour + "時";
            res += (minute < 10 ? "0" : "") + minute + "分";
            res += (second < 10 ? "0" : "") + second + "秒";
        }

        if (isSimple) {
            for (int i = 0; i < jyutMingzi.length; i++) {
                res = res.replaceAll(jyutMingzi[i], jyutMingziSim[i]);
            }
            res = res.replaceAll("回曆", "回历");
            res = res.replaceAll("時", "时");
        }
        return res;
    }

    /**
     * 格式化回曆年月日
     * 
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return 回曆年月日，格式如“(-)1420 09 24”
     */
    public static String fomatHuili2Str(int year, int month, int day) {
        boolean beforeEra = (year < 0);
        String yearStr = Math.abs(year) + "";
        while (yearStr.length() < 4) {
            yearStr = "0" + yearStr;
        }
        if (beforeEra) {
            yearStr = "-" + yearStr;
        }
        return yearStr + " " + (month < 10 ? "0" : "") + month + " " + (day < 10 ? "0" : "") + day;
    }

    public static void main(String[] args) throws Exception {
        String dateStr = "0621-07-15";
        System.out.println(dateStr + ": " + getHuiLiByDate(sdf_yyyyMMdd.parse(dateStr)));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -90);
        System.out.println(getHuiLiByDate(cal.getTime()));
        System.out.println(getHuiLiStrByDate(cal.getTime(), true, true, false));
        System.out.println(getHuiLiStrByDate(cal.getTime(), false, false, true));
    }
}