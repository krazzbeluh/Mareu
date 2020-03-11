package com.paulleclerc.mareu;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.paulleclerc.mareu.Model.Meeting;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.paulleclerc.mareu.utils.RecyclerViewItemCountAssertion.atPosition;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class MeetingListInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private Meeting mMeeting;

    @Before
    public void setUp() {
        Date date = new Date(1583964935);
        List<String> participants = new ArrayList<>();
        participants.add("Consectetur");
        mMeeting = new Meeting(date, "Lorem Ipsum", "Dolor Sit Amet", participants);
        Meeting.sMeetingList.add(mMeeting);
    }

    @Test
    public void recyclerViewShouldPresentCorrectMeetingInformations() {
        onView(withId(R.id.meeting_list_view)).check(matches(atPosition(0, hasDescendant(allOf(withId(R.id.meeting_list_subtitle), withText(mMeeting.getParticipants()))))));
    }
}
