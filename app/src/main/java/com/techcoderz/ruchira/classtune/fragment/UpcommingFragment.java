package com.techcoderz.ruchira.classtune.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;
import com.techcoderz.ruchira.R;
import com.techcoderz.ruchira.classtune.adapter.NewRealeaseAdapter;
import com.techcoderz.ruchira.classtune.model.NewRelease;
import com.techcoderz.ruchira.utills.TaskUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priom on 11/24/16.
 */
public class UpcommingFragment extends MovieTuneFragment {
    private final static String TAG = "UpcommingFragment";
    Fragment toLaunchFragment = null;
    private String url = " https://api.themoviedb.org/3/movie/upcoming?api_key=c37d3b40004717511adb2c1fbb15eda4&page=1";
    private String url2 = "https://api.themoviedb.org/3/movie/upcoming?api_key=c37d3b40004717511adb2c1fbb15eda4&page=";

    private List<NewRelease> newReleaseList;
    private int counter = 1;
    private int counterLimit = 46;
    private RecyclerView outlet_rcview;
    private NewRealeaseAdapter newRealeaseAdapter;
    private GridLayoutManager gridLayoutManager;

    public UpcommingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_release, container, false);
        setupToolbar();
        initialize(rootView);
        fetchDataFromServer();
        action();
        return rootView;
    }

    private void setupToolbar() {
        ownerActivity.getSupportActionBar().show();
//        ownerActivity.getSupportActionBar().setTitle("");
    }

    private void initialize(View rootView) {

        TextView title = (TextView) ownerActivity.findViewById(R.id.title);
        title.setText("MovieTune");

        newReleaseList = new ArrayList<>();
        outlet_rcview = (RecyclerView) rootView.findViewById(R.id.outlet_rcview);
        gridLayoutManager = new GridLayoutManager(ownerActivity, 2);
        newRealeaseAdapter = new NewRealeaseAdapter(ownerActivity, newReleaseList);

        outlet_rcview.setAdapter(newRealeaseAdapter);
        outlet_rcview.setHasFixedSize(true);
        outlet_rcview.setLayoutManager(gridLayoutManager);
    }

    private void action() {
        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                // Load next page of data (e.g. network or database)
                fetchDataFromServer2();
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


        Paginate.with(outlet_rcview, callbacks)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(false ? new CustomLoadingListItemCreator() : null)
                .build();
    }


    private void fetchDataFromServer2() {

        if (counter != counterLimit) {
            JsonObjectRequest jsonRequest = new JsonObjectRequest(
                    Request.Method.GET, url2 + counter++, "", new Response.Listener<JSONObject>() {

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
                Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

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

    private void execute(String result) {
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