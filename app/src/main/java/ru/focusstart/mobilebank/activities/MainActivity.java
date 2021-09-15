package ru.focusstart.mobilebank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.focusstart.mobilebank.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button exchangeRates = findViewById(R.id.btn_exchange_rates);
        exchangeRates.setOnClickListener(v -> switchActivity(ExchangeRatesActivity.class));

        Button currencyConverter = findViewById(R.id.btn_currency_converter);
        currencyConverter.setOnClickListener(v -> switchActivity(CurrencyConverterActivity.class));
    }

    private void switchActivity(Class<?> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }
}