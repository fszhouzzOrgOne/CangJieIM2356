安卓7字典不能用：查詢框沒有。

配置文件assets\config\cangjie2356.config，可以指定包含哪些輸入法，字典也以此爲準。
換行鍵和回車鍵，用1畵圖，再用80畵陰影。
/Cj2356InputMethod2/assets/database/cjmbdb.db，需要用myweb工程中的類生成。
/Cj2356InputMethod2/androidzzz.keystore是签名文件，密碼是hello1234。

x.1版本，默认字體：
刪除字體文件，
刪除類FontManager。

純六代：
assets\config\cangjie2356.config只有cj6，卽：
im.order.cj=cj6

倉頡三五：
三五代的碼表混合
assets\config\cangjie2356.config只有cj6，卽：
im.order.cj=cj35

給鬼島人：
純五代，
assets\config\cangjie2356.config只有cj5，卽：
im.order.cj=cj5
其牠輸入法都不要
碼表用這個：https://github.com/Jackchows/Cangjie5
字的話只要基本區跟A區，安卓4.4.2
符號界面中只要中文、英文符號


