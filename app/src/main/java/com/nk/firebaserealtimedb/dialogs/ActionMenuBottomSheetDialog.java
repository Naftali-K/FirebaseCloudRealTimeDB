package com.nk.firebaserealtimedb.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nk.firebaserealtimedb.R;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/10/18
 */
public class ActionMenuBottomSheetDialog extends BottomSheetDialogFragment {

    public static final String DIALOG_TAG = "ActionMenuBottomSheetDialog";

    private String id;
    private ActionMenuCallback callback;
    private TextView deleteBtn;

    public ActionMenuBottomSheetDialog() {
    }

    public ActionMenuBottomSheetDialog(String id, ActionMenuCallback callback) {
        this.id = id;
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.customBottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog_action_menu, container, false);
        setReferences(view);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.delete(id);
                dismiss();
            }
        });

        return view;
    }

    private void setReferences(View view) {
        deleteBtn = view.findViewById(R.id.delete_btn);
    }

    public interface ActionMenuCallback {
        void delete(String id);
    }
}
