package android2.emelyanovsergey.anroid2_2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private OnMenuItemClickListener itemMenuClickListener;  // Слушатель, будет устанавливаться извне
    private ArrayList<City> citys = new ArrayList<City>();
    private int tekPosition;

    public void setCitys(ArrayList<City> elems) {
        citys.clear();
        citys.addAll(elems);
        notifyDataSetChanged();
    }

    // установка слушателя
    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.itemMenuClickListener = onMenuItemClickListener;
    }

    // интерфейс для обработки меню
    public interface OnMenuItemClickListener {
        void onItemWeatherClick(City city);
        void onItemDeleteClick(City city);
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView cityWeather;
        TextView cityUpdateTime;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.cityName);
            cityWeather = (TextView) itemView.findViewById(R.id.cityWeather);
            cityUpdateTime = (TextView) itemView.findViewById(R.id.cityUpdateTime);

            cityName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemMenuClickListener != null) {
                        showPopupMenu(cityName, getAdapterPosition());
                    }

                }
            });
        }
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_item, viewGroup, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder cityViewHolder, int i) {
        cityViewHolder.cityName.setText(citys.get(i).getName());
        cityViewHolder.cityWeather.setText(" " + citys.get(i).getWeather() + " ");
        cityViewHolder.cityUpdateTime.setText("[" + citys.get(i).getUpToDate() + "]");
    }

    @Override
    public int getItemCount() {
        return citys.size();
    }

    private void showPopupMenu(View view, final int position) {
        // Покажем меню на элементе
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.content_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            // обработка выбора пункта меню
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // делегируем обработку слушателю
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        itemMenuClickListener.onItemDeleteClick(citys.get(position));
                        return true;
                    case R.id.menu_weather:
                        itemMenuClickListener.onItemWeatherClick(citys.get(position));
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }
}
