package br.com.caronacerta.caronacerta.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.model.Carona;

public class CaronaAdapter extends RecyclerView.Adapter<CaronaAdapter.CaronaViewHolder> {

    private List<Carona> caronaList;

    public CaronaAdapter(List<Carona> caronaList) {
        this.caronaList = caronaList;
    }


    @Override
    public int getItemCount() {
        return caronaList.size();
    }

    @Override
    public void onBindViewHolder(CaronaViewHolder caronaViewHolder, int i) {
        Carona ci = caronaList.get(i);
        caronaViewHolder.vName.setText(ci.name);
        caronaViewHolder.vSurname.setText(ci.surname);
        caronaViewHolder.vEmail.setText(ci.email);
        caronaViewHolder.vTitle.setText(ci.name + " " + ci.surname);
    }

    @Override
    public CaronaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.carona_card_layout, viewGroup, false);

        return new CaronaViewHolder(itemView);
    }

    public static class CaronaViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;

        public CaronaViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView) v.findViewById(R.id.txtSurname);
            vEmail = (TextView) v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.title);
        }
    }
}