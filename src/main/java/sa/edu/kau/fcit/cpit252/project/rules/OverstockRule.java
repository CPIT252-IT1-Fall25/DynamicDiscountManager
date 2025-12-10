package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public class OverstockRule implements DiscountRule {

    @Override
    public String getName() {
        return "OVERSTOCK";
    }

    @Override
    public boolean applies(Product p, LocalDate today) {
        return p.daysUntilExpiry(today) > 30 && p.stock > 50;
    }

    @Override
    public double getDiscount(Product p, LocalDate today) {
        return 5.0;
    }
}

