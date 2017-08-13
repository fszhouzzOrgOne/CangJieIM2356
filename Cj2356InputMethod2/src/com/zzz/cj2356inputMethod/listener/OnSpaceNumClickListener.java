package com.zzz.cj2356inputMethod.listener;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

public class OnSpaceNumClickListener implements OnClickListener {

    private Context context;

    public OnSpaceNumClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        InputConnection inputConnection = ((InputMethodService) context)
                .getCurrentInputConnection();
        inputConnection.commitText(" ", 1);
    }

}
