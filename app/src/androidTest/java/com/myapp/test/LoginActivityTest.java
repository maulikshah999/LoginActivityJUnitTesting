package com.myapp.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by mauli on 2/28/2018.
 */
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity mLoginActivity = null;
    private String emailId = "Maulik@gmail.com";
    private String password = "Adm!n123";

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ItemDetailActivity.class.getName(), null, false);
    @Before
    public void setUp() throws Exception {
        mLoginActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mLoginActivity.findViewById(R.id.email);
        assertNotNull(view);
    }

    /*@Test
    public void testLaunchItemDetailActivityOnButtonClick(){
        assertNotNull(mLoginActivity.findViewById(R.id.email_sign_in_button));

        onView(withId(R.id.email_sign_in_button)).perform(click());

        Activity itemDetailActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(itemDetailActivity);
        itemDetailActivity.finish();
    }*/

    @Test
    public void isEmailValid(){
        Espresso.onView(withId(R.id.email)).perform(typeText(emailId));
        Espresso.onView(withId(R.id.password)).perform((typeText(password)));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.email_sign_in_button)).perform(click());


        String email = ((EditText) mLoginActivity.findViewById(R.id.email)).getText().toString();
        System.out.println("email"+email);
        assertTrue(email.length() > 0 && email.endsWith(".com") && email.contains("@"));
    }

    @Test
    public void isValidPassword(){
        Espresso.onView(withId(R.id.password)).perform(typeText(password));
        String pass = ((EditText)mLoginActivity.findViewById(R.id.password)).getText().toString();

        String patternMatch = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        Pattern p = Pattern.compile(patternMatch);
        Matcher m = p.matcher(pass);

        assertTrue("Password is invalid!", m.matches());
    }

    @Test
    public void testLoginValidation(){
        Espresso.onView(withId(R.id.email)).perform(typeText("maulik@gmail.com"));
        Espresso.onView(withId(R.id.password)).perform(typeText("admin123"));
        Espresso.onView(withId(R.id.email_sign_in_button)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mLoginActivity = null;
    }


}