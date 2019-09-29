package com.recognition.demo.ui.activity;

import android.view.View;

import com.recognition.demo.R;
import com.recognition.demo.common.MyActivity;

import butterknife.OnClick;

public class Accountsecurity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.password_reset,R.id.email_reset,R.id.phone_reset})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.password_reset:
                startActivity(PasswordResetActivity.class);
                break;
            case R.id.email_reset:
                break;
            case R.id.phone_reset:
                break;

        }

    }
}
