package com.breadwallet.presenter.activities.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.breadwallet.R;
import com.breadwallet.presenter.activities.util.BRActivity;

public class SignDetailActivity extends BRActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_details_layout);
    }
}