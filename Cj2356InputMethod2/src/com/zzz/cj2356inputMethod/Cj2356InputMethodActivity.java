package com.zzz.cj2356inputMethod;

import com.zzz.cj2356inputMethod.exception.CrashHandler;
import com.zzz.cj2356inputMethod.view.SettingLoayoutTabIniter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * 倉頡輸入法
 */
public class Cj2356InputMethodActivity extends Activity {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 爲了隱藏輸入法之一
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(R.layout.setting);
        mContext = Cj2356InputMethodActivity.this;

        TextView tvBtn = (TextView) findViewById(R.id.setsuMyouSimbBtn);
        tvBtn.setTextColor(Color.LTGRAY);

        Button setInputMethodBtn1 = (Button) findViewById(R.id.setInputMethodBtn1);
        setInputMethodBtn1.setTextColor(Color.DKGRAY);
        setInputMethodBtn1.setTextSize(16);
        setInputMethodBtn1.setOnClickListener(new OnClickSetInputMethodBtnListener1(this));

        Button setInputMethodBtn2 = (Button) findViewById(R.id.setInputMethodBtn2);
        setInputMethodBtn2.setTextColor(Color.DKGRAY);
        setInputMethodBtn2.setTextSize(16);
        setInputMethodBtn2.setOnClickListener(new OnClickSetInputMethodBtnListener2(this));

        SettingLoayoutTabIniter.initSettingLoayoutTab(mContext);

        // 爲了隱藏輸入法之二
        // 之三還要在xml中設置focusable和focusableInTouchMode為真
        InputMethodManager imm1 = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(getContentView().getWindowToken(), 0);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    /**
     * 上面有setContentView，模擬get
     * 
     * @author fsz
     * @time 2017年9月27日上午9:29:43
     * @return
     */
    private View getContentView() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        ViewGroup content = (ViewGroup) view.getChildAt(0);
        return content.getChildAt(0);
    }

}

class OnClickSetInputMethodBtnListener1 implements OnClickListener {

    private Context context;

    public OnClickSetInputMethodBtnListener1(Context con) {
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        // 隱藏輸入法
        v.requestFocus();
        toChooseInputMethod();
    }

    public void toChooseInputMethod() {
        ((Cj2356InputMethodActivity) context).startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
    }
}

class OnClickSetInputMethodBtnListener2 implements OnClickListener {

    private Context context;

    public OnClickSetInputMethodBtnListener2(Context con) {
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        // 隱藏輸入法
        v.requestFocus();
        showInputMethodPicker();
    }

    public void showInputMethodPicker() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showInputMethodPicker();
    }
}
