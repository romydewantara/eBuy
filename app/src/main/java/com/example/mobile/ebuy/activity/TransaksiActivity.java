package com.example.mobile.ebuy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.adapter.TransaksiAdapter;
import com.example.mobile.ebuy.sql.DatabaseItemHelper;

public class TransaksiActivity extends AppCompatActivity {

    DatabaseItemHelper databaseItemHelper;
    ListView listview_transaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        databaseItemHelper = new DatabaseItemHelper(this);
        listview_transaksi = findViewById(R.id.listview_user);
        listview_transaksi.setAdapter(new TransaksiAdapter(this, databaseItemHelper.getTransaksiItem()));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
