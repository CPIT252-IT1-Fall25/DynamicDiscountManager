package sa.edu.kau.fcit.cpit252.project.domain;

import java.time.LocalDate;

public class Product {
    public final int id;  // Auto-generated database ID
    public final String productId;  // Actual product barcode/ID
    public final String name;
    public final String category;
    public int stock;
    public LocalDate expiryDate;
    public double basePrice;
    public double appliedDiscount;

    public Product(int id, String productId, String name, String category, int stock, LocalDate expiryDate, double basePrice) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.expiryDate = expiryDate;
        this.basePrice = basePrice;
        this.appliedDiscount = 0.0;
    }

    // Legacy constructor for backward compatibility
    public Product(int id, String name, String category, int stock, LocalDate expiryDate, double basePrice) {
        this(id, "", name, category, stock, expiryDate, basePrice);
    }

    public int daysUntilExpiry(LocalDate today) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(today, expiryDate);
    }

    public double getFinalPrice() {
        return basePrice * (1 - appliedDiscount / 100.0);
    }

    public boolean isHighRisk(LocalDate today) {
        return daysUntilExpiry(today) <= 3 && stock > 5;
    }
}

