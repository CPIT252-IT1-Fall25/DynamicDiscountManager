package sa.edu.kau.fcit.cpit252.project.cli;

import java.sql.SQLException;
import java.util.Scanner;

import sa.edu.kau.fcit.cpit252.project.DB.Database;
import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;

public class ApplyCustomDiscountCommand implements Command {
    private final InventoryManager inventory;
    private final ProductRepository repo;
    private final Scanner scanner;

    public ApplyCustomDiscountCommand(InventoryManager inventory, ProductRepository repo, Scanner scanner) {
        this.inventory = inventory;
        this.repo = repo;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws SQLException {
        System.out.print("\nProduct barcode/ID: ");
        String productId = scanner.nextLine().trim();
        
        System.out.print("Discount %: ");
        double disc = Double.parseDouble(scanner.nextLine().trim());

        if (disc < 0 || disc > 100) {
            System.out.println("❌ Invalid percentage (0-100)");
            return;
        }

        Product p = findProductByBarcode(productId);
        if (p != null) {
            inventory.applyDiscount(p.id, disc);
            persistChanges(p.id);
            System.out.println("✅ Custom discount of " + disc + "% applied to " + p.name);
        } else {
            System.out.println("❌ Product ID not found");
        }
    }

    private Product findProductByBarcode(String barcode) {
        return inventory.allProducts().values().stream()
            .filter(p -> p.productId.equals(barcode))
            .findFirst()
            .orElse(null);
    }

    private void persistChanges(int productId) throws SQLException {
        var conn = Database.getDBConnection();
        Product p = inventory.getProduct(productId);
        if (p != null) {
            repo.updateProductDiscount(productId, p.appliedDiscount, conn);
        }
    }
}
