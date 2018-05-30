package com.zzz.cj2356inputMethod.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.zzz.cj2356inputMethod.dto.Item;

public class DateUtils {

    /** 格式化 */
    public static String formatDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.TRADITIONAL_CHINESE);
            return sdf.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 當前時間的輸入提示
     */
    public static ArrayList<Item> resolveTime(Item item) {
        ArrayList<Item> items = new ArrayList<Item>();
        if ("時間".equals(item.getCharacter()) || "時".equals(item.getCharacter())) {
            items.addAll(addFormatTimeItems(item, false));
        } else if ("时间".equals(item.getCharacter()) || "时".equals(item.getCharacter())) {
            items.addAll(addFormatTimeItems(item, true));
        } else if ("日期".equals(item.getCharacter()) || "日".equals(item.getCharacter())) {
            Date now = new Date();
            items.add(new Item(null, item.getGenCode(), null, formatDate(now, "yyyy年MM月dd日")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now, "yyyy-MM-dd")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now, "yyyyMMdd")));
            try {
                String chineseDate = HialiUtils.getChineseCalByWest(now);
                String ganzhi = HialiUtils.getGanZhiByChinesYear(Integer
                        .parseInt(HialiUtils.replaceChinaNumberByArab(chineseDate.split("年")[0].replace("前", "-"))));
                ganzhi += "年";
                String dateGanzhiStr = DateGanzhiTest.getDateGanzhi(now) + "日";
                items.add(new Item(null, item.getGenCode(), null,
                        formatDate(now, "yyyy年") + ganzhi + chineseDate.split("年")[1] + dateGanzhiStr));
                items.add(new Item(null, item.getGenCode(), null,
                        "夏曆" + chineseDate.split("年")[0] + "年" + ganzhi + chineseDate.split("年")[1] + dateGanzhiStr));
                items.add(new Item(null, item.getGenCode(), null,
                        "夏历" + chineseDate.split("年")[0] + "年" + ganzhi + chineseDate.split("年")[1] + dateGanzhiStr));
            } catch (Exception e) {
            }
        } else if ("星期".equals(item.getCharacter()) || "週".equals(item.getCharacter())
                || "周".equals(item.getCharacter())) {
            Date now = new Date();
            items.add(new Item(null, item.getGenCode(), null, formatDate(now, "EEEE")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now, "EEEE").replace("星期", "週")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now, "EEEE").replace("星期", "周")));
        } else if ("時辰".equals(item.getCharacter()) || "时辰".equals(item.getCharacter())
                || "辰".equals(item.getCharacter())) {
            try {
                Date now = new Date();
                List<String> shis = new ArrayList<String>();
                String hourGz = DateGanzhiTest.getHourGanzhi(now);
                String quarterTimeStr = DateGanzhiTest.getQuarterTimeStr(now);
                shis.add(hourGz + "時");
                shis.add(hourGz + "时");
                String couZing = quarterTimeStr.startsWith("正") ? "正" : "初";
                shis.add(hourGz + "時" + couZing);
                shis.add(hourGz + "时" + couZing);
                shis.add(hourGz + "時" + quarterTimeStr);
                shis.add(hourGz + "时" + quarterTimeStr);
                for (String str : shis) {
                    items.add(new Item(null, item.getGenCode(), null, str));
                }
            } catch (Exception e) {
            }
        }
        return items;
    }

    /**
     * 加入格式化的時間
     * 
     * @param item
     *            節點參數
     * @param isSimp
     *            是否簡化字
     * @return 在他末尾加入新生成的時間節點
     */
    private static ArrayList<Item> addFormatTimeItems(Item item, boolean isSimp) {
        ArrayList<Item> items = new ArrayList<Item>();
        Date now = new Date();
        items.add(new Item(null, item.getGenCode(), null,
                formatDate(now, "yyyy年MM月dd日HH" + (isSimp ? "时" : "時") + "mm分ss秒")));
        items.add(new Item(null, item.getGenCode(), null, formatDate(now, "yyyy-MM-dd HH:mm:ss")));
        items.add(new Item(null, item.getGenCode(), null, formatDate(now, "yyyyMMddHHmmssSSS")));
        try {
            String chineseDate = HialiUtils.getChineseCalByWest(now);
            String ganzhi = HialiUtils.getGanZhiByChinesYear(
                    Integer.parseInt(HialiUtils.replaceChinaNumberByArab(chineseDate.split("年")[0].replace("前", "-"))));
            ganzhi += "年";
            String dateGanzhiStr = DateGanzhiTest.getDateGanzhi(now) + "日";
            dateGanzhiStr += DateGanzhiTest.getHourGanzhi(now) + (isSimp ? "时" : "時");
            dateGanzhiStr += DateGanzhiTest.getQuarterTimeStr(now);
            items.add(new Item(null, item.getGenCode(), null,
                    formatDate(now, "yyyy年") + ganzhi + chineseDate.split("年")[1] + dateGanzhiStr));
            items.add(new Item(null, item.getGenCode(), null, (isSimp ? "夏历" : "夏曆") + chineseDate.split("年")[0] + "年"
                    + ganzhi + chineseDate.split("年")[1] + dateGanzhiStr));
        } catch (Exception e) {
        }
        return items;
    }

    /** 兩個日期相隔天數，日期不同就算一天 */
    public static int datesMoreAfterBegin(Date begin, Date end) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = sdf.parse(sdf.format(begin)); // 格式yyyyMMdd
        Date endDate = sdf.parse(sdf.format(end));
        return ((Long) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24))).intValue();
    }

    /** 兩個日期相差天數，滿一天才算一天 */
    public static int daysMoreAfterBegin(Date begin, Date end) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date beginDate = sdf.parse(sdf.format(begin)); // 格式yyyyMMdd
        Date endDate = sdf.parse(sdf.format(end));
        return ((Long) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24))).intValue();
    }

}
