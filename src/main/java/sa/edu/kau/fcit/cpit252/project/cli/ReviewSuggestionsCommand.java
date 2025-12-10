package sa.edu.kau.fcit.cpit252.project.cli;

import java.sql.SQLException;
import java.util.Scanner;

import sa.edu.kau.fcit.cpit252.project.DB.Database;
import sa.edu.kau.fcit.cpit252.project.discount.DiscountCalculator;
import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;
import sa.edu.kau.fcit.cpit252.project.persistence.ProductRepository;

public class ReviewSuggestionsCommand implements Command {
    private final InventoryManager inventory;
    private final ProductRepository repo;
    private final Scanner scanner;

    public ReviewSuggestionsCommand(InventoryManager inventory, ProductRepository repo, Scanner scanner) {
        this.inventory = inventory;
        this.repo = repo;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws SQLException {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ PRODUCT ID      │ DISCOUNT % │ REASON        │ BASE PRICE │ FINAL PRICE │ DAYS LEFT ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════╣");
        
        inventory.allProducts().values().stream()
            .filter(p -> {
                var suggestion = DiscountCalculator.suggest(p, inventory.getCurrentDate());
                return suggestion.suggestedDiscount > 0;
            })
            .forEach(p -> {
                var suggestion = DiscountCalculator.suggest(p, inventory.getCurrentDate());
                String reason = suggestion.reason.length() > 12 ? suggestion.reason.substring(0, 12) : 
                           String.format("%-12s", suggestion.reason);
                double finalPrice = p.basePrice * (1 - suggestion.suggestedDiscount / 100.0);
                System.out.printf("║ %-15s │ %8.1f%% │ %-13s │ %9.2f │ %10.2f │ %8d ║%n",
                    p.productId, suggestion.suggestedDiscount, reason, p.basePrice, finalPrice, suggestion.daysLeft);
            });
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.print("\nApply discount by product ID (or 'skip'): ");
        String input = scanner.nextLine().trim();
        
        if (input.equalsIgnoreCase("skip") || input.isEmpty()) {
            return;
        }
        
        try {
            Product p = findProductByBarcode(input);
            if (p == null) {
                System.out.println("❌ Product ID not found");
                return;
            }
            
            var sug = DiscountCalculator.suggest(p, inventory.getCurrentDate());
            System.out.println("\n[1] Apply suggested " + sug.suggestedDiscount + "%");
            System.out.println("[2] Apply custom discount");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            
            double discount = 0;
            if (choice.equals("1")) {
                discount = sug.suggestedDiscount;
            } else if (choice.equals("2")) {
                System.out.print("Enter discount %: ");
                discount = Double.parseDouble(scanner.nextLine().trim());
                if (discount < 0 || discount > 100) {
                    System.out.println("❌ Invalid percentage (0-100)");
                    return;
                }
            } else {
                System.out.println("❌ Invalid choice");
                return;
            }
            
            inventory.applyDiscount(p.id, discount);
            persistChanges(p.id);
            System.out.println("✅ Applied " + discount + "% discount to " + p.name);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input");
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
