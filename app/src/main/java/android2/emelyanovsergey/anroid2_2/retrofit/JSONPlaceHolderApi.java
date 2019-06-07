package android2.emelyanovsergey.anroid2_2.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("data/2.5/weather")
    public Call<OpenWeather> getWeatherByCityName(@Query("q") String cityName, @Query("appid") String apiKey);
}
