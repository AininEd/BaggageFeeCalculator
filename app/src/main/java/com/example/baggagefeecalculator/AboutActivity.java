
package com.example.baggagefeecalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set ActionBar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About Application");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Developer details
        TextView tvDeveloperName = findViewById(R.id.tvDeveloperName);
        TextView tvStudentNumber = findViewById(R.id.tvStudentNumber);
        TextView tvGroup = findViewById(R.id.tvGroup);
        TextView tvProgrammeCode = findViewById(R.id.tvProgrammeCode);

        tvDeveloperName.setText("AININ SOFIYA BT EDRUS");
        tvStudentNumber.setText("2024280908");
        tvGroup.setText("5A");
        tvProgrammeCode.setText("CDCS251");

        // Application website
        TextView tvWebsite = findViewById(R.id.tvWebsite);

        String websiteUrl = "https://github.com/AininEd/baggage-fee-calculator";

        tvWebsite.setText("Visit GitHub Repository");
        tvWebsite.setPaintFlags(
                tvWebsite.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG
        );

        tvWebsite.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(websiteUrl)
            );

            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
