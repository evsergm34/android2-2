package android2.emelyanovsergey.anroid2_2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private ArrayList<City> citys = new ArrayList<City>();

    public void setCitys(ArrayList<City> elems) {
        citys.clear();
        citys.addAll(elems);
        notifyDataSetChanged();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder{
        TextView cityName;
        TextView cityWeather;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName=(TextView) itemView.findViewById(R.id.cityName);
            cityWeather=(TextView) itemView.findViewById(R.id.cityWeather);
        }
    }


    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_item,viewGroup,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder cityViewHolder, int i) {
        cityViewHolder.cityName.setText(citys.get(i).getName());
        cityViewHolder.cityWeather.setText(""+citys.get(i).getID());
    }

    @Override
    public int getItemCount() {
        return citys.size();
    }
}
