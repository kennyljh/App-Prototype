package com.example.careerconnect.LoginSignupElements;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careerconnect.R;

public class LoginSignupSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Button loginButton = findViewById(R.id.login_btn);
        Button signupButton = findViewById(R.id.signup_btn);
        Button guestButton = findViewById(R.id.guest_btn);


        loginButton.setOnClickListener(v -> {

        });

        signupButton.setOnClickListener(v -> {

            AccountTypeSelectionBottomSheetFragment bottomSheetFragment = new AccountTypeSelectionBottomSheetFragment(getApplicationContext());

            Bundle args = new Bundle();
            args.putString("action", "signup");
            bottomSheetFragment.setArguments(args);

            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

        });

    }
}
