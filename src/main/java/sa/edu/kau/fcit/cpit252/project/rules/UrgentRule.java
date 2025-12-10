package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public class UrgentRule implements DiscountRule {
    
    @Override
    public String getName() {
        return "URGENT";
    }
    
    @Override
    public boolean applies(Product p, LocalDate today) {
        int days = p.daysUntilExpiry(today);
        return days > 2 && days <= 5;
    }
    
    @Override
    public double getDiscount(Product p, LocalDate today) {
        return 30.0 + (p.stock > 15 ? 10.0 : 0.0);
    }
}

