package com.example.mobile.ebuy.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.config.Config;
import com.example.mobile.ebuy.model.ItemsModel;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private Button buttonPay;

    private List<ItemsModel> listData;
    ImageView img_detail;
    TextView product_detail, price_detail;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        img_detail = findViewById(R.id.img_detail);
        product_detail = findViewById(R.id.product_detail);
        price_detail = findViewById(R.id.price_detail);
        index = getIntent().getIntExtra("index", 0);

        //m
        listData = new ItemsModel().getData();
        ItemsModel data = listData.get(index);
        img_detail.setImageResource(data.getImage_items());
        product_detail.setText(data.getProduct_name());
        price_detail.setText(String.valueOf(data.getProduct_price()));


        buttonPay = findViewById(R.id.btn_beli);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CheckoutActivity.class);
                i.putExtra("index", index);
                startActivity(i);
                finish();
            }
        });

    }

}
