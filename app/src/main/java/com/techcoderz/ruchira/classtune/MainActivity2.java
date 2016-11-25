package com.techcoderz.ruchira.classtune;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.techcoderz.ruchira.R;
import com.techcoderz.ruchira.activity.RuchiraActivity;
import com.techcoderz.ruchira.classtune.adapter.TabbedFragmentStatePagerAdapter;

public class MainActivity2 extends RuchiraActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean isRoot = getSupportFragmentManager().getBackStackEntryCount() == 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("MovieTune");

        ViewPager tabbedViewPager = (ViewPager) findViewById(R.id.tabbedViewPager);
        tabbedViewPager.setAdapter(new TabbedFragmentStatePagerAdapter(getSupportFragmentManager()));
        tabbedViewPager.setOffscreenPageLimit(3);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip);
        pagerSlidingTabStrip.setViewPager(tabbedViewPager);
    }
}
