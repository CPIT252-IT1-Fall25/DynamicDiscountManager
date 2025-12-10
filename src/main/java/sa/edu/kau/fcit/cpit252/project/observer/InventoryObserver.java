package sa.edu.kau.fcit.cpit252.project.observer;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public interface InventoryObserver {
    void onHighRiskDetected(Product p, LocalDate today);
    void onDiscountApplied(Product p, double percentage);
    void onDayAdvance(int newDay, LocalDate date);
}

