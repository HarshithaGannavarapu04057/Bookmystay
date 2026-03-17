import java.util.*;
public class UC6 {
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
    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();
        public void addRequest(Reservation r) {
            queue.offer(r);
        }
        public Reservation getNextRequest() {
            return queue.poll();
        }
        public boolean hasPendingRequests() {
            return !queue.isEmpty();
        }
    }
    static class RoomAllocationService {
        private Set<String> allocatedRoomIds;
        private Map<String, Set<String>> assignedRoomsByType;
        public RoomAllocationService() {
            allocatedRoomIds = new HashSet<>();
            assignedRoomsByType = new HashMap<>();
        }
        public void allocateRoom(Reservation reservation, RoomInventory inventory) {
            String roomType = reservation.getRoomType();
            Map<String, Integer> availability = inventory.getRoomAvailability();
            if (availability.get(roomType) > 0) {
                String roomId = generateRoomId(roomType);
                allocatedRoomIds.add(roomId);
                assignedRoomsByType
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);
                inventory.updateAvailability(roomType, availability.get(roomType) - 1);
                System.out.println("Booking confirmed for Guest: "
                        + reservation.getGuestName()
                        + ", Room ID: "
                        + roomId);

            } else {
                System.out.println("No rooms available for " + reservation.getGuestName());
            }
        }

        private String generateRoomId(String roomType) {

            int count = assignedRoomsByType
                    .getOrDefault(roomType, new HashSet<>())
                    .size() + 1;

            return roomType + "-" + count;
        }
    }
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing");

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService service = new RoomAllocationService();

        // Add booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Process queue (FIFO)
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            service.allocateRoom(r, inventory);
        }
    }
}