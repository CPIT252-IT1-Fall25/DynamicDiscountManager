package sa.edu.kau.fcit.cpit252.project;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.discount.DiscountCalculator;
import sa.edu.kau.fcit.cpit252.project.ui.TerminalUI;
import java.time.LocalDate;

public class DiscountManagerDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║  DISCOUNT MANAGER - DEMO SCRIPT     ║");
        System.out.println("║  Observer Pattern + Time Simulation  ║");
        System.out.println("╚════════════════════════════════════╝\n");

        InventoryManager inventory = new InventoryManager(LocalDate.of(2025, 1, 1));
        TerminalUI ui = new TerminalUI();
        inventory.subscribe(ui);

        demoPart1_InitialSetup(inventory);
        demoPart2_AdvanceTimeWithObservation(inventory);
        demoPart3_ApplyDiscounts(inventory);
        demoPart4_CriticalExpiry(inventory);
    }

    private static void demoPart1_InitialSetup(InventoryManager mgr) {
        ui.printBanner("PART 1: Load Inventory (Jan 1)");

        mgr.addProduct(new Product(101, "Milk (expires Jan 4)", "Dairy", 45, LocalDate.of(2025, 1, 4), 3.99));
        mgr.addProduct(new Product(102, "Bread (expires Jan 3)", "Bakery", 18, LocalDate.of(2025, 1, 3), 2.99));
        mgr.addProduct(new Product(103, "Yogurt (expires Jan 5)", "Dairy", 30, LocalDate.of(2025, 1, 5), 5.49));
        mgr.addProduct(new Product(104, "Tomatoes (expires Jan 10)", "Produce", 120, LocalDate.of(2025, 1, 10), 0.79));

        System.out.println("\nInitial inventory:");
        mgr.allProducts().values().forEach(p ->
            System.out.printf("  ID %d | %s | Stock: %d | Expires: %s%n", p.id, p.name, p.stock, p.expiryDate)
        );
    }

    private static void demoPart2_AdvanceTimeWithObservation(InventoryManager mgr) throws InterruptedException {
        ui.printBanner("PART 2: Advance Time (Jan 1 → Jan 3) - Watch for alerts");

        for (int i = 0; i < 2; i++) {
            mgr.advanceDay();
            Thread.sleep(800);
        }
        System.out.println("\n✓ Progressed to Jan 3");
    }

    private static void demoPart3_ApplyDiscounts(InventoryManager mgr) {
        ui.printBanner("PART 3: Discount Suggestions");

        mgr.allProducts().values().forEach(p -> {
            var sug = DiscountCalculator.suggest(p, mgr.getCurrentDate());
            System.out.println("  Suggestion: " + sug);
        });

        System.out.println("\nApplying suggested discounts...");
        mgr.getProduct(101).appliedDiscount = 30.0;
        mgr.getProduct(102).appliedDiscount = 50.0;
        System.out.println("✓ Discounts applied (would persist via db_builder)");
    }

    private static void demoPart4_CriticalExpiry(InventoryManager mgr) throws InterruptedException {
        ui.printBanner("PART 4: Rapid Expiry Simulation (Jan 3 → Jan 5)");

        System.out.println("Advancing 2 more days... (expect high-risk alerts)");
        for (int i = 0; i < 2; i++) {
            mgr.advanceDay();
            Thread.sleep(600);
        }

        System.out.println("\n\nFinal inventory state:");
        mgr.allProducts().values().forEach(p -> {
            int daysLeft = p.daysUntilExpiry(mgr.getCurrentDate());
            String status = daysLeft < 0 ? "💀 EXPIRED" : daysLeft <= 3 ? "🔴 CRITICAL" : "🟢 OK";
            System.out.printf("  %s | %s | %.2f → %.2f (-%.1f%%)%n",
                status, p.name, p.basePrice, p.getFinalPrice(), p.appliedDiscount);
        });

        System.out.println("\n✓ Demo complete!");
    }

    private static TerminalUI ui = new TerminalUI();
}
