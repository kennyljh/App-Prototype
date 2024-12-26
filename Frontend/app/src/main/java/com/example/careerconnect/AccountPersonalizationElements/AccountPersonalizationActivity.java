package com.example.careerconnect.AccountPersonalizationElements;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.careerconnect.R;
import com.example.careerconnect.SingletonRepository.CompanyProfile;
import com.example.careerconnect.SingletonRepository.IdentifyingDataRepository;
import com.example.careerconnect.SingletonRepository.UserProfile;

public class AccountPersonalizationActivity extends AppCompatActivity {

    private String USERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_personalization);

        activityDisplaySetup();

        Button acceptButton = findViewById(R.id.accept_personalization_btn);
        Button rejectButton = findViewById(R.id.reject_personalization_btn);
        Button frameColorInfoButton = findViewById(R.id.profile_card_info_btn);
        Button setupProfileInfoButton = findViewById(R.id.account_setup_info_button);

        acceptButton.setOnClickListener(v -> {

            //todo use the account type
        });

        rejectButton.setOnClickListener(v -> {
            //todo
        });

        frameColorInfoButton.setOnClickListener(v -> {

            FrameColorInfoDialogFragment dialogFragment = new FrameColorInfoDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "Information");
        });

        setupProfileInfoButton.setOnClickListener(v -> {

            ProfilePersonalizationInfoDialogFragment dialogFragment = new ProfilePersonalizationInfoDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "Information");
        });
    }

    private void activityDisplaySetup(){

        ScrollView userCard = findViewById(R.id.user_info_card_layout);
        LinearLayout companyCard = findViewById(R.id.company_info_card_layout);
        RelativeLayout profileFrame = findViewById(R.id.picture_card_layout);

        TextView firstLastNameTxtView = findViewById(R.id.firstLastName_textView);
        TextView userUsernameTxtView = findViewById(R.id.user_username_textView);

        TextView brandNameTxtView = findViewById(R.id.brandName_textView);
        TextView companyUsernameTxtView = findViewById(R.id.company_username_textView);

        // retrieve data from singleton repository
        IdentifyingDataRepository repository = IdentifyingDataRepository.getInstance();
        switch (getIntent().getStringExtra("ACCOUNT TYPE")){
            case "USER":
                USERNAME = repository.getUserProfile().getUsername();
                userCard.setVisibility(View.VISIBLE);
                companyCard.setVisibility(View.GONE);
                ViewCompat.setBackgroundTintList(profileFrame,
                        ContextCompat.getColorStateList(this, R.color.medium_blue));

                UserProfile userProfile = repository.getUserProfile();
                String firstLastName = userProfile.getFirstName() + " " + userProfile.getLastName();

                firstLastNameTxtView.setText(firstLastName);
                userUsernameTxtView.setText(userProfile.getUsername());
                break;
            case "COMPANY":
                USERNAME = repository.getCompanyProfile().getUsername();
                userCard.setVisibility(View.GONE);
                companyCard.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(profileFrame,
                        ContextCompat.getColorStateList(this, R.color.grey));

                CompanyProfile companyProfile = repository.getCompanyProfile();

                brandNameTxtView.setText(companyProfile.getBrandName());
                companyUsernameTxtView.setText(companyProfile.getUsername());

                TextView infoCard = findViewById(R.id.information_textView);
                TextView awardsCard = findViewById(R.id.qualifications_textView);

                String companyInfo = "Company Information";
                String awardsInfo = "Awards";

                infoCard.setText(companyInfo);
                awardsCard.setText(awardsInfo);
                break;
        }
    }
}
