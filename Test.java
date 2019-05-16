import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Test
 */
public class Test {

    public static void main(String[] args) {
        Calendar cal1 = new GregorianCalendar(2019, 5, 19, 23, 59);
        Calendar cal2 = new GregorianCalendar(2018, 5, 20, 00, 00);
        System.out.println(cal1.get(Calendar.YEAR)); // YEAR
        System.out.println(cal1.get(Calendar.MONTH)); // MONTH
        System.out.println(cal1.get(Calendar.DATE));
        System.out.println(cal1.get(Calendar.DAY_OF_YEAR));
        System.out.println(cal2.get(Calendar.DAY_OF_YEAR));



    }
}