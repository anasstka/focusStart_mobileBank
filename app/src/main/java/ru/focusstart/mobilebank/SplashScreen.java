package ru.focusstart.mobilebank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.FoldingCube;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ru.focusstart.mobilebank.models.Valute;

public class SplashScreen extends AppCompatActivity {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    ProgressBar progressBar;
    private PreferencesRepository preferences;
    private ArrayList<Valute> valutesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        valutesArrayList = new ArrayList<>();
        preferences = new PreferencesRepository(getApplicationContext());

        progressBar = findViewById(R.id.spin_kit);

        FoldingCube foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);

        String currDate = dateFormat.format(new Date());
        String previousDate = preferences.getDate();
        if (currDate.equals(previousDate)) {
            switchActivity();
        } else {
            loadingData();
        }

    }

    private void loadingData() {
        System.out.println("YES");
        AsyncTask.execute(() -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    parser(isr);
                    isr.close();

                    runOnUiThread(() -> {
                        preferences.saveValutes(valutesArrayList);
                        switchActivity();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private void parser(InputStreamReader file) {
        try {
            Object object = new JSONParser().parse(file);
            JSONObject json = (JSONObject) object;
            JSONObject valutes = (JSONObject) json.get("Valute");
            for (Object key : valutes.keySet()) {
                JSONObject obj = (JSONObject) valutes.get(key);

                String id = (String) obj.get("ID");
                String numCode = (String) obj.get("NumCode");
                String charCode = (String) obj.get("CharCode");
                long nominal = (long) obj.get("Nominal");
                String name = (String) obj.get("Name");
                Double value = (Double) obj.get("Value");
                Double previous = (Double) obj.get("Previous");

                Valute valute = new Valute(id, numCode, charCode, nominal, name, value, previous);
                valutesArrayList.add(valute);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchActivity() {
        Intent intent = new Intent(getApplicationContext(), ExchangeRatesActivity.class);
        startActivity(intent);
        finish();
    }
}