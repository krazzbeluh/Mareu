package com.paulleclerc.mareu;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.paulleclerc.mareu.model.Meeting;
import com.paulleclerc.mareu.model.MeetingService;
import com.paulleclerc.mareu.ui.meetingList.MeetingListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.paulleclerc.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.paulleclerc.mareu.utils.TestUtils.*;

@RunWith(AndroidJUnit4.class)
public class MeetingListInstrumentedTest {
    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityRule = new ActivityTestRule<>(MeetingListActivity.class, true, false);

    private Meeting mMeeting;
    private MeetingService mMeetingService;

    @Before
    public void setUp() {
        mMeetingService = new MeetingService();

        while (mMeetingService.getMeetingList().size() > 0) mMeetingService.getMeetingList().remove(0);

        Date date = new Date(1583964935);
        List<String> participants = new ArrayList<>();
        participants.add("Consectetur");
        mMeeting = new Meeting(date, "Lorem Ipsum", "Dolor Sit Amet", participants, R.color.MeetingGreen);
        mMeetingService.getMeetingList().add(mMeeting);

        mActivityRule.launchActivity(new Intent());
    }

    @Test
    public void recyclerViewShouldPresentCorrectMeetingNumberEvenAfterADeletion() {
        onView(withId(R.id.meeting_list_view)).check(withItemCount(1));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(0, R.id.meeting_list_delete)).perform(click());
        onView(withId(R.id.meeting_list_view)).check(withItemCount(0));
    }

    @Test
    public void recyclerViewShouldPresentCorrectMeetingsAfterAddition() {
        onView(withId(R.id.meeting_list_view)).check(withItemCount(1));

        List<String> participants = new ArrayList<>(Arrays.asList("azerty@lamzone.com", "qwerty@lamzone.com"));
        Meeting meeting = new Meeting(new Date(), "Room 9", "subject", participants, R.color.MeetingGreen);
        mMeetingService.addMeeting(meeting);

        mActivityRule.finishActivity();
        mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.meeting_list_view)).check(withItemCount(2));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(1, R.id.meeting_list_title)).check(matches(withText(meeting.getSubject() + " - " + meeting.getLocation() + " - " + meeting.getDateFormatted())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(1, R.id.meeting_list_subtitle)).check(matches(withText(meeting.getParticipants())));
    }

    @Test
    public void recyclerViewShouldPresentCorrectMeetingInformations() {
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(0, R.id.meeting_list_title)).check(matches(withText(mMeeting.getSubject() + " - " + mMeeting.getLocation() + " - " + mMeeting.getDateFormatted())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(0, R.id.meeting_list_subtitle)).check(matches(withText(mMeeting.getParticipants())));
    }

    @After
    public void tearDown() {
        mActivityRule.finishActivity();
    }
}
