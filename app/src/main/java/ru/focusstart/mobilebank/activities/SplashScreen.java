package ru.focusstart.mobilebank.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.FadingCircle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.focusstart.mobilebank.DataParser;
import ru.focusstart.mobilebank.ExtensionActivity;
import ru.focusstart.mobilebank.R;
import ru.focusstart.mobilebank.models.Currency;
import ru.focusstart.mobilebank.repository.PreferencesRepository;

public class SplashScreen extends AppCompatActivity {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private PreferencesRepository preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        ProgressBar progressBar = findViewById(R.id.spin_kit);
        FadingCircle fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);

        preferences = new PreferencesRepository(getApplicationContext());

        String currDate = dateFormat.format(new Date());
        String previousDate = preferences.getDate();
        if (currDate.equals(previousDate)) {
            ExtensionActivity.switchActivity(this, MainActivity.class, true);
        } else {
            loadingData();
        }

    }

    private void loadingData() {
        AsyncTask.execute(() -> {
            ArrayList<Currency> currencies = DataParser.sendRequest();

            runOnUiThread(() -> {
                preferences.saveCurrencies(currencies);
                ExtensionActivity.switchActivity(this, MainActivity.class, true);
            });
        });
    }
}