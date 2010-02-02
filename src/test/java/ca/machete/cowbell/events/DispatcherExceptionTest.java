package ca.machete.cowbell.events;

import java.util.ArrayList;
import org.junit.Test;

public class DispatcherExceptionTest {

    @SuppressWarnings("serial")
    @Test(expected = UnsupportedOperationException.class)
    public void thrownExceptionsAreImmutable() {
        DispatcherException exception = new DispatcherException(new ArrayList<Exception>());
        exception.getThrownExceptions().add(new Exception() {});
    }
}
