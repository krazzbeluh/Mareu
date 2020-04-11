package com.paulleclerc.mareu;

import android.content.res.Resources;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.rule.ActivityTestRule;

import com.paulleclerc.mareu.ui.addMeeting.AddMeetingActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.paulleclerc.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.paulleclerc.mareu.utils.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class AddMeetingInstrumentedTests {

    private final int[] mColorButtons = {R.id.red_button,
            R.id.orange_button,
            R.id.yellow_button,
            R.id.green_button,
            R.id.blue_button,
            R.id.purple_button};

    @Rule
    public ActivityTestRule<AddMeetingActivity> mActivityRule = new ActivityTestRule<>(AddMeetingActivity.class);

    @Before
    public void setUp() {
        Espresso.closeSoftKeyboard();
    }

    @Test
    public void addMeeting_onClickOnFinishWithNoData_ShouldDisplayErrorDialog() {
        onView(withId(R.id.done_button)).perform(scrollTo(), click());
        onView(withText("Erreur")).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void addMeeting_onClickOnLocationList_shouldDisplay10Items() {
        String[] items = mActivityRule.getActivity().getApplicationContext().getResources().getStringArray(R.array.meetingLocations);
        for (String item : items) {
            onView(withId(R.id.location_spinner)).perform(click());
            onData(allOf(is(instanceOf(String.class)), is(item))).perform(click());
            onView(withId(R.id.location_spinner)).check(matches(withSpinnerText(item)));
        }
    }

    @Test
    public void addMeeting_onEditingEmailTextView_shouldNotEnableButtonIfTextIsNotEmail() {
        onView(withId(R.id.add_email_button)).check(matches(not(isEnabled())));
        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("paul"));
        onView(withId(R.id.add_email_button)).check(matches(not(isEnabled())));

        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("paul@"));
        onView(withId(R.id.add_email_button)).check(matches(not(isEnabled())));

        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("paul@lamzone"));
        onView(withId(R.id.add_email_button)).check(matches(not(isEnabled())));

        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("paul@lamzone.com"));
        onView(withId(R.id.add_email_button)).check(matches(isEnabled()));

        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("Paul@Lamzone.com"));
        onView(withId(R.id.add_email_button)).check(matches(isEnabled()));
    }

    @Test
    public void addMeeting_onClickOnColorButton_shouldSelectOnlyThisButton() {
        for (int button : mColorButtons) {
            onView(withId(button)).perform(scrollTo(), click());
        }
    }

    @Test
    public void addMeeting_onAddAndDeleteEmails_shouldHaveCorrectEmails() {
        onView(withId(R.id.participants_rv)).check(withItemCount(0));
        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("paul@lamzone.com"));
        onView(withId(R.id.add_email_button)).perform(click());
        onView(withId(R.id.participants_rv)).check(withItemCount(1));
        onView(withRecyclerView(R.id.participants_rv).atPositionOnView(0, R.id.email_text_view)).check(matches(withText("paul@lamzone.com")));
        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("alexandra@lamzone.com"));
        onView(withId(R.id.add_email_button)).perform(click());
        onView(withId(R.id.participants_rv)).check(withItemCount(2));
        onView(withRecyclerView(R.id.participants_rv).atPositionOnView(0, R.id.email_text_view)).check(matches(withText("paul@lamzone.com")));
        onView(withRecyclerView(R.id.participants_rv).atPositionOnView(1, R.id.email_text_view)).check(matches(withText("alexandra@lamzone.com")));
        onView(withRecyclerView(R.id.participants_rv).atPositionOnView(0, R.id.delete_email_button)).perform(click());
        onView(withRecyclerView(R.id.participants_rv).atPositionOnView(0, R.id.email_text_view)).check(matches(withText("alexandra@lamzone.com")));
    }

    @Test
    public void addMeeting_onClickOnDateTextEdit_shouldOpenDatePicker() {
        onView(withId(R.id.meetingDate_editText)).perform(clearText(), click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022, 10, 28));
        onView(withText("OK")).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(18, 9));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.meetingDate_editText)).check(matches(withText("28-10-2022 18:09:00")));
    }

    @Test
    public void addMeeting_onClickOnFinishButtonWithFieldsFilled_shouldFinishActivity() {
        onView(withId(R.id.subject_edit_text)).perform(clearText(), typeText("azerty"));
        Espresso.closeSoftKeyboard();

        // Set date and time
        onView(withId(R.id.meetingDate_editText)).perform(clearText(), click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022, 10, 28));
        onView(withText("OK")).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(18, 9));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("paul@lamzone.com"));
        onView(withId(R.id.green_button)).perform(scrollTo(), click());
    }
}
