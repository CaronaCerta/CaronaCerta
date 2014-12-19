package br.com.caronacerta.caronacerta.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.fragment.BasicFragment;
import br.com.caronacerta.caronacerta.fragment.ProcurarCaronaFragment;
import br.com.caronacerta.caronacerta.model.Carona;

public class CaronaAdapter extends ArrayAdapter<Carona> {

    private Context context;
    private ArrayList<Carona> caronas;
    private BasicFragment fragment;

    public CaronaAdapter(Context context, ArrayList<Carona> caronas, BasicFragment fragment) {
        super(context, R.layout.carona_card_layout, caronas);
        this.context = context;
        this.caronas = caronas;
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.carona_card_layout, parent, false);
        }

        Button entrarCarona = (Button) convertView.findViewById(R.id.btnEntrarCarona);
        entrarCarona.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id_carona = caronas.get(position).id_carona;
                ((ProcurarCaronaFragment) fragment).entrarCarona(id_carona);
            }
        });

        TextView txtLugarSaida = (TextView) convertView.findViewById(R.id.lugar_saida);
        TextView txtLugarDestino = (TextView) convertView.findViewById(R.id.lugar_destino);
        TextView txtLugaresDisponiveis = (TextView) convertView.findViewById(R.id.lugares_disponiveis);
        TextView txtData = (TextView) convertView.findViewById(R.id.data);
        TextView txtObservacoes = (TextView) convertView.findViewById(R.id.observacoes);
        txtLugarSaida.setText("Saida: " + caronas.get(position).lugar_saida);
        txtLugarDestino.setText("Destino: " + caronas.get(position).lugar_destino);
        txtLugaresDisponiveis.setText("Lugares: " + caronas.get(position).lugares_disponiveis);
        txtData.setText("Data: " + caronas.get(position).data);
        txtObservacoes.setText("Observações: " + caronas.get(position).observacoes);

        return convertView;
    }
}