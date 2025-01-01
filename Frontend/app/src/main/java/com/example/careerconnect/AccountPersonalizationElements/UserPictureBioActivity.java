package com.example.careerconnect.AccountPersonalizationElements;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.careerconnect.Global.ButterToast;
import com.example.careerconnect.Global.ImageConverter;
import com.example.careerconnect.R;
import com.example.careerconnect.SingletonRepository.IdentifyingDataRepository;
import com.example.careerconnect.SingletonRepository.UserProfile;
import com.example.careerconnect.Volley.VolleyJSONObjectRequests;

import org.json.JSONException;
import org.json.JSONObject;

public class UserPictureBioActivity extends AppCompatActivity {

    private String USERNAME = IdentifyingDataRepository.getInstance().getUserProfile().getUsername();

    private Uri selectedUri;
    // used to help fetch image from gallery
    private ActivityResultLauncher<String> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picture_bio);

        LinearLayout frameLayout = findViewById(R.id.picture_card_layout);
        ViewCompat.setBackgroundTintList(frameLayout,
                ContextCompat.getColorStateList(this, R.color.medium_blue));

        Button choosePictureButton = findViewById(R.id.choose_picture_button);
        choosePictureButton.setOnClickListener(v -> {

            mGetContent.launch("image/*");
        });

        ImageView profilePicture = findViewById(R.id.profile_picture_imageView);
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    // Handle the returned Uri
                    if (uri != null){
                        selectedUri = uri;
                        profilePicture.setImageURI(selectedUri);
                    }
                });

        EditText biographyEditTxt = findViewById(R.id.biography_edittext);

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {

            String biography = biographyEditTxt.getText().toString();

            if (!checkPictureBioInfo(selectedUri, biography)) return;

            // saving details to singleton repository
            UserProfile userProfile = IdentifyingDataRepository.getInstance().getUserProfile();
            userProfile.setProfilePicture(ImageConverter.imageUriToBitmap(selectedUri, this));
            userProfile.setBiography(biography);

            updateProfileBioInfo();
        });

    }

    private boolean checkPictureBioInfo(Uri uri, String biography){

        if (uri == null){
            ButterToast.show(this, "Choose a profile picture", Toast.LENGTH_SHORT);
            return false;
        }
        if (biography.isEmpty()){
            ButterToast.show(this, "Write something for your biography", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private void updateProfileBioInfo(){

        VolleyJSONObjectRequests.makeVolleyJSONObjectGETRequest(this,
                LibraryURL.getUserProfileGETRequest() + USERNAME,
                new VolleyJSONObjectRequests.VolleyJSONObjectCallback() {
                    @Override
                    public void onResult(boolean result) {

                        if (!result){
                            ButterToast.show(getApplicationContext(), "Server is acting slow. Please try again", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onJSONObject(JSONObject jsonObject) {

                        if (jsonObject != null){

                            UserProfile userProfile = IdentifyingDataRepository.getInstance().getUserProfile();
                            try{
                                jsonObject.put("profilePicture", ImageConverter.bitmapToBase64EncodedString(userProfile.getProfilePicture()));
                                jsonObject.put("biography", userProfile.getBiography());
                            } catch (JSONException e){
                                throw new RuntimeException(e);
                            }

                            VolleyJSONObjectRequests.makeVolleyJSONObjectPUTRequest(
                                    jsonObject,
                                    getApplicationContext(),
                                    LibraryURL.getUserProfilePUTRequest() + USERNAME,
                                    new VolleyJSONObjectRequests.VolleyCallback() {
                                        @Override
                                        public void onResult(boolean result) {

                                            if (!result){
                                                ButterToast.show(getApplicationContext(), "Server is acting slow. Please try again", Toast.LENGTH_SHORT);
                                            }
                                            else {
                                                ButterToast.show(getApplicationContext(), "Profile picture and biography updated", Toast.LENGTH_SHORT);
                                                Intent intent = new Intent(UserPictureBioActivity.this, UserBackgroundActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                            );

                        }
                    }
                });
    }
}
