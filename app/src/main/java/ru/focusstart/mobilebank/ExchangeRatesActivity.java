package ru.focusstart.mobilebank;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ru.focusstart.mobilebank.models.Valute;

public class ExchangeRatesActivity extends AppCompatActivity {

    private ValuteAdapter valuteAdapter;
    private ArrayList<Valute> valutesArrayList;
    private ListView listViewValutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rates);

        valutesArrayList = new ArrayList<>();
        listViewValutes = findViewById(R.id.lv_currency);
        View header = getLayoutInflater().inflate(R.layout.listview_header_exchange_rates, null);
        listViewValutes.addHeaderView(header);

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
                        valuteAdapter = new ValuteAdapter(getApplicationContext(), valutesArrayList);
                        listViewValutes.setAdapter(valuteAdapter);
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
}