package com.paulleclerc.mareu.ui.addMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.paulleclerc.mareu.R;

public class AddMeetingActivity extends AppCompatActivity {

    private AddMeetingPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        mPagerAdapter = new AddMeetingPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mPagerAdapter);
    }
}
