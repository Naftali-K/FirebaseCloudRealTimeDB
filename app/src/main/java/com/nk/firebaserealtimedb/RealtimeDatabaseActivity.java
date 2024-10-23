package com.nk.firebaserealtimedb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * https://youtu.be/LJpn81pG06Y?si=Dv1-ZW7dmELjuHWE - video lesson
 *
 */

public class RealtimeDatabaseActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";

    private DatabaseReference databaseReference;

    private TextView gottenValueTextView;
    private EditText inputEditText;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_realtime_database);
        setReferences();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("This is the path");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewValue();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d(TAG, "onDataChange: Value is: " + value);

                gottenValueTextView.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: Failed to read value. Error: " + error.toException());
            }
        });

    }

    private void setReferences() {
        gottenValueTextView = findViewById(R.id.gotten_value_text_view);
        inputEditText = findViewById(R.id.input_edit_text);
        sendBtn = findViewById(R.id.send_btn);
    }

    private void addNewValue() {
        String newValue = inputEditText.getText().toString();
        databaseReference.setValue(newValue).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: On Success set value");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: On Failure set value");
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: On Complete set value");

                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: New value added");
                } else {
                    Log.d(TAG, "onComplete: Has any error. New value not added");
                }
            }
        });
    }
}