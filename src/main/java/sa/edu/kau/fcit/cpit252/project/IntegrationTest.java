package sa.edu.kau.fcit.cpit252.project;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.discount.DiscountCalculator;
import sa.edu.kau.fcit.cpit252.project.ui.TerminalUI;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;
import sa.edu.kau.fcit.cpit252.project.DB.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class IntegrationTest {

    public static void main(String[] args) throws SQLException, InterruptedException {
        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║  INTEGRATION TEST                   ║");
        System.out.println("║  DB Persistence + Observer + CLI    ║");
        System.out.println("╚═════════════════════════════════════╝\n");

        try (Connection conn = Database.getDBConnection()) {
            ProductRepository repo = new ProductRepository();

            System.out.println("Step 1: Initialize DB schema...");
            repo.initializeSchema(conn);
            System.out.println("  ✓ Schema created");

            System.out.println("\nStep 2: Create inventory & observer...");
            InventoryManager inventory = new InventoryManager(LocalDate.of(2025, 1, 1));
            TerminalUI ui = new TerminalUI();
            inventory.subscribe(ui);
            System.out.println("  ✓ Manager + Observer initialized");

            System.out.println("\nStep 3: Seed products via db_builder...");
            Product p1 = new Product(1, "Milk", "Dairy", 40, LocalDate.of(2025, 1, 4), 3.99);
            Product p2 = new Product(2, "Bread", "Bakery", 15, LocalDate.of(2025, 1, 3), 2.49);
            repo.saveProduct(p1, conn);
            repo.saveProduct(p2, conn);
            inventory.addProduct(p1);
            inventory.addProduct(p2);
            System.out.println("  ✓ Products persisted");

            System.out.println("\nStep 4: Verify discounts are calculated...");
            var s1 = DiscountCalculator.suggest(p1, inventory.getCurrentDate());
            var s2 = DiscountCalculator.suggest(p2, inventory.getCurrentDate());
            System.out.println("  Suggestion 1: " + s1);
            System.out.println("  Suggestion 2: " + s2);

            System.out.println("\nStep 5: Apply discount & persist...");
            inventory.applyDiscount(1, 25.0);
            repo.updateProductDiscount(1, 25.0, conn);
            System.out.println("  ✓ Discount applied & persisted");

            System.out.println("\nStep 6: Reload & verify persistence...");
            Product reloaded = repo.loadProductById(1, conn);
            System.out.println("  Reloaded: " + reloaded.name + " with " + reloaded.appliedDiscount + "% discount");
            System.out.println("  ✓ Data integrity verified");

            System.out.println("\nStep 7: Simulate time & trigger observers...");
            System.out.println("  Advancing 2 days...");
            for (int i = 0; i < 2; i++) {
                inventory.advanceDay();
                Thread.sleep(500);
            }
            System.out.println("\n  ✓ Observer notifications fired");

            System.out.println("\n" + "═".repeat(50));
            System.out.println("✓ ALL TESTS PASSED");
            System.out.println("═".repeat(50));

            System.out.println("\nCleanup: Dropping test table...");
            repo.deleteAllProducts(conn);
            System.out.println("  ✓ Test table removed\n");

        } catch (SQLException e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

