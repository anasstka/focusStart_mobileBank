package ru.focusstart.mobilebank.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.focusstart.mobilebank.R;
import ru.focusstart.mobilebank.models.Currency;

public class CurrencyAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Currency> currencies;

    public CurrencyAdapter(Context context, ArrayList<Currency> currencies) {
        this.context = context;
        this.currencies = currencies;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return currencies.size();
    }

    @Override
    public Currency getItem(int position) {
        return currencies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.listview_item_exchange_rates, parent, false);
        }

        if (position % 2 == 0) {
            view.setBackgroundColor(context.getColor(R.color.color_secondary));
        } else {
            view.setBackgroundColor(context.getColor(R.color.color_secondary_light));
        }

        Currency currency = getItem(position);

        ((TextView) view.findViewById(R.id.nominal)).setText(String.valueOf(currency.getNominal()).replace(".", ","));
        ((TextView) view.findViewById(R.id.name)).setText(currency.getName().replace(".", ","));

        TextView valueTextView = view.findViewById(R.id.value);
        Double value = currency.getValue();
        Double previous = currency.getPrevious();
        Pair<String, Integer> trendWithColor = trend(value, previous);
        valueTextView.setText(value + trendWithColor.first);
        valueTextView.setTextColor(trendWithColor.second);

        return view;
    }

    private Pair<String, Integer> trend(Double current, Double previous) {
        if (current > previous) return Pair.create(" ▲", context.getColor(R.color.color_green));
        if (current < previous) return Pair.create(" ▼", context.getColor(R.color.color_red));
        return Pair.create("", Color.BLACK);
    }
}