package com.example.chatapp.ksk.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatapp.R;
import com.example.chatapp.ksk.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    Button sendMessageBtn;
    RecyclerView messagesRv;
    EditText messageTv;
    String receiverId;
    FirebaseUser senderUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        sendMessageBtn = findViewById(R.id.sendMessageBtn);

        sendMessageBtn.setOnClickListener(v -> {
            String msg = messageTv.getText().toString();
            if (!msg.isEmpty()) {
                sendMessage(senderUser.getUid(), receiverId, msg);
                messageTv.setText("");
            }
        });

        messageTv = findViewById(R.id.messageTv);
        messagesRv = findViewById(R.id.messagesRv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        messagesRv.setLayoutManager(llm);

        receiverId = getIntent().getStringExtra("receiverId");
        senderUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(receiverId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }

    private void sendMessage(String senderUserUid, String receiverId, String msg) {

    }
}