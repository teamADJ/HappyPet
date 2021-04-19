package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.Adapter.ChatAdapter;
import com.adj.happypet.Model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatInUser extends AppCompatActivity {

    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser fuser;
    RecyclerView rvChat;
    EditText etTypeMsg;
    Button sendChatBtn;
    String userId;
    ChatAdapter chatAdapter;
    List<Chat> chatList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_in_user);

        rvChat = findViewById(R.id.rvChat);
        etTypeMsg = findViewById(R.id.etTypeMsg);
        sendChatBtn = findViewById(R.id.sendChatBtn);

        rvChat.setHasFixedSize(true);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager =new LinearLayoutManager(
                ChatInUser.this,LinearLayoutManager.VERTICAL,false
        );
        layoutManager.setStackFromEnd(true);
        rvChat.setLayoutManager(layoutManager);

        //        toolbar
        Toolbar inbox_toolbar = findViewById(R.id.chat_user_toolbar);
        setSupportActionBar(inbox_toolbar);
        getSupportActionBar().setTitle("Joec Lim");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        userId = fuser.getUid();

        readMessage(fuser.getUid(), "djMBm0lIhEaOuFIT85LZxsGghIs1");

        sendChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etTypeMsg.getText().toString();
                if(!msg.equals("")){
                    etTypeMsg.getText().clear();
                    sendMessage(fuser.getUid(), "djMBm0lIhEaOuFIT85LZxsGghIs1", msg);
                }else{
                    Toast.makeText(ChatInUser.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message){

        databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        databaseReference.child("Chats").push().setValue(hashMap);
    }

    private  void readMessage(final String myId, final String userId){
        chatList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                    chat.getReceiver().equals(userId) && chat.getSender().equals(myId)){

                        chatList.add(chat);

                    }

                    chatAdapter = new ChatAdapter(ChatInUser.this, chatList);
                    rvChat.setAdapter(chatAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}