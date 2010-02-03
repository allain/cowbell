package ca.machete.cowbell.events;

import org.junit.Test;



public class MouseAdapterTest {
    @Test
    public void methodsMayBeInvokedWithoutEffect() {
        MouseAdapter adapter = new MouseAdapter();
        adapter.mouseClicked(null);
        adapter.mouseReleased(null);
        adapter.mousePressed(null);
        adapter.mouseMoved(null);
    }
}
