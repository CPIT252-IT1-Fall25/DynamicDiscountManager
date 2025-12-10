package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;
import java.time.Month;

public class SeasonalRule implements DiscountRule {
    
    @Override
    public String getName() {
        return "SEASONAL";
    }
    
    @Override
    public boolean applies(Product p, LocalDate today) {
        Month month = today.getMonth();
        return (month == Month.OCTOBER || month == Month.DECEMBER) && p.daysUntilExpiry(today) > 0;
    }
    
    @Override
    public double getDiscount(Product p, LocalDate today) {
        int units = p.stock;
        if (units >= 6) return 15.0;
        if (units >= 2) return 10.0;
        return 0.0;
    }
}

