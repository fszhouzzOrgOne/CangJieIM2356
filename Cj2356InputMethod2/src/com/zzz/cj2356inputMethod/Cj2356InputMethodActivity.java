package com.zzz.cj2356inputMethod;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * 倉頡輸入法
 */
public class Cj2356InputMethodActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        TextView setTextview = (TextView) findViewById(R.id.setsuMyouTextview);
        setTextview
                .setText("倉頡輸入法2356說明："
                        + "\n    點“英/倉/牠”，切換中英文等輸入種類。"
                        + "\n    英文狀態下，點“aa/Aa/AA”，切換大小寫。"
                        + "\n    倉頡狀態下，可以切換“倉頡二代”“倉頡三代”“倉頡五代”“蒼頡六代”“微軟倉頡”“雅虎奇摩”等輸入。" 
                        + "\n    其牠狀態下，可以切換“普語拼音”“注音符號”“日文假名”“圈點滿文”“朝鮮諺文”等輸入。"
                        + "“普語拼音”只能打单字，且用字母m的個數表示聲調，五個m表示輕聲；" +
                        "“注音符號”只能打出對應符號，不能打漢字。"
                        + "\n    候選欄中，有左右箭頭，可以左右移動，選字；有向下的箭頭，隱藏輸入法。"
                        + "打出“時間”或“日期”“星期”“時辰”“時”“辰”“週”“日”等詞，會列出幾種格式的當前時間供選。"
                        + "版本v2加了自定義字體，在輸入法中會展示到統一碼擴展E區的漢字，版本v1用系统默認字體，但兼容性好些。"
                        + "\n    點“12”，進入數字鍵盤，一般鍵盤上所有的符號都在。"
                        + "\n    點“符”進入符號鍵盤，可以在選項卡選種類，選項卡可以左右滑動，找符號可以上下翻頁。"
                        + "\n    請在輸入法設置中選擇本輸入法，願使用愉快！（製作人：日月遞炤 fszhouzz@qq.com）"
                        + "\n    版本更新："
                        + "\n    版本1.9、2.7，打詞語“時辰”，列出幾種當前時辰格式供選；調整輸入法分類，分英文、倉頡、其他三種。"
                        + "\n    版本1.10、2.8，加入了四角號碼輸入法，可以打詞組；各輸入法打詞語“日期”“時間”，列出陰曆干支年月日時供選，" +
                        "格式如“2017年丁酉正月十五日”“夏曆四七一五年丁酉正月十五日”" +
                        "“2017年丁酉正月十五日午初四刻”“夏曆四七一五年丁酉正月十五日午初四刻”。"
                        + "\n    版本1.11、2.9，修正詞組編碼错誤；四角號碼輸入法，加到兩萬七千字，排序方法也修改了；拼音打字，加了聲調。"
                        + "\n    版本1.12、2.10，修正刪除鍵的問題，原先只能刪除單個字符，多謝鬼島人指錯。"
                        + "\n    版本1.13、2.11，解決了有的手機輸入法總是全屛的問題：各組件全固定高度，然後全局都用內容定高度；符號鍵盤上的選項卡標題樣式優化。"
                        + "\n    版本1.14、2.12，鍵盤可以選擇常規QWERTY鍵盤、ABC順序鍵盤；候選欄優化，有候選时顯示候選字，沒有就顯示幾個控制按鈕。"
                        + "\n    版本1.15、2.13，優化有的手機不能用下拉框的問題，多謝榕樹葉指出異常；優化鍵盤有時會空白的問題，多謝鬼島人指錯；符號鍵盤加入日文假名。"
                        + "\n    版本1.16、2.14，加入了雅虎奇摩、微軟倉頡兩個輸入法，多謝Ejsoon的建議。" +
                        "空格鍵上展示輸入法名字；加入韓文符號。所有詞組編碼都刪除了，瘦身；" +
                        "打字“時”“辰”“週”“日”等會列出幾種格式的當前時間供選，因沒有詞組而新加。"
                        + "\n    版本1.17、2.15，修改六代碼表某字錯誤。夏曆計算方法改正。"
                        + "\n    版本1.18、2.16，修改三代碼表疒字頭字數處錯誤；加入八卦符號，用日月或一金、廿金編碼。" +
                        "加入韓文輸入法，多謝榕樹葉幫忙編輯碼表。四角號碼輸入法改成常規的QWERTY鍵盤。"
                        + "\n    版本1.19、2.17，修改韓文輸入法碼表錯誤，日文輸入法加入2000常用漢字，" +
                        "蒼頡六代編碼了統一碼兼容漢字和兼容擴展，及部首、注音符號等。"
                        + "\n    版本1.20、2.20，小版本號統一下。修改韓文輸入法碼表錯誤，蒼六修改自編碼的若干符號編碼。" +
                        "有網友硬要糾結注音不是註音，今改之以杜閒口。"
                        + "\n    版本1.21、2.21，增加蒼六幾個字編碼，韓文和日文符號編碼修改。鍵盤上的刪除及換行鍵用了自定義圖標。加了一些符號。" +
                        "鍵盤上的各個按鈕都用了自定義背景，所有系統都會是同樣的界面了。"
                        + "\n    版本1.22、2.22，蒼六修改了幾個字的編碼，如叔字的異體“廾加四點”等。" +
                        "倉頡用的原都不是我做的碼表，以上説的修改，其實都是增加編碼。" +
                        "各輸入法候選字排序優化，常用字按使用頻率排序，四角、拼音、日文等輸入法效果明顯，注意拼音用字母m的個數表示聲調。" +
                        "倉頡碼表優化，取出各版本編碼相同者，另作一交集類，查字時都查詢本類和交集中的數據，又瘦些了。"
                        + "\n    版本1.23、2.23，修改蒼六編碼一個錯誤。以後修改編碼都不爲版本作說明。"
                        + "\n    版本1.25、2.25，加入圈點滿文輸入法，只能打些常用字母和簡單音節，不能連詞成句。標點符號在q中，q不用於拼寫。" +
                        "帶'號的使用雙字母，還有雙n。漢語zh ch sh r z c s用jy cy xi z dz ts sy。kgh的不規則拼寫，提供候選項。"
                        + "\n    版本1.26、2.26，加點注意符號，日文中兩點都用y等。"
                        + "\n    版本1.27、2.27，調整幾個字候選排序。英文符號排序。順序鍵盤修改，鍵盤去掉數字。進入符號和數字鍵盤加快。多謝鬼島人的建議。"
                        + "\n    版本28，更改版本號規則，28.1版本默認字體，28.2版本自定義字體，28.2.1純六代，28.2.2純六代加詞組。" +
                        "修改正在編輯內容展示方法，更改若干字排序，數字鍵盤調整，修改幾個符號。" +
                        "解決問题候選區底隱顯，使其牠應用老是上下移動，使覆蓋輸入法後應用底高度和主要內容區一致卽可。"
                        + "\n    版本29，更改版本號規則，29.1版本默認字體，29.2版本自定義字體，29.2.1純六代，29.2.2純六代加詞組。" +
                        "倉頡輸入法、日文不模糊提示，打字快很多了。拼音只提示本拼音底字，如ha不提示hai。其牠輸入法還是帶上模糊提示，有時卡一卡也不管了。"
                        );

        Button setInputMethodBtn = (Button) findViewById(R.id.setInputMethodBtn);
        setInputMethodBtn
                .setOnClickListener(new OnClickSetInputMethodBtnListener(this));
    }

}

class OnClickSetInputMethodBtnListener implements OnClickListener {

    private Context context;

    public OnClickSetInputMethodBtnListener(Context con) {
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showInputMethodPicker();
    }
}
