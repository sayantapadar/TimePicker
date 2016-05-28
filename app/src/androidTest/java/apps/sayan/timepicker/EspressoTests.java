package apps.sayan.timepicker;

import android.app.Activity;
import android.support.test.espresso.contrib.PickerActions;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TimePicker;

import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by SAYAN on 28-05-2016.
 */


public class EspressoTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public Activity currentActivity;

    public EspressoTests() throws IncompatibleClassChangeError{
        super(MainActivity.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        currentActivity=getActivity();
    }

    public void testChangeStartTime() throws Exception{
        int hour = 0;
        int minutes = 0;

        onView(withId(R.id.set_hours)).perform(click());
        onView(withId(R.id.timeStart)).perform(click());

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.timeStart)).check(matches(withText("12:00")));
    }
}
