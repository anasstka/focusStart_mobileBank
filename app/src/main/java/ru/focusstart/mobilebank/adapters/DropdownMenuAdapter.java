package ru.focusstart.mobilebank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ru.focusstart.mobilebank.R;
import ru.focusstart.mobilebank.models.Currency;

public class DropdownMenuAdapter extends ArrayAdapter<Currency> {

    private final DecimalFormat decimalFormat = new DecimalFormat("###,###.####");

    public DropdownMenuAdapter(Context context, ArrayList<Currency> currencies) {
        super(context, R.layout.dropdown_menu_item, currencies);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_menu_item, null);
        }

        Currency currency = getItem(position);
        Double value = currency.getValue() / currency.getNominal();
        String row = String.format("%s\n(%s руб.)", currency.getName(), decimalFormat.format(value));

        ((TextView) view.findViewById(R.id.text)).setText(row);

        return view;
    }
}
