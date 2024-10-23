package com.nk.firebaserealtimedb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nk.firebaserealtimedb.models.User;
import com.nk.firebaserealtimedb.viewModel.CloudFirebaseDatabaseActivityViewModel;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";
    public static final String EXTRA_USER_ID = "userID";

    private String userID;
    private EditText userNameEt, familyNameEt, ageEt;
    private Button saveBtn, cancelBtn;

    private CloudFirebaseDatabaseActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);
        setReferences();
        setViewModel();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
            userID = intent.getStringExtra(EXTRA_USER_ID);

        if (userID == null) {
            getOnBackPressedDispatcher().onBackPressed();
            finish();
        }

        getData();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
                finish();
            }
        });
    }

    private void setReferences() {
        userNameEt = findViewById(R.id.user_name_et);
        familyNameEt = findViewById(R.id.family_name_et);
        ageEt = findViewById(R.id.age_et);
        saveBtn = findViewById(R.id.save_btn);
        cancelBtn = findViewById(R.id.cancel_btn);
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(CloudFirebaseDatabaseActivityViewModel.class);
        viewModel.init(getBaseContext());

        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
//                Log.d(TAG, "onChanged: Got Single User: " + user.toString());
                setValuesToViews(user);
            }
        });
    }

    private void getData() {
        viewModel.getSingleUser(userID);
    }

    private void setValuesToViews(User user) {
        userNameEt.setText(user.getUser_name());
        familyNameEt.setText(user.getFamily_name());
        ageEt.setText(user.getAge());
    }
}