package com.nk.firebaserealtimedb.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nk.firebaserealtimedb.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/10/18
 */
public class CloudFirebaseDatabaseActivityViewModel extends ViewModel {

    private static final String TAG = "Test_code";
    public static final String COLLECTION_PATH = "users";

    private Context context;

    private FirebaseFirestore firebaseFirestore;

    private MutableLiveData<User> userData = new MutableLiveData<>();
    private MutableLiveData<List<User>> usersList = new MutableLiveData<>();
    private MutableLiveData<Boolean> addedNewUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> deletedUser = new MutableLiveData<>();

    public void init(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<User> getUser() {
        return userData;
    }

    public LiveData<List<User>> getUsersList() {
        return usersList;
    }

    public LiveData<Boolean> isAddedNewUser() {
        return addedNewUser;
    }

    public LiveData<Boolean> isDeletedUser() {
        return deletedUser;
    }

    public void getSingleUser(String userID) {
        firebaseFirestore.collection(COLLECTION_PATH)
                .document(userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();

                            userData.setValue(documentSnapshot.toObject(User.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Fail get single user. Error: " + e.getLocalizedMessage());
                    }
                });
    }

    public void getUsersListData() {
        List<User> users = new ArrayList<>();

        firebaseFirestore.collection(COLLECTION_PATH)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()) {

                                //see ID, and full object with data from Firebase
//                                Log.d(TAG, "onComplete: Document ID: " + document.getId() + " => " + document.getData());

                                // get special value from data of Firebase
//                                String userName = document.getData().get("user_name").toString();
//                                Log.d(TAG, "onComplete: User name: " + userName);

                                // convert data from Firebase to model object
                                users.add(document.toObject(User.class));

                            }

                            // checking size of list
//                            Log.d(TAG, "onComplete: Users list size: " + users.size());

                            usersList.setValue(users);

                        } else {
                            Log.d(TAG, "onComplete: Error get documents. Error: " + task.getException());
                        }
                    }
                });
    }


    /* Code add user with generated ID  */
    public void addNewUser(String userName, String familyName, String age) {
        Map<String, Object> user = new HashMap<>();
        user.put("user_name", userName);
        user.put("family_name", familyName);
        user.put("age", age);

        firebaseFirestore.collection(COLLECTION_PATH)
                .add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "onSuccess: Added new user with ID: " + documentReference.getId());
                        addedNewUser.setValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Fail add new user. Error: " + e.getLocalizedMessage());
                        addedNewUser.setValue(false);
                    }
                });
    }

    /* Code add user with custom ID, and by using model */
    public void addNewUser(User user) {

        firebaseFirestore.collection(COLLECTION_PATH)
                .document(user.getId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "onSuccess: Added new user");
                        addedNewUser.setValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Fail add new user. Error: " + e.getLocalizedMessage());
                        addedNewUser.setValue(false);
                    }
                });
    }

    public void addCollectionDate(String userID, String date, String dateTime) {
        Map<String, String> dateMap = new HashMap<>();
            dateMap.put("Date", dateTime);

        firebaseFirestore.collection(COLLECTION_PATH)
                .document(userID)
                .collection(date)
                .document()
                .set(dateMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Fail add new user. Error: " + e.getLocalizedMessage());
                    }
                });
    }

    public void deleteUser(String userID) {
        if (userID == null) {
            return;
        }

        firebaseFirestore.collection(COLLECTION_PATH)
                .document(userID)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: Complete delete user. Response: " + task.toString());

//                        if (task.isSuccessful()) {
//                            deletedUser.setValue(true);
//                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        deletedUser.setValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        deletedUser.setValue(false);
                    }
                });
    }
}
