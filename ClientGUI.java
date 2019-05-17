import javafx.application.Application;
import javafx.stage.Stage;

/**
 * ClientGUI
 */
public class ClientGUI extends Application {

    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Flight Reservation System");

        this.stage.setMinWidth(500);
        this.stage.setMinHeight(100);

        this.stage.setWidth(500);
        this.stage.setHeight(200);
        this.stage.show();
    }
}