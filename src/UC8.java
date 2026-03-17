import java.util.*;
public class UC8{

    // ================= RESERVATION =================
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // ================= BOOKING HISTORY =================
    static class BookingHistory {

        private List<Reservation> confirmedReservations;

        public BookingHistory() {
            confirmedReservations = new ArrayList<>();
        }

        // Add reservation
        public void addReservation(Reservation reservation) {
            confirmedReservations.add(reservation);
        }

        // Get all reservations
        public List<Reservation> getConfirmedReservations() {
            return confirmedReservations;
        }
    }

    // ================= REPORT SERVICE =================
    static class BookingReportService {

        public void generateReport(BookingHistory history) {

            System.out.println("\nBooking History Report");

            for (Reservation r : history.getConfirmedReservations()) {
                System.out.println("Guest: " + r.getGuestName()
                        + ", Room Type: " + r.getRoomType());
            }
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("Booking History and Reporting");

        // Create history
        BookingHistory history = new BookingHistory();

        // Add confirmed bookings
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}