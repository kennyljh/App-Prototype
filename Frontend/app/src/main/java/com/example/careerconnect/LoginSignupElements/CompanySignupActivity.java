package com.example.careerconnect.LoginSignupElements;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careerconnect.R;
import com.example.careerconnect.Volley.VolleyJSONObjectRequests;
import com.example.careerconnect.Volley.VolleyStringRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanySignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_signup);

        EditText brandNameEdtTxt = findViewById(R.id.brandName_edittext);
        EditText usernameEdtTxt = findViewById(R.id.username_edittext);

        EditText passwordEdtTxt = findViewById(R.id.password_edittext);
        EditText confirmPasswordEdtTxt = findViewById(R.id.confirm_password_edittext);

        EditText emailEdtTxt = findViewById(R.id.email_edittext);
        EditText phoneNumberEdtTxt = findViewById(R.id.phone_number_edittext);

        Button checkUsernameButton = findViewById(R.id.username_availability_button);
        Button signupButton = findViewById(R.id.signup_button);
        Button backButton = findViewById(R.id.back_button);

        /**
         * Button for checking if a desired username is available
         */
        checkUsernameButton.setOnClickListener(v -> {

            if (usernameEdtTxt.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Nothing to check", Toast.LENGTH_SHORT).show();
                return;
            }

            VolleyStringRequests.makeVolleyStringGETRequest(getApplicationContext(), LibraryURL.getUsernameCheckGETRequest() + usernameEdtTxt.getText().toString(), new VolleyStringRequests.VolleyStringCallback() {
                @Override
                public void onResult(boolean result) {

                    if (!result){
                        Toast.makeText(getApplicationContext(), "Failed to check username availability", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onString(String string) {

                    if (string != null) {
                        Toast.makeText(getApplicationContext(), "This username is available", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "This username has been taken", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        /**
         * Button to initialize account creation
         */
        signupButton.setOnClickListener(v -> {

            String brandName = brandNameEdtTxt.getText().toString();
            String username = usernameEdtTxt.getText().toString();
            String password = passwordEdtTxt.getText().toString();
            String confirmPassword = confirmPasswordEdtTxt.getText().toString();
            String email = emailEdtTxt.getText().toString();
            String phoneNumber = phoneNumberEdtTxt.getText().toString();

            if (signupInfoConfirmation(brandName, username, password,
                                        confirmPassword, email, phoneNumber) &&
                passwordValidation(password, confirmPassword)){

                JSONObject signupInfo = accountInfoToJSONObject(brandName, username, password,
                                                                email, phoneNumber);

                VolleyStringRequests.makeVolleyStringGETRequest(getApplicationContext(), LibraryURL.getUsernameCheckGETRequest() + usernameEdtTxt.getText().toString(), new VolleyStringRequests.VolleyStringCallback() {
                    @Override
                    public void onResult(boolean result) {

                        if (!result){
                            Toast.makeText(getApplicationContext(), "Failed to check username availability", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onString(String string) {

                        if (string != null){

                            VolleyJSONObjectRequests.makeVolleyJSONObjectPOSTRequest(signupInfo, getApplicationContext(), LibraryURL.getUSERAccountCreationPOSTRequest(), result -> {

                                if (result){
                                    Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CompanySignupActivity.this, CareerClusterSelectionActivity.class);
                                    intent.putExtra("USERNAME", username);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Account creation unsuccessful. Try again", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "This username has been taken", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(CompanySignupActivity.this, LoginSignupSelectionActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Checks if all required account information has been entered
     * @param brandName client brand name
     * @param username client desired username
     * @param password client password
     * @param confirmPassword client password confirmation
     * @param email client email
     * @param phoneNumber client phone number
     * @return true if successful, false otherwise
     */
    private boolean signupInfoConfirmation(String brandName, String username, String password,
                                           String confirmPassword, String email, String phoneNumber){

        if (brandName.isEmpty() || username.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()){

            Toast.makeText(getApplicationContext(), "Please fill in all required details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Checks if password matches password confirmation, and has at least one capital letter
     * and one special character.
     * @param password client password
     * @param confirmPassword client password confirmation
     * @return true if successful, false otherwise
     */
    private boolean passwordValidation(String password, String confirmPassword){

        if (password.equals(confirmPassword)){

            boolean hasUpperCase = password.matches(".*[A-Z].*");
            boolean hasSpecialChar = password.matches(".*[^a-zA-Z0-9].*");

            if (hasUpperCase && hasSpecialChar){

                return true;
            }
            else {

                Toast.makeText(getApplicationContext(), "Password must contain at least one upper case and special character", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {

            Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Returns JSONObject of all account information
     * @param brandName client brand name
     * @param username client desired username
     * @param password client password
     * @param email client email
     * @param phoneNumber client phone number
     * @return JSONObject of account information
     */
    private JSONObject accountInfoToJSONObject(String brandName, String username, String password,
                                               String email, String phoneNumber){

        JSONObject companyProfileInfo = new JSONObject();
        try {
            companyProfileInfo.put("brandName", brandName);
            companyProfileInfo.put("email", email);
            companyProfileInfo.put("phoneNumber", phoneNumber);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }

        JSONObject accountInfo = new JSONObject();
        try {
            accountInfo.put("accountType", "COMPANY");
            accountInfo.put("password", password);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }

        JSONObject signupInfo = new JSONObject();
        try {
            signupInfo.put("accountInfo", accountInfo);
            signupInfo.put("companyProfileInfo", companyProfileInfo);
            signupInfo.put("username", username);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
        return signupInfo;
    }
}
