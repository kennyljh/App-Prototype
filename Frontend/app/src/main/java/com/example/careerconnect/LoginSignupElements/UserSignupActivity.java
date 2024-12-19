package com.example.careerconnect.LoginSignupElements;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careerconnect.R;
import com.example.careerconnect.Volley.VolleyJSONObjectRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        EditText firstNameEdtTxt = findViewById(R.id.firstName_edittext);
        EditText middleNameEdtTxt = findViewById(R.id.middleName_edittext);
        EditText lastNameEdtTxt = findViewById(R.id.lastName_edittext);
        EditText usernameEdtTxt = findViewById(R.id.username_edittext);
        Button checkUsernameButton = findViewById(R.id.username_availability_button);

        EditText passwordEdtTxt = findViewById(R.id.password_edittext);
        EditText confirmPasswordEdtTxt = findViewById(R.id.confirm_password_edittext);

        EditText emailEdtTxt = findViewById(R.id.email_edittext);
        EditText phoneNumberEdtTxt = findViewById(R.id.phone_number_edittext);

        Button signupButton = findViewById(R.id.signup_button);
        Button backButton = findViewById(R.id.back_button);

        checkUsernameButton.setOnClickListener(v -> {

            VolleyJSONObjectRequests.makeVolleyJSONObjectGETRequest(getApplicationContext(), "http://10.0.2.2:8080/username/check/" + usernameEdtTxt.getText().toString(), new VolleyJSONObjectRequests.VolleyJSONObjectCallback() {
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
                        } catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });

        signupButton.setOnClickListener(v -> {

            String firstName = firstNameEdtTxt .getText().toString();
            String middleName = middleNameEdtTxt.getText().toString();
            String lastName = lastNameEdtTxt.getText().toString();
            String username = usernameEdtTxt.getText().toString();
            String password = passwordEdtTxt.getText().toString();
            String confirmPassword = confirmPasswordEdtTxt.getText().toString();
            String email = emailEdtTxt.getText().toString();
            String phoneNumber = phoneNumberEdtTxt.getText().toString();

            if (signupInfoConfirmation(firstName, lastName, password,
                                        username, confirmPassword, email,
                                        phoneNumber) &&
                passwordValidation(password, confirmPassword)){

                JSONObject accountInfo = new JSONObject();
                try {
                    accountInfo.put("firstName", firstName);
                    accountInfo.put("middleName", middleName);
                    accountInfo.put("lastName", lastName);
                    accountInfo.put("password", password);
                    accountInfo.put("email",  email);
                    accountInfo.put("phoneNumber", phoneNumber);
                    accountInfo.put("accountType", "USER");
                } catch (JSONException e){
                    throw new RuntimeException(e);
                }

                JSONObject signupInfo = new JSONObject();
                try {
                    signupInfo.put("accountInfo", accountInfo);
                    signupInfo.put("username", username);
                } catch (JSONException e){
                    throw new RuntimeException(e);
                }

                VolleyJSONObjectRequests.makeVolleyJSONObjectGETRequest(getApplicationContext(), "http://10.0.2.2:8080/username/check/" + usernameEdtTxt.getText().toString(), new VolleyJSONObjectRequests.VolleyJSONObjectCallback() {
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

                                    VolleyJSONObjectRequests.makeVolleyJSONObjectPOSTRequest(signupInfo, getApplicationContext(), "http://10.0.2.2:8080/account/create", result -> {

                                        if (result){
                                            Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Account creation unsuccessful", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "This username is already taken", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e){
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(UserSignupActivity.this, LoginSignupSelectionActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean signupInfoConfirmation(String firstName, String lastName, String username,
                                           String password, String confirmPassword, String email,
                                           String phoneNumber){

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
            password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() ||
            phoneNumber.isEmpty()){

            Toast.makeText(getApplicationContext(), "Please fill in all required details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean passwordValidation(String password, String confirmPassword){

        if (password.equals(confirmPassword)){

            boolean hasUpperCase = password.matches(".*[A-Z].*");
            boolean hasSpecialChar = password.matches(".*[^a-zA-Z0-9].*");

            if (hasUpperCase && hasSpecialChar){

                return true;
            }
            else {

                Toast.makeText(getApplicationContext(), "Password must contain at least one upper case and special character", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {

            Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
            return false;
        }
    }


}
