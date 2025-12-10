package sa.edu.kau.fcit.cpit252.project.observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sa.edu.kau.fcit.cpit252.project.domain.Product;

public class InventoryManager {
    private Map<Integer, Product> products;
    private List<InventoryObserver> observers;
    private LocalDate currentDate;

    public InventoryManager(LocalDate startDate) {
        this.products = new HashMap<>();
        this.observers = new ArrayList<>();
        this.currentDate = startDate;
    }

    public void subscribe(InventoryObserver obs) {
        observers.add(obs);
    }

    public void unsubscribe(InventoryObserver obs) {
        observers.remove(obs);
    }

    public void addProduct(Product p) {
        products.put(p.id, p);
    }

    public void removeProduct(int productId) {
        products.remove(productId);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public Map<Integer, Product> allProducts() {
        return new HashMap<>(products);
    }

    public void advanceDay() {
        currentDate = currentDate.plusDays(1);
        notifyDayAdvance();
    }

    public void advanceDays(int count) {
        for (int i = 0; i < count; i++) {
            advanceDay();
        }
    }

    public void applyDiscount(int productId, double percentage) {
        Product p = products.get(productId);
        if (p != null) {
            p.appliedDiscount = percentage;
            notifyDiscountApplied(p, percentage);
        }
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

  

    private void notifyDayAdvance() {
        int dayNum = (int) java.time.temporal.ChronoUnit.DAYS.between(
            LocalDate.of(2025, 1, 1), currentDate);
        observers.forEach(obs -> obs.onDayAdvance(dayNum, currentDate));
    }

    private void notifyDiscountApplied(Product p, double percentage) {
        observers.forEach(obs -> obs.onDiscountApplied(p, percentage));
    }
}

