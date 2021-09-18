package ru.focusstart.mobilebank.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.focusstart.mobilebank.ExtensionActivity;
import ru.focusstart.mobilebank.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button exchangeRates = findViewById(R.id.btn_exchange_rates);
        exchangeRates.setOnClickListener(v -> ExtensionActivity.switchActivity(this, ExchangeRatesActivity.class, false));

        Button currencyConverter = findViewById(R.id.btn_currency_converter);
        currencyConverter.setOnClickListener(v -> ExtensionActivity.switchActivity(this, CurrencyConverterActivity.class, false));
    }
}