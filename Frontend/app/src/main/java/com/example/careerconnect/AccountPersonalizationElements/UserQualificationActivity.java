package com.example.careerconnect.AccountPersonalizationElements;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careerconnect.Global.ButterToast;
import com.example.careerconnect.Global.DateValidator;
import com.example.careerconnect.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserQualificationActivity extends AppCompatActivity {

    String USERNAME = "john123";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qualification);

        EditText qualificationTitleEdtTxt = findViewById(R.id.qualification_title_edittext);
        EditText qualificationDescriptionEdtTxt = findViewById(R.id.qualification_description_edittext);
        EditText fromMonthEdtTxt = findViewById(R.id.from_month_edittext);
        EditText fromDayEdtTxt = findViewById(R.id.from_day_edittext);
        EditText fromYearEdtTxt = findViewById(R.id.from_year_edittext);
        EditText toMonthEdtTxt = findViewById(R.id.to_month_edittext);
        EditText toDayEdtTxt = findViewById(R.id.to_day_edittext);
        EditText toYearEdtTxt = findViewById(R.id.to_year_edittext);

        Button addQualificationButton = findViewById(R.id.add_qualification_button);
        addQualificationButton.setOnClickListener(v -> {

            String qualificationTitle = qualificationTitleEdtTxt.getText().toString();
            String qualificationDescription = qualificationDescriptionEdtTxt.getText().toString();
            String qualificationFromDate = fromMonthEdtTxt.getText().toString() + "/" +
                                            fromDayEdtTxt.getText().toString() + "/" +
                                            fromYearEdtTxt.getText().toString();
            String qualificationToDate = toMonthEdtTxt.getText().toString() + "/" +
                                            toDayEdtTxt.getText().toString() + "/" +
                                            toYearEdtTxt.getText().toString();

            if (!checkQualificationInfo(qualificationTitle, qualificationDescription, qualificationFromDate,
                                        qualificationToDate)) return;


        });
    }

    private boolean checkQualificationInfo(String title, String description, String fromDate,
                                           String toDate){

        if (title.isEmpty()){
            ButterToast.show(this, "Qualification title required", Toast.LENGTH_SHORT);
            return false;
        }
        if (description.isEmpty()){
            ButterToast.show(this, "Qualification description required", Toast.LENGTH_SHORT);
            return false;
        }
        if (fromDate.isEmpty()){
            ButterToast.show(this, "Qualification starting date required", Toast.LENGTH_SHORT);
            return false;
        }
        else if (!toDate.isEmpty()){

            return DateValidator.isValidDate(fromDate) && DateValidator.isValidDate(toDate);
        }
        return true;
    }

    private JSONObject qualificationToJSONObject(String title, String description, String fromDate,
                                                 String toDate){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            String duration;
            if (toDate.isEmpty()){
                duration = fromDate + " - --/--/----";
            }
            else {
                duration = fromDate + " - " + toDate;
            }
            jsonObject.put("duration", duration);
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
}
