import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        this.stage.setMinWidth(850);
        this.stage.setMinHeight(150);

        this.stage.setWidth(850);
        this.stage.setHeight(200);

        this.stage.show();

        // connect to appServer
        client_connect();
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
        departure_year.setPromptText("YYYY");
        departure_year.setPrefWidth(60);

        hBox10.getChildren().addAll(new Text("departure_date"), departure_date, new Text("/"), departure_month,
                new Text("/"), departure_year);

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
        arrival_year.setPromptText("YYYY");
        arrival_year.setPrefWidth(60);

        hBox11.getChildren().addAll(new Text("arrival_date"), arrival_date, new Text("/"), arrival_month, new Text("/"),
                arrival_year);

        HBox hBox12 = new HBox(20);
        hBox12.setAlignment(Pos.CENTER);
        HBox.setHgrow(hBox12, Priority.ALWAYS);

        TextField passengers = new TextField();
        passengers.setPromptText("#");
        passengers.setPrefWidth(60);

        Button search = new Button("Search");

        hBox12.getChildren().addAll(new Text("passengers"), passengers, search);

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

        search.setOnAction(e -> {
            check(departure_date.getText(), departure_month.getText(), departure_year.getText(),
                    departure_location.getText(), arrival_date.getText(), arrival_month.getText(),
                    arrival_year.getText(), arrival_location.getText(), passengers.getText(), resultsBox);
        });

        stage.setScene(new Scene(vBox));
        stage.show();
    }

    // method that checks and displays for available flights
    public void check(String departure_date, String departure_month, String departure_year, String departure_location,
            String arrival_date, String arrival_month, String arrival_year, String arrival_location, String passengers,
            VBox vbox) {
        vbox.getChildren().clear();

        ArrayList<ArrayList<Flight>> flights = null;

        try {
            flights = client.check(
                    (Calendar) new GregorianCalendar(Integer.parseInt(departure_year),
                            Integer.parseInt(departure_month), Integer.parseInt(departure_date)),
                    departure_location, (Calendar) new GregorianCalendar(Integer.parseInt(arrival_year),
                            Integer.parseInt(arrival_month), Integer.parseInt(arrival_date)),
                    arrival_location, Integer.parseInt(passengers));
        } catch (NumberFormatException e) {
            alert(AlertType.WARNING, "Invalid arguments", e.getMessage());
            return;
        } catch (RemoteException e) {
            alert(AlertType.ERROR, "Network error", e.getMessage());
            e.printStackTrace();
            return;
        }

        if (flights == null) {
            alert(AlertType.WARNING, "Could not find any flights", "Try again with different dates...");
            return;
        }

        TableView<Flight> departures = new TableView<>();
        HBox.setHgrow(departures, Priority.ALWAYS);
        departures.getItems().addAll(flights.get(0));

        TableColumn<Flight, String> flight_code_dep = new TableColumn<>("flight_code");
        flight_code_dep.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFlight_code()));

        TableColumn<Flight, String> dep_location_dep = new TableColumn<>("departure_location");
        dep_location_dep
                .setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDeparture_location()));

        TableColumn<Flight, String> arr_location_dep = new TableColumn<>("arrival_location");
        arr_location_dep.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getArrival_location()));

        TableColumn<Flight, String> date_dep = new TableColumn<>("date");
        date_dep.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getDate_time().get(Calendar.DATE) + "/"
                        + param.getValue().getDate_time().get(Calendar.MONTH) + "/"
                        + param.getValue().getDate_time().get(Calendar.YEAR)));

        TableColumn<Flight, String> time_dep = new TableColumn<>("time");
        time_dep.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getDate_time().get(Calendar.HOUR) + ":"
                        + param.getValue().getDate_time().get(Calendar.MINUTE) + " "
                        + param.getValue().getDate_time().get(Calendar.AM_PM)));

        TableColumn<Flight, String> available_seats_dep = new TableColumn<>("available_seats");
        available_seats_dep.setCellValueFactory(
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getAvailable_seats())));

        TableColumn<Flight, String> price_dep = new TableColumn<>("price");
        price_dep.setCellValueFactory(param -> new SimpleStringProperty(Double.toString(param.getValue().getPrice())));

        departures.getColumns().addAll(flight_code_dep, dep_location_dep, arr_location_dep, date_dep, time_dep,
                available_seats_dep, price_dep);

        TableView<Flight> arrivals = new TableView<>();
        HBox.setHgrow(arrivals, Priority.ALWAYS);
        arrivals.getItems().addAll(flights.get(1));

        TableColumn<Flight, String> flight_code_arr = new TableColumn<>("flight_code");
        flight_code_arr.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFlight_code()));

        TableColumn<Flight, String> dep_location_arr = new TableColumn<>("departure_location");
        dep_location_arr
                .setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDeparture_location()));

        TableColumn<Flight, String> arr_location_arr = new TableColumn<>("arrival_location");
        arr_location_arr.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getArrival_location()));

        TableColumn<Flight, String> date_arr = new TableColumn<>("date");
        date_arr.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getDate_time().get(Calendar.DATE) + "/"
                        + param.getValue().getDate_time().get(Calendar.MONTH) + "/"
                        + param.getValue().getDate_time().get(Calendar.YEAR)));

        TableColumn<Flight, String> time_arr = new TableColumn<>("time");
        time_arr.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getDate_time().get(Calendar.HOUR) + ":"
                        + param.getValue().getDate_time().get(Calendar.MINUTE) + " "
                        + param.getValue().getDate_time().get(Calendar.AM_PM)));

        TableColumn<Flight, String> available_seats_arr = new TableColumn<>("available_seats");
        available_seats_arr.setCellValueFactory(
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getAvailable_seats())));

        TableColumn<Flight, String> price_arr = new TableColumn<>("price");
        price_arr.setCellValueFactory(param -> new SimpleStringProperty(Double.toString(param.getValue().getPrice())));

        arrivals.getColumns().addAll(flight_code_arr, dep_location_arr, arr_location_arr, date_arr, time_arr,
                available_seats_arr, price_arr);

        HBox hbox = new HBox(departures, arrivals);
        hbox.setAlignment(Pos.CENTER);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        HBox hbox2 = new HBox(10);
        hbox2.setAlignment(Pos.CENTER);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        ComboBox<String> departures_to_book = new ComboBox<>();
        HBox.setHgrow(departures_to_book, Priority.ALWAYS);

        for (Flight flight : flights.get(0)) {
            departures_to_book.getItems().add(flight.getFlight_code());
        }

        departures_to_book.getSelectionModel().selectFirst();

        ComboBox<String> arrivals_to_book = new ComboBox<>();
        HBox.setHgrow(arrivals_to_book, Priority.ALWAYS);

        for (Flight flight : flights.get(1)) {
            arrivals_to_book.getItems().add(flight.getFlight_code());
        }

        arrivals_to_book.getSelectionModel().selectFirst();

        TextField passengers_to_be_booked = new TextField();
        passengers_to_be_booked.setPromptText("#");
        passengers_to_be_booked.setPrefWidth(60);

        Button book = new Button("Book flights");

        // book flights method
        book.setOnAction(e -> {
            try {
                if (client.book(departures_to_book.getSelectionModel().getSelectedItem(),arrivals_to_book.getSelectionModel().getSelectedItem(), Integer.parseInt(passengers_to_be_booked.getText()))) {
                    alert(AlertType.CONFIRMATION, "Success", "Flights booked successfully");
                } else {
                    alert(AlertType.ERROR, "Booking Failed :'(", "Please search again");
                }
            } catch (NumberFormatException e1) {
                alert(AlertType.WARNING, "Invalid arguments", e1.getMessage());
                return;
            } catch (RemoteException e1) {
                alert(AlertType.ERROR, "Network error", e1.getMessage());
                e1.printStackTrace();
                return;
            }
        });

        hbox2.getChildren().addAll(new Text("departure"), departures_to_book, new Text("arrival"), arrivals_to_book, new Text("passengers"), passengers_to_be_booked, book);
        vbox.getChildren().addAll(hbox, hbox2);
        this.stage.setHeight(300);
    }

    public void client_connect() {
        try {
            client = new Client();
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Connection Error!!");
            alert.setHeaderText(null);
            alert.setContentText(e.toString());
            // close program if connection to appServer fails
            alert.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(-1);
            });
            alert.show();
            e.printStackTrace();
        }
    }

    // alert generating method
    private void alert(AlertType type, String title, String context){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(context);
        alert.show();
    }
}