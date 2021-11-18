package com.example.locationfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Geocoder geocoder;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText latValue = (EditText)findViewById(R.id.et_lat);
        final EditText longValue = (EditText)findViewById(R.id.et_long);
        Button findBtn = (Button)findViewById(R.id.bt_find);
        Button updateBtn = (Button)findViewById(R.id.btn_update);
        final ListView resultList = (ListView)findViewById(R.id.list_result);
        geocoder = new Geocoder(this);
        dbHelper = new DatabaseHelper(this);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latStr = latValue.getText().toString();
                String longStr = longValue.getText().toString();

                boolean isParsable = true;
                Double latitude = null, longitude = null;

                try {
                    latitude = Double.parseDouble(latStr);
                }catch (NumberFormatException e){
                    isParsable = false;
                    Toast.makeText(MainActivity.this, "Enter valid Latitude value", Toast.LENGTH_LONG).show();

                }

                try {
                    longitude = Double.parseDouble(longStr);
                } catch (NumberFormatException e){
                    isParsable = false;
                            Toast.makeText(MainActivity.this, "Enter valid Longitude value", Toast.LENGTH_LONG).show();
                }

                if(isParsable){
                    Toast.makeText(MainActivity.this, "find " + latitude + " : " + longitude, Toast.LENGTH_LONG).show();

                    List<Address> geoResult = findGeocoder(latitude, longitude);
                    if(geoResult != null){
                        List<String> geoStringResult = new ArrayList<String>();
                        for(int i=0; i<geoResult.size(); i++){
                            Address thisAddress = geoResult.get(i);
                            String stringThisAddress = "";
                            for(int j=0; j<thisAddress.getMaxAddressLineIndex(); j++){
                                stringThisAddress += thisAddress.getAddressLine(j) + "\n";
                            }
                            stringThisAddress += "CountryName: " + thisAddress.getCountryName() + "\n" + "CountryCode: " + thisAddress.getCountryCode() + "\n" + "AdminArea: " + thisAddress.getAdminArea() + "\n" + "FeatureName: " + thisAddress.getFeatureName();
                            geoStringResult.add(stringThisAddress);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, geoStringResult);
                        resultList.setAdapter(adapter);
                     }

                }
            }

        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String
            }
        });
    }

    public void AddData(String address, String latitude, String longitude){
        boolean insertData = dbHelper.addData();
        if (insertData) {
            Toast.makeText(MainActivity.this, "Data successfully inserted", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private List<Address> findGeocoder(Double latitude, Double longitude){
        final int maxResults = 1;
        List<Address> addresses = null;
        try{
            addresses = geocoder.getFromLocation(latitude, longitude, maxResults);
        }catch (IOException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return addresses;
    }
}