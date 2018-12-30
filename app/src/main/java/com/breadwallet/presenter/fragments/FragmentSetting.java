package com.breadwallet.presenter.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.breadwallet.R;
import com.breadwallet.presenter.activities.settings.SettingsActivity;
import com.breadwallet.presenter.customviews.BaseTextView;
import com.breadwallet.presenter.entities.BRSettingsItem;
import com.breadwallet.tools.adapter.SettingsAdapter;
import com.breadwallet.tools.util.SettingsUtil;
import com.breadwallet.wallet.WalletsMaster;
import com.breadwallet.wallet.abstracts.BaseWalletManager;

import java.util.ArrayList;
import java.util.List;

public class FragmentSetting extends Fragment {

    private static final String TAG = FragmentSetting.class.getSimpleName();
    public static final String EXTRA_MODE = "com.breadwallet.presenter.activities.settings.EXTRA_MODE";
    public static final String MODE_SETTINGS = "settings";
    public static final String MODE_PREFERENCES = "preferences";
    public static final String MODE_SECURITY = "security";
    public static final String MODE_CURRENCY_SETTINGS = "currency_settings";
    private boolean mIsButtonBackArrow;
    private View mRootview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootview = inflater.inflate(R.layout.activity_settings, container, false);
        return mRootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleAndList(mRootview);
    }

    private void setTitleAndList(View rootView) {
        BaseTextView title = rootView.findViewById(R.id.title);
        ListView settingsList = rootView.findViewById(R.id.settings_list);
        List<BRSettingsItem> settingsItems = new ArrayList<>();
        String mode = SettingsActivity.MODE_SETTINGS;
        if (mode == null) {
            throw new IllegalArgumentException("Need mode for the settings activity");
        }
        switch (mode) {
            case MODE_SETTINGS:
                settingsItems = SettingsUtil.getMainSettings(getActivity());
                title.setText(getString(R.string.Settings_title));
                mIsButtonBackArrow = false;
                break;
            case MODE_PREFERENCES:
                settingsItems = SettingsUtil.getPreferencesSettings(getActivity());
                title.setText(getString(R.string.Settings_preferences));
                mIsButtonBackArrow = true;
                break;
            case MODE_SECURITY:
                settingsItems = SettingsUtil.getSecuritySettings(getActivity());
                title.setText(getString(R.string.MenuButton_security));
                mIsButtonBackArrow = true;
                break;
            case MODE_CURRENCY_SETTINGS:
                BaseWalletManager walletManager = WalletsMaster.getInstance(getActivity()).getCurrentWallet(getActivity());
                settingsItems = walletManager.getSettingsConfiguration().getSettingsList();
                String currencySettingsLabel = String.format("%s %s", walletManager.getName(), getString(R.string.Settings_title));
                title.setText(currencySettingsLabel);
                mIsButtonBackArrow = true;
                break;
        }

        settingsList.setAdapter(new SettingsAdapter(getActivity(), R.layout.settings_list_item, settingsItems));
    }


    public static FragmentSetting newInstance(String text) {

        FragmentSetting f = new FragmentSetting();
        Bundle b = new Bundle();
        b.putString("text", text);
        f.setArguments(b);

        return f;
    }

}
