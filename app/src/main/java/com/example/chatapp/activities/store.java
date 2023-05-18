//package com.example.chatapp.activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//
//import com.example.chatapp.R;
//
//public class store extends AppCompatActivity {
//    private ImageView next2, next3, next4;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_store);
//
//        next2 = findViewById(R.id.next2);
//        next3 = findViewById(R.id.next3);
//        next4 = findViewById(R.id.next4);
//
//
//        next2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(store.this, tovar1.class));
//            }
//        });
//    }
//}