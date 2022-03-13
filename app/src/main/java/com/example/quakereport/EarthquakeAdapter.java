package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public EarthquakeAdapter(@NonNull Context context, List<Earthquake> list) {
        super(context, 0,list);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listView = convertView;
        if (listView==null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Earthquake details = getItem(position);
        TextView magtext = listView.findViewById(R.id.magnitude);

        int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magtext.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(details.getMagnitudeToShow());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        magtext.setText(formatMagnitude(details.getMagnitudeToShow()));

        String orgPlace = details.getplaceNameToShow();
        TextView placetext = listView.findViewById(R.id.location_offset);
        TextView offsetText = listView.findViewById(R.id.primary_location);

        if (orgPlace.contains("of")){
         String[] placeNameArray = orgPlace.split("(?<=of)");
        String offset = placeNameArray[0];
        String primary = placeNameArray[1];
        placetext.setText(offset);
        offsetText.setText(primary);}
        else {
            placetext.setText("Near the ");
            offsetText.setText(orgPlace);
        }


        Date date = new Date(details.getTimeDateToShow());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateToDisplay = dateFormatter.format(date);

        TextView datetext = listView.findViewById(R.id.date);
        datetext.setText(dateToDisplay);



        TextView timeText = listView.findViewById(R.id.time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String formattedTime = timeFormat.format(date);
        timeText.setText(formattedTime);


        return listView;
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


    }



