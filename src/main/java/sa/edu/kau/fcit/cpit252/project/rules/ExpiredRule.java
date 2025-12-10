package sa.edu.kau.fcit.cpit252.project.rules;
}
    }
        return 100.0;
    public double getDiscount(Product p, LocalDate today) {
    @Override
    
    }
        return p.daysUntilExpiry(today) <= 0;
    public boolean applies(Product p, LocalDate today) {
    @Override
    
    }
        return "EXPIRED";
    public String getName() {
    @Override
    
public class ExpiredRule implements DiscountRule {

import java.time.LocalDate;
import sa.edu.kau.fcit.cpit252.project.domain.Product;


