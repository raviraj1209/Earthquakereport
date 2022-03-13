package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.quakereport.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {


    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    private static final String newUrl = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

//        ArrayList<>
//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);

        List<Earthquake> toCheck = new ArrayList<>();

        mAdapter = new EarthquakeAdapter(this,toCheck );
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake getDetails = mAdapter.getItem(i);
                Uri earthquakeUri = Uri.parse(getDetails.getJsonurl());

                Intent website = new Intent(Intent.ACTION_VIEW,earthquakeUri);
                startActivity(website);
            }
        });
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);



        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface



    }


    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... urls) {

           if (urls.length < 1 && urls[0] == null){
               return null;
           }

           List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
           return result;
        }
        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}