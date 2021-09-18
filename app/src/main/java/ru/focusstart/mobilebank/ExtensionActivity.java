package ru.focusstart.mobilebank;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

public class ExtensionActivity {

    public static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        imm.hideSoftInputFromWindow(
                view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void switchActivity(AppCompatActivity activity1, Class<?> activity2, boolean finish) {
        Intent intent = new Intent(activity1.getApplicationContext(), activity2);
        activity1.startActivity(intent);
        if (finish) activity1.finish();
        activity1.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
