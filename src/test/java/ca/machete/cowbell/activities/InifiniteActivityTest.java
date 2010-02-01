package ca.machete.cowbell.activities;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class InifiniteActivityTest {

    @Test
    public void callingStopOnInfiniteActivityDoesNotInvokeStep() {
        final List<String> calls = new ArrayList<String>();

        InfiniteActivity activity = new InfiniteActivity() {

            @Override
            public void step() {
                calls.add("InfiniteActivity.step");
            }
        };

        activity.stop();
        assertTrue(calls.isEmpty());
    }
}
