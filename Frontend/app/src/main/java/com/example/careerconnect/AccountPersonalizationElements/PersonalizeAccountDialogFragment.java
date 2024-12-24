package com.example.careerconnect.AccountPersonalizationElements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.careerconnect.LoginSignupElements.LoginSignupSelectionActivity;
import com.example.careerconnect.R;

public class PersonalizeAccountDialogFragment extends DialogFragment {

    private final Context context;

    public PersonalizeAccountDialogFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.account_personalization_layout,
                                        container, false);

        Button acceptButton = view.findViewById(R.id.accept_personalization_btn);
        Button rejectButton = view.findViewById(R.id.reject_personalization_btn);

        Bundle args = getArguments();
        if (args != null){

            String accountType = args.getString("ACCOUNT TYPE");
            String username = args.getString("USERNAME");

            switch (accountType){

                case "USER":
                    acceptButton.setOnClickListener(v -> {

                        //todo
                        Intent intent = new Intent(context, LoginSignupSelectionActivity.class);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
                        dismiss();
                    });
                    break;

                case "COMPANY":
                    acceptButton.setOnClickListener(v -> {

                        //todo
                        Intent intent = new Intent(context, LoginSignupSelectionActivity.class);
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
                        dismiss();
                    });
                    break;
            }
        }

        rejectButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoginSignupSelectionActivity.class);
            startActivity(intent);
            dismiss();
        });
        return view;
    }
}
