import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * ClientGUI
 */
public class ClientGUI extends Application {

    private static Client client;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initialize();

        this.stage.setTitle("Flight Reservation System");

        this.stage.setMinWidth(650);
        this.stage.setMinHeight(150);

        this.stage.setWidth(650);
        this.stage.setHeight(200);

        this.stage.show();

        // client_connect();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initialize() {
        HBox hBox0 = new HBox(10);
        hBox0.setAlignment(Pos.CENTER);
        VBox.setVgrow(hBox0, Priority.ALWAYS);

        TextField departure_location = new TextField();
        departure_location.setPromptText("departure_location");
        HBox.setHgrow(departure_location, Priority.ALWAYS);

        TextField arrival_location = new TextField();
        arrival_location.setPromptText("departure_location");
        HBox.setHgrow(arrival_location, Priority.ALWAYS);

        hBox0.getChildren().addAll(new Text("departure_location"), departure_location, new Text("arrival_location"), arrival_location);

        HBox hBox10 = new HBox(5);
        hBox10.setAlignment(Pos.CENTER);
        HBox.setHgrow(hBox10, Priority.ALWAYS);

        TextField departure_date = new TextField();
        departure_date.setPromptText("DD");
        departure_date.setPrefWidth(40);

        TextField departure_month = new TextField();
        departure_month.setPromptText("MM");
        departure_month.setPrefWidth(40);

        TextField departure_year = new TextField();
        departure_year.setPromptText("YY");
        departure_year.setPrefWidth(40);

        hBox10.getChildren().addAll(new Text("departure_date"), departure_date, new Text("/"), departure_month, new Text("/"), departure_year);

        HBox hBox11 = new HBox(5);
        hBox11.setAlignment(Pos.CENTER);
        HBox.setHgrow(hBox11, Priority.ALWAYS);

        TextField arrival_date = new TextField();
        arrival_date.setPromptText("DD");
        arrival_date.setPrefWidth(40);

        TextField arrival_month = new TextField();
        arrival_month.setPromptText("MM");
        arrival_month.setPrefWidth(40);

        TextField arrival_year = new TextField();
        arrival_year.setPromptText("YY");
        arrival_year.setPrefWidth(40);

        hBox11.getChildren().addAll(new Text("arrival_date"), arrival_date, new Text("/"), arrival_month, new Text("/"), arrival_year);

        HBox hBox12 = new HBox(5);
        hBox12.setAlignment(Pos.CENTER);
        HBox.setHgrow(hBox12, Priority.ALWAYS);

        Button search = new Button("Search");
        search.setOnAction(e -> {
            check(departure_date.getText(), departure_month.getText(), departure_year.getText(), departure_location.getText(), arrival_date.getText(), arrival_month.getText(), arrival_year.getText(), arrival_location.getText());
        });

        hBox12.getChildren().add(search);

        HBox hBox1 = new HBox(20);
        hBox1.setAlignment(Pos.CENTER);
        VBox.setVgrow(hBox1, Priority.ALWAYS);

        hBox1.getChildren().addAll(hBox10, hBox11, hBox12);

        VBox resultsBox = new VBox(10);
        resultsBox.setPadding(new Insets(5, 5, 5, 5));
        VBox.setVgrow(resultsBox, Priority.ALWAYS);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        vBox.getChildren().addAll(hBox0, hBox1, resultsBox);

        stage.setScene(new Scene(vBox));
        stage.show();
    }

    public void check(String departure_date, String departure_month, String departure_year, String departure_location, String arrival_date, String arrival_month, String arrival_year, String arrival_location) {

    }

    public void client_connect() {
        try {
            client = new Client();
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Connection Error!!");
            alert.setHeaderText(null);
            alert.setContentText(e.toString());
            alert.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(-1);
            });
            alert.show();
            e.printStackTrace();
        }
    }
}