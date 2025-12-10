package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public class SoonRule implements DiscountRule {

    @Override
    public String getName() {
        return "SOON";
    }

    @Override
    public boolean applies(Product p, LocalDate today) {
        int days = p.daysUntilExpiry(today);
        return days > 5 && days <= 10;
    }

    @Override
    public double getDiscount(Product p, LocalDate today) {
        return 15.0 + (p.stock > 10 ? 10.0 : 0.0);
    }
}

