package ru.focusstart.mobilebank;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ru.focusstart.mobilebank.models.Valute;

public class ExchangeRatesActivity extends AppCompatActivity {

    private ValuteAdapter valuteAdapter;
    private ListView listViewValutes;
    private PreferencesRepository preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rates);

        preferences = new PreferencesRepository(getApplicationContext());

        listViewValutes = findViewById(R.id.lv_currency);
        View header = getLayoutInflater().inflate(R.layout.listview_header_exchange_rates, null);
        listViewValutes.addHeaderView(header);

        ArrayList<Valute> valutes = preferences.getValutes();
        valuteAdapter = new ValuteAdapter(getApplicationContext(), valutes);
        listViewValutes.setAdapter(valuteAdapter);
    }
}