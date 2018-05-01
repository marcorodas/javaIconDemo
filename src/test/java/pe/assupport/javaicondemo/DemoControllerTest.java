package pe.assupport.javaicondemo;

/**
 *
 * @author skynet
 */
public class DemoControllerTest extends TestBase {

    @Override
    public void start() throws Exception {
        DemoController controller = new DemoController();
        controller.getStage().show();  
    }

}
