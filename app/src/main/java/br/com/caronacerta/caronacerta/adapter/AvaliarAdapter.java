package br.com.caronacerta.caronacerta.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.fragment.AvaliarCaronasFragment;
import br.com.caronacerta.caronacerta.fragment.BasicFragment;
import br.com.caronacerta.caronacerta.model.Carona;

public class AvaliarAdapter extends ArrayAdapter<Carona> {

    private Context context;
    private ArrayList<Carona> caronas;
    private BasicFragment fragment;

    public AvaliarAdapter(Context context, ArrayList<Carona> caronas, BasicFragment fragment) {
        super(context, R.layout.avaliar_card_layout, caronas);
        this.context = context;
        this.caronas = caronas;
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.avaliar_card_layout, parent, false);
        }

        TextView txtLugarSaida = (TextView) convertView.findViewById(R.id.lugar_saida);
        TextView txtLugarDestino = (TextView) convertView.findViewById(R.id.lugar_destino);
        TextView txtData = (TextView) convertView.findViewById(R.id.data);
        txtLugarSaida.setText("Saida: " + caronas.get(position).lugar_saida);
        txtLugarDestino.setText("Destino: " + caronas.get(position).lugar_destino);
        txtData.setText("Data: " + caronas.get(position).data);

        Button dislike = (Button) convertView.findViewById(R.id.btnDislikeCarona);
        dislike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id_carona = caronas.get(position).id_carona;
                String nota = "0";
                ((AvaliarCaronasFragment) fragment).avaliarCarona(id_carona, nota);
            }
        });

        Button like = (Button) convertView.findViewById(R.id.btnLikeCarona);
        like.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String id_carona = caronas.get(position).id_carona;
                String nota = "5";
                ((AvaliarCaronasFragment) fragment).avaliarCarona(id_carona, nota);
            }
        });

        return convertView;
    }
}