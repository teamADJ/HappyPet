package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateEmail extends AppCompatActivity {

    private static final java.lang.String TAG ="" ;
    private EditText edt_email;
    private Button btn_update;
    private TextView view_email;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private FirebaseFirestore db;
    private String userID;
   // private DocumentReference documentReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        edt_email = findViewById(R.id.et_update_email);
//        view_email = findViewById(R.id.view_email);
        btn_update = findViewById(R.id.btn_update);

        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = fUser.getUid();
        db = FirebaseFirestore.getInstance();
  //      documentReference = db.collection("Member").document();

        Bundle bundle = getIntent().getExtras();
        String gEmail = bundle.getString("email");

        //set text edittext

        if(fUser != null){
            if(bundle != null){
                view_email.setText(gEmail);
            }else{

            }
        }else{

        }



//        btn_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(edt_email.getText().toString().isEmpty()){
//                    Toast.makeText(UpdateEmail.this, "Tidak boleh kosong!", Toast.LENGTH_SHORT).show();
//
//                }
//
//                final String email = edt_email.getText().toString().trim();
//                fUser.updateEmail(userID).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//
//                        //untuk update email member
//                        db.collection("Member").document(userID).update("email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(UpdateEmail.this, "Updated Successfully",
//                                        Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(UpdateEmail.this, BottomNavigationActivity.class);
//                                startActivity(i);
//                            }
//                        });
//
//                        //untuk update email owner
//                        db.collection("Owner").document(userID).update("email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(UpdateEmail.this, "Updated Successfully",
//                                        Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(UpdateEmail.this, BottomNavigationActivity.class);
//                                startActivity(i);
//                            }
//                        });
//
//                    }
//                });
//            }
//        });
    }

    private void updateEmail() {


//            db.collection("Member").document(userID).update("email", email).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(UpdateEmail.this, "Email member berhasil diganti", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(UpdateEmail.this, "Gagal update email member!", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            db.collection("Owner").document(userID).update("email", email).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(UpdateEmail.this, "Email owner berhasil diganti", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(UpdateEmail.this, "Gagal update email owner!", Toast.LENGTH_SHORT).show();
//                }
//            });




//        user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//
//
//
//                DocumentReference documentReferenceMember = db.collection("Member").document(documentIdMember);
//                Map<String,Object> update = new HashMap<>();
//                update.put("email",email);
//                documentReference.update(update);
//                Toast.makeText(UpdateEmail.this, "Email berhasil diganti!", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(UpdateEmail.this,e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });




    }
}




//   db.collection("Member").document(id).update("email",email)
//           .addOnCompleteListener(new OnCompleteListener<Void>() {
//@Override
//public void onComplete(@NonNull Task<Void> task) {
//        if(task.isSuccessful()){
//        Toast.makeText(UpdateEmail.this, "Sukses Update", Toast.LENGTH_SHORT).show();
//        }else {
//        Toast.makeText(UpdateEmail.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        }
//        }).addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Toast.makeText(UpdateEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//        });