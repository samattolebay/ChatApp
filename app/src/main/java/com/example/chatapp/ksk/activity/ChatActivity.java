package com.example.chatapp.ksk.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.ksk.adapters.ChatAdapter;
import com.example.chatapp.models.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    Button sendMessageBtn;
    RecyclerView messagesRv;
    EditText messageTv;
    String receiverId;
    String orderId;
    FirebaseUser senderUser;
    DatabaseReference reference;
    ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        receiverId = getIntent().getStringExtra("receiverId");
        orderId = getIntent().getStringExtra("orderId");
        senderUser = FirebaseAuth.getInstance().getCurrentUser();

        sendMessageBtn = findViewById(R.id.sendMessageBtn);

        sendMessageBtn.setOnClickListener(v -> {
            String msg = messageTv.getText().toString().trim();
            if (!msg.isEmpty()) {
                sendMessage(senderUser.getUid(), receiverId, orderId, msg);
                messageTv.setText("");
                hideKeyboard();
            }
        });

        messageTv = findViewById(R.id.messageTv);
        messagesRv = findViewById(R.id.messagesRv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        messagesRv.setLayoutManager(llm);

        reference = FirebaseDatabase.getInstance().getReference("Users").child(receiverId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessage(senderUser.getUid(), receiverId, orderId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void readMessage(String senderUserUid, String receiverId, String orderId) {
        List<ChatMessage> messages = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                snapshot.getRef().removeValue();
                messages.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String sId = Objects.requireNonNull(child.child("senderId").getValue()).toString();
                    String rId = Objects.requireNonNull(child.child("receiverId").getValue()).toString();
                    String oId = Objects.requireNonNull(child.child("orderId").getValue()).toString();
                    String msg = Objects.requireNonNull(child.child("message").getValue()).toString();
                    String dt = Objects.requireNonNull(child.child("dateTime").getValue()).toString();
                    if (oId.equals(orderId)) {
//                        if (sId.equals(senderUserUid))
                        messages.add(new ChatMessage(sId, rId, msg, dt, oId));
//                        else if (sId.equals(receiverId))
//                            messages.add(new ChatMessage(rId, sId, msg, dt, oId));
                    }
                }
                messageAdapter = new ChatAdapter(messages, "Sol adam", senderUserUid);
                messagesRv.setAdapter(messageAdapter);
                messagesRv.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String senderUserUid, String receiverId, String orderId, String msg) {
        reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("senderId", senderUserUid);
        hashMap.put("receiverId", receiverId);
        hashMap.put("orderId", orderId);
        hashMap.put("message", msg);
        Date date = Calendar.getInstance().getTime();
        String month = DateFormat.getDateTimeInstance().format(date);
        hashMap.put("dateTime", month);
        reference.child("Chats").push().setValue(hashMap);

        // todo consider to send notification
    }
}