package ru.focusstart.mobilebank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ru.focusstart.mobilebank.ActivityHelper;
import ru.focusstart.mobilebank.R;
import ru.focusstart.mobilebank.adapters.DropdownMenuAdapter;
import ru.focusstart.mobilebank.models.Currency;
import ru.focusstart.mobilebank.repository.PreferencesRepository;

public class CurrencyConverterActivity extends AppCompatActivity {

    private final DecimalFormat decimalFormat = new DecimalFormat("###,###.####");

    private TextInputLayout inputRublesField;
    private TextInputLayout currencySelectionField;
    private TextView conversionResultField;
    private Currency selectedCurrency = null;
    private ArrayList<Currency> currencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        Button buttonConvert = findViewById(R.id.btn_convert);
        inputRublesField = findViewById(R.id.input_rubles_field);
        currencySelectionField = findViewById(R.id.currency_selection_field);
        conversionResultField = findViewById(R.id.tv_conversion_result);

        PreferencesRepository preferences = new PreferencesRepository(getApplicationContext());
        currencies = preferences.getCurrencies();

        DropdownMenuAdapter adapter = new DropdownMenuAdapter(getApplicationContext(), currencies);
        AutoCompleteTextView dropdownMenu = (AutoCompleteTextView) currencySelectionField.getEditText();
        dropdownMenu.setAdapter(adapter);
        dropdownMenu.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ActivityHelper.hideKeyboard(this);
            }
        });
        dropdownMenu.setOnItemClickListener((adapterView, view, position, id) -> {
            selectedCurrency = adapter.getItem(position);
        });

        buttonConvert.setOnClickListener(v -> {
            ActivityHelper.hideKeyboard(this);

            String enteredAmount = inputRublesField.getEditText().getText().toString();
            if (enteredAmount.isEmpty()) return;
            if (selectedCurrency == null) return;

            double result = Double.parseDouble(enteredAmount) * (selectedCurrency.getValue() / selectedCurrency.getNominal());

            conversionResultField.setText(String.format("%s  руб. — %s %s",
                    enteredAmount,
                    decimalFormat.format(result),
                    selectedCurrency.getName().toLowerCase()));
        });
    }

    public void switchActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}