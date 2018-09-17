package com.example.mobile.ebuy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.modal.Transaksi;
import com.example.mobile.ebuy.sql.DatabaseItemHelper;

import java.lang.reflect.Field;
import java.util.List;

public class TransaksiAdapter extends BaseAdapter {

    DatabaseItemHelper databaseItemHelper;

    private Context context;
    private int[] image;
    private String product;
    private String price;
    private String state;
    private boolean isReceive = false;
    private LayoutInflater layoutInflater;
    private List<Transaksi> transaksiList;

    public TransaksiAdapter(Context context, int[] image, String product, String price) {
        this.context = context;
        this.image = image;
        this.product = product;
        this.price = price;
    }

    public TransaksiAdapter(Context context, List<Transaksi> transaksiList) {
        this.context = context;
        this.transaksiList = transaksiList;
        layoutInflater = LayoutInflater.from(context);
        databaseItemHelper = new DatabaseItemHelper(context);
    }

    @Override
    public int getCount() {
        return transaksiList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TableRow tableRowConfirm;
        ImageView imageView1;

        //confirmation
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;

        TextView textView1;
        TextView textView2;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Transaksi transaksi = transaksiList.get(position);
        final Holder holder = new Holder();
        View view;
        view = layoutInflater.inflate(R.layout.card_view_user, null);

        holder.imageView1 = view.findViewById(R.id.img_product_usr);
        holder.imageView2 = view.findViewById(R.id.img_payment);
        holder.imageView3 = view.findViewById(R.id.img_sending);
        holder.imageView4 = view.findViewById(R.id.img_sent);

        holder.textView1 = view.findViewById(R.id.tv_product_usr);
        holder.textView2 = view.findViewById(R.id.tv_price_usr);
        holder.tableRowConfirm = view.findViewById(R.id.tb_sampai);


        holder.imageView1.setImageResource(transaksi.getImageId());
        holder.textView1.setText(transaksi.getProduct());
        holder.textView2.setText(transaksi.getPrice());
//        Toast.makeText(context, "" + transaksi.getState(), Toast.LENGTH_SHORT).show();
        holder.imageView2.setImageResource(context.getResources().getIdentifier(transaksi.getState(), null, context.getPackageName()));
        holder.imageView3.setImageResource(context.getResources().getIdentifier(transaksi.getState(), null, context.getPackageName()));

        holder.tableRowConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isReceive) {
                    final AlertDialog.Builder builderInner = new AlertDialog.Builder(context);
                    builderInner.setTitle("Barang diterima");
                    builderInner.setMessage("Anda yakin menerima Barang?");
                    builderInner.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isReceive = true;
                            holder.imageView4.setImageResource(R.drawable.confirm_grey);
                            Toast.makeText(context, "Barang diterima", Toast.LENGTH_SHORT).show();
                            Integer deletedRows = databaseItemHelper.deleteData(String.valueOf(transaksi.getId()));
                            if(deletedRows > 0){
                                Toast.makeText(context, "Pembelian telah selesai", Toast.LENGTH_SHORT).show();
                                transaksiList.remove(position);
                                notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "Konfirmasi dibatalkan", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builderInner.show();
                } else {

                }
            }
        });
        return view;
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
