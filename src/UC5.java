import java.util.LinkedList;
import java.util.Queue;
public class UC5  {
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

    // ================= BOOKING QUEUE =================
    static class BookingRequestQueue {

        private Queue<Reservation> requestQueue;

        public BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        // Add request
        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }

        // Get next request (FIFO)
        public Reservation getNextRequest() {
            return requestQueue.poll();
        }

        // Check if queue is not empty
        public boolean hasPendingRequests() {
            return !requestQueue.isEmpty();
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("Booking Request Queue");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: "
                    + r.getGuestName()
                    + ", Room Type: "
                    + r.getRoomType());
        }
    }
}