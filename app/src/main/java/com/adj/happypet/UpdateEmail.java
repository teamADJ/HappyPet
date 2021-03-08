package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateEmail extends AppCompatActivity {

    private static final java.lang.String TAG ="" ;
    private EditText edt_email;
    private Button btn_update;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
   // private DocumentReference documentReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        edt_email = findViewById(R.id.et_update_email);
        btn_update = findViewById(R.id.btn_update);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
  //      documentReference = db.collection("Member").document();

        if(user != null){
            Log.d(TAG, "onCreate: " +user.getEmail());
            if(user.getEmail() !=null){
                edt_email.setText(user.getEmail());
                edt_email.setSelection(user.getEmail().length());
            }
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmail();
            }
        });
    }

    private void updateEmail() {
        if(edt_email.getText().toString().isEmpty()){
            Toast.makeText(this, "Tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String email = edt_email.getText().toString();
        user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DocumentReference documentReference = db.collection("Member").document(user.getUid());
                Map<String,Object> update = new HashMap<>();
                update.put("email",email);
                documentReference.update(update);
                Toast.makeText(UpdateEmail.this, "Email berhasil diganti!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateEmail.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



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