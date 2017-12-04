package com.example.dgp.conejonegro;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Alvaro on 04/12/2017.
 */

public class ContenidoSalaFragment extends Fragment {
    ListView mSalasList;
    ContenidoSalaAdapter mSalasAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contenido_sala, container, false);

        mSalasList = (ListView) root.findViewById(R.id.contenido_sala_list);

        String idZona = getActivity().getIntent().getExtras().getString("id");
        Sala sala = Museo.getInstance().getSala(idZona);

        mSalasAdapter = new ContenidoSalaAdapter(getActivity(), sala.getElementos());


        mSalasList.setAdapter(mSalasAdapter);
/*
        mSalasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Sala sala = mSalasAdapter.getItem(position);
                Intent shareIntent = new Intent(getActivity(), verContenidoSala.class);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra("id", sala.getIdZona());
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });*/



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
