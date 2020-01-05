package com.zzz.cj2356inputMethod.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 西曆到夏曆：西曆年月日必須佔八位數字，且在西元後才能正确
 */
public class HialiUtils {
    // 配置
    private static List<String> conCals = null;

    private static final String configFile = "config.cal";
    private static final SimpleDateFormat defaultSdf = new SimpleDateFormat(
            "yyyyMMdd");
    private static final String[] chinaMoons = { "正月", "二月", "三月", "四月", "五月",
            "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };
    private static final String[] chinaMoonDays = { "初一", "初二", "初三", "初四",
            "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五",
            "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五",
            "二十六", "二十七", "二十八", "二十九", "三十" };
    private static final String[] ganzhi = { "甲子", "乙丑", "丙寅", "丁卯", "戊辰", "己巳",
            "庚午", "辛未", "壬申", "癸酉", "甲戌", "乙亥", "丙子", "丁丑", "戊寅", "己卯", "庚辰",
            "辛巳", "壬午", "癸未", "甲申", "乙酉", "丙戌", "丁亥", "戊子", "己丑", "庚寅", "辛卯",
            "壬辰", "癸巳", "甲午", "乙未", "丙申", "丁酉", "戊戌", "己亥", "庚子", "辛丑", "壬寅",
            "癸卯", "甲辰", "乙巳", "丙午", "丁未", "戊申", "己酉", "庚戌", "辛亥", "壬子", "癸丑",
            "甲寅", "乙卯", "丙辰", "丁巳", "戊午", "己未", "庚申", "辛酉", "壬戌", "癸亥" };

    static {
        conCals = readConfigFile();
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> mapWestChina = new HashMap<String, String>();
        mapWestChina.put("20170226 18:00:00", "二月初一日");
        mapWestChina.put("20170131 18:00:00", "正月初四日");
        mapWestChina.put("20141010 18:00:00", "九月十七日");
        mapWestChina.put("20141030 18:00:00", "閏九月初七日");
        mapWestChina.put("19900211 18:00:00", "正月十六日");
        mapWestChina.put("19880719 18:00:00", "六月初六日");
        mapWestChina.put("19491001 09:30:00", "八月初十日");

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        System.out.println("當前夏曆年： " + getChineseYearByWest(now));
        System.out.println("當前西曆主體對應的夏曆年： "
                + getChineseYearByMainWestYear(cal.get(Calendar.YEAR)));
        System.out.println("夏曆" + getChineseCalByWest(now));
        System.out.println("西曆" + defaultSdf
                .format(getWestCalByChinese(getChineseCalByWest(now))));
        System.out.println(getGanZhiByMainWestYear(cal.get(Calendar.YEAR)));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        for (String str : mapWestChina.keySet()) {
            Date date = sdf.parse(str);
            System.out.println("夏曆正确結果" + mapWestChina.get(str) + "，计算結果："
                    + getChineseCalByWest(date));
            System.out.println("西曆" + defaultSdf
                    .format(getWestCalByChinese(getChineseCalByWest(date))));
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);
            System.out
                    .println(getGanZhiByMainWestYear(cal2.get(Calendar.YEAR)));
        }

        String dateStr = "四七一二年某月大初七日";
        dateStr = dateStr.replaceAll("大|小", "");
        System.out.println(dateStr);
    }

    /**
     * 按西曆主體對應的年得到干支
     */
    public static String getGanZhiByMainWestYear(int mainWestYear) {
        int chinaYear = getChineseYearByMainWestYear(mainWestYear);
        return getGanZhiByChinesYear(chinaYear);
    }

    /**
     * 按夏曆年得到干支
     */
    public static String getGanZhiByChinesYear(int chinaYear) {
        if (chinaYear == 1) {
            return ganzhi[ganzhi.length - 1];
        } else if (chinaYear > 1) {
            int yu = (chinaYear - 1) % 60;
            if (yu == 0) {
                return ganzhi[60 - 1];
            } else {
                return ganzhi[yu - 1];
            }
        } else if (chinaYear < 0) {
            int year2 = Math.abs(chinaYear);
            int yu = year2 % 60;
            return ganzhi[59 - yu];
        }
        return null;
    }

