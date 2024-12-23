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

/**
 * An activity for clients to signup as a USER account
 */
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

        /**
         * Button for checking if a desired username is available
         */
        checkUsernameButton.setOnClickListener(v -> {

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

                JSONObject signupInfo = accountInfoToJSONObject(firstName, middleName, lastName,
                                                                username, password, email,
                                                                phoneNumber);

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

                            VolleyJSONObjectRequests.makeVolleyJSONObjectPOSTRequest(signupInfo, getApplicationContext(), LibraryURL.getAccountCreationPOSTRequest(), result -> {

                                if (result){
                                    Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserSignupActivity.this, CareerClusterSelectionActivity.class);
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

        /**
         * Button to return to starting screen
         */
        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(UserSignupActivity.this, LoginSignupSelectionActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Checks if all required account information has been entered
     * @param firstName client first name
     * @param lastName client last name
     * @param username client desired username
     * @param password client password
     * @param confirmPassword client password confirmation
     * @param email client email
     * @param phoneNumber client phone number
     * @return true if successful, otherwise false
     */
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
     * @param firstName client first name
     * @param lastName client last name
     * @param username client desired username
     * @param password client password
     * @param email client email
     * @param phoneNumber client phone number
     * @return JSONObject of account information
     */
    private JSONObject accountInfoToJSONObject(String firstName, String middleName, String lastName,
                                               String username, String password, String email,
                                               String phoneNumber){

        JSONObject userProfileInfo = new JSONObject();
        try {
            userProfileInfo.put("firstName", firstName);
            userProfileInfo.put("middleName", middleName);
            userProfileInfo.put("lastName", lastName);
            userProfileInfo.put("password", password);
            userProfileInfo.put("email", email);
            userProfileInfo.put("phoneNumber", phoneNumber);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }

        JSONObject accountTypeInfo = new JSONObject();
        try {
            accountTypeInfo.put("accountType", "USER");
        } catch (JSONException e){
            throw new RuntimeException(e);
        }

        JSONObject signupInfo = new JSONObject();
        try {
            signupInfo.put("accountInfo", accountTypeInfo);
            signupInfo.put("userProfileInfo", userProfileInfo);
            signupInfo.put("username",username);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
        return signupInfo;
    }
}
