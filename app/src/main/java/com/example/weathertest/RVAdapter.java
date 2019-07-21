package com.example.weathertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weathertest.ResponseDAO.WeatherCity;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.WeatherViewHolder> {

    List<WeatherCity> weatherCities;
    private LayoutInflater inflater;
    private Context context;
    private final OnItemClickListener clickListener;

    public RVAdapter(Context context, List<WeatherCity> weatherCities, OnItemClickListener clickListener) {
        this.weatherCities = weatherCities;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;


    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(inflater.inflate(R.layout.weather_city_layout, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.bind(weatherCities.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherCities.size();
    }

    public interface OnItemClickListener {
        void onItemClick(WeatherCity weatherCity);
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconWeather;
        private TextView nameCity;
        private TextView tempCity;

        public WeatherViewHolder(@NonNull View itemView, @NonNull OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(weatherCities.get(position));
                }
            });
            iconWeather = itemView.findViewById(R.id.iconWeather);
            nameCity = itemView.findViewById(R.id.nameCity);
            tempCity = itemView.findViewById(R.id.tempCity);


        }


        public void bind(WeatherCity weatherCity) {
            Glide
                    .with(context)
                    .load(weatherCity.getImageCity())
                    .into(iconWeather);
            nameCity.setText(weatherCity.getNameCity());
            tempCity.setText(weatherCity.getTemp() + "°C");
        }
    }
}
