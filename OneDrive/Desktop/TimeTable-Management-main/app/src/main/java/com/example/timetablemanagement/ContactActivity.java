package com.example.timetablemanagement;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView contactTextView = findViewById(R.id.contact_text_view);
        String contactText = "<h2>Contact Us</h2>" +
                "<p>If you have any questions, feedback, or need support, feel free to reach out to us. We are here to help you!</p>" +
                "<h3>Contact Information:</h3>" +
                "<p><strong>Email:</strong> <a href='mailto:abc@example.com'>abc@example.com</a></p>" +
                "<p><strong>Website:</strong> <a href='http://www.example.com'>www.example.com</a></p>" +
                "<h3>Office Address:</h3>" +
                "<p>CUCEK<br/>" +
                "CUSAT, KERALA<br/>" +
                "India</p>" +
                "<h3>Phone:</h3>" +
                "<p>Thank you for using our app! We look forward to assisting you.</p>";

        contactTextView.setText(Html.fromHtml(contactText, Html.FROM_HTML_MODE_COMPACT));
    }
}
