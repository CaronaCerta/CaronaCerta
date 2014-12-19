package br.com.caronacerta.caronacerta.adapter;

import android.app.ListActivity;
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

public class RatingListAdapter extends ListActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

    }

    public class RatingAdapter extends ArrayAdapter<RowModel> {
        public RatingAdapter(ArrayList<RowModel> list) {
            super(RatingListAdapter.this, R.layout.row, list);
        }

        public RowModel getModel(int position) {
            return (((RatingAdapter) getListAdapter()).getItem(position));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewWrapper wrapper;
            RatingBar rate;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
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

    public class RowModel {
        String label;
        float rating = 2.0f;

        public RowModel(String label) {
            this.label = label;
        }

        public String toString() {
            if (rating >= 3.0) {
                return (label.toUpperCase());
            }
            return (label);
        }
    }
}