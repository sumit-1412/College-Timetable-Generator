package com.example.timetablemanagement;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutTextView = findViewById(R.id.about_text_view);
        String aboutText = "<h2>About Timetable Management App</h2>" +
                "<p><strong>Version:</strong> 1.0.0</p>" +
                "<p>Timetable Management App is developed to help users efficiently manage their schedules. " +
                "The app provides a simple and intuitive interface to create, edit, and organize timetables for teachers." +
                " Our goal is to make scheduling easier and more convenient for everyone.</p>" +
                "<h3>Developed by:</h3>" +
                "<ul>" +
                "<li>Yash Raj</li>" +
                "<li>Sameer</li>" +
                "<li>Sumit Singh</li>" +
                "<li>Shubham Kumar</li>" +
                "<li>Ankit Kumar</li>" +
                "</ul>" +
                "<h3>Contact Us:</h3>" +
                "<p>Email: <a href='mailto:abc@example.com'>abc@example.com</a></p>" +
                "<p>Website: <a href='http://www.example.com'>www.example.com</a></p>" +
                "<p>Thank you for using our app! We hope it helps you stay organized and manage your time effectively.</p>";

        aboutTextView.setText(Html.fromHtml(aboutText, Html.FROM_HTML_MODE_COMPACT));
    }
}
