package com.paulleclerc.mareu;

import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.paulleclerc.mareu.ui.addMeeting.AddMeetingActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.paulleclerc.mareu.utils.RecyclerViewItemCountAssertion.atPosition;
import static com.paulleclerc.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.paulleclerc.mareu.utils.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class AddMeetingInstrumentedTests {
    private static final String TAG = AddMeetingInstrumentedTests.class.getSimpleName();

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
        onView(withId(R.id.done_button)).perform(click());
        onView(withText("Erreur")).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void addMeeting_onClickOnLocationList_shouldDisplay10Items() {
        String[] items = mActivityRule.getActivity().getApplicationContext().getResources().getStringArray(R.array.meetingLocations);
        for (String item: items) {
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
        for (int button: mColorButtons) {
            onView(withId(button)).perform(click());
            checkOnlyThisColorButtonIsSelected(button);
        }
    }

    private void checkOnlyThisColorButtonIsSelected(int button) {
        for (int currentButtonId: mColorButtons) {
            String color = "";
            switch (currentButtonId) {
                case R.id.red_button:
                    color = "rouge";
                    break;
                case R.id.orange_button:
                    color = "orange";
                    break;
                case R.id.yellow_button:
                    color = "jaune";
                    break;
                case R.id.green_button:
                    color = "vert";
                    break;
                case R.id.blue_button:
                    color = "bleu";
                    break;
                case R.id.purple_button:
                    color = "violet";
                    break;
            }

            String contentDescription = "Bouton ";
            contentDescription += color;
            contentDescription += (currentButtonId == button) ? " sélectionné" : " désélectionné";

            onView(withId(currentButtonId)).check(matches(withContentDescription(contentDescription)));
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

    /*@Test
    public void addMeeting_onClickOnDateTextEdit_shouldOpenDatePicker() {
        onView(withId(R.id.meetingDate_editText)).perform(click());
    }*/

    /*@Test
    public void addMeeting_onClickOnFinishButtonWithFieldsFilled_shouldFinishActivity() {
        onView(withId(R.id.subject_edit_text)).perform(clearText(), typeText("azerty"));
        // TODO: date
        onView(withId(R.id.email_edit_text)).perform(clearText(), typeText("paul@lamzone.com"));
        onView(withId(R.id.green_button)).perform(click());
    }*/
}
