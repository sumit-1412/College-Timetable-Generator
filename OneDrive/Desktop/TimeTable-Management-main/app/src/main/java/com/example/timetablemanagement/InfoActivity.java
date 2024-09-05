package com.example.timetablemanagement;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView infoTextView = findViewById(R.id.info_text_view);
        String infoText = "Timetable Management App\n\n" +
                "Welcome to the Timetable Management App!\n\n" +
                "This application is designed to help you efficiently manage and organize your schedule. " +
                "Whether you're a student, a teacher, or a professional, this app offers a range of features " +
                "to streamline your planning and keep you on track.\n\n" +
                "Key Features:\n" +
                "- Easy Timetable Creation: Create and customize your timetable with ease. Add classes, " +
                "meetings, or any other events and adjust their timings as needed.\n" +
                "- Automated Scheduling: Input your courses and let the app generate an optimal timetable for " +
                "you, avoiding conflicts and ensuring a balanced schedule.\n" +
                "- Notifications and Reminders: Stay on top of your schedule with timely notifications and reminders " +
                "for upcoming events and classes.\n" +
                "- Color Coding: Assign different colors to various activities for a clear and organized view of your timetable.\n" +
                "- Synchronization: Sync your timetable with other calendar applications to have all your events in one place.\n" +
                "- User-friendly Interface: Navigate through the app effortlessly with our intuitive and clean design.\n" +
                "- Data Security: Your timetable data is securely stored and protected to ensure your privacy.\n\n" +
                "How to Use:\n" +
                "1. Setup: Start by creating your profile and inputting your courses or events.\n" +
                "2. Create Timetable: Use the easy drag-and-drop interface to add and arrange your events.\n" +
                "3. Manage Schedule: Edit, delete, or move events as needed to keep your timetable up to date.\n" +
                "4. Get Notifications: Enable notifications to receive reminders for your scheduled events.\n" +
                "5. Sync Calendars: Link your timetable with other calendar apps for seamless integration.\n\n" +
                "We hope this app helps you stay organized and manage your time effectively. " +
                "For any questions or support, feel free to contact us through the Help section.";

        infoTextView.setText(infoText);
    }
}
