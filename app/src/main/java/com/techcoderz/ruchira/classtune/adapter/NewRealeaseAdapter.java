package com.techcoderz.ruchira.classtune.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techcoderz.ruchira.R;
import com.techcoderz.ruchira.classtune.fragment.DetailsFragment;
import com.techcoderz.ruchira.classtune.model.NewRelease;
import com.techcoderz.ruchira.utills.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by priom on 11/24/16.
 */
public class NewRealeaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewRelease> newReleaseList = new ArrayList<>();
    private Context context;
    Fragment toLaunchFragment = null;
    private int type;

    public NewRealeaseAdapter(Context context, List<NewRelease> newReleaseList) {

        this.newReleaseList = newReleaseList;
        this.context = context;

        if (toLaunchFragment != null) {
            ViewUtils.launchFragmentKeepingInBackStack(context, toLaunchFragment);
        }
    }

    @Override
    public RecyclerViewSubHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row_new_release, null);
        RecyclerViewSubHolders rcv = new RecyclerViewSubHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewSubHolders) {
            if (newReleaseList.size() > 0) {

                Picasso.with(context)
                        .load("http://image.tmdb.org/t/p/w500/"+newReleaseList.get(position).getPoster_path())
                        .into(((RecyclerViewSubHolders) holder).title_image);


                ((RecyclerViewSubHolders) holder).title_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openOpenShopProfile(position);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return this.newReleaseList.size();
    }

    private void openOpenShopProfile(int position) {
        toLaunchFragment = new DetailsFragment();
        if (toLaunchFragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", newReleaseList.get(position).getId());
            toLaunchFragment.setArguments(bundle);
            ViewUtils.launchFragmentKeepingInBackStack(context, toLaunchFragment);
            toLaunchFragment = null;
        }
    }

    class RecyclerViewSubHolders extends RecyclerView.ViewHolder {

        public ImageView title_image;
        public LinearLayout wholeContent;

        public RecyclerViewSubHolders(View itemView) {
            super(itemView);
            title_image = (ImageView) itemView.findViewById(R.id.title_image);
        }

    }
}