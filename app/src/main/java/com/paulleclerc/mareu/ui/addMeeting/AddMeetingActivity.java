package com.paulleclerc.mareu.ui.addMeeting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.paulleclerc.mareu.R;
import com.paulleclerc.mareu.ui.addMeeting.fragments.subject.MeetingSubjectFragment;
import com.paulleclerc.mareu.ui.utils.NonSwipeableViewPager;

public class AddMeetingActivity extends AppCompatActivity implements MeetingSubjectFragment.OnNavigateClickListener {

    private AddMeetingPagerAdapter mPagerAdapter;
    private NonSwipeableViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        mPagerAdapter = new AddMeetingPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onNavButtonClicked(boolean isRight) {
        int currPos=mViewPager.getCurrentItem();

        if (isRight) mViewPager.setCurrentItem(currPos+1);
        else mViewPager.setCurrentItem(currPos-1);
    }
}
