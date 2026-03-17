import java.util.*;

// ================= MAIN CLASS =================
public class UC9{

    // ================= CUSTOM EXCEPTION =================
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // ================= RESERVATION =================
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    // ================= INVENTORY =================
    static class RoomInventory {
        private Map<String, Integer> roomAvailability;

        public RoomInventory() {
            roomAvailability = new HashMap<>();
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getRoomAvailability() {
            return roomAvailability;
        }
    }

    // ================= BOOKING QUEUE =================
    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.offer(r);
        }
    }

    // ================= VALIDATOR =================
    static class ReservationValidator {

        public void validate(
                String guestName,
                String roomType,
                RoomInventory inventory
        ) throws InvalidBookingException {

            // Check guest name
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            // Check valid room type (case sensitive)
            if (!inventory.getRoomAvailability().containsKey(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }

            // Check availability
            if (inventory.getRoomAvailability().get(roomType) <= 0) {
                throw new InvalidBookingException("Selected room type is not available.");
            }
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Take input
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validate input
            validator.validate(name, roomType, inventory);

            // If valid → add booking
            bookingQueue.addRequest(new Reservation(name, roomType));

            System.out.println("Booking request accepted!");

        } catch (InvalidBookingException e) {
            // Handle validation error
            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}