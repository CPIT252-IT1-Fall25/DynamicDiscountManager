package sa.edu.kau.fcit.cpit252.project.cli;

import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;

public class ViewProductsCommand implements Command {
    private final InventoryManager inventory;

    public ViewProductsCommand(InventoryManager inventory) {
        this.inventory = inventory;
    }

    @Override
    public void execute() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ PRODUCT ID      │ NAME                              │ CATEGORY      │ PRICE   │ FINAL   │ STOCK ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════╣");
        inventory.allProducts().values().forEach(p -> {
            String name = p.name.length() > 30 ? p.name.substring(0, 30) : String.format("%-30s", p.name);
            String category = String.format("%-13s", p.category);
            System.out.printf("║ %-15s │ %-32s │ %s │ %6.2f │ %6.2f │ %5d ║%n",
                p.productId, name, category, p.basePrice, p.getFinalPrice(), p.stock);
        });
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
