package com.paulleclerc.mareu;

import android.content.Intent;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.paulleclerc.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.paulleclerc.mareu.utils.TestUtils.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MeetingListInstrumentedTest {
    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityRule = new ActivityTestRule<>(MeetingListActivity.class, true, false);

    private Meeting mMeeting;
    private MeetingService mMeetingService;
    private final Date date = new Date();

    @Before
    public void setUp() {
        mMeetingService = new MeetingService();

        while (mMeetingService.getMeetingList().size() > 0) mMeetingService.getMeetingList().remove(0);

        List<String> participants = new ArrayList<>();
        participants.add("Consectetur");
        mMeeting = new Meeting(date, "Salle 3", "Dolor Sit Amet", participants, R.color.MeetingGreen);
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
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(1, R.id.meeting_list_title)).check(matches(withText(meeting.getSubject())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(1, R.id.meeting_list_date)).check(matches(withText(meeting.getDateFormatted())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(1, R.id.meeting_list_location)).check(matches(withText(meeting.getLocation())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(1, R.id.meeting_list_subtitle)).check(matches(withText(meeting.getParticipants())));
    }

    @Test
    public void recyclerViewShouldPresentCorrectMeetingInformations() {
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(0, R.id.meeting_list_title)).check(matches(withText(mMeeting.getSubject())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(0, R.id.meeting_list_date)).check(matches(withText(mMeeting.getDateFormatted())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(0, R.id.meeting_list_location)).check(matches(withText(mMeeting.getLocation())));
        onView(withRecyclerView(R.id.meeting_list_view).atPositionOnView(0, R.id.meeting_list_subtitle)).check(matches(withText(mMeeting.getParticipants())));
    }

    @Test
    public void menu_onOpenMenu_ShouldHave3Items() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_by_date)).check(matches(isDisplayed()));
        onView(withText(R.string.filter_by_room)).check(matches(isDisplayed()));
        onView(withText(R.string.no_filter)).check(matches(isDisplayed()));
    }

    @Test
    public void menu_onClickOnFilterByDateItem_ShouldOpenPickerDialog() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_by_date)).perform(click());

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.meeting_list_view)).check(withItemCount(1));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_by_date)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1999, 5, 27));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.meeting_list_view)).check(withItemCount(0));
    }

    @Test
    public void menu_onClickOnFilterByLocationItem_ShouldOpenListDialog() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_by_room)).perform(click());
        for (String room: mActivityRule.getActivity().getApplicationContext().getResources().getStringArray(R.array.meetingLocations)) {
            onData(allOf(is(instanceOf(String.class)), is(room))).perform(scrollTo()).check(matches(isDisplayed()));
        }
        onData(allOf(is(instanceOf(String.class)), is("Salle 3"))).perform(scrollTo(), click());
        onView(withId(R.id.meeting_list_view)).check(withItemCount(1));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filter_by_room)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Salle 2"))).perform(scrollTo(), click());
        onView(withId(R.id.meeting_list_view)).check(withItemCount(0));
    }

    @After
    public void tearDown() {
        mActivityRule.finishActivity();
    }
}
