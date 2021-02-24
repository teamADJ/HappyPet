package com.adj.happypet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class RegisterOwnerFragment extends Fragment {

    private TextView edt_age_tv;
    private EditText edt_age;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_register_owner, viewGroup, false);

        edt_age_tv = v.findViewById(R.id.register_age_tv_owner);
        edt_age = v.findViewById(R.id.age_edt_regis_owner);

        edt_age.setVisibility(View.GONE);
        edt_age_tv.setVisibility(View.GONE);
        return v;
    }
}
