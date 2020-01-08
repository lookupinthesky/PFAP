package com.example.pfa_p.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.pfa_p.R;


public class LoginScreenFragment extends DialogFragment {

    EditText prisonerId;
    EditText volunteerId;
    Button nextButton;
    NextButtonListener mListener;
    RelativeLayout dialogContainer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    Context context;

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = getResources().getConfiguration().screenHeightDp;                       // displayMetrics.heightPixels;
        int width = getResources().getConfiguration().screenWidthDp;
      //    getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    getActivity().finish();
                    return true;
                }
                return false;
            }});

           /* @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                //   return false;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    getActivity().finish();
                }
                return true;
            }});*/

           /* @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {


            }
        });*/
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_details_layout, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
        //     setCancelable(false);
        //      (LoginActivity)context.

        prisonerId = view.findViewById(R.id.user_id);
        //  dialogContainer = view.findViewById(R.id.dialog_container);

        volunteerId = view.findViewById(R.id.volunteer_id);
        nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String prisonerIdText = prisonerId.getText().toString();
                String volunteerIdText = volunteerId.getText().toString();
                /*if(volunteerIdText.equals("")){
                    volunteerIdText = "volunteerId";
                }*/
                if (validatefields(prisonerIdText, volunteerIdText)) {

                    mListener.onNextButtonClick(prisonerIdText,volunteerIdText);
                    dismiss();
                } else  {
                    Toast.makeText(view.getContext(),"Please enter a value", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public static LoginScreenFragment getInstance(NextButtonListener mListener) {

        LoginScreenFragment fragment = new LoginScreenFragment();
        fragment.mListener = mListener;
        return fragment;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    private boolean validatefields(String prisonerId, String volunteerId) {

        return !prisonerId.equals("") && !volunteerId.equals("");
        //TODO: conditions for text fields

    }

    public interface NextButtonListener {

        void onNextButtonClick(String prisonerId, String volunteerId);
    }


}
