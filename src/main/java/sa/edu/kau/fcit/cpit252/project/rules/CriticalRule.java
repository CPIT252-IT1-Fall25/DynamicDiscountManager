package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public class CriticalRule implements DiscountRule {
    
    @Override
    public String getName() {
        return "CRITICAL";
    }
    
    @Override
    public boolean applies(Product p, LocalDate today) {
        int days = p.daysUntilExpiry(today);
        return days > 0 && days <= 2;
    }
    
    @Override
    public double getDiscount(Product p, LocalDate today) {
        return 50.0 + (p.stock > 20 ? 10.0 : 0.0);
    }
}

