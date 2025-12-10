package sa.edu.kau.fcit.cpit252.project.cli;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import sa.edu.kau.fcit.cpit252.project.DB.Database;
import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;

public class AddProductCommand implements Command {
    private final InventoryManager inventory;
    private final ProductRepository repo;
    private final Scanner scanner;

    public AddProductCommand(InventoryManager inventory, ProductRepository repo, Scanner scanner) {
        this.inventory = inventory;
        this.repo = repo;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws SQLException {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     ADD NEW PRODUCT                 ║");
        System.out.println("╚════════════════════════════════════╝");

        System.out.print("Product ID (barcode): ");
        String productId = scanner.nextLine().trim();
        if (productId.isEmpty()) {
            System.out.println("❌ Product ID cannot be empty");
            return;
        }

        System.out.print("Product name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Product name cannot be empty");
            return;
        }

        System.out.print("Category: ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) {
            System.out.println("❌ Category cannot be empty");
            return;
        }

        System.out.print("Stock quantity: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());
        if (stock < 0) {
            System.out.println("❌ Stock cannot be negative");
            return;
        }

        System.out.print("Expiry date (YYYY-MM-DD): ");
        LocalDate expiryDate = LocalDate.parse(scanner.nextLine().trim());

        System.out.print("Base price: ");
        double basePrice = Double.parseDouble(scanner.nextLine().trim());
        if (basePrice < 0) {
            System.out.println("❌ Price cannot be negative");
            return;
        }

        try {
            Connection conn = Database.getDBConnection();
            int generatedId = repo.saveProductAndGetId(productId, name, category, stock, expiryDate, basePrice, conn);
            Product newProduct = new Product(generatedId, productId, name, category, stock, expiryDate, basePrice);
            inventory.addProduct(newProduct);
            System.out.println("✅ Product added successfully: " + name);
        } catch (SQLException e) {
            System.err.println("DB error: " + e.getMessage());
        }
    }
}
