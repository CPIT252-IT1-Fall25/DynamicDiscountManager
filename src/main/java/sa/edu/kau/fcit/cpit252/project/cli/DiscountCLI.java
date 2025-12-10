package sa.edu.kau.fcit.cpit252.project.cli;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.discount.DiscountCalculator;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;
import sa.edu.kau.fcit.cpit252.project.DB.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DiscountCLI {
    private InventoryManager inventory;
    private ProductRepository repo;
    private Scanner scanner;

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
                    case "1" -> viewProducts();
                    case "2" -> reviewSuggestions();
                    case "3" -> applyCustomDiscount();
                    case "4" -> advanceTime();
                    case "5" -> {
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

    private void showMenu() {
        System.out.println("\n┌─ MENU (" + inventory.getCurrentDate() + ") ─┐");
        System.out.println("│ [1] View all products");
        System.out.println("│ [2] Review discount suggestions");
        System.out.println("│ [3] Apply custom discount");
        System.out.println("│ [4] Advance time (simulate days)");
        System.out.println("│ [5] Exit");
        System.out.print("└─ Choice: ");
    }

    private void viewProducts() {
        System.out.println("\n╔════ INVENTORY ════╗");
        inventory.allProducts().values().forEach(p -> {
            int days = p.daysUntilExpiry(inventory.getCurrentDate());
            String status = days < 0 ? "🔴 EXPIRED" : days <= 3 ? "🟠 DANGER" : "🟢 OK";
            System.out.printf("  %s | %s | Stock:%d | Exp:%s | Price:%.2f → %.2f (%.1f%%)%n",
                status, p.name, p.stock, p.expiryDate, p.basePrice, p.getFinalPrice(), p.appliedDiscount);
        });
        System.out.println("╚═══════════════════╝");
    }

    private void reviewSuggestions() {
        System.out.println("\n╔════ SUGGESTIONS ════╗");
        inventory.allProducts().values().forEach(p -> {
            var suggestion = DiscountCalculator.suggest(p, inventory.getCurrentDate());
            System.out.println("  → " + suggestion);
        });
        System.out.println("╚═════════════════════╝");

        System.out.print("\nApply suggestion? (product_id or skip): ");
        String input = scanner.nextLine().trim();
        if (!input.equalsIgnoreCase("skip") && !input.isEmpty()) {
            try {
                int pid = Integer.parseInt(input);
                Product p = inventory.getProduct(pid);
                if (p != null) {
                    var sug = DiscountCalculator.suggest(p, inventory.getCurrentDate());
                    inventory.applyDiscount(pid, sug.suggestedDiscount);
                    persistChanges(pid);
                    System.out.println("✅ Applied " + sug.suggestedDiscount + "% discount");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid ID");
            } catch (SQLException e) {
                System.err.println("DB error: " + e.getMessage());
            }
        }
    }

    private void applyCustomDiscount() throws SQLException {
        System.out.print("\nProduct ID: ");
        int pid = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Discount %: ");
        double disc = Double.parseDouble(scanner.nextLine().trim());

        if (disc < 0 || disc > 100) {
            System.out.println("❌ Invalid percentage (0-100)");
            return;
        }

        Product p = inventory.getProduct(pid);
        if (p != null) {
            inventory.applyDiscount(pid, disc);
            persistChanges(pid);
            System.out.println("✅ Custom discount applied!");
        } else {
            System.out.println("❌ Product not found");
        }
    }

    private void advanceTime() {
        System.out.print("\nDays to advance: ");
        int days = Integer.parseInt(scanner.nextLine().trim());
        inventory.advanceDays(days);
        System.out.println("⏩ Simulated " + days + " days forward");
    }

    private void persistChanges(int productId) throws SQLException {
        try (Connection conn = Database.getDBConnection()) {
            Product p = inventory.getProduct(productId);
            if (p != null) {
                repo.updateProductDiscount(productId, p.appliedDiscount, conn);
            }
        }
    }
}
