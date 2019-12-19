package com.example.autowork.kasir;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autowork.GlobalVariabel;
import com.example.autowork.R;
import com.example.autowork.adapter.MemintaTransaksikasir;
import com.example.autowork.model.Meminta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeKasirFragment extends Fragment {


    public HomeKasirFragment() {
        // Required empty public constructor
    }

    private DatabaseReference database;

    private ArrayList<Meminta> daftarReq;
    private MemintaTransaksikasir memintatransaksikasir;

    private RecyclerView rc_list_request;
    private ProgressDialog loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_kasir, container, false);


        database = FirebaseDatabase.getInstance().getReference();
        TextView tv_totalBayar;
        tv_totalBayar = v.findViewById(R.id.tv_totalBayar);

        rc_list_request = v.findViewById(R.id.rc_list_request);
        //fab_add = findViewById(R.id.fab_add);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rc_list_request.setLayoutManager(mLayoutManager);
        rc_list_request.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(getActivity(),
                null,
                "Please wait...",
                true,
                false);

        database.child(GlobalVariabel.Toko).child(GlobalVariabel.Transaksi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                daftarReq = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object Wisata
                     * Dan juga menyimpan primary key pada object Wisata
                     * untuk keperluan Edit dan Delete data
                     */
                    Meminta requests = noteDataSnapshot.getValue(Meminta.class);
                    requests.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object Wisata yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    daftarReq.add(requests);

                    //=========================================================================================================
                    // MENAMPILKAN TOTAL HARGA KESELURUHAN
                    String totalbayar = dataSnapshot.child("zzzzzzzzz").child("total").getValue().toString();

                    tv_totalBayar.setText("Rp. "+totalbayar);
                    //==========================================================================================================
                }

                /**
                 * Inisialisasi adapter dan data hotel dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                memintatransaksikasir = new MemintaTransaksikasir(daftarReq, getActivity());
                rc_list_request.setAdapter(memintatransaksikasir);
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });


        v.findViewById(R.id.btn_bayar).setOnClickListener((view) -> {



        });


//        database = FirebaseDatabase.getInstance().getReference().child(GlobalVariabel.Toko).child(GlobalVariabel.Transaksi);
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String totalbayar = dataSnapshot.child("zzzzzzzzz").getValue().toString();
//                tv_totalBayar.setText(totalbayar);
//
//                Toast.makeText(getActivity(), "tes", Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        return v;
    }


}