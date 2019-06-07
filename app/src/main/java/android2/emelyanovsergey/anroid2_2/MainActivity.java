package android2.emelyanovsergey.anroid2_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import android2.emelyanovsergey.anroid2_2.retrofit.OpenWeather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button getWeatherBtn;
    private EditText cityNameEditText;
    private RecyclerView cityRecyclerView;
    private static String API_KEY = "743bc0364f4eb375eacf9c9b2e3977fd";

    private CityAdapter cityAdapter;

    ArrayList<City> citys = new ArrayList<City>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initElements();
        registerBrodcastReceivers();
    }

    private void registerBrodcastReceivers() {
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showToast("получен Brodcast для обновления");
                String action = intent.getStringExtra("action");
                if (action.equals("updateAllCity")) {
                    for (int i=0;i<citys.size();i++) {
                        LoadCityWeather(citys.get(i));
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("ru.android2.evserg.action.updateWeather");
        registerReceiver(br,intentFilter);

    }


    private void initElements() {
        cityNameEditText = (EditText) findViewById(R.id.cityNameEditText);

        getWeatherBtn = (Button) findViewById(R.id.getWeatherBtn);
        getWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadCityWeather(MyDataBaseHelper.getInstance(MainActivity.this).getCity(cityNameEditText.getText().toString()));
            }
        });

        cityRecyclerView = (RecyclerView) findViewById(R.id.cityRecyclerView);
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter = new CityAdapter();
        cityAdapter.setOnMenuItemClickListener(new CityAdapter.OnMenuItemClickListener() {
            @Override
            public void onItemWeatherClick(City city) {
                loadOpenWeather(city);
            }

            @Override
            public void onItemDeleteClick(City city) {
                MyDataBaseHelper.getInstance(MainActivity.this).delCity(city);
                updateCitys();
            }
        });

        cityRecyclerView.setAdapter(cityAdapter);
        updateCitys();
    }

    private void loadOpenWeather(final City city) {

        NetworkService.getInstance()
                .getJSONApi()
                .getWeatherByCityName(city.getName(), API_KEY)
                .enqueue(new Callback<OpenWeather>() {
                    @Override
                    public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {
                        OpenWeather openWeather = response.body();

                        if (openWeather != null) {
                            String nowWeather;
                            Double nowTemp = openWeather.getMain().getTemp() - 273.15;
                            nowWeather = String.format("%3.1f C", nowTemp) + "";
                            Log.d("TAG", nowWeather);

                            City modCity = MyDataBaseHelper.getInstance(MainActivity.this).getCity(city.getName());
                            modCity.setWeather(nowWeather);
                            MyDataBaseHelper.getInstance(MainActivity.this).updCity(modCity);
                            showToast("погода в " + city.getName() + " загружена");
                        } else {
                            showToast("город " + city.getName() + "не найден на openweather");
                            MyDataBaseHelper.getInstance(MainActivity.this).delCity(city);
                        }
                        
                        updateCitys();
                    }

                    @Override
                    public void onFailure(Call<OpenWeather> call, Throwable t) {
                        city.setWeather("??.?");
                        showToast("ошибка при загрузке с openweather");
                        t.printStackTrace();
                        updateCitys();
                    }
                });
    }

    private void LoadCityWeather(City city) {
        loadOpenWeather(
                MyDataBaseHelper.getInstance(MainActivity.this).updCity(city)
        );
        updateCitys();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateCitys() {
        citys = MyDataBaseHelper.getInstance(MainActivity.this).query();
        cityAdapter.setCitys(citys);
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }



}
