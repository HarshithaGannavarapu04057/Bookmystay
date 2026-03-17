import java.util.*;

// ================= MAIN CLASS =================
public class UC7{

    // ================= ADD-ON SERVICE =================
    static class AddOnService {

        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    // ================= SERVICE MANAGER =================
    static class AddOnServiceManager {

        // Map<ReservationId, List of Services>
        private Map<String, List<AddOnService>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        // Add service to reservation
        public void addService(String reservationId, AddOnService service) {

            servicesByReservation
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);
        }

        // Calculate total cost
        public double calculateTotalServiceCost(String reservationId) {

            List<AddOnService> services =
                    servicesByReservation.getOrDefault(reservationId, new ArrayList<>());

            double total = 0;

            for (AddOnService s : services) {
                total += s.getCost();
            }

            return total;
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        // Example reservation ID (from allocation)
        String reservationId = "Single-1";

        // Create manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservationId, new AddOnService("Breakfast", 500.0));
        manager.addService(reservationId, new AddOnService("Airport Pickup", 800.0));
        manager.addService(reservationId, new AddOnService("WiFi", 260.0));

        // Calculate total
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        // Display result
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}