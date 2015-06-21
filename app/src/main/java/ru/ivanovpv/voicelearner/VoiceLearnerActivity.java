package ru.ivanovpv.voicelearner;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class VoiceLearnerActivity extends AppCompatActivity implements ExamsListFragment.OnFragmentInteractionListener, LessonsListFragment.OnFragmentInteractionListener, View.OnClickListener {
    private static final String TAG=VoiceLearnerActivity.class.getName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    FloatingActionButton fab;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        fab=(FloatingActionButton )findViewById(R.id.fab);
        if(mViewPager.getCurrentItem()==0)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);
        fab.setOnClickListener(this);


        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                if(position==0)
                    fab.setVisibility(View.VISIBLE);
                else
                    fab.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_new_lesson);
        if(mViewPager.getCurrentItem()==0)
            menuItem.setVisible(true);
        else
            menuItem.setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem()));
                transaction.replace(R.id.idMain, new PrefsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                fab.setVisibility(View.GONE);
                return true;
            case R.id.action_new_lesson:
                addLesson();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(long id) {
        Log.i("VoiceLearner", "Bingo, received id="+id);
    }

    @Override
    public void onClick(View v) {
        addLesson();
    }

    private void addLesson() {
        Log.i(TAG, "Add lesson");
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
                return LessonsListFragment.newInstance("", "");
            else if(position==1)
                return ExamsListFragment.newInstance("", "");
            else
                return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_lessons);
                case 1:
                    return getString(R.string.title_exams);
            }
            return null;
        }
    }
}
