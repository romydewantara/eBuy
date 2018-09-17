package com.example.mobile.ebuy.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.config.Config;
import com.example.mobile.ebuy.modal.Item;
import com.example.mobile.ebuy.model.ItemsModel;
import com.example.mobile.ebuy.sql.DatabaseItemHelper;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseItemHelper databaseItemHelper;
    private ScrollView nestedScrollView;
    private Item item;
    private List<ItemsModel> listData;
    ImageView img_total, img_plus, img_minus;
    TextView tv_qty, tv_result, total_master, total_ongkir, total_harga, harga_desc, nama_desc;
    Button btn_bayar;

    int counter = 0;
    int harga = 0;
    int total = 0;

    int img_id;
    String prod_name;

    //Payment Amount
    private String paymentAmount;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;


    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        nestedScrollView = findViewById(R.id.scrollView);
        img_total = findViewById(R.id.img_total);
        img_plus = findViewById(R.id.img_plus);
        img_minus = findViewById(R.id.img_minus);
        harga_desc = findViewById(R.id.harga_desc);
        total_ongkir = findViewById(R.id.total_ongkir);
        total_master = findViewById(R.id.total_master);
        nama_desc = findViewById(R.id.nama_desc);
        total_harga = findViewById(R.id.total_harga);

        tv_qty = findViewById(R.id.tv_qty);
        tv_result = findViewById(R.id.tv_result);
        btn_bayar = findViewById(R.id.btn_bayar);
        btn_bayar.setOnClickListener(this);
        img_plus.setOnClickListener(this);
        img_minus.setOnClickListener(this);

        //Inside onCreate() method we need to start PayPalService.
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        int indexModel = getIntent().getIntExtra("index", 0);
        listData = new ItemsModel().getData();
        ItemsModel data = listData.get(indexModel);
        img_id = data.getImage_items();
        prod_name = data.getProduct_name();
        img_total.setImageResource(img_id);
        nama_desc.setText(prod_name);
        harga_desc.setText(String.valueOf(data.getProduct_price()));
        tv_result.setText(String.valueOf(data.getProduct_price()));
        total_harga.setText(String.valueOf(data.getProduct_price()));
        total_master.setText(String.valueOf(data.getProduct_price()));

    }

    //Now complete the method getPayment() with the following code.
    public void getPayment() {
        //Getting the amount from TextView
        paymentAmount = total_master.getText().toString();

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Simplified Coding Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    private void displayQty(String quantity) {
        tv_qty.setText(quantity);
    }

    private void displayHarga(String harga, String total) {
        tv_result.setText(harga);
        total_harga.setText(harga);
        total_master.setText(total);
    }

    public void addQty() {
        counter++;
        displayQty(Integer.toString(counter));
        harga = (Integer.parseInt(tv_qty.getText().toString()) * Integer.parseInt(harga_desc.getText().toString()));
        total = (harga + 18000);
        displayHarga(Integer.toString(harga), Integer.toString(total));
    }

    public void subQty() {
        if (Integer.parseInt(tv_qty.getText().toString()) > 1) {
            counter--;
            displayQty(Integer.toString(counter));
            harga = (Integer.parseInt(harga_desc.getText().toString()) * Integer.parseInt(tv_qty.getText().toString()));
            total = (harga  + 18000);
            displayHarga(Integer.toString(harga), Integer.toString(total));
        }
    }

    //We should destroy the service when app closes.
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    //The above method will invoke the onActivityResult() method after completion.
    // So override onActivityResult() and write the following code.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bayar:
                dialogPayment();
                break;
            case R.id.img_plus:
                addQty();
                break;
            case R.id.img_minus:
                subQty();
                break;
        }
    }

    public void dialogPayment() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(CheckoutActivity.this);
        builderSingle.setIcon(R.drawable.ebuylogo);
        builderSingle.setTitle("Select Payment Method :");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CheckoutActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Mandiri");
        arrayAdapter.add("BNI");
        arrayAdapter.add("CIMB Niaga");
        arrayAdapter.add("PayPal");

        builderSingle.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(CheckoutActivity.this);
                if (strName.equals("PayPal")) {
                    getPayment();
                } else {

                    //post to database
                    postDataToSQLite();

                    //Show Dialog Payment
                    builderInner.setTitle("Waiting payment...");
                    builderInner.setMessage("You must pay as per bill before \n1 day. We will wait for your payment \nconfirmation. \n\nBank Account Under the Name \nMandiri \n008 12400071982\nPT. EBUY ONLINE, Tbk.");
                    builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });
                    builderInner.show();
                }
            }
        });
        builderSingle.show();
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {

        item = new Item();
        item.setImageId(img_id);
        item.setProduct(prod_name);
        item.setPrice(total_master.getText().toString().trim());
        item.setState("drawable/accept_grey");
        item.setState2("drawable/accept_grey");
        databaseItemHelper = new DatabaseItemHelper(CheckoutActivity.this);

            databaseItemHelper.addItem(item);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.ordered_message), Snackbar.LENGTH_LONG).show();

    }

}
