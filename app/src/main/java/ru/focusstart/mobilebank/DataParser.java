package ru.focusstart.mobilebank;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import ru.focusstart.mobilebank.models.Currency;

public class DataParser {

    private static final String API = "https://www.cbr-xml-daily.ru/daily_json.js";

    public static ArrayList<Currency> sendRequest() {
        ArrayList<Currency> currencies = new ArrayList<>();

        HttpURLConnection connection = null;
        try {
            URL url = new URL(API);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                currencies = parsingJson(isr);
                isr.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        Collections.sort(currencies, (c1, c2) -> c1.getName().compareTo(c2.getName()));

        return currencies;
    }

    public static ArrayList<Currency> parsingJson(InputStreamReader file) {
        ArrayList<Currency> currencies = new ArrayList<>();

        try {
            Object object = new JSONParser().parse(file);
            JSONObject json = (JSONObject) object;
            JSONObject valutes = (JSONObject) json.get("Valute");
            assert valutes != null;
            for (Object key : valutes.keySet()) {
                JSONObject obj = (JSONObject) valutes.get(key);
                assert obj != null;

                String id = (String) obj.get("ID");
                String numCode = (String) obj.get("NumCode");
                String charCode = (String) obj.get("CharCode");
                long nominal = (long) obj.get("Nominal");
                String name = (String) obj.get("Name");
                Double value = (Double) obj.get("Value");
                Double previous = (Double) obj.get("Previous");

                Currency currency = new Currency(id, numCode, charCode, nominal, name, value, previous);
                currencies.add(currency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currencies;
    }
}
