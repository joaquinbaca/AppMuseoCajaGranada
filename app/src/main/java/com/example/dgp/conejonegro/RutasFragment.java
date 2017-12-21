package com.example.dgp.conejonegro;

import android.support.v4.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by Alvaro on 21/12/2017.
 */

public class RutasFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ListView mRutasList;
    RutaAdapter mRutasAdapter;

    Museo museo = Museo.getInstance();


    // TODO: Rename and change types of parameters


    private RutasFragment.OnFragmentInteractionListener mListener;

    public RutasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RutasFragment newInstance() {
        RutasFragment fragment = new RutasFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rutas, container, false);

        mRutasList = (ListView) root.findViewById(R.id.rutas_list);


        mRutasAdapter = new RutaAdapter(getActivity(), museo.getRutas());


        mRutasList.setAdapter(mRutasAdapter);

        mRutasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*
                Ruta ruta = mRutasAdapter.getItem(position);
                Intent shareIntent = new Intent(getActivity(), verContenidoRuta.class);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra("id", ruta.getIdRuta());
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                getActivity().finish();
                */
            }
        });


        setHasOptionsMenu(true);
        return root;
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.filtrar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSalasAdapter = new SalaAdapter(getActivity(), museo.getSalasFiltro(query));
                mSalasList.setAdapter(mSalasAdapter);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query)
            {
                mSalasAdapter = new SalaAdapter(getActivity(), museo.getSalasFiltro(query));
                mSalasList.setAdapter(mSalasAdapter);
                return true;
            }
        });
    }
*/
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filtrar) {
            // Eliminar todos los lea
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
