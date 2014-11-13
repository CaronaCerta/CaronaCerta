package br.com.caronacerta.caronacerta.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.com.caronacerta.caronacerta.MainActivity;
import br.com.caronacerta.caronacerta.R;

public class AdicionarCaronasFragment extends Fragment {


    public AdicionarCaronasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_adicionar_caronas, container, false);

        final TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentMinute(0);
        final DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);

        Button confirm = (Button) rootView.findViewById(R.id.add_carona_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Integer min_int = timePicker.getCurrentMinute();
                String min_str = "00";
                if (min_int != 0) min_str = min_int.toString();
                Integer hour_int = datePicker.getDayOfMonth();
                String hour_str;
                if (hour_int.toString().length() == 1) hour_str = "0" + hour_int.toString();
                else hour_str = hour_int.toString();

                String dateTime = (hour_str + "/" + datePicker.getMonth() +
                                    " - " + timePicker.getCurrentHour() + "h" + min_str);

                MainActivity.caronasELVGroup.add(dateTime);
                List<String> l = new ArrayList<String>();

                EditText nameView1 = (EditText) rootView.findViewById(R.id.add_caronas_name_1);
                EditText nameView2 = (EditText) rootView.findViewById(R.id.add_caronas_name_2);
                EditText nameView3 = (EditText) rootView.findViewById(R.id.add_caronas_name_3);
                EditText nameView4 = (EditText) rootView.findViewById(R.id.add_caronas_name_4);

                EditText numView1 = (EditText) rootView.findViewById(R.id.add_caronas_num_1);
                EditText numView2 = (EditText) rootView.findViewById(R.id.add_caronas_num_2);
                EditText numView3 = (EditText) rootView.findViewById(R.id.add_caronas_num_3);
                EditText numView4 = (EditText) rootView.findViewById(R.id.add_caronas_num_4);

                if (nameView1.getText().length() != 0){
                    l.add(nameView1.getText() + " - " + numView1.getText());
                }

                if (nameView2.getText().length() != 0){
                    l.add(nameView2.getText() + " - " + numView2.getText());
                }

                if (nameView3.getText().length() != 0){
                    l.add(nameView3.getText() + " - " + numView3.getText());
                }

                if (nameView4.getText().length() != 0){
                    l.add(nameView4.getText() + " - " + numView4.getText());
                }

                MainActivity.caronasELVChild.put(MainActivity.caronasELVGroup.get(MainActivity.caronasELVGroup.size()-1), l);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("Caronas adicionadas com sucesso.")
                builder.setMessage("Rides added successfully.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return rootView;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this.getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /* AUTOCOMPLETE API */
    public static final String LOG_TAG = "ExampleApp";

    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json";

    public static final String API_KEY = "AIzaSyC_n8kc6HNTM2yUlLm-ztqbTApWlueLhxo";

    static public ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:br");
            sb.append("&input=").append(URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } catch (Exception e) {
            Log.e(LOG_TAG, "ARGHHHHHHHHHHH!!!!!", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }
}
