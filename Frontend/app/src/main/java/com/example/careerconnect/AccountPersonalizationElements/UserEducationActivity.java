package com.example.careerconnect.AccountPersonalizationElements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.careerconnect.Global.ButterToast;
import com.example.careerconnect.R;
import com.example.careerconnect.SingletonRepository.IdentifyingDataRepository;
import com.example.careerconnect.SingletonRepository.UserProfile;
import com.example.careerconnect.Volley.VolleyJSONArrayRequests;
import com.example.careerconnect.Volley.VolleyJSONObjectRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserEducationActivity extends AppCompatActivity {

    private String USERNAME = IdentifyingDataRepository.getInstance().getUserProfile().getUsername();

    private final List<String> universityList = new ArrayList<>();
    private UniversityAdapter universityAdapter;
    private String selectedUniversity = "";

    private final List<String> majorList = new ArrayList<>();
    private MajorAdapter majorAdapter;
    private String selectedMajor = "";

    private TextView selectedUniversityTxtView;
    private TextView selectedMajorTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_education);

        selectedUniversityTxtView = findViewById(R.id.selected_university_textView);
        selectedMajorTxtView = findViewById(R.id.selected_major_edittext);

        universityRecyclerViewSetup();
        majorRecyclerViewSetup();

        // Confirming education details
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> {

            String selectedUniversity = selectedUniversityTxtView.getText().toString();
            String selectedMajor = selectedMajorTxtView.getText().toString();

            if (!checkEducationInfo(selectedUniversity, selectedMajor)) return;

            // saving details to singleton repository
            UserProfile userProfile = IdentifyingDataRepository.getInstance().getUserProfile();
            userProfile.setUniversity(selectedUniversity);
            userProfile.setMajor(selectedMajor);

            updateAccountBackgroundInfo();
        });
    }

    private boolean checkEducationInfo(String selectedUniversity, String selectedMajor){

        if (selectedUniversity.isEmpty()){
            ButterToast.show(getApplicationContext(), "Select university", Toast.LENGTH_SHORT);
            return false;
        }
        if (selectedMajor.isEmpty()){
            ButterToast.show(getApplicationContext(), "Select major", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private void updateAccountBackgroundInfo(){

        VolleyJSONObjectRequests.makeVolleyJSONObjectGETRequest(
                this,
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
                                jsonObject.put("university", userProfile.getUniversity());
                                jsonObject.put("major", userProfile.getMajor());
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
                                                ButterToast.show(getApplicationContext(), "Education information updated", Toast.LENGTH_SHORT);
                                            }
                                        }
                                    }
                            );
                        }
                    }
                }
        );
    }

    private void universityRecyclerViewSetup(){

        // Retrieving universities
        RecyclerView universityRecyclerView = findViewById(R.id.university_list_recyclerView);
        universityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        universityAdapter = new UniversityAdapter(universityList, this);
        universityRecyclerView.setAdapter(universityAdapter);

        // starter search for universities
        getUniversityByPrefix("iowa");

        Button findUniversityButton = findViewById(R.id.find_university_button);
        EditText searchUniversityEdtTxt = findViewById(R.id.search_university_edittext);
        findUniversityButton.setOnClickListener(v -> {

            getUniversityByPrefix(searchUniversityEdtTxt.getText().toString());
        });
    }

    private void majorRecyclerViewSetup(){

        // Retrieving countries
        RecyclerView majorRecyclerView = findViewById(R.id.major_list_recyclerView);
        majorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        majorAdapter = new MajorAdapter(majorList, this);
        majorRecyclerView.setAdapter(majorAdapter);

        // starter search for countries
        getMajorByPrefix("co");

        Button findMajorButton = findViewById(R.id.find_major_button);
        EditText searchMajorEdtTxt = findViewById(R.id.search_major_edittext);
        findMajorButton.setOnClickListener(v -> {

            getMajorByPrefix(searchMajorEdtTxt.getText().toString());
        });
    }

    private class PlainTextViewHolder extends RecyclerView.ViewHolder{

        LinearLayout plainTextLayout;
        TextView plainTextTxtView;

        public PlainTextViewHolder(View itemView){

            super(itemView);
            plainTextLayout = itemView.findViewById(R.id.plain_text_layout);
            plainTextTxtView = itemView.findViewById(R.id.plain_text_textView);
        }
    }

    private class UniversityAdapter extends RecyclerView.Adapter<PlainTextViewHolder>{

        private final List<String> universityList;
        private final Context context;
        private int lastSelectedPosition = -1;

        private UniversityAdapter(List<String> universityList, Context context){

            this.universityList = universityList;
            this.context = context;
        }

        @NonNull
        @Override
        public PlainTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plain_text_layout, parent, false);
            return new PlainTextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlainTextViewHolder holder, @SuppressLint("RecyclerView") int position) {

            String university = universityList.get(position);

            holder.plainTextLayout.setOnClickListener(v -> {

                if (!selectedUniversity.isEmpty()){

                    selectedUniversity = university;
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                }
                else {

                    selectedUniversity = university;
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                }
                selectedUniversityTxtView.setText(selectedUniversity);
            });

            if (selectedUniversity.equals(university)){

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.medium_blue));
            }
            else {

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.white));
            }
            holder.plainTextTxtView.setText(university);
        }

        @Override
        public int getItemCount() {
            return universityList.size();
        }
    }

    private class MajorAdapter extends RecyclerView.Adapter<PlainTextViewHolder> {

        private final List<String> majorList;
        private final Context context;
        private int lastSelectedPosition = -1;

        private MajorAdapter(List<String> majorList, Context context) {

            this.majorList = majorList;
            this.context = context;
        }

        @NonNull
        @Override
        public PlainTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plain_text_layout, parent, false);
            return new PlainTextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlainTextViewHolder holder, @SuppressLint("RecyclerView") int position) {

            String major = majorList.get(position);

            holder.plainTextLayout.setOnClickListener(v -> {

                if (!selectedMajor.isEmpty()) {

                    selectedMajor = major;
                    notifyItemChanged(lastSelectedPosition);
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                } else {

                    selectedMajor = major;
                    lastSelectedPosition = position;
                    ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                            ContextCompat.getColorStateList(context, R.color.medium_blue));
                }
                selectedMajorTxtView.setText(selectedMajor);
            });

            if (selectedMajor.equals(major)) {

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.medium_blue));
            } else {

                ViewCompat.setBackgroundTintList(holder.plainTextLayout,
                        ContextCompat.getColorStateList(context, R.color.white));
            }
            holder.plainTextTxtView.setText(major);
        }

        @Override
        public int getItemCount() {
            return majorList.size();
        }
    }

    private void getUniversityByPrefix(String prefix){

        VolleyJSONArrayRequests.makeVolleyJSONArrayGETRequest(this, LibraryURL.getUniversitiesGETRequest() + prefix, new VolleyJSONArrayRequests.VolleyJSONArrayCallback() {
            @Override
            public void onResult(boolean result) {

                if (!result){
                    ButterToast.show(getApplicationContext(), "Server is acting slow. Search again to find universities", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onJSONArray(JSONArray jsonArray) {

                if (jsonArray != null){

                    universityList.clear();
                    universityAdapter.notifyDataSetChanged();
                    for (int i = 0; i < jsonArray.length(); i++){

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            universityList.add(jsonObject.getString("name"));
                            universityAdapter.notifyItemInserted(i);
                        } catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    private void getMajorByPrefix(String prefix){

        VolleyJSONArrayRequests.makeVolleyJSONArrayGETRequest(this, LibraryURL.getMajorsGETRequest() + prefix, new VolleyJSONArrayRequests.VolleyJSONArrayCallback() {
            @Override
            public void onResult(boolean result) {

                if (!result){
                    ButterToast.show(getApplicationContext(), "Server is acting slow. Search again to find majors", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onJSONArray(JSONArray jsonArray) {

                if (jsonArray != null){

                    majorList.clear();
                    majorAdapter.notifyDataSetChanged();
                    for (int i = 0; i < jsonArray.length(); i++){

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            majorList.add(jsonObject.getString("name"));
                            majorAdapter.notifyItemInserted(i);
                        } catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
}
