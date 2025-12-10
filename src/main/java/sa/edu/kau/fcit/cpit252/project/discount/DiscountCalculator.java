package sa.edu.kau.fcit.cpit252.project.discount;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.rules.RuleEngine;
import java.time.LocalDate;

public class DiscountCalculator {
    private static final RuleEngine engine = new RuleEngine();

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
        RuleEngine.RuleResult result = engine.evaluate(p, today);
        return new Suggestion(p.id, result.reason, result.discount, result.daysLeft);
    }
}

