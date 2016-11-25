package com.techcoderz.ruchira.classtune.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;
import com.squareup.picasso.Picasso;
import com.techcoderz.ruchira.R;
import com.techcoderz.ruchira.classtune.adapter.NewRealeaseAdapter;
import com.techcoderz.ruchira.classtune.model.Genres;
import com.techcoderz.ruchira.classtune.model.NewRelease;
import com.techcoderz.ruchira.classtune.model.ProductionCompanies;
import com.techcoderz.ruchira.classtune.model.ProductionCountries;
import com.techcoderz.ruchira.utills.TaskUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priom on 11/25/16.
 */
public class DetailsFragment extends MovieTuneFragment {
    private final static String TAG = "DetailsFragment";
    private String url = "https://api.themoviedb.org/3/movie/";
    private String url2 = "?api_key=c37d3b40004717511adb2c1fbb15eda4";
    Fragment toLaunchFragment = null;

    private ImageView title_img;
    TextView mNametextView12, genur, details_txt, procompany, procountries, budget, lan, title;
    Button popuimageView3, avg;
    RecyclerView similarRc;
    String Id = "";
    private Bundle bundle;


    private String url3 = "https://api.themoviedb.org/3/movie/now_playing?api_key=c37d3b40004717511adb2c1fbb15eda4&page=1";
    private String url4 = "https://api.themoviedb.org/3/movie/now_playing?api_key=c37d3b40004717511adb2c1fbb15eda4&page=";

    private List<NewRelease> newReleaseList;
    private List<Genres> genresList;
    private List<ProductionCompanies> productionCompaniesList;
    private List<ProductionCountries> productionCountriesList;
    private int counter = 1;
    private int counterLimit = 5;
    private RecyclerView outlet_rcview;
    private NewRealeaseAdapter newRealeaseAdapter;
    private GridLayoutManager gridLayoutManager;

    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        setupToolbar();
        initialize(rootView);

        fetchDataFromServer();
        fetchDataFromServer2();
        action();
        return rootView;
    }

    private void setupToolbar() {
        ownerActivity.getSupportActionBar().show();
//        ownerActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ownerActivity.getSupportActionBar().setTitle("View Memo");
    }

    private void initialize(View rootView) {
        bundle = getArguments();
        Id = bundle.getString("id");
        title_img = (ImageView) rootView.findViewById(R.id.title_img);

        mNametextView12 = (TextView) rootView.findViewById(R.id.textView12);
        genur = (TextView) rootView.findViewById(R.id.genur);
        details_txt = (TextView) rootView.findViewById(R.id.details_txt);
        procompany = (TextView) rootView.findViewById(R.id.procompany);
        procountries = (TextView) rootView.findViewById(R.id.procountries);
        budget = (TextView) rootView.findViewById(R.id.budget);
        lan = (TextView) rootView.findViewById(R.id.lan);

        title = (TextView) ownerActivity.findViewById(R.id.title);

        popuimageView3 = (Button) rootView.findViewById(R.id.imageView3);
        avg = (Button) rootView.findViewById(R.id.avg);

        similarRc = (RecyclerView) rootView.findViewById(R.id.similarRc);


        newReleaseList = new ArrayList<>();
        genresList = new ArrayList<>();

        productionCompaniesList = new ArrayList<>();
        productionCountriesList = new ArrayList<>();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ownerActivity, LinearLayoutManager.HORIZONTAL, false);

//        gridLayoutManager = new GridLayoutManager(ownerActivity, 2);
        newRealeaseAdapter = new NewRealeaseAdapter(ownerActivity, newReleaseList);

        similarRc.setAdapter(newRealeaseAdapter);
        similarRc.setHasFixedSize(true);
        similarRc.setLayoutManager(layoutManager);
    }

    private void action() {
        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                // Load next page of data (e.g. network or database)
                fetchDataFromServer3();
            }

            @Override
            public boolean isLoading() {
                // Indicate whether new page loading is in progress or not
                return false;
            }

            @Override
            public boolean hasLoadedAllItems() {
                // Indicate whether all data (pages) are loaded or not
                return true;
            }
        };


        Paginate.with(similarRc, callbacks)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(false ? new CustomLoadingListItemCreator() : null)
                .build();
    }

    private void fetchDataFromServer() {

        ProgressDialog progressDialog = null;

        progressDialog = new ProgressDialog(ownerActivity);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        final ProgressDialog finalProgressDialog = progressDialog;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, url + Id + url2, "", new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, response.toString());
                finalProgressDialog.dismiss();
                execute(response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(ownerActivity).add(jsonRequest);

    }

    private void fetchDataFromServer2() {

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET, url3, "", new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, response.toString());
                execute2(response.toString());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(ownerActivity).add(jsonRequest);

    }

    private void fetchDataFromServer3() {

        if (counter != counterLimit) {
            JsonObjectRequest jsonRequest = new JsonObjectRequest(
                    Request.Method.GET, url4 + counter++, "", new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, response.toString());
                    execute(response.toString());

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(ownerActivity).add(jsonRequest);
        }

    }

    private void execute(String result) {
        Log.d(TAG, result.toString());

        try {

            JSONObject obj = new JSONObject(result);

            genresList.addAll(TaskUtils.setGenur(result));

            productionCompaniesList.addAll(TaskUtils.setsetCorporation(result));
            productionCountriesList.addAll(TaskUtils.setCountries(result));


            String abc = "";
            String countries = "";
            String companies = "";

            for (int i = 0; i < genresList.size(); i++) {
                abc += genresList.get(i).getName() + ".";
            }

            for (int i = 0; i < productionCountriesList.size(); i++) {
                countries += productionCountriesList.get(i).getName() + ",";
            }

            for (int i = 0; i < productionCompaniesList.size(); i++) {
                companies += productionCompaniesList.get(i).getName() + ",";
            }

            genur.setText(abc);
            procompany.setText(companies);
            procountries.setText(countries);

            Picasso.with(ownerActivity)
                    .load("http://image.tmdb.org/t/p/w500/" + obj.getString("backdrop_path"))
                    .into(title_img);

            mNametextView12.setText(obj.getString("title"));

//            title.setText(obj.getString("title"));

            details_txt.setText(obj.getString("overview"));

            budget.setText("$ " + obj.getString("budget"));
            lan.setText(obj.getString("original_language"));

            popuimageView3.setText(obj.getString("vote_count"));
            avg.setText(obj.getString("vote_average"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void execute2(String result) {
        Log.d(TAG, result.toString());
        newReleaseList.clear();

        try {

            JSONObject obj = new JSONObject(result);
            newReleaseList.addAll(TaskUtils.setNewRelease(result));
            newRealeaseAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class CustomLoadingListItemCreator implements LoadingListItemCreator {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // Bind custom loading row if needed
        }
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvLoading;

        public VH(View itemView) {
            super(itemView);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
        }

    }
}