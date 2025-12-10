package sa.edu.kau.fcit.cpit252.project;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;
import sa.edu.kau.fcit.cpit252.project.cli.DiscountCLI;
import sa.edu.kau.fcit.cpit252.project.ui.TerminalUI;
import sa.edu.kau.fcit.cpit252.project.DB.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class DiscountManagerApp {

    public static void main(String[] args) {
        try (Connection conn = Database.getDBConnection()) {
            ProductRepository repo = new ProductRepository();
            repo.initializeSchema(conn);

            InventoryManager inventory = new InventoryManager(LocalDate.of(2025, 1, 1));
            seedDemoData(inventory, repo, conn);

            TerminalUI ui = new TerminalUI();
            inventory.subscribe(ui);

            DiscountCLI cli = new DiscountCLI(inventory, repo);
            cli.start();

        } catch (SQLException e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void seedDemoData(InventoryManager mgr, ProductRepository repo, Connection conn)
            throws SQLException {
        Product[] demoProducts = {
            new Product(1, "Fresh Milk", "Dairy", 45, LocalDate.of(2025, 1, 4), 3.99),
            new Product(2, "Greek Yogurt", "Dairy", 30, LocalDate.of(2025, 1, 5), 5.49),
            new Product(3, "Whole Wheat Bread", "Bakery", 18, LocalDate.of(2025, 1, 3), 2.99),
            new Product(4, "Tomatoes", "Produce", 120, LocalDate.of(2025, 1, 10), 0.79),
            new Product(5, "Cheddar Cheese", "Dairy", 12, LocalDate.of(2025, 1, 15), 7.99),
        };

        for (Product p : demoProducts) {
            repo.saveProduct(p, conn);
            mgr.addProduct(p);
        }

        System.out.println("✓ Seeded 5 products into inventory");
    }
}

