package ru.focusstart.mobilebank.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.FadingCircle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.focusstart.mobilebank.DataHelper;
import ru.focusstart.mobilebank.R;
import ru.focusstart.mobilebank.models.Currency;
import ru.focusstart.mobilebank.repository.PreferencesRepository;

public class SplashScreen extends AppCompatActivity {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    ProgressBar progressBar;
    private PreferencesRepository preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        preferences = new PreferencesRepository(getApplicationContext());

        progressBar = findViewById(R.id.spin_kit);

        FadingCircle fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);

        String currDate = dateFormat.format(new Date());
        String previousDate = preferences.getDate();
        if (currDate.equals(previousDate)) {
            switchActivity();
        } else {
            loadingData();
        }

    }

    public void loadingData() {
        AsyncTask.execute(() -> {
            ArrayList<Currency> currencies = DataHelper.sendRequest();

            runOnUiThread(() -> {
                preferences.saveCurrencies(currencies);
                switchActivity();
            });
        });
    }

    private void switchActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}