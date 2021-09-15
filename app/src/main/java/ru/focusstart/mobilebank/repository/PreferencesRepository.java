package ru.focusstart.mobilebank.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import ru.focusstart.mobilebank.models.Currency;

public class PreferencesRepository {
    private static final String APP_PREFERENCES = "PREFS";

    private static final String CURRENCY = "CURRENCY";
    private static final String DATE = "DATE";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final SharedPreferences prefs;

    public PreferencesRepository(Context context) {
        prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveCurrencies(ArrayList<Currency> currencies) {
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<>();
        for (Currency currency : currencies) {
            objStrings.add(gson.toJson(currency));
        }

        String date = dateFormat.format(new Date());
        saveDate(date);
        prefs.edit().putString(CURRENCY, TextUtils.join("‚‗‚", objStrings)).apply();
    }

    public ArrayList<Currency> getCurrencies() {
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>(Arrays.asList(TextUtils.split(prefs.getString(CURRENCY, ""), "‚‗‚")));
        ArrayList<Currency> currencies = new ArrayList<Currency>();
        for (String str : objStrings) {
            Currency currency = gson.fromJson(str, Currency.class);
            currencies.add(currency);
        }
        return currencies;
    }

    private void saveDate(String date) {
        prefs.edit().putString(DATE, date).apply();
    }

    public String getDate() {
        return prefs.getString(DATE, null);
    }
}
