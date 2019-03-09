package com.zzz.cj2356inputMethod.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.zzz.cj2356inputMethod.dto.Item;

public class DateUtils {

    /** 格式化 */
    public static String formatDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format,
                    Locale.TRADITIONAL_CHINESE);
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
        Date now = new Date();
        if ("時間".equals(item.getCharacter()) || "時".equals(item.getCharacter())
                || "曆".equals(item.getCharacter())) {
            items.addAll(addFormatTimeItems(item, false));
        } else if ("时间".equals(item.getCharacter())
                || "时".equals(item.getCharacter())
                || "历".equals(item.getCharacter())) {
            items.addAll(addFormatTimeItems(item, true));
        } else if ("日期".equals(item.getCharacter())
                || "日".equals(item.getCharacter())) {
            items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                    "yyyy年MM月dd日")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                    "yyyy-MM-dd")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                    "yyyyMMdd")));
            // 回曆
            String huiliStr = IslamicCalendarUtil.getHuiLiStrByDate(now, false,
                    true, false);
            String huiliStrSim = IslamicCalendarUtil.getHuiLiStrByDate(now,
                    true, true, false);
            items.add(new Item(null, item.getGenCode(), null, huiliStr));
            items.add(new Item(null, item.getGenCode(), null, huiliStrSim));
            // 夏曆
            ArrayList<Item> items1 = addFormatChineseDateItems(item, now,
                    false, false);
            addDistinctItems2List(items1, items);
            ArrayList<Item> items2 = addFormatChineseDateItems(item, now, true,
                    false);
            addDistinctItems2List(items2, items);
        }
        // 三個夏曆時間
        else if ("夏".equals(item.getCharacter())) {
            ArrayList<Item> items1 = addFormatChineseDateItems(item, now,
                    false, true);
            addDistinctItems2List(items1, items);
            ArrayList<Item> items2 = addFormatChineseDateItems(item, now, true,
                    true);
            addDistinctItems2List(items2, items);
        } else if ("農".equals(item.getCharacter())
                || "農曆".equals(item.getCharacter())
                || "夏曆".equals(item.getCharacter())) {
            ArrayList<Item> items1 = addFormatChineseDateItems(item, now,
                    false, true);
            addDistinctItems2List(items1, items);
        } else if ("农".equals(item.getCharacter())
                || "农历".equals(item.getCharacter())
                || "夏历".equals(item.getCharacter())) {
            ArrayList<Item> items2 = addFormatChineseDateItems(item, now, true,
                    true);
            addDistinctItems2List(items2, items);
        } else if ("星期".equals(item.getCharacter())
                || "週".equals(item.getCharacter())
                || "周".equals(item.getCharacter())
                || "星".equals(item.getCharacter())) {
            items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                    "EEEE")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                    "EEEE").replace("星期", "週")));
            items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                    "EEEE").replace("星期", "周")));
        } else if ("時辰".equals(item.getCharacter())
                || "时辰".equals(item.getCharacter())
                || "辰".equals(item.getCharacter())) {
            boolean isSimp = "时辰".equals(item.getCharacter());
            items.addAll(addFormatHourItems(item, isSimp));
            if ("辰".equals(item.getCharacter())) {
                ArrayList<Item> items2 = addFormatHourItems(item, true);
                addDistinctItems2List(items2, items);
            }
        }
        // 回曆
        else if ("回".equals(item.getCharacter())
                || "伊".equals(item.getCharacter())) {
            String huiliStr = IslamicCalendarUtil.getHuiLiStrByDate(now, false,
                    true, true);
            String huiliStrSim = IslamicCalendarUtil.getHuiLiStrByDate(now,
                    true, true, true);
            items.add(new Item(null, item.getGenCode(), null, huiliStr));
            items.add(new Item(null, item.getGenCode(), null, huiliStrSim));
        } else if ("回曆".equals(item.getCharacter())
                || "伊斯蘭曆".equals(item.getCharacter())) {
            String huiliStr = IslamicCalendarUtil.getHuiLiStrByDate(now, false,
                    true, true);
            items.add(new Item(null, item.getGenCode(), null, huiliStr));
        } else if ("回历".equals(item.getCharacter())
                || "伊斯兰历".equals(item.getCharacter())) {
            String huiliStrSim = IslamicCalendarUtil.getHuiLiStrByDate(now,
                    true, true, true);
            items.add(new Item(null, item.getGenCode(), null, huiliStrSim));
        }

        // 另起一判斷
        // 共、民，共和國多少週年、民國多少年
        if ("共和國".equals(item.getCharacter())
                || "共和国".equals(item.getCharacter())
                || "共".equals(item.getCharacter())) {
            int gungYear = (Calendar.getInstance().get(Calendar.YEAR) - 1949);
            items.add(new Item(null, item.getGenCode(), null, "共和國" + gungYear
                    + "週年"));
            items.add(new Item(null, item.getGenCode(), null, "共和国" + gungYear
                    + "周年"));
        } else if ("民國".equals(item.getCharacter())
                || "民国".equals(item.getCharacter())
                || "民".equals(item.getCharacter())) {
            int minYear = (Calendar.getInstance().get(Calendar.YEAR) - 1911);
            items.add(new Item(null, item.getGenCode(), null, "民國" + minYear
                    + "年"));
            items.add(new Item(null, item.getGenCode(), null, "民国" + minYear
                    + "年"));
        }
        // 孔、孔子，孔子紀年多少年
        else if ("孔子".equals(item.getCharacter())
                || "孔".equals(item.getCharacter())) {
            int kungYear = (Calendar.getInstance().get(Calendar.YEAR) + 551);
            items.add(new Item(null, item.getGenCode(), null, "孔子紀元" + kungYear
                    + "年"));
            items.add(new Item(null, item.getGenCode(), null, "孔子纪元" + kungYear
                    + "年"));
        }
        // 漢、汉，漢元年以來紀年
        else if ("漢".equals(item.getCharacter())
                || "汉".equals(item.getCharacter())) {
            int hanYear = (Calendar.getInstance().get(Calendar.YEAR) + 206);
            items.add(new Item(null, item.getGenCode(), null, "漢" + hanYear
                    + "年"));
            items.add(new Item(null, item.getGenCode(), null, "汉" + hanYear
                    + "年"));
        }
        // 周秦唐宋明
        else if ("周".equals(item.getCharacter())) {
            int year = (Calendar.getInstance().get(Calendar.YEAR) + 1046);
            items.add(new Item(null, item.getGenCode(), null, "周" + year + "年"));
        } else if ("秦".equals(item.getCharacter())) {
            int year = (Calendar.getInstance().get(Calendar.YEAR) + 221);
            items.add(new Item(null, item.getGenCode(), null, "秦" + year + "年"));
        } else if ("唐".equals(item.getCharacter())) {
            int year = (Calendar.getInstance().get(Calendar.YEAR) - 617);
            items.add(new Item(null, item.getGenCode(), null, "唐" + year + "年"));
        } else if ("宋".equals(item.getCharacter())) {
            int year = (Calendar.getInstance().get(Calendar.YEAR) - 959);
            items.add(new Item(null, item.getGenCode(), null, "宋" + year + "年"));
        } else if ("明".equals(item.getCharacter())) {
            int year = (Calendar.getInstance().get(Calendar.YEAR) - 1367);
            items.add(new Item(null, item.getGenCode(), null, "明" + year + "年"));
        }
        return items;
    }

    /**
     * 加入元素到列表，列表已存在，不加入
     * 
     * @author fszhouzz@qq.com
     * @time 2018年6月5日 下午11:00:23
     * @param items1
     *            源列表
     * @param items
     *            目標列表
     */
    private static void addDistinctItems2List(ArrayList<Item> items1,
            ArrayList<Item> items) {
        if (null != items && null != items1 && !items1.isEmpty()) {
            for (Item it1 : items1) {
                boolean exists = false;
                for (Item it : items) {
                    if (it.getCharacter().equals(it1.getCharacter())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    items.add(it1);
                }
            }
        }
    }

    /**
     * 得到夏曆時間字符串
     * 
     * @author fszhouzz@qq.com
     * @time 2018年6月5日 下午10:52:42
     * @param item
     *            原節點
     * @param now
     *            當前時間，不傳入則默認當前時間
     * @param isSimp
     *            是否簡化字
     * @param withTime
     *            是否加入時間
     * @return
     */
    private static ArrayList<Item> addFormatChineseDateItems(Item item,
            Date now, boolean isSimp, boolean withTime) {
        ArrayList<Item> items = new ArrayList<Item>();
        if (null == now) {
            now = new Date();
        }
        try {
            String chineseDate = HialiUtils.getChineseCalByWest(now);
            String ganzhi = HialiUtils.getGanZhiByChinesYear(Integer
                    .parseInt(HialiUtils.replaceChinaNumberByArab(chineseDate
                            .split("年")[0].replace("前", "-"))));
            ganzhi += "年";
            String dateGanzhiStr = DateGanzhiTest.getDateGanzhi(now) + "日";
            if (withTime) {
                dateGanzhiStr += DateGanzhiTest.getHourGanzhi(now)
                        + (isSimp ? "时" : "時");
                dateGanzhiStr += DateGanzhiTest.getQuarterTimeStr(now);
            }
            // items.add(new Item(null, item.getGenCode(), null,
            // formatDate(now, "yyyy年") + ganzhi + chineseDate.split("年")[1] +
            // dateGanzhiStr));
            items.add(new Item(null, item.getGenCode(), null, (isSimp ? "夏历"
                    : "夏曆")
                    + chineseDate.split("年")[0]
                    + "年"
                    + ganzhi
                    + chineseDate.split("年")[1] + dateGanzhiStr));
        } catch (Exception e) {
        }
        return items;
    }

    /**
     * 加入格式化的時辰
     * 
     * @param item
     *            節點參數
     * @param isSimp
     *            是否簡化字
     * @return 在他末尾加入新生成的時辰節點
     */
    private static ArrayList<Item> addFormatHourItems(Item item, boolean isSimp) {
        ArrayList<Item> items = new ArrayList<Item>();
        try {
            Date now = new Date();
            List<String> shis = new ArrayList<String>();
            String hourGz = DateGanzhiTest.getHourGanzhi(now);
            String quarterTimeStr = DateGanzhiTest.getQuarterTimeStr(now);
            shis.add(hourGz + (isSimp ? "时" : "時"));
            String couZing = quarterTimeStr.startsWith("正") ? "正" : "初";
            shis.add(hourGz + (isSimp ? "时" : "時") + couZing);
            shis.add(hourGz + (isSimp ? "时" : "時") + quarterTimeStr);
            for (String str : shis) {
                items.add(new Item(null, item.getGenCode(), null, str));
            }
        } catch (Exception e) {
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
        items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                "yyyy年MM月dd日HH" + (isSimp ? "时" : "時") + "mm分ss秒")));
        items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                "yyyy-MM-dd HH:mm:ss")));
        items.add(new Item(null, item.getGenCode(), null, formatDate(now,
                "yyyyMMddHHmmssSSS")));
        // 回曆
        String huiliStr = IslamicCalendarUtil.getHuiLiStrByDate(now, isSimp,
                true, true);
        items.add(new Item(null, item.getGenCode(), null, huiliStr));
        // 夏曆
        ArrayList<Item> items1 = addFormatChineseDateItems(item, now, isSimp,
                true);
        addDistinctItems2List(items1, items);
        return items;
    }

    /** 兩個日期相隔天數，日期不同就算一天 */
    public static int datesMoreAfterBegin(Date begin, Date end)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = sdf.parse(sdf.format(begin)); // 格式yyyyMMdd
        Date endDate = sdf.parse(sdf.format(end));
        return ((Long) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24)))
                .intValue();
    }

    /** 兩個日期相差天數，滿一天才算一天 */
    public static int daysMoreAfterBegin(Date begin, Date end) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date beginDate = sdf.parse(sdf.format(begin)); // 格式yyyyMMdd
        Date endDate = sdf.parse(sdf.format(end));
        return ((Long) ((endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24)))
                .intValue();
    }

}
