package michael.pio.converteverything;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    int SelectLine = 1;
    String spFirstCountry,spSecondCountry;
    private RequestQueue mQueue;
    Button BTNconvert;
    TextView TVFirst,TVSecond;
    EditText ETFirst ,ETSecond;
    double convertedAmount;
    String[] countryName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BTNconvert = (Button) findViewById(R.id.btnConvert);
        TVFirst = (TextView) findViewById(R.id.TVFirstCountry);
        TVSecond = (TextView) findViewById(R.id.TVSecondCountry);
        ETFirst = (EditText) findViewById(R.id.ETFirstNumber) ;
        ETSecond = (EditText) findViewById(R.id.ETSecondNumber);
        SpinnerSetup();
        countryName = getResources().getStringArray(R.array.country_name);

        BTNconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(SelectLine == 1 && !(ETFirst.getText().toString().isEmpty())) {
                        convertCurrency(spFirstCountry, spSecondCountry, ETFirst.getText().toString());
                    }else if(SelectLine == 2 && !(ETSecond.getText().toString().isEmpty())){
                        convertCurrency(spSecondCountry, spFirstCountry, ETSecond.getText().toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        TextChanged();
    }

    private void TextChanged() {
        final ColorStateList[] textColors = new ColorStateList[1];
        ETFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectLine = 1;
            }
        });
        ETSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectLine = 2;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void convertCurrency(String baseCurrency, String foreignCurrency , String amount ){
        String API = "https://api.frankfurter.app/latest?amount="+amount+"&from="+baseCurrency+"&to="+foreignCurrency;

        if(baseCurrency == foreignCurrency ){
            Toast.makeText(MainActivity.this,"[Error] Base and Foreign are same!",Toast.LENGTH_SHORT).show();
            int dum = SelectLine;
            ETSecond.setText("" + amount);
            ETFirst.setText("" + amount);
            SelectLine = dum;
        }else{
            mQueue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API, null, new Response.Listener<JSONObject>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        convertedAmount = response.getJSONObject("rates").getDouble(foreignCurrency);
                        if(SelectLine == 1) {
                            ETSecond.setText("" + convertedAmount);
                            ETFirst.setText("" + amount);

                        }else if(SelectLine == 2){
                            ETFirst.setText("" + convertedAmount);
                            ETSecond.setText("" + amount);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this,"Error Catch is called",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }

    }

    private void SpinnerSetup(){
        Spinner spinnerFirst = (Spinner) findViewById(R.id.spinnerFirst);
        Spinner spinnerSecond = (Spinner) findViewById(R.id.spinnerSecond);

        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(MainActivity.this, R.array.country_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);


        spinnerFirst.setAdapter(adapter);
        spinnerSecond.setAdapter(adapter);

        spinnerFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spFirstCountry = adapterView.getItemAtPosition(i).toString();
                try {
                    TVFirst.setText(countryName[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spSecondCountry = adapterView.getItemAtPosition(i).toString();
                try {
                    TVSecond.setText(countryName[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}