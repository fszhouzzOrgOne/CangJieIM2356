package com.zzz.cj2356inputMethod;

import com.zzz.cj2356inputMethod.view.SettingLoayoutTabIniter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * 倉頡輸入法
 */
public class Cj2356InputMethodActivity extends Activity {

    private Context mContext;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        mContext = Cj2356InputMethodActivity.this;

        Button setInputMethodBtn = (Button) findViewById(R.id.setInputMethodBtn);
        setInputMethodBtn.setTextColor(Color.DKGRAY);
        setInputMethodBtn.setTextSize(16);
        setInputMethodBtn
                .setOnClickListener(new OnClickSetInputMethodBtnListener(this));
        
        try {
            SettingLoayoutTabIniter.initSettingLoayoutTab(mContext);
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
