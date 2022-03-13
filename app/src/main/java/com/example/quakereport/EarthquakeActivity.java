package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakereport.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {


    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    private static final String newUrl = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private EarthquakeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView earthquakeListView = (ListView) findViewById(R.id.list);



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
        TextView emptyTextView = findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(emptyTextView);

        ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkState =  connectivityManager.getActiveNetworkInfo();

        if (networkState!=null && networkState.isConnected()){
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL,newUrl);
        }
        else {
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("No Internet Connection");
        }

    }


    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... urls) {

           if (urls.length < 1 && urls[0] == null){
               return null;
           }

           List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
           List<Earthquake> result1 = QueryUtils.fetchEarthquakeData(urls[1]);
           result.addAll(result1);

            return result;
        }
        @Override
        protected void onPostExecute(List<Earthquake> data) {

            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            TextView emptyTextView = findViewById(R.id.empty_view);
                String whenEmpty = " No Earthquake Data Found";
                emptyTextView.setText(whenEmpty);

            mAdapter.clear();

            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}