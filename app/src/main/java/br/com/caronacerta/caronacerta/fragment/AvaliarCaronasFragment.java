package br.com.caronacerta.caronacerta.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.AvaliarAdapter;
import br.com.caronacerta.caronacerta.model.Carona;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;

public class AvaliarCaronasFragment extends BasicFragment {

    ListView viewList;

    public AvaliarCaronasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_avaliar_caronas, container, false);

        viewList = (ListView) rootView.findViewById(R.id.avaliar_carona_results);

        JSONObject jsonObject = RequestUtil.getData("avaliacao/pending", SessionUtil.getToken(getActivity().getApplicationContext()));

        try {
            if (!jsonObject.getBoolean("error")) {
                if (jsonObject.getJSONArray("caronas").length() > 0) {
                    ListAdapter av = new AvaliarAdapter(getActivity().getApplicationContext(), createList(jsonObject.getJSONArray("caronas")), this);
                    viewList.setAdapter(av);
                    viewList.setScrollContainer(false);
                    setListViewHeightBasedOnChildren(viewList);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.avaliar_carona_no_results, Toast.LENGTH_LONG).show();
                }
            }
            // Some error returned
            else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.avaliar_carona_list_error_message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), R.string.server_response_error, Toast.LENGTH_LONG).show();
        }


        return rootView;
    }

    private ArrayList<Carona> createList(JSONArray caronas) throws JSONException {

        ArrayList<Carona> result = new ArrayList<Carona>();
        for (int i = 0; i < caronas.length(); i++) {
            Carona av = new Carona();
            JSONObject carona = (JSONObject) caronas.get(i);
            av.id_carona = carona.getString("id_carona");
            av.lugar_saida = carona.getString("lugar_saida");
            av.lugar_destino = carona.getString("lugar_destino");
            av.data = carona.getString("data");

            result.add(av);
        }

        return result;
    }

    public void avaliarCarona(String id_carona, String nota) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // TODO change id_atributo to be dynamic
        nameValuePairs.add(new BasicNameValuePair("id_atributo", "1"));

        nameValuePairs.add(new BasicNameValuePair("id_carona", id_carona));
        nameValuePairs.add(new BasicNameValuePair("id_usuario_avaliador", SessionUtil.getUserId(getActivity().getApplicationContext())));
        nameValuePairs.add(new BasicNameValuePair("id_usuario_avaliado", SessionUtil.getUserId(getActivity().getApplicationContext())));
        nameValuePairs.add(new BasicNameValuePair("papel", "1"));
        nameValuePairs.add(new BasicNameValuePair("nota", nota));

        JSONObject jsonObject = RequestUtil.postData("avaliacao", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

        try {
            if (!jsonObject.getBoolean("error")) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.avaliar_carona_success_message, Toast.LENGTH_LONG).show();
                navigateToFragment(new AvaliarCaronasFragment());
            }
            // Some error returned
            else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.avaliar_carona_error_message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.server_response_error, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle(R.string.avaliar_caronas_title);
    }
}                                                     