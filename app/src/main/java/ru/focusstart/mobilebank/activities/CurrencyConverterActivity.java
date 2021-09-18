package ru.focusstart.mobilebank.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ru.focusstart.mobilebank.ExtensionActivity;
import ru.focusstart.mobilebank.R;
import ru.focusstart.mobilebank.adapters.DropdownMenuAdapter;
import ru.focusstart.mobilebank.models.Currency;
import ru.focusstart.mobilebank.repository.PreferencesRepository;

public class CurrencyConverterActivity extends AppCompatActivity {

    private static final String SELECTED_CURRENCY = "SELECTED_CURRENCY";
    private static final String CONVERSION_RESULT = "CONVERSION_RESULT";

    private final DecimalFormat decimalFormat = new DecimalFormat("###,###.####");

    private TextInputLayout inputRublesField;
    private TextInputLayout currencySelectionField;
    private AutoCompleteTextView dropdownMenu;
    private TextView conversionResultField;

    private DropdownMenuAdapter adapter;

    private Currency selectedCurrency = null;
    private String conversionResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        if (savedInstanceState != null) {
            conversionResult = savedInstanceState.getString(CONVERSION_RESULT, null);
            selectedCurrency = (Currency) savedInstanceState.getSerializable(SELECTED_CURRENCY);
        }

        Button buttonConvert = findViewById(R.id.btn_convert);
        inputRublesField = findViewById(R.id.input_rubles_field);
        currencySelectionField = findViewById(R.id.currency_selection_field);
        conversionResultField = findViewById(R.id.tv_conversion_result);
        dropdownMenu = (AutoCompleteTextView) currencySelectionField.getEditText();

        conversionResultField.setText(conversionResult);

        dropdownMenu.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ExtensionActivity.hideKeyboard(this);
            }
        });

        dropdownMenu.setOnItemClickListener((adapterView, view, position, id) -> selectedCurrency = adapter.getItem(position));

        buttonConvert.setOnClickListener(v -> {
            ExtensionActivity.hideKeyboard(this);

            String enteredAmount = inputRublesField.getEditText().getText().toString();
            if (enteredAmount.isEmpty()) return;
            if (selectedCurrency == null) return;

            double result = (selectedCurrency.getNominal() * Double.parseDouble(enteredAmount)) / selectedCurrency.getValue();

            conversionResultField.setText(String.format("%s  руб. — %s %s",
                    enteredAmount,
                    decimalFormat.format(result),
                    selectedCurrency.getName().toLowerCase()));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        PreferencesRepository preferences = new PreferencesRepository(getApplicationContext());
        ArrayList<Currency> currencies = preferences.getCurrencies();
        adapter = new DropdownMenuAdapter(getApplicationContext(), currencies);
        dropdownMenu.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CONVERSION_RESULT, conversionResultField.getText().toString());
        outState.putSerializable(SELECTED_CURRENCY, selectedCurrency);
        super.onSaveInstanceState(outState);
    }

    public void switchActivity(View v) {
        ExtensionActivity.switchActivity(this, MainActivity.class, false);
    }
}