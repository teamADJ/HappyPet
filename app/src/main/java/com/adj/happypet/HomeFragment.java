package com.adj.happypet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn_logout;
    private ProgressBar progressBar;
    private Button btn_update;

    private TextView tv_nama, tv_age, tv_email, banner ;

    // Firebase
    private FirebaseUser fUser;
    private DatabaseReference userDBRef;
    private String userID;
    private FirebaseAuth mAuth;
    private AlertDialog.Builder email_dialog;
    LayoutInflater inflater;


    public static Fragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        //initialize Firebase ,Database Ref, get UserID
        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member");
        userID = fUser.getUid();

        email_dialog = new AlertDialog.Builder(getActivity());
        inflater = this.getLayoutInflater();




    }


    public View onCreateView(final LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View v =  inflater.inflate(R.layout.fragment_bottom_home, viewGroup, false);


        btn_update = v.findViewById(R.id.btn_update);
        btn_logout = v.findViewById(R.id.btn_logout);

        progressBar = v.findViewById(R.id.progressBar);
        tv_nama = v.findViewById(R.id.tv_fName_home);
        tv_age = v.findViewById(R.id.tv_age_home);
        tv_email = v.findViewById(R.id.tv_email_home);
        banner = v.findViewById(R.id.happy_pet_banner);






        userDBRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                User userPofile = dataSnapshot.getValue(User.class);

                if(userPofile != null){
                    String fullname = userPofile.getFullName();
                    String age = userPofile.getAge();
                    String email = userPofile.getEmail();

                    banner.setText("Welcome " + fullname + "!");
                    tv_nama.setText(fullname);
                    tv_age.setText(age);
                    tv_email.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent keLogin = new Intent(getActivity(),LoginActivity.class);
                startActivity(keLogin);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent moveToUpdate = new Intent(MainActivity.this,UpdateEmail.class);
//                startActivity(moveToUpdate);
                final View view = inflater.inflate(R.layout.activity_update_email,null);
                email_dialog.setTitle("Update your Email").setMessage("Please enter your new Email!")
                        .setPositiveButton("Update Email", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText email = view.findViewById(R.id.et_update_email) ;
//                                HashMap hashMap = new HashMap();
//                                hashMap.put("email",email);
                                //  hashMap.put("id",userID);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Required Filled ");
                                    return;
                                }

//                                userDBRef.child("Member").updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
//                                    @Override
//                                    public void onComplete(@NonNull Task task) {
//                                        Toast.makeText(MainActivity.this, "Success Update", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(MainActivity.this, "Check", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
                                //send reset link udah tpi DB belum ke update
                                fUser = mAuth.getCurrentUser();
                                fUser.updateEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity(), "Reset email sent!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cacel",null).setView(view).create().show();

            }
        });


        return v;
    }
}