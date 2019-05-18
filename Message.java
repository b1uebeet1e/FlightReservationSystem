import java.io.Serializable;
import java.util.Calendar;

/**
 * Message
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 8057478671149855300L;
    private String message;
    private String departure_flight_code;
    private String arrival_flight_code;
    private int passengers;
    private Calendar departure_date;
    private String departure_location;
    private Calendar arrival_date;
    private String arrival_location;

    // Constructor for CHECK message
    public Message(Calendar departure_date, String departure_location, Calendar arrival_date, String arrival_location, int passengers) {
        this.message = "CHECK";
        this.departure_flight_code = null;
        this.arrival_flight_code = null;
        this.passengers = passengers;
        this.departure_date = departure_date;
        this.departure_location = departure_location;
        this.arrival_date = arrival_date;
        this.arrival_location = arrival_location;
    }

    // Constructor for BOOK message
    public Message(String departure_flight_code, String arrival_flight_code, int passengers) {
        this.message = "BOOK";
        this.departure_flight_code = departure_flight_code;
        this.arrival_flight_code = arrival_flight_code;
        this.passengers = passengers;
        this.departure_date = null;
        this.departure_location = null;
        this.arrival_date = null;
        this.arrival_location = null;
    }

    public String getMessage() {
        return message;
    }

    public String getDeparture_flight_code() {
        return departure_flight_code;
    }

    public String getArrival_flight_code() {
        return arrival_flight_code;
    }

    public int getPassengers() {
        return passengers;
    }

    public Calendar getDeparture_date() {
        return departure_date;
    }

    public String getDeparture_location() {
        return departure_location;
    }

    public Calendar getArrival_date() {
        return arrival_date;
    }

    public String getArrival_location() {
        return arrival_location;
    }
}