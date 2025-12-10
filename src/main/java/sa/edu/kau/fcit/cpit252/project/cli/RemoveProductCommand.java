package sa.edu.kau.fcit.cpit252.project.cli;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import sa.edu.kau.fcit.cpit252.project.DB.Database;
import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;

public class RemoveProductCommand implements Command {
    private final InventoryManager inventory;
    private final ProductRepository repo;
    private final Scanner scanner;

    public RemoveProductCommand(InventoryManager inventory, ProductRepository repo, Scanner scanner) {
        this.inventory = inventory;
        this.repo = repo;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws SQLException {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║     REMOVE PRODUCT                  ║");
        System.out.println("╚════════════════════════════════════╝");

        System.out.print("Product ID (barcode) to remove: ");
        String productId = scanner.nextLine().trim();

        Product p = findProductByBarcode(productId);
        if (p == null) {
            System.out.println("❌ Product not found");
            return;
        }

        System.out.print("Are you sure you want to delete \"" + p.name + "\"? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("❌ Operation cancelled");
            return;
        }

        try {
            Connection conn = Database.getDBConnection();
            repo.deleteProductByBarcode(productId, conn);
            inventory.removeProduct(p.id);
            System.out.println("✅ Product removed successfully: " + p.name);
        } catch (SQLException e) {
            System.err.println("DB error: " + e.getMessage());
        }
    }

    private Product findProductByBarcode(String barcode) {
        return inventory.allProducts().values().stream()
            .filter(p -> p.productId.equals(barcode))
            .findFirst()
            .orElse(null);
    }
}
