package ca.machete.cowbell.events;

import static org.junit.Assert.assertSame;
import org.junit.Test;

public class IMouseEventTest {

    @Test
    public void mapsTypeNamesToTypesProperly() {
        assertSame(IMouseEvent.Type.Click, IMouseEvent.Type.valueOf("Click"));
    }
}
