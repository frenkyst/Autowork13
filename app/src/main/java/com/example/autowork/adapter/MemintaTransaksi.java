package com.example.autowork.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.autowork.GlobalVariabel;
import com.example.autowork.R;
import com.example.autowork.kasir.DetailBayarFragment;
import com.example.autowork.kasir.HomeKasirFragment;
import com.example.autowork.kasir.KasirActivity;
import com.example.autowork.model.Meminta;
import com.example.autowork.model.Transaksi;

import java.util.List;

public class MemintaTransaksi extends RecyclerView.Adapter<MemintaTransaksi.MyViewHolder> {

    private Context context;
    private List<Transaksi> moviesList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layouttransaksi;
        public TextView tv_nama, tv_total, tv_taptransaksi;

        public MyViewHolder(View view) {
            super(view);
            rl_layouttransaksi = view.findViewById(R.id.rl_layout);
            tv_nama = view.findViewById(R.id.tv_namakaryawan);
            tv_total = view.findViewById(R.id.tv_totalkaryawan);

            tv_taptransaksi = view.findViewById(R.id.tv_taptransaksi);
        }
    }

    public MemintaTransaksi(List<Transaksi> moviesList, Activity activity) {
        this.moviesList = moviesList;
        this.mActivity = activity;
    }

    @Override
    public MemintaTransaksi.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meminta_transaksi, parent, false);


        return new MemintaTransaksi.MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MemintaTransaksi.MyViewHolder holder, final int position) {
        final Transaksi movie = moviesList.get(position);

        holder.tv_nama.setText(movie.getNamakaryawan());
        holder.tv_total.setText(movie.getTotal());
//        holder.tv_totalBayar.setText(movie.getTotal());
        holder.tv_taptransaksi.setText(movie.getUid());


        holder.tv_taptransaksi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //creating a popup menu
                PopupMenu popup = new PopupMenu(mActivity, holder.tv_taptransaksi);
                //inflating menu from xml resource
                popup.inflate(R.menu.kasir_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                GlobalVariabel.uid = movie.getUid();
//                                f_manager.beginTransaction()
//                                        .replace(R.id.frameawal, new DetailBayarFragment())
//                                        .commit();

//                                HomeKasirFragment fragment = new HomeKasirFragment();
//                                ((KasirActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.framekasir, fragment).addToBackStack(null).commit();

//                                FragmentManager manager=((AppCompatActivity)context).getSupportFragmentManager();
//                                FragmentTransaction Ft=manager.beginTransaction();
//                                Ft.replace(R.id.framekasir,new DetailBayarFragment());
//
//                                Ft.commit();

                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                Fragment myFragment = new DetailBayarFragment();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.framekasir, myFragment).addToBackStack(null).commit();


                                break;
                            case R.id.menu2:
                                //handle menu2 click
                                break;
                            case R.id.menu3:
                                //handle menu3 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();




            }
        });



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public FragmentManager f_manager;
//    //in your constructor add FragmentManager
//    public MemintaTransaksi(Context context, FragmentManager f_manager)
//    {
//        this.context = context;
//        this.f_manager = f_manager;
//    }

}