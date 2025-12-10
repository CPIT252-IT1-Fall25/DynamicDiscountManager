package sa.edu.kau.fcit.cpit252.project.observer;

import java.time.LocalDate;

import sa.edu.kau.fcit.cpit252.project.domain.Product;

public interface InventoryObserver {
    void onDiscountApplied(Product p, double percentage);
    void onDayAdvance(int newDay, LocalDate date);
}

