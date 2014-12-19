package br.com.caronacerta.caronacerta.adapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.model.RowModel;

public class RatingAdapter extends ArrayAdapter<RowModel> {
    private Context context;
    private ArrayList<RowModel> list;

    public RatingAdapter(Context context, ArrayList<RowModel> list) {
        super(context, R.layout.row, list);
        this.context = context;
        this.list = list;
    }

    public RowModel getModel(int position) {
        return list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewWrapper wrapper;
        RatingBar rate;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);;
            row = inflater.inflate(R.layout.row, parent, false);
            wrapper = new ViewWrapper(row);
            row.setTag(wrapper);
            rate = wrapper.getRatingBar();
            RatingBar.OnRatingBarChangeListener l =
                    new RatingBar.OnRatingBarChangeListener() {
                        public void onRatingChanged(RatingBar ratingBar,
                                                    float rating, boolean fromTouch) {
                            Integer myPosition = (Integer) ratingBar.getTag();
                            RowModel model = getModel(myPosition);
                            model.rating = rating;
                            LinearLayout parent = (LinearLayout) ratingBar.getParent();
                            TextView label = (TextView) parent.findViewById(R.id.label);
                            label.setText(model.toString());
                        }
                    };
            rate.setOnRatingBarChangeListener(l);
        } else {
            wrapper = (ViewWrapper) row.getTag();
            rate = wrapper.getRatingBar();
        }

        RowModel model = getModel(position);

        wrapper.getLabel().setText(model.toString());
        rate.setTag(new Integer(position));
        rate.setRating(model.rating);
        return (row);
    }
}