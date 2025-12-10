package sa.edu.kau.fcit.cpit252.project.ui;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryObserver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TerminalUI implements InventoryObserver {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MMM dd");
    private static final String[] SPINNER = {"⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏"};
    private int spinIdx = 0;

    @Override
    public void onHighRiskDetected(Product p, LocalDate today) {
        int days = p.daysUntilExpiry(today);
        String icon = days < 0 ? "💀" : days <= 1 ? "🔴" : "🟠";
        System.out.printf("\n⚠️  [%s] %s is HIGH-RISK! %d days left, %d units in stock%n",
            icon, p.name, days, p.stock);
    }

    @Override
    public void onDiscountApplied(Product p, double percentage) {
        System.out.printf("  ✓ Discount: %s → %.1f%% (now: %.2f)%n",
            p.name, percentage, p.getFinalPrice());
    }

    @Override
    public void onDayAdvance(int newDay, LocalDate date) {
        String spinner = SPINNER[spinIdx++ % SPINNER.length];
        System.out.printf("\r%s Day %d (%s)          ", spinner, newDay, date.format(FMT));
        System.out.flush();
    }

    public void printBanner(String text) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("  " + text);
        System.out.println("═".repeat(50));
    }
}
