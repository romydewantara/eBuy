package com.example.mobile.ebuy.model;

import com.example.mobile.ebuy.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsModel {

    private int image_items;
    private String product_name;
    private Integer product_price;
    private String category;

    public ItemsModel(int image_items, String product_name, Integer product_price, String category){
        this.image_items = image_items;
        this.product_name = product_name;
        this.product_price = product_price;
        this.category = category;
    }

    public ItemsModel(){

    }

    public List<ItemsModel> getData(){
        List<ItemsModel> data = new ArrayList<>();
        data.add(new ItemsModel(R.drawable.squaretif, "Sweater Flanel",210000, "Baju"));
        data.add(new ItemsModel(R.drawable.tshirt, "Kemeja",110000,"Baju"));
        data.add(new ItemsModel(R.drawable.stshirt, "Jacket",150000,"Baju"));
        data.add(new ItemsModel(R.drawable.ajax, "Casual Shirt",90000,"Baju"));
        data.add(new ItemsModel(R.drawable.levisa, "Levi's", 325000,"Celana"));
        data.add(new ItemsModel(R.drawable.levisb, "Cino Casual",325000,"Celana"));
        data.add(new ItemsModel(R.drawable.converse, "Converse", 299000,"Sepatu"));
        data.add(new ItemsModel(R.drawable.airwalk, "Airwalk",125000,"Sepatu"));
        return data;
    }

    public int getImage_items() {
        return image_items;
    }

    public void setImage_items(int image_items) {
        this.image_items = image_items;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Integer product_price) {
        this.product_price = product_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
