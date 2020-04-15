package com.example.ecocyam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.ecocyam.entities.Product;
import com.example.ecocyam.interfaces.VolleyCallBack;
import com.example.ecocyam.utility.ConnectionTo;
import com.example.ecocyam.utility.CustomRequest;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public final class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    /* default */ ZXingScannerView ScannerView;
    private static final int CAMERA_PERMISSION_CODE = 100;
    /* default */private String URL = "https://ecocyam-web.cfapps.io/api/items/search";
    /* default */static final Logger log = Logger.getLogger(LoginActivity.class.getName());
    /* default */private Product scannedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {

        //MainActivity.textViewScan.setText(result.getText());
        //onBackPressed();
        searchItemByApi(result.getText(), new VolleyCallBack() {
            @Override
            public void onSuccess() {
                ConnectionTo.switchActivityWithObejctExtra(ScannerActivity.this.getApplicationContext()
                        ,ProductFeaturesActivity.class,getScannedProduct());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }


    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void searchItemByApi(String query, final VolleyCallBack callBack) {
        log.info("code !!!!!!!!!!! "+query);
        JSONObject requestJsonObject = new JSONObject();
        try {

            requestJsonObject.put("barcode", query);
            requestJsonObject.put("keyword", "");

        } catch (JSONException e) {
            log.fine(e.getMessage());
        }

        CustomRequest jsonObjReq = new CustomRequest(Request.Method.POST, URL, requestJsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                log.fine(response.toString() + "connection success");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonobject = response.getJSONObject(i);
                        int itemId = Integer.parseInt(jsonobject.getString("itemId"));
                        String title = jsonobject.getString("name");
                        //  double rating = Double.parseDouble(jsonobject.getString("overallScore"));
                        Product product = new Product(itemId,title,"aRenseigner",0.0d,0);
                        log.info(product.getTitle());
                        ScannerActivity.this.setScannedProduct(product);
                    } catch (JSONException e) {
                        log.info(e.getMessage());
                    }
                }
                callBack.onSuccess();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        log.fine("Error: " + error.getMessage());
                        ConnectionTo.switchActivity(ScannerActivity.this.getApplicationContext(),AddProductFormActivity.class);
                        Toast.makeText(ScannerActivity.this.getApplicationContext(), "no product found, add this one", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    public Product getScannedProduct(){
        return this.scannedProduct;
    }

    public void setScannedProduct(Product product){
        this.scannedProduct = product;
    }
}
