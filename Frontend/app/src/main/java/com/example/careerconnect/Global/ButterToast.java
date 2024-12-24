package com.example.careerconnect.Global;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.careerconnect.R;

/**
 * Custom Toast message layout for displaying short forms of information.
 * <p>MMMmmm... Butter Toast.</p>
 */
public class ButterToast {

    /**
     * Shows a Toast message with desired message and duration
     * @param context application context
     * @param message desired message
     * @param duration desired message duration
     */
    public static void show(Context context, String message, int duration){

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.butter_toast_message, null);

        TextView textView = layout.findViewById(R.id.toast_message);
        textView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }
}
