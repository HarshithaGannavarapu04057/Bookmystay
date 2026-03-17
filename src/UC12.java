import java.io.*;
import java.util.*;

// ================= MAIN CLASS =================
public class UC12{

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

    // ================= FILE PERSISTENCE =================
    static class FilePersistenceService {

        // Save inventory to file
        public void saveInventory(RoomInventory inventory, String filePath) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }

                System.out.println("Inventory saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

        // Load inventory from file
        public void loadInventory(RoomInventory inventory, String filePath) {

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;
                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("=");

                    if (parts.length == 2) {
                        String roomType = parts[0];
                        int count = Integer.parseInt(parts[1]);

                        inventory.updateAvailability(roomType, count);
                    }
                }

            } catch (IOException e) {
                System.out.println("Error loading inventory.");
            }
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        // Load inventory (if file exists)
        service.loadInventory(inventory, filePath);

        // Display current inventory
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Save inventory
        service.saveInventory(inventory, filePath);
    }
}