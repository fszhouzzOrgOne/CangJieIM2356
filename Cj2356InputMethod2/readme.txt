
換行鍵和回車鍵，用1畵圖，再用80畵陰影。
/Cj2356InputMethod2/assets/database/cjmbdb.db，需要用myweb工程中的類生成。
/Cj2356InputMethod2/androidzzz.keystore是签名文件，密碼是hello1234。

x.1版本，默认字體：
刪除字體文件，
刪除類FontManager。

純六代：
InputMethodStatusCnCj6#getNextStatus()切換狀態只能是蒼六。
字典SettingDictMbUtils.selectDbByChar和selectDbByCode兩個查詢，只查蒼六的。

鬼島人的三代要求：
順序鍵盤不要
12鍵不要
符號鍵盤的12鍵隱藏
只留下三代
三代Z鍵去符號鍵盤
符號鍵盤只要中文、英文、特殊、製表
時間提示也不要

倉頡三五：
三五代的碼表混合
其他輸入法不要
符號鍵盤只要中文、部首、英文
順序鍵盤不要
時間提示也不要
出個ANSI字集的，用默認字體


