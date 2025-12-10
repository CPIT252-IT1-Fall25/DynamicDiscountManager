package sa.edu.kau.fcit.cpit252.project.cli;

import java.sql.SQLException;
import java.util.Scanner;

import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;

/**
 * Main CLI router - delegates to individual command classes
 */
public class DiscountCLI {
    private final InventoryManager inventory;
    private final ProductRepository repo;
    private final Scanner scanner;

    public DiscountCLI(InventoryManager inventory, ProductRepository repo) {
        this.inventory = inventory;
        this.repo = repo;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║  DISCOUNT MANAGER v1.0.0            ║");
        System.out.println("║  Grocery Store Expiry Handler        ║");
        System.out.println("╚════════════════════════════════════╝\n");

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> executeCommand(new ViewProductsCommand(inventory));
                    case "2" -> executeCommand(new ReviewSuggestionsCommand(inventory, repo, scanner));
                    case "3" -> executeCommand(new ApplyCustomDiscountCommand(inventory, repo, scanner));
                    case "4" -> executeCommand(new AdvanceTimeCommand(inventory));
                    case "5" -> executeCommand(new AddProductCommand(inventory, repo, scanner));
                    case "6" -> executeCommand(new RemoveProductCommand(inventory, repo, scanner));
                    case "7" -> {
                        running = false;
                        System.out.println("Shutting down gracefully...");
                    }
                    default -> System.out.println("❌ Invalid choice");
                }
            } catch (SQLException e) {
                System.err.println("DB error: " + e.getMessage());
            }
        }
    }

    private void executeCommand(Command command) throws SQLException {
        command.execute();
    }

    private void showMenu() {
        System.out.println("\n┌─ MENU (" + inventory.getCurrentDate() + ") ─┐");
        System.out.println("│ [1] View all products");
        System.out.println("│ [2] Review discount suggestions");
        System.out.println("│ [3] Apply custom discount");
        System.out.println("│ [4] Advance time (simulate days)");
        System.out.println("│ [5] Add new product");
        System.out.println("│ [6] Remove product");
        System.out.println("│ [7] Exit");
        System.out.print("└─ Choice: ");
    }
}

