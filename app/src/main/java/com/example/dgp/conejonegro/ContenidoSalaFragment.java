package com.example.dgp.conejonegro;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by Alvaro on 04/12/2017.
 */

public class ContenidoSalaFragment extends Fragment {
    ListView mSalasList;
    ContenidoSalaAdapter mSalasAdapter;
    Sala sala;
    Museo museo = Museo.getInstance();


    // TODO: Rename and change types of parameters


    private ContenidoSalaFragment.OnFragmentInteractionListener mListener;

    public ContenidoSalaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContenidoSalaFragment newInstance() {
        ContenidoSalaFragment fragment = new ContenidoSalaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


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
                mSalasAdapter = new ContenidoSalaAdapter(getActivity(), museo.getElementosFiltro(query, sala));
                mSalasList.setAdapter(mSalasAdapter);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query)
            {
                mSalasAdapter = new ContenidoSalaAdapter(getActivity(), museo.getElementosFiltro(query, sala));
                mSalasList.setAdapter(mSalasAdapter);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contenido_sala, container, false);

        mSalasList = (ListView) root.findViewById(R.id.contenido_sala_list);

        String idZona = getActivity().getIntent().getExtras().getString("id");
        sala = Museo.getInstance().getSala(idZona);

        mSalasAdapter = new ContenidoSalaAdapter(getActivity(), sala.getElementos());


        mSalasList.setAdapter(mSalasAdapter);

        mSalasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Elemento elemento = mSalasAdapter.getItem(position);
                Intent shareIntent = new Intent(getActivity(), verElemento.class);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra("id", elemento.getIdElemento());
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });


        setHasOptionsMenu(true);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
