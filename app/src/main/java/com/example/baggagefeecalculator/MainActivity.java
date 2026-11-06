package com.example.baggagefeecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private static final String APP_URL =
            "https://github.com/AininEd/baggage-fee-calculator";

    // Input components
    private EditText etBaggageWeight, etRatePerKg;
    private RadioGroup rgTicketClass;

    // Buttons
    private Button btnCalculate, btnReset,btnAbout;
    // Output components
    private TextView tvExcessWeight;
    private TextView tvExcessCharge;
    private TextView tvTotalPayable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML components to Java
        etBaggageWeight = findViewById(R.id.etBaggageWeight);
        etRatePerKg = findViewById(R.id.etRatePerKg);

        rgTicketClass = findViewById(R.id.rgTicketClass);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        btnAbout = findViewById(R.id.btnAbout);

        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotalPayable = findViewById(R.id.tvTotalPayable);

        // Calculate button
        btnCalculate.setOnClickListener(v -> calculateFee());

        // Reset button
        btnReset.setOnClickListener(v -> resetCalculator());

        // Open About page
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }


    // Calculate excess baggage fee
    private void calculateFee() {

        String weightInput = etBaggageWeight.getText().toString().trim();
        String rateInput = etRatePerKg.getText().toString().trim();

        // Validate empty baggage weight
        if (weightInput.isEmpty()) {
            etBaggageWeight.setError("Please enter baggage weight");
            etBaggageWeight.requestFocus();
            return;
        }

        // Validate empty rate
        if (rateInput.isEmpty()) {
            etRatePerKg.setError("Please enter rate per kg");
            etRatePerKg.requestFocus();
            return;
        }

        double baggageWeight;
        double ratePerKg;

        try {
            baggageWeight = Double.parseDouble(weightInput);
            ratePerKg = Double.parseDouble(rateInput);

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Please enter valid numeric values",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate negative or invalid values
        if (Double.isNaN(baggageWeight)
                || Double.isInfinite(baggageWeight)
                || baggageWeight <= 0) {

            etBaggageWeight.setError(
                    "Baggage weight must be greater than 0");
            etBaggageWeight.requestFocus();
            return;
        }

        if (Double.isNaN(ratePerKg)
                || Double.isInfinite(ratePerKg)
                || ratePerKg < 0) {

            etRatePerKg.setError(
                    "Rate cannot be negative");
            etRatePerKg.requestFocus();
            return;
        }

        // Determine baggage allowance
        double allowance;

        int selectedClass = rgTicketClass.getCheckedRadioButtonId();

        if (selectedClass == R.id.rbBusiness) {
            allowance = 40;
        } else {
            allowance = 20;
        }

        // Perform calculations
        double excessWeight = Math.max(
                0, baggageWeight - allowance);

        double excessCharge = excessWeight * ratePerKg;

        double serviceTax = excessCharge * 0.08;

        double totalPayable = excessCharge + serviceTax;

        // Format decimal values
        DecimalFormat weightFormat = new DecimalFormat("0.##");
        DecimalFormat moneyFormat = new DecimalFormat("0.00");

        // Display calculation results
        tvExcessWeight.setText(
                "Excess Baggage Weight: "
                        + weightFormat.format(excessWeight) + " kg");

        tvExcessCharge.setText(
                "Excess Baggage Charge: RM "
                        + moneyFormat.format(excessCharge));

        tvTotalPayable.setText(
                "Total Amount Payable: RM "
                        + moneyFormat.format(totalPayable));

        // Notify user
        Toast.makeText(this,
                "Calculation completed successfully!",
                Toast.LENGTH_SHORT).show();
    }

    // Reset calculator
    private void resetCalculator() {

        etBaggageWeight.setText("");
        etRatePerKg.setText("");

        // Clear error messages
        etBaggageWeight.setError(null);
        etRatePerKg.setError(null);

        // Reset ticket class to Economy
        rgTicketClass.check(R.id.rbEconomy);

        // Reset calculation results
        tvExcessWeight.setText(
                "Excess Baggage Weight: - kg");

        tvExcessCharge.setText(
                "Excess Baggage Charge: RM -");

        tvTotalPayable.setText(
                "Total Amount Payable: RM -");

        Toast.makeText(this,
                "Calculator has been reset",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out my Baggage Fee Calculator application!\n"
                            + APP_URL
            );

            startActivity(
                    Intent.createChooser(shareIntent, "Share Application")
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
