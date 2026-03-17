import java.util.*;

// ================= MAIN CLASS =================
public class UC10 {

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

        public void updateAvailability(String roomType, int count) {
            roomAvailability.put(roomType, count);
        }
    }

    // ================= CANCELLATION SERVICE =================
    static class CancellationService {

        // Stack to store recently cancelled reservations
        private Stack<String> releasedRoomIds;

        // Map<ReservationId, RoomType>
        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationRoomTypeMap = new HashMap<>();
        }

        // Register booking
        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        // Cancel booking
        public void cancelBooking(String reservationId, RoomInventory inventory) {

            if (!reservationRoomTypeMap.containsKey(reservationId)) {
                System.out.println("Invalid reservation ID.");
                return;
            }

            String roomType = reservationRoomTypeMap.get(reservationId);

            // Restore inventory
            int current = inventory.getRoomAvailability().get(roomType);
            inventory.updateAvailability(roomType, current + 1);

            // Push to stack (rollback history)
            releasedRoomIds.push(reservationId);

            // Remove from map
            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }

        // Show rollback history
        public void showRollbackHistory() {

            System.out.println("\nRollback History (Most Recent First):");

            while (!releasedRoomIds.isEmpty()) {
                System.out.println("Released Reservation ID: " + releasedRoomIds.pop());
            }
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Simulate confirmed booking
        String reservationId = "Single-1";
        service.registerBooking(reservationId, "Single");

        // Cancel booking
        service.cancelBooking(reservationId, inventory);

        // Show rollback history
        service.showRollbackHistory();

        // Show updated inventory
        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getRoomAvailability().get("Single"));
    }
}