package com.example.mobile.ebuy.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.adapter.AdminAdapter;
import com.example.mobile.ebuy.sql.DatabaseItemHelper;

public class AdminActivity extends AppCompatActivity {

    DatabaseItemHelper databaseItemHelper;
    ListView listview_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        databaseItemHelper = new DatabaseItemHelper(this);
        listview_admin = findViewById(R.id.listview_admin);
        listview_admin.setAdapter(new AdminAdapter(this, databaseItemHelper.getAllItem()));

    }


    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_exit, null);
        Button btn_yes = view.findViewById(R.id.btn_yes);
        Button btn_no = view.findViewById(R.id.btn_no);
        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finishAffinity();
                dialog.dismiss();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
