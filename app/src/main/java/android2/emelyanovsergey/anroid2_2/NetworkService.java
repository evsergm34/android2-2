package android2.emelyanovsergey.anroid2_2;

import android2.emelyanovsergey.anroid2_2.retrofit.JSONPlaceHolderApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkService {

    private static NetworkService mInstance;
    private static String API_KEY = "743bc0364f4eb375eacf9c9b2e3977fd";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";//Moscow&appid=743bc0364f4eb375eacf9c9b2e3977fd" +

    private Retrofit mRetrofit;

    public NetworkService() {
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

}

