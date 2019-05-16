import java.io.Serializable;
import java.util.Calendar;

/**
 * Flight
 */
public class Flight implements Serializable {

    private static final long serialVersionUID = -6239765691663793790L;

    private Calendar date_time;

    private String departure_location;

    private String arrival_location;

    private String flight_code;

    private int total_seats;

    private int booked_seats;

    private double price;

    public Flight(Calendar date_time, String departure_location, String arrival_location, String flight_code, int total_seats, int booked_seats, double price){
        this.date_time = date_time;
        this.departure_location = departure_location;
        this.arrival_location = arrival_location;
        this.flight_code = flight_code;
        this.total_seats = total_seats;
        this.booked_seats = booked_seats;
        this.price = price;
    }

    public Calendar getDate_time() {
        return date_time;
    }

    public String getDeparture_location() {
        return departure_location;
    }

    public String getArrival_location() {
        return arrival_location;
    }

    public String getFlight_code() {
        return flight_code;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public int getBooked_seats() {
        return booked_seats;
    }

    public int getAvailable_seats() {
        return total_seats - booked_seats;
    }

    public double getPrice() {
        return price;
    }

    public void book_seats(int seats_to_be_booked) {
        booked_seats += seats_to_be_booked;
    }
    
}