package com.zzz.cj2356inputMethod.listener;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.View.OnClickListener;

public class OnHideClickListener implements OnClickListener {

    private Context context;

    public OnHideClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        ((InputMethodService) context).hideWindow();
    }

}
