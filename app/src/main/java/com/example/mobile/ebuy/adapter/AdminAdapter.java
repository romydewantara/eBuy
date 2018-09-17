package com.example.mobile.ebuy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.modal.Item;
import com.example.mobile.ebuy.modal.Transaksi;
import com.example.mobile.ebuy.model.ItemsModel;
import com.example.mobile.ebuy.sql.DatabaseItemHelper;

import java.lang.reflect.Field;
import java.util.List;


public class AdminAdapter extends BaseAdapter {

    DatabaseItemHelper databaseItemHelper;

    private Context context;
    private int[] image;
    private String product;
    private String price;
    private boolean isPaid = false;
    private boolean isSend = false;
    private LayoutInflater layoutInflater;
    private List<Item> itemList;

    private Item item;
    private List<ItemsModel> listData;

    public AdminAdapter(Context context, int[] image, String product, String price) {
        this.context = context;
        this.image = image;
        this.product = product;
        this.price = price;
        databaseItemHelper = new DatabaseItemHelper(context);
    }

    public AdminAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        layoutInflater = LayoutInflater.from(context);
        databaseItemHelper = new DatabaseItemHelper(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).getId();
    }

    public class Holder{
        TableRow tableRowConfirm;
        TableRow tableRowReject;
        TableRow tableRowKirim;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        TextView textView1;
        TextView textView2;

    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Item item = itemList.get(position);
        final Holder holder = new Holder();
        final View view;
        view = layoutInflater.inflate(R.layout.card_view_admin, null);

        holder.imageView1 = view.findViewById(R.id.img_product_adm);
        holder.imageView2 = view.findViewById(R.id.img_confirm);
        holder.imageView3 = view.findViewById(R.id.img_reject);
        holder.imageView4 = view.findViewById(R.id.img_send);

        holder.textView1 = view.findViewById(R.id.tv_product_adm);
        holder.textView2 = view.findViewById(R.id.tv_price_adm);
        holder.tableRowConfirm = view.findViewById(R.id.tb_confirm);
        holder.tableRowReject = view.findViewById(R.id.tb_reject);
        holder.tableRowKirim = view.findViewById(R.id.tb_kirim);

        holder.imageView1.setImageResource(item.getImageId());
        holder.textView1.setText(item.getProduct());
        holder.textView2.setText(item.getPrice());

        //button confirm
        holder.tableRowConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaid == false){
                    holder.imageView2.setImageResource(R.drawable.accept_grey);
                    holder.imageView3.setImageResource(R.drawable.error_grey);
                    holder.imageView4.setImageResource(R.drawable.send_green);
                    isPaid = true;
                    updateDataFromTable(item);
                    Toast.makeText(context, "Pembayaran telah dikonfirmasi", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        //button reject
        holder.tableRowReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaid == false){
                    Toast.makeText(context, "Pembayaran telah selesai secara otomatis, \nsilahkan klik tombol konfirmasi dan \nsegera lakukan pengiriman", Toast.LENGTH_SHORT).show();
                } else {

                }

            }
        });

        //button reject
        holder.tableRowKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaid){
                    Toast.makeText(context, "Anda belum mengkonfirmasi pembayaran", Toast.LENGTH_SHORT).show();
                } else {
                    if (isSend){

                    } else {
                        holder.imageView4.setImageResource(R.drawable.send_grey);
                        updateDataFromTable(item);
                        isSend = true;
                    }
                }
            }
        });


        return view;
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void updateDataFromTable(Item item) {
        item.setState("drawable/accept_green");
        Integer updatedata = databaseItemHelper.updateStatus(item);
        if (updatedata > 0){
            Toast.makeText(context, "Pengiriman Barang...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Gagal mengirim barang", Toast.LENGTH_SHORT).show();
        }
    }

}
