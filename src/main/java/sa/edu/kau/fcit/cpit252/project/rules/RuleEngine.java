package sa.edu.kau.fcit.cpit252.project.rules;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.edu.kau.fcit.cpit252.project.domain.Product;

public class RuleEngine {
    private final List<DiscountRule> rules;

    public RuleEngine() {
        this.rules = new ArrayList<>();
        registerDefaultRules();
    }

    private void registerDefaultRules() {
        rules.add(DiscountRuleFactory.createRule("EXPIRED"));
        rules.add(DiscountRuleFactory.createRule("CRITICAL"));
        rules.add(DiscountRuleFactory.createRule("URGENT"));
        rules.add(DiscountRuleFactory.createRule("SOON"));
        rules.add(DiscountRuleFactory.createRule("OVERSTOCK"));
    }

    public void registerRule(DiscountRule rule) {
        rules.add(rule);
    }

    public RuleResult evaluate(Product p, LocalDate today) {
        for (DiscountRule rule : rules) {
            if (rule.applies(p, today)) {
                double discount = rule.getDiscount(p, today);
                return new RuleResult(rule.getName(), discount, p.daysUntilExpiry(today));
            }
        }
        return new RuleResult("OK", 0.0, p.daysUntilExpiry(today));
    }

    public static class RuleResult {
        public final String reason;
        public final double discount;
        public final int daysLeft;

        public RuleResult(String reason, double discount, int daysLeft) {
            this.reason = reason;
            this.discount = discount;
            this.daysLeft = daysLeft;
        }
    }
}

