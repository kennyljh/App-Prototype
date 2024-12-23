package com.example.careerconnect.LoginSignupElements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.careerconnect.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * Gives clients the option to choose between a USER or COMPANY type account
 */
public class AccountTypeSelectionBottomSheetFragment extends BottomSheetDialogFragment {

    private final Context context;

    /**
     * Shows a bottom sheet fragment displaying the options to either select
     * USER or COMPANY
     * @param context Current application context
     */
    public AccountTypeSelectionBottomSheetFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.account_type_selection_layout,
                                    container, false);

        Bundle args = getArguments();
        if (args != null){

            String action = args.getString("action");

            Button userSelectionButton = view.findViewById(R.id.user_selection_btn);
            Button companySelectionButton = view.findViewById(R.id.company_selection_button);

            switch (action){

                case "login" :
                    userSelectionButton.setOnClickListener(v -> {
                        //todo
                        dismiss();
                    });
                    companySelectionButton.setOnClickListener(v -> {
                        //todo
                        dismiss();
                    });
                case "signup" :
                    userSelectionButton.setOnClickListener(v -> {

                        Intent intent = new Intent(context, UserSignupActivity.class);
                        startActivity(intent);
                        dismiss();
                    });
                    companySelectionButton.setOnClickListener(v -> {
                        //todo
                        dismiss();
                    });
            }
        }
        return view;
    }
}
