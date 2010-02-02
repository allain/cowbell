package ca.machete.cowbell.events;

import java.util.Collection;
import java.util.Collections;

public class DispatcherException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Collection<Exception> thrownExceptions;

    public DispatcherException(final Collection<Exception> thrownExceptions) {
        this.thrownExceptions = Collections.unmodifiableCollection(thrownExceptions);
    }

    public Collection<Exception> getThrownExceptions() {
        return thrownExceptions;
    }
}