    /**
     * 按西曆年得到夏曆年<br />
     * 西元前，前面有負號
     */
    public static int getChineseYearByMainWestYear(int mainWestYear) {
        Integer chineseYear = 0;
        if (mainWestYear > 0) {
            chineseYear = mainWestYear + 2698;
        } else if (-2698 <= mainWestYear && mainWestYear < 0) {
            chineseYear = 1 + 2698 + mainWestYear;
        } else {
            chineseYear = 2698 + mainWestYear;
        }
        return chineseYear;
    }

    /**
     * 按夏曆年得到西曆年<br />
     * 夏曆前，前面有負號
     */
    public static int getWestYearByMainChineseYear(int mainChinaYear) {
        Integer westyear = 0;
        if (mainChinaYear >= 2699) {
            westyear = mainChinaYear - 2698;
        } else if (1 <= mainChinaYear && mainChinaYear < 2699) {
            westyear = mainChinaYear - 2698 - 1;
        } else {
            westyear = mainChinaYear - 2698;
        }
        return westyear;
    }

    /**
     * 把阿拉伯數字换成中文的
     */
    public static String replaceArabByChinaNumber(Integer num) {
        String input = num.toString();
        input = input.replaceAll("0", "〇");
        input = input.replaceAll("1", "一");
        input = input.replaceAll("2", "二");
        input = input.replaceAll("3", "三");
        input = input.replaceAll("4", "四");
        input = input.replaceAll("5", "五");
        input = input.replaceAll("6", "六");
        input = input.replaceAll("7", "七");
        input = input.replaceAll("8", "八");
        input = input.replaceAll("9", "九");
        return input;
    }

    /**
     * 把中文數字換成阿拉伯數字
     */
    public static String replaceChinaNumberByArab(String chinaNum) {
        String input = chinaNum.toString();
        input = input.replaceAll("〇", "0");
        input = input.replaceAll("零", "0");
        input = input.replaceAll("一", "1");
        input = input.replaceAll("二", "2");
        input = input.replaceAll("三", "3");
        input = input.replaceAll("四", "4");
        input = input.replaceAll("五", "5");
        input = input.replaceAll("六", "6");
        input = input.replaceAll("七", "7");
        input = input.replaceAll("八", "8");
        input = input.replaceAll("九", "9");
        return input;
    }

    /**
     * 按西曆得到當前是夏曆哪年
     * 
     * @author fszhouzz@qq.com
     * @time 2020年1月4日 下午11:22:58
     * @param mainWestYear
     * @return
     */
    public static Integer getChineseYearByWest(Date date) throws Exception {
        if (null == conCals || conCals.isEmpty()) {
            return null;
        }
        // 是否不合法
        long dateLong = Long.parseLong(defaultSdf.format(date));
        long dateMinLong = Long.parseLong(getConfigCalMin(conCals));
        long dateMaxLong = Long.parseLong(getConfigCalMax(conCals));
        if (dateLong < dateMinLong || dateLong > dateMaxLong) {
            return null;
        }
        // 用來運算的配置
        String targetCal = getConfigCalTarget(date, conCals);
        if (null == targetCal) {
            return null;
        }
        int mainWestYear = Integer.parseInt(targetCal.substring(0, 4));
        return getChineseYearByMainWestYear(mainWestYear);
    }

    /**
     * 按西曆得到夏曆
     * 
     * @throws Exception
     */
    public static String getChineseCalByWest(Date date) throws Exception {
        if (null == conCals || conCals.isEmpty()) {
            return null;
        }
        // 是否不合法
        long dateLong = Long.parseLong(defaultSdf.format(date));
        long dateMinLong = Long.parseLong(getConfigCalMin(conCals));
        long dateMaxLong = Long.parseLong(getConfigCalMax(conCals));
        if (dateLong < dateMinLong || dateLong > dateMaxLong) {
            return null;
        }
        // 用來運算的配置
        String targetCal = getConfigCalTarget(date, conCals);
        if (null == targetCal) {
            return null;
        }
        return resolveChineseCal(date, targetCal);
    }

