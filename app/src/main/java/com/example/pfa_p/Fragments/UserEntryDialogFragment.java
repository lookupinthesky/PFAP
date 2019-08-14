package com.example.pfa_p.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.pfa_p.R;


public class UserEntryDialogFragment extends DialogFragment {

    EditText prisonerId;
    EditText volunteerId;
    Button nextButton;
    NextButtonListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.user_details_layout, container, false);

        prisonerId  = view.findViewById(R.id.user_id);
        volunteerId   = view.findViewById(R.id.volunteer_id);
        nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validatefields()) {
                    dismiss();
                    mListener.onNextButtonClick();
                }
            }
        });
        return view;
    }

    public static UserEntryDialogFragment getInstance(NextButtonListener mListener){

        UserEntryDialogFragment fragment = new UserEntryDialogFragment();
        fragment.mListener = mListener;
        return fragment;

    }


    private boolean validatefields(){

        return true;
        //TODO: conditions for text fields

    }

 public   interface NextButtonListener{

        void onNextButtonClick();
    }
}