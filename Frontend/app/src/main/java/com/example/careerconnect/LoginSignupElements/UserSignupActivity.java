package com.example.careerconnect.LoginSignupElements;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.careerconnect.R;
import com.example.careerconnect.Volley.VolleyJSONObjectRequests;

import org.json.JSONObject;

public class UserSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        EditText firstName = findViewById(R.id.firstName_edittext);
        EditText middleName = findViewById(R.id.middleName_edittext);
        EditText lastName = findViewById(R.id.lastName_edittext);
        EditText username = findViewById(R.id.username_edittext);
        Button checkUsernameButton = findViewById(R.id.username_availability_button);

        EditText password = findViewById(R.id.password_edittext);
        EditText confirmPassword = findViewById(R.id.confirm_password_edittext);

        EditText email = findViewById(R.id.email_edittext);
        EditText phoneNumber = findViewById(R.id.phone_number_edittext);

        Button signupButton = findViewById(R.id.signup_button);
        Button backButton = findViewById(R.id.back_button);

        checkUsernameButton.setOnClickListener(v -> {

            VolleyJSONObjectRequests.makeVolleyJSONObjectGETRequest(getApplicationContext(), "http://10.0.2.2:8080/username/check/" + username.getText().toString(), new VolleyJSONObjectRequests.VolleyJSONObjectCallback() {
                @Override
                public void onResult(boolean result) {

                    if (!result){
                        Toast.makeText(getApplicationContext(), "Failed to check username availability", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onJSONObject(JSONObject jsonObject) {

                    if (jsonObject != null){

                        try {
                            if (jsonObject.getBoolean("availability")){
                                Toast.makeText(getApplicationContext(), "This username is available", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "This username is already taken", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        });

        signupButton.setOnClickListener(v -> {



        });

        backButton.setOnClickListener(v -> {



        });
    }


}