    /**
     * 按夏曆得到西曆
     * 
     * @param dateStr
     *            如四七一二年某月大初七日
     * @throws Exception
     */
    public static Date getWestCalByChinese(String dateStr) throws Exception {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }
        dateStr = dateStr.replaceAll("大|小", "");

        String[] yearParts = dateStr.split("年");
        Integer westyear = getWestYearByMainChineseYear(
                Integer.parseInt(replaceChinaNumberByArab(yearParts[0])));
        // 用來運算的配置
        String config = null;
        if (null == conCals || conCals.isEmpty()) {
            return null;
        }
        for (String con : conCals) {
            if (con.startsWith(westyear.toString())) {
                config = con;
            }
        }
        if (null == config) {
            return null;
        }
        // 算日期
        String moonDay = yearParts[1]; // 如: 閏九月初七日
        boolean isLeapMoon = false;
        int chinaMoon = 0;
        int chinaDay = 0;
        if (moonDay.startsWith("閏")) {
            isLeapMoon = true;
            moonDay = moonDay.split("閏")[1];
        }
        String chinaMoonStr = moonDay.split("月")[0] + "月";
        for (int i = 0; i < chinaMoons.length; i++) {
            if (chinaMoonStr.equals(chinaMoons[i])) {
                chinaMoon = i + 1;
                break;
            }
        }
        String chinaDayStr = moonDay.split("月")[1].split("日")[0];
        for (int j = 0; j < chinaMoonDays.length; j++) {
            if (chinaDayStr.equals(chinaMoonDays[j])) {
                chinaDay = j + 1;
                break;
            }
        }
        // 夏曆日期在夏曆這年的第幾天
        int tempDasCnt = 0;
        String[] conParts = config.split("_");
        String dateOne = conParts[0];
        String moonsCon = conParts[1];
        int leapMoon = Integer.parseInt(conParts[2]);
        if ((leapMoon != 0 && leapMoon < chinaMoon) || isLeapMoon) {
            chinaMoon++;
        }
        for (int index = 1; index < chinaMoon; index++) {
            Character ch = moonsCon.charAt(index - 1);
            int days = 0;
            if ("1".equals(ch.toString())) {
                days = 30;
            } else if ("0".equals(ch.toString())) {
                days = 29;
            }
            tempDasCnt += days;
        }
        tempDasCnt += chinaDay;
        // 計算西曆日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(defaultSdf.parse(dateOne));
        cal.add(Calendar.DATE, tempDasCnt - 1);
        return cal.getTime();
    }

    /**
     * 按配置和日期得到夏曆日期
     * 
     * @throws Exception
     */
    private static String resolveChineseCal(Date date, String config)
            throws Exception {
        String[] conParts = config.split("_");
        String dateOne = conParts[0];
        String moonsCon = conParts[1];
        int leapMoon = Integer.parseInt(conParts[2]);
        int end = 12;
        if (0 != leapMoon) {
            end++;
        } else if (leapMoon > 12) {
            return null;
        }
        int chinaMoon = 1;
        int chinaDay = 0;
        long dateLong = Long.parseLong(defaultSdf.format(date));

        // System.out.println("正月初一的西曆年月日：" + dateOne);
        Calendar calOne = Calendar.getInstance();
        calOne.setTime(defaultSdf.parse(dateOne));
        calOne.add(Calendar.DATE, -1); // 正月初一的前一天

        boolean isBigMon = false;
        for (int index = 1; index <= end; index++) {
            Character ch = moonsCon.charAt(index - 1);
            int days = 0;
            if ("1".equals(ch.toString())) {
                days = 30;
            } else if ("0".equals(ch.toString())) {
                days = 29;
            } else {
                return null;
            }
            calOne.add(Calendar.DATE, days);
            // 月晦的西曆年月日
            long callong = Long.parseLong(defaultSdf.format(calOne.getTime()));

            if (callong >= dateLong) { // 月晦大于給定日期
                Calendar caldate = Calendar.getInstance();
                caldate.setTime(date);
                // 月晦比給定日期相隔天數
                int moreDays = DateUtils.datesMoreAfterBegin(caldate.getTime(),
                        calOne.getTime());
                chinaDay = days - moreDays;
                isBigMon = days > 29;
                break;
            } else {
                chinaMoon++;
            }
        }
        return resolveChineseCalFormat(Integer.parseInt(config.substring(0, 4)),
                chinaMoon, chinaDay, isBigMon, leapMoon);
    }

    /**
     * 得到夏曆日期格式
     * 
     * @param isBigMon
     *            是否大月
     * @param mainWestYear
     *            夏曆年主體對應的西曆年
     */
    private static String resolveChineseCalFormat(int mainWestYear,
            int chinaMoon, int chinaDay, boolean isBigMon, int leapMoon) {
        StringBuilder result = new StringBuilder(replaceArabByChinaNumber(
                getChineseYearByMainWestYear(mainWestYear)));
        result.append("年");
        result.append((leapMoon != 0 && chinaMoon - 1 == leapMoon) ? "閏" : "");
        result.append((leapMoon != 0 && (chinaMoon - 1) >= leapMoon)
                ? chinaMoons[chinaMoon - 2] : chinaMoons[chinaMoon - 1]);
        result.append(isBigMon ? "大" : "小");
        result.append(chinaMoonDays[chinaDay - 1]);
        result.append("日");
        return result.toString();
    }

    /** 得到用來運算的配置 */
    private static String getConfigCalTarget(Date date, List<String> conCals) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer year = cal.get(Calendar.YEAR);
        List<String> targetCals = new ArrayList<String>();
        for (String c : conCals) {
            if (c.startsWith(((Integer) (year - 1)).toString())
                    || c.startsWith(year.toString())) {
                targetCals.add(c);
            }
        }
        if (targetCals.size() != 2) {
            return null;
        }
        long dateLong = Long.parseLong(defaultSdf.format(date));
        long target2dateLong = Long.parseLong(targetCals.get(1).split("_")[0]);
        return (dateLong >= target2dateLong) ? targetCals.get(1)
                : targetCals.get(0);
    }

    /** 西曆最小可轉換日期 */
    private static String getConfigCalMin(List<String> cons) {
        Collections.sort(cons);
        return cons.get(0).split("_")[0];
    }

    /**
     * 西曆最大可轉換日期
     * 
     * @throws Exception
     */
    private static String getConfigCalMax(List<String> cons) throws Exception {
        Collections.sort(cons);
        String[] conParts = cons.get(cons.size() - 1).split("_");
        String dateOne = conParts[0];
        String moonsCon = conParts[1];
        int leapMoon = Integer.parseInt(conParts[2]);
        Calendar calmax = Calendar.getInstance();
        calmax.setTime(defaultSdf.parse(dateOne));
        int end = 12;
        if (0 == leapMoon) {
            end++;
        } else if (leapMoon > 12) {
            return null;
        }
        for (int index = 1; index <= end; index++) {
            Character ch = moonsCon.charAt(index - 1);
            if ("1".equals(ch.toString())) {
                calmax.add(Calendar.DATE, 30);
            } else if ("0".equals(ch.toString())) {
                calmax.add(Calendar.DATE, 29);
            }
        }
        calmax.add(Calendar.DATE, -1); // 要減一天，因爲是從正月初一開始加的
        return defaultSdf.format(calmax.getTime());
    }

    /** 讀取夏曆配置文件 */
    private static List<String> readConfigFile() {
        List<String> result = new ArrayList<String>();
        InputStreamReader isr = null;
        BufferedReader br = null;
        String str = null;
        try {
            InputStream is = HialiUtils.class.getResourceAsStream(configFile);
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                if (!"".equals(str)) {
                    String line = str.trim();
                    result.add(line);
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

}
