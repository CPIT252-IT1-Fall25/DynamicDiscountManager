package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public class CategoryRule implements DiscountRule {
    
    @Override
    public String getName() {
        return "CATEGORY_SPECIAL";
    }
    
    @Override
    public boolean applies(Product p, LocalDate today) {
        return (p.category.equals("Bakery") || p.category.equals("Dairy")) 
            && p.daysUntilExpiry(today) > 0;
    }
    
    @Override
    public double getDiscount(Product p, LocalDate today) {
        return p.category.equals("Bakery") ? 8.0 : 3.0;
    }
}

