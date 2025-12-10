package sa.edu.kau.fcit.cpit252.project.discount;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public class DiscountCalculator {

    public static class Suggestion {
        public final int productId;
        public final String reason;
        public final double suggestedDiscount;
        public final int daysLeft;

        public Suggestion(int productId, String reason, double discount, int daysLeft) {
            this.productId = productId;
            this.reason = reason;
            this.suggestedDiscount = discount;
            this.daysLeft = daysLeft;
        }

        @Override
        public String toString() {
            return String.format("ID:%d | %.1f%% off (%s) | %d days",
                productId, suggestedDiscount, reason, daysLeft);
        }
    }

    public static Suggestion suggest(Product p, LocalDate today) {
        int daysLeft = p.daysUntilExpiry(today);
        String reason = "";
        double discount = 0.0;

        if (daysLeft <= 0) {
            discount = 100.0;
            reason = "EXPIRED";
        } else if (daysLeft <= 2) {
            discount = 50.0 + (p.stock > 20 ? 10.0 : 0.0);
            reason = "CRITICAL";
        } else if (daysLeft <= 5) {
            discount = 30.0 + (p.stock > 15 ? 10.0 : 0.0);
            reason = "URGENT";
        } else if (daysLeft <= 10) {
            discount = 15.0 + (p.stock > 10 ? 10.0 : 0.0);
            reason = "SOON";
        } else if (daysLeft > 30 && p.stock > 50) {
            discount = 5.0;
            reason = "OVERSTOCK";
        }

        return new Suggestion(p.id, reason, discount, daysLeft);
    }
}

