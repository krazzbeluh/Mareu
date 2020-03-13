package com.paulleclerc.mareu.ui.addMeeting;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.paulleclerc.mareu.ui.addMeeting.fragments.MeetingDateFragment;
import com.paulleclerc.mareu.ui.addMeeting.fragments.MeetingParticipantsFragment;
import com.paulleclerc.mareu.ui.addMeeting.fragments.MeetingSubjectFragment;
import com.paulleclerc.mareu.ui.addMeeting.fragments.MeetingTimeFragment;

public class AddMeetingPagerAdapter extends FragmentPagerAdapter {

    AddMeetingPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MeetingSubjectFragment();
            case 1:
                return new MeetingDateFragment();
            case 2:
                return new MeetingTimeFragment();
            case 3:
                return new MeetingParticipantsFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
