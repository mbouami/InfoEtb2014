package com.bouami.infoetb2014.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohammed on 08/03/14.
 */
public class ItemListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    public static String url_villes = "http://www.bouami.fr/GestionPn/public/pn/listevilles";
    public static ArrayList<Map<String,String>> listevilles = null;
    List<Ville> listedesvilles = null;
    public static List<Etab> etabs = null;
    String idvilleencours = null;
    public static Integer indiceville, indiceetab;
    String[] fromColumns = { "id", "nom" };
    int[] toViews = { R.id.id, R.id.nom };

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sEtabCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String Idetab);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sEtabCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String Idetab) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new SimpleAdapter(getActivity(),new ArrayList<Map<String, String>>(), R.layout.model_villes, fromColumns,toViews));
        if (savedInstanceState == null) {
            new RecupererListeVille().execute(url_villes,"POST");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sEtabCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        Map<String, String> item = (Map<String, String>) listView.getItemAtPosition(position);
        assert item != null;
        indiceetab = Integer.valueOf(item.get("id"));
        setActivatedPosition(position);
/*        mActivatedPosition = position;*/
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(item.get("id"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
/*        Toast.makeText(getActivity(),"Choix : "+mActivatedPosition+":::"+ListView.INVALID_POSITION+":::"+savedInstanceState.getInt(STATE_ACTIVATED_POSITION),Toast.LENGTH_SHORT).show();*/
/*        Toast.makeText(getActivity(),"Choix : "+mActivatedPosition+":::"+ListView.INVALID_POSITION+":::"+position+":::",Toast.LENGTH_SHORT).show();*/
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public class RecupererListeVille extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Chargement des données en cours. Merci de patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                listevilles = loadJsonFromNetwork(params[0],params[1]);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            final AutoCompleteTextView lesvillesdudepart = (AutoCompleteTextView) getActivity().findViewById(R.id.liste_villes);
/*            SimpleAdapter mAdapter;*/
            lesvillesdudepart.setAdapter(new SimpleAdapter(getActivity(), listevilles, R.layout.model_villes, fromColumns, toViews));
/*            etabs = listedesvilles.get(0).getEtablissements();
            ArrayList<Map<String, String>> listetabs = new ArrayList<Map<String, String>>();
            if (etabs != null) {
                for(int j = 0; j < etabs.size(); j++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", Integer.toString(j));
                    map.put("nom", etabs.get(j).type+ " "+etabs.get(j).nom);
                    listetabs.add(map);
                }
            }
            setListAdapter(new SimpleAdapter(getActivity(),listetabs, R.layout.model_villes, fromColumns,toViews));*/
            lesvillesdudepart.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Map<String, String> ville_choisi = (Map<String, String>) parent.getItemAtPosition(position);
                    ArrayList<Map<String, String>> listetabs = new ArrayList<Map<String, String>>();
                    idvilleencours = ville_choisi != null ? ville_choisi.get("id") : null;
                    lesvillesdudepart.setText(ville_choisi != null ? ville_choisi.get("nom") : null);
                    for(int i = 0; i < listedesvilles.size(); i++) {
                        if (listedesvilles.get(i).getId().equals(idvilleencours)) {
                            indiceville = i;
                            etabs = listedesvilles.get(i).getEtablissements();
                            if (etabs != null) {
                                for(int j = 0; j < etabs.size(); j++) {
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("id", Integer.toString(j));
                                    map.put("nom", etabs.get(j).type+ " "+etabs.get(j).nom);
                                    listetabs.add(map);
                                }
                            }
                            break;
                        }
                    }
                    setListAdapter(new SimpleAdapter(getActivity(),listetabs, R.layout.model_villes, fromColumns,toViews));
                }
            });
        }

        public ArrayList<Map<String, String>> loadJsonFromNetwork(String urlString, String method) throws IOException {
            ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
            JSONParser jsonparservilles = new JSONParser();
            listedesvilles = jsonparservilles.parse(urlString,method);
            for (Ville entry : listedesvilles) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", entry.id);
                map.put("nom", entry.nom +"("+entry.depart+")");
                list.add(map);
            }
            return list;

        }
    }
}
