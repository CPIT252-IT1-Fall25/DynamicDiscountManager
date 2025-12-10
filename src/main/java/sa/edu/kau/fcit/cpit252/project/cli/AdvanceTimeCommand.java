package sa.edu.kau.fcit.cpit252.project.cli;

import java.time.LocalDate;

import sa.edu.kau.fcit.cpit252.project.discount.DiscountCalculator;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;

public class AdvanceTimeCommand implements Command {
    private final InventoryManager inventory;

    public AdvanceTimeCommand(InventoryManager inventory) {
        this.inventory = inventory;
    }

    @Override
    public void execute() {
        System.out.print("\nHow many days to simulate? ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int totalDays = Integer.parseInt(scanner.nextLine().trim());
        
        if (totalDays <= 0) {
            System.out.println("❌ Please enter a positive number");
            return;
        }
        
        System.out.println("\n⏳ Simulating days... Press Ctrl+C to stop\n");
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ DATE       │ PRODUCT ID      │ DISCOUNT % │ REASON        │ BASE PRICE │ FINAL PRICE ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════╣");
        
        for (int day = 1; day <= totalDays; day++) {
            inventory.advanceDays(1);
            LocalDate currentDate = inventory.getCurrentDate();
            
            // Find products with non-zero discount recommendations
            inventory.allProducts().values().forEach(p -> {
                var suggestion = DiscountCalculator.suggest(p, currentDate);
                
                // Only display if discount is suggested AND product hasn't expired
                if (suggestion.suggestedDiscount > 0 && p.daysUntilExpiry(currentDate) >= 0) {
                    String reason = suggestion.reason.length() > 12 ? 
                        suggestion.reason.substring(0, 12) : 
                        String.format("%-12s", suggestion.reason);
                    double finalPrice = p.basePrice * (1 - suggestion.suggestedDiscount / 100.0);
                    
                    System.out.printf("║ %s │ %-15s │ %8.1f%% │ %-13s │ %9.2f │ %10.2f ║%n",
                        currentDate,
                        p.productId,
                        suggestion.suggestedDiscount,
                        reason,
                        p.basePrice,
                        finalPrice);
                }
            });
            
            // Simulate delay (0.5 seconds per day)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("║ Simulation stopped by user                                                           ║");
                break;
            }
        }
        
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("✅ Simulation complete. Current date: " + inventory.getCurrentDate());
    }
}
