package ca.machete.cowbell.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class EventDispatcherTest {

    private EventDispatcher<Object> dispatcher;

    @Before
    public void setupDispatcher() {
        dispatcher = new EventDispatcher<Object>();
    }

    @Test
    public void eventDispatcherCanBeConstructedWithoutParams() {
        assertNotNull(dispatcher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registrationDoesNotAcceptNullFilter() {
        dispatcher.register(null, new DispatchCounterListener());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registrationDoesNotAcceptNullEventListener() {
        dispatcher.register(new AcceptAllEventFilter(), null);
    }

    @Test
    public void registeringListenerWorks() {
        dispatcher.register(new AcceptAllEventFilter(), new DispatchCounterListener());
    }

    @Test
    public void dispatchEventOnlyDoesSoIfFilterReturnsTrue() {
        DispatchCounterListener callCounter = new DispatchCounterListener();
        dispatcher.register(new AcceptAllEventFilter(), callCounter);
        dispatcher.register(new AcceptNoneEventFilter(), callCounter);

        dispatcher.dispatch(null);
        assertEquals(1, callCounter.getCallCount());
    }

    @Test
    public void dispatchDoesNotAllowMultipleOfTheSameRegistration() {
        DispatchCounterListener callCounter = new DispatchCounterListener();
        AcceptAllEventFilter filter = new AcceptAllEventFilter();
        dispatcher.register(filter, callCounter);
        dispatcher.register(filter, callCounter);

        dispatcher.dispatch(null);
        assertEquals(1, callCounter.getCallCount());
    }

    @Test
    public void listenerThrowingExceptionDoesntAffectOtherListeners() {
        DispatchCounterListener callCounter = new DispatchCounterListener();
        AcceptAllEventFilter filter = new AcceptAllEventFilter();
        dispatcher.register(filter, new EventListener<Object>() {

            @Override
            public void dispatch(final Object object) {
                throw new UnsupportedOperationException();
            }

        });

        dispatcher.register(filter, callCounter);

        try {
            dispatcher.dispatch(null);
            fail("Exception not thrown");
        } catch (DispatcherException e) {}

        assertEquals(1, callCounter.getCallCount());
    }

    private final class AcceptAllEventFilter implements EventFilter<Object> {

        @Override
        public boolean test(final Object object) {
            return true;
        }
    }

    private final class AcceptNoneEventFilter implements EventFilter<Object> {

        @Override
        public boolean test(final Object object) {
            return false;
        }
    }

    private final class DispatchCounterListener implements EventListener<Object> {

        private int callCount = 0;

        public int getCallCount() {
            return callCount;
        }

        @Override
        public void dispatch(final Object object) {
            callCount++;
        }
    }

}
