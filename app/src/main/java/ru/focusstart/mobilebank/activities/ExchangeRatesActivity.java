package ru.focusstart.mobilebank.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ru.focusstart.mobilebank.DataParser;
import ru.focusstart.mobilebank.ExtensionActivity;
import ru.focusstart.mobilebank.R;
import ru.focusstart.mobilebank.adapters.CurrencyAdapter;
import ru.focusstart.mobilebank.models.Currency;
import ru.focusstart.mobilebank.repository.PreferencesRepository;

public class ExchangeRatesActivity extends AppCompatActivity {

    private ArrayList<Currency> currencies;
    private CurrencyAdapter currencyAdapter;
    private ListView listViewCurrencies;
    private PreferencesRepository preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rates);

        preferences = new PreferencesRepository(getApplicationContext());

        listViewCurrencies = findViewById(R.id.lv_currency);
        View header = getLayoutInflater().inflate(R.layout.listview_header_exchange_rates, null);
        listViewCurrencies.addHeaderView(header);

        currencies = preferences.getCurrencies();
        currencyAdapter = new CurrencyAdapter(getApplicationContext(), currencies);
        listViewCurrencies.setAdapter(currencyAdapter);
    }

    public void switchActivity(View v) {
        ExtensionActivity.switchActivity(this, MainActivity.class, false);
    }

    public void reloadListView(View view) {
        AsyncTask.execute(() -> {
            currencies = new ArrayList<>(DataParser.sendRequest());

            runOnUiThread(() -> {
                preferences.saveCurrencies(currencies);
                currencyAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Данные обновлены!", Toast.LENGTH_SHORT).show();
            });
        });
    }
}