package com.nk.firebaserealtimedb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nk.firebaserealtimedb.adapters.UserRecyclerViewAdapter;
import com.nk.firebaserealtimedb.dialogs.ActionMenuBottomSheetDialog;
import com.nk.firebaserealtimedb.models.DateLogin;
import com.nk.firebaserealtimedb.models.TimeLogin;
import com.nk.firebaserealtimedb.models.User;
import com.nk.firebaserealtimedb.viewModel.CloudFirebaseDatabaseActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://youtu.be/eSN2a3Py4Pk?si=3x94K5Gfb2_daMNX - lesson video
 */

public class CloudFirebaseDatabaseActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";
    public static final String COLLECTION_PATH = "users";
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    private RecyclerView recyclerView;
    private UserRecyclerViewAdapter adapter;
    private EditText userNameEt, familyNameEt, ageEt;
    private Button addBtn, addDateCollectionBtn;

    private List<User> usersList = new ArrayList<>();

    private CloudFirebaseDatabaseActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cloud_firebase_database);
        setReferences();
        setViewModel();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
//        collectionReference = firebaseFirestore.collection(COLLECTION_PATH);

        getData();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUser();
            }
        });

        addDateCollectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDateCollection();
            }
        });

        adapter = new UserRecyclerViewAdapter(new UserRecyclerViewAdapter.UserActionsCallback() {
            @Override
            public void openUser(String userID) {
                openUserInfoActivity(userID);
            }

            @Override
            public void openMenuDialog(String userID) {
                openActionMenu(userID);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setReferences() {
        recyclerView = findViewById(R.id.recycler_view);
        userNameEt = findViewById(R.id.user_name_et);
        familyNameEt = findViewById(R.id.family_name_et);
        ageEt = findViewById(R.id.age_et);
        addBtn = findViewById(R.id.add_btn);
        addDateCollectionBtn = findViewById(R.id.add_date_collection_btn);
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(CloudFirebaseDatabaseActivityViewModel.class);
        viewModel.init(getBaseContext());

        viewModel.getUsersList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsersList(users);
            }
        });

        viewModel.isAddedNewUser().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (!aBoolean) {
                    return;
                }

                userNameEt.setText(null);
                familyNameEt.setText(null);
                ageEt.setText(null);

                getData();
            }
        });

        viewModel.isDeletedUser().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    return;
                }

                getData();
            }
        });
    }

    private void getData() {
        viewModel.getUsersListData();
    }

    private void addNewUser() {
        String userName = userNameEt.getText().toString();
        String familyName = familyNameEt.getText().toString();
        String age = ageEt.getText().toString();
        long millisecinds = System.currentTimeMillis();

        /* Code add user with generated ID  */
//        viewModel.addNewUser(userName, familyName, age);

        List<DateLogin> dateLogins = new ArrayList<>();
            dateLogins.add(new DateLogin("2024-10-22", new TimeLogin("01", "13:04", "18:03")));

//        User user = new User(String.valueOf(millisecinds), userName, familyName, age);
        User user = new User(String.valueOf(millisecinds), userName, familyName, age, dateLogins);

        /* Code add user with custom ID, and by using model */
        viewModel.addNewUser(user);
    }

    private void addDateCollection() {
        viewModel.addCollectionDate("1729569956848", "2024-10-22", "14:23");
    }

    private void openUserInfoActivity(String userID) {
        Intent intent = new Intent(getBaseContext(), UserInfoActivity.class);
            intent.putExtra(UserInfoActivity.EXTRA_USER_ID, userID);
        startActivity(intent);
    }

    private void openActionMenu(String userID) {
        ActionMenuBottomSheetDialog dialog = new ActionMenuBottomSheetDialog(userID, new ActionMenuBottomSheetDialog.ActionMenuCallback() {
            @Override
            public void delete(String id) {
                deleteUser(id);
            }
        });

        dialog.show(getSupportFragmentManager(), ActionMenuBottomSheetDialog.DIALOG_TAG);
    }

    private void deleteUser(String userID) {
        viewModel.deleteUser(userID);
    }
}