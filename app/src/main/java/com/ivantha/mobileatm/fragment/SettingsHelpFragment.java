package com.ivantha.mobileatm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivantha.mobileatm.R;

public class SettingsHelpFragment extends Fragment {

    public SettingsHelpFragment() {

    }

    public static SettingsHelpFragment newInstance() {
        SettingsHelpFragment fragment = new SettingsHelpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_help, container, false);
    }
}