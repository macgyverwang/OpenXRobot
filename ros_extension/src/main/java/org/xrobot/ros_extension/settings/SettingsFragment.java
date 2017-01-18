package org.xrobot.ros_extension.settings;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    private EditText mIPThird, mIPFour;
    private Button mBtnConfirm;
    private TextView mTextStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.setting, container, false);
        mIPThird = (EditText)view.findViewById(R.id.ip1);
        mIPFour = (EditText)view.findViewById(R.id.ip2);
        mBtnConfirm = (Button) view.findViewById(R.id.ok);
        mTextStatus = (TextView) view.findViewById(R.id.status);

        final String uri = sharedPreferences.getString(Settings.MASTER_IP, null);
        if (uri != null)
            mTextStatus.setText(uri);

        SharedPreferences sharedPreferences =  = getSharedPreferences();

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String ip = mIPThird.getText().toString() + "." + mIPFour.getText().toString();
                if (ip != null) {
                    ip = "http://192.168." + ip + ":11311";
                    editor.putString(Settings.MASTER_IP, ip);
                    editor.apply();
                    mTextStatus.setText("Status: Master_URI = " + ip);
                    Toast.makeText(getContext(), "master uri = " + ip, Toast.LENGTH_LONG).show();
                }else{
                    mTextStatus.setText("ip is invailid");}
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences();
        final String uri = sharedPreferences.getString(Settings.MASTER_IP, null);
        if (uri != null)
            mTextStatus.setText("Status: Master_URI = " + uri);
    }

    private SharedPreferences getSharedPreferences() {
        return getActivity().getApplicationContext().getSharedPreferences(Settings.KEY_MASTER_URI, Context.MODE_PRIVATE);
    }
}
