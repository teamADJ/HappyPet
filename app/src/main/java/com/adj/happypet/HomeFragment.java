package com.adj.happypet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Adapter.ArticleListAdapter;
import com.adj.happypet.Adapter.PetshopListUserAdapter;
import com.adj.happypet.Model.ArtikelModel;
import com.adj.happypet.Model.PetGroomingListUser;
import com.adj.happypet.Model.PetGrooming_list;
import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn_logout;
    private ProgressBar progressBar;
    private Button btn_update;
    private Button btn_updateProfile;
    TextView btn_findPetGroomer;

    private TextView tv_nama, tv_age, tv_email, banner, city;

    // Firebase
    private FirebaseUser fUser;
    private DatabaseReference userDBRef;
    private String userID;
    private FirebaseAuth mAuth;
    private AlertDialog.Builder email_dialog;
    LayoutInflater inflater;

    private CardView search_box;

    private String getEmail;
    private FirebaseFirestore db;

    private static final String email_email = "email";

    private DocumentReference documentReference;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private RecyclerView recyclerViewListGroomingUser, recyclerViewListArtikel;

    private PetshopListUserAdapter petshopListUserAdapter;
    private ArrayList<PetGroomingListUser> petGroomingListUsers;
    private ArrayList<ArtikelModel> artikelModels;
    private ArticleListAdapter articleListAdapter;

    AlertDialog.Builder dialog;

    View dialogView;


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
        //userID = mAuth.getUid();
        userID = fUser.getUid();

        db = FirebaseFirestore.getInstance();
        petGroomingListUsers = new ArrayList<>();
        artikelModels = new ArrayList<>();
        email_dialog = new AlertDialog.Builder(getActivity());
        inflater = this.getLayoutInflater();


    }


    public View onCreateView(final LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_home, viewGroup, false);

        search_box = v.findViewById(R.id.search_box);


        btn_update = v.findViewById(R.id.btn_update);
        btn_update.setVisibility(View.GONE);
        btn_logout = v.findViewById(R.id.btn_logout);
        btn_logout.setVisibility(View.GONE);
        btn_updateProfile = v.findViewById(R.id.btn_update_profile);
        btn_updateProfile.setVisibility(View.GONE);
        btn_findPetGroomer = v.findViewById(R.id.btn_findPetgroomer);
        city = v.findViewById(R.id.city);

        progressBar = v.findViewById(R.id.progressBar);
        tv_nama = v.findViewById(R.id.tv_fName_home);
        //  tv_age = v.findViewById(R.id.tv_age_home);
//        tv_email = v.findViewById(R.id.tv_email_home);
        banner = v.findViewById(R.id.happy_pet_banner);

        db.collection("Member").whereEqualTo("userId", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        tv_nama.setText((CharSequence) documentSnapshot.get("fullname"));
                        city.setText((CharSequence) documentSnapshot.get("city"));
//                        tv_email.setText((CharSequence) documentSnapshot.get("email"));
                    }

                    if(city.getText().toString().equals("") || city.getText().toString().equals("-")){
                        dialogMoveToUpdateProfile();
                    }
                }
            }
        });


        search_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PetGroomerList.class);
                startActivity(i);
            }
        });

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent keLogin = new Intent(getActivity(), UpdateProfileActivity.class);
//                keLogin.putExtra("email", tv_email.getText().toString().trim());
                startActivity(keLogin);
            }
        });

        btn_findPetGroomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PetGroomerList.class);
                startActivity(i);
            }
        });

//        cardlist artikel
        recyclerViewListArtikel = v.findViewById(R.id.recyclerViewListArtikel);
        RecyclerView.LayoutManager layoutManagerArtikel = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        articleListAdapter = new ArticleListAdapter(this, artikelModels);
        recyclerViewListArtikel.setLayoutManager(layoutManagerArtikel);
        recyclerViewListArtikel.setAdapter(articleListAdapter);
        showDataArtikel();

//        cardlist petshop
        recyclerViewListGroomingUser = v.findViewById(R.id.recyclerViewListGroomingUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewListGroomingUser.setLayoutManager(layoutManager);
        petshopListUserAdapter = new PetshopListUserAdapter(this, petGroomingListUsers);
        recyclerViewListGroomingUser.setAdapter(petshopListUserAdapter);
        showData();



        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent keLogin = new Intent(getActivity(), LoginActivity.class);
                keLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(keLogin);
            }
        });


//        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getActivity(),UpdateProfileActivity.class);
//                startActivity(i);
//            }
//        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent moveToUpdate = new Intent(getActivity(),UpdateEmail.class);
//                moveToUpdate.putExtra("email", tv_email.getText().toString().trim());
//                startActivity(moveToUpdate);
                final View view = inflater.inflate(R.layout.activity_update_email, null);
                email_dialog.setTitle("Update your Email").setMessage("Please enter your new Email!")
                        .setPositiveButton("Update Email", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText email = view.findViewById(R.id.et_update_email);
                                if (email.getText().toString().isEmpty()) {
                                    email.setError("Required Filled ");
                                    return;
                                }
                                getEmail = email.getText().toString().trim();
                                fUser = mAuth.getCurrentUser();
                                fUser.updateEmail(getEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getActivity(), "Reset email sent!", Toast.LENGTH_SHORT).show();
                                        final String emailUpdate = email.getText().toString().trim();

                                        DocumentReference updateData = db.collection("Member").document(userID);

                                        updateData.update(email_email, emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Updated Successfully",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        db.collection("Owner").document(userID).update("email", emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Updated Successfully",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel", null).setView(view).create().show();

            }
        });


        return v;
    }


    private void showData() {

        db.collection("Owner").whereEqualTo("status", "Recommended").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                petGroomingListUsers.clear();

                for(DocumentSnapshot documentSnapshot :task.getResult()){

                    PetGroomingListUser petGroomingList_Users = new PetGroomingListUser(documentSnapshot.getString("ownerId"),
                            documentSnapshot.getString("groomingshopname"),
                            documentSnapshot.getString("contact"),
                            documentSnapshot.getString("address"),
                            documentSnapshot.getString("description"),
                            documentSnapshot.getString("status")
                    );
                    //add model to list
                    petGroomingListUsers.add(petGroomingList_Users);
                }

                petshopListUserAdapter.notifyDataSetChanged();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Oppsss.... something wrong ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showDataArtikel() {

        db.collection("Artikel").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                artikelModels.clear();

                for(DocumentSnapshot documentSnapshot :task.getResult()){

                    ArtikelModel artikel_list = new ArtikelModel(documentSnapshot.getString("artikel_id"),
                           documentSnapshot.getString("description"),
                            documentSnapshot.getString("source"),
                            documentSnapshot.getString("title")
                    );
                    //add model to list
                    artikelModels.add(artikel_list);
                }

                articleListAdapter.notifyDataSetChanged();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Oppsss.... something wrong ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void dialogMoveToUpdateProfile() {

        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_alert_move_to_update_owner, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Alert");

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });


        dialog.show();

    }
}




