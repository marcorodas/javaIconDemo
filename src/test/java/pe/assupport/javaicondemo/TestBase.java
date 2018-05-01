package pe.assupport.javaicondemo;

import java.util.concurrent.TimeUnit;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author skynet
 */
public abstract class TestBase extends ApplicationTest {

    private int duration = 0;

    public abstract void start() throws Exception;

    public TestBase() {
    }

    public TestBase(int duration) {
        this(duration, false);
    }

    public TestBase(boolean sessionUnset) {
        this(0, sessionUnset);
    }

    public TestBase(int duration, boolean sessionUnset) {
        this.duration = duration;
    }

    @Test
    public void testShow() throws Exception {
        sleep(duration == 0 ? 30 : duration, TimeUnit.SECONDS);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        BaseController.setAppTitle("Icon Demo");
        this.start();
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }
}
