package sa.edu.kau.fcit.cpit252.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import sa.edu.kau.fcit.cpit252.project.DB.Database;
import sa.edu.kau.fcit.cpit252.project.cli.DiscountCLI;
import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.observer.TerminalUI;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;

public class DiscountManagerApp {

    public static void main(String[] args) {
        try (Connection conn = Database.getDBConnection()) {
            ProductRepository repo = new ProductRepository();
            repo.initializeSchema(conn);

            // Start date set to December 2025 to demonstrate discount rules
            // (many products in the database expire around this time)
            InventoryManager inventory = new InventoryManager(LocalDate.of(2025, 12, 10));
            loadProductsFromDatabase(inventory, repo, conn);

            TerminalUI ui = new TerminalUI();
            inventory.subscribe(ui);

            DiscountCLI cli = new DiscountCLI(inventory, repo);
            cli.start();

        } catch (SQLException e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadProductsFromDatabase(InventoryManager mgr, ProductRepository repo, Connection conn)
            throws SQLException {
        java.util.List<Product> products = repo.loadAllProducts(conn);
        
        
        if (!products.isEmpty()) {
            for (Product p : products) {
                mgr.addProduct(p);
            }
            System.out.println("✓ Loaded " + products.size() + " products");
        }
    }

    
}

