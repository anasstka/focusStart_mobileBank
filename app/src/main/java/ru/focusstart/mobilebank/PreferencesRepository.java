package ru.focusstart.mobilebank;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import ru.focusstart.mobilebank.models.Valute;

public class PreferencesRepository {
    private static final String APP_PREFERENCES = "PREFS";

    private static final String VALUTE = "VALUTE";
    private static final String DATE = "DATE";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final SharedPreferences prefs;

    public PreferencesRepository(Context context) {
        prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveValutes(ArrayList<Valute> valutes) {
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<>();
        for (Valute valute : valutes) {
            objStrings.add(gson.toJson(valute));
        }

        String date = dateFormat.format(new Date());
        saveDate(date);
        prefs.edit().putString(VALUTE, TextUtils.join("‚‗‚", objStrings)).apply();
    }

    public ArrayList<Valute> getValutes() {
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>(Arrays.asList(TextUtils.split(prefs.getString(VALUTE, ""), "‚‗‚")));
        ArrayList<Valute> valutes = new ArrayList<Valute>();
        for (String str : objStrings) {
            Valute valute = gson.fromJson(str, Valute.class);
            valutes.add(valute);
        }
        return valutes;
    }

    private void saveDate(String date) {
        prefs.edit().putString(DATE, date).apply();
    }

    public String getDate() {
        return prefs.getString(DATE, null);
    }
}
