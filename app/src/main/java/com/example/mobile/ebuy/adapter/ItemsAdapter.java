package com.example.mobile.ebuy.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.activity.Main2Activity;
import com.example.mobile.ebuy.model.ItemsModel;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    private List<ItemsModel> listItems;
    private Context context;
    private boolean isLiked = false;

    public ItemsAdapter(List<ItemsModel> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_store, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ItemsModel items  = listItems.get(position);

        holder.tvItemsName.setText(items.getProduct_name());
        holder.tvItemsPrice.setText("Rp. " + items.getProduct_price());

        Glide.with(context)
                .load(items.getImage_items())
                .into(holder.ivItems);

        holder.tvItemsAvailability.setText("Category : " + items.getCategory());

        //on click linear layout to check the position
        holder.llItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Main2Activity.class).putExtra("index", holder.getAdapterPosition()));
            }
        });

        holder.cb_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLiked) {
                    Toast.makeText(context, "Sukses menambah Wishlist", Toast.LENGTH_SHORT).show();
                    isLiked = true;
                } else {
                    isLiked = false;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    // inner class of adapter
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivItems;
        public TextView tvItemsName;
        public TextView tvItemsPrice;
        public TextView tvItemsAvailability;
        public CheckBox cb_like;
        public LinearLayout llItems;

        public ViewHolder(View itemView) {
            super(itemView);

            ivItems = itemView.findViewById(R.id.img_item);
            tvItemsName = itemView.findViewById(R.id.tv_product_name);
            tvItemsPrice = itemView.findViewById(R.id.tv__product_price);
            tvItemsAvailability = itemView.findViewById(R.id.tv_available);
            cb_like = itemView.findViewById(R.id.likeIcon);
            llItems = itemView.findViewById(R.id.ll_items);
        }
    }
}