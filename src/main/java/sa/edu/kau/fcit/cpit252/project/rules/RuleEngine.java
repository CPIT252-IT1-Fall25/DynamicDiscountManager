package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;
import java.util.*;

public class RuleEngine {
    private final List<DiscountRule> rules;
    
    public RuleEngine() {
        this.rules = new ArrayList<>();
        registerDefaultRules();
    }
    
    private void registerDefaultRules() {
        rules.add(new ExpiredRule());
        rules.add(new CriticalRule());
        rules.add(new UrgentRule());
        rules.add(new SoonRule());
        rules.add(new OverstockRule());
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

