package com.adj.happypet.Owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.adj.happypet.BottomNavigationActivity;
import com.adj.happypet.R;


public class InboxOwnerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static Fragment newInstance(String param1, String param2) {
        InboxOwnerFragment fragment = new InboxOwnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_bottom_inbox_owner, viewGroup, false);

        //        toolbar
        Toolbar inbox_owner_toolbar = v.findViewById(R.id.inbox_owner_toolbar);
        ((BottomNavigationOwnerActivity)getActivity()).setSupportActionBar(inbox_owner_toolbar);
        ((BottomNavigationOwnerActivity) getActivity()).getSupportActionBar().setTitle("Inbox");

        return v;
    }
}
