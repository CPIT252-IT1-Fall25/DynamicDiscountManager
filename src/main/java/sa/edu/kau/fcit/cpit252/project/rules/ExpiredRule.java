package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public class ExpiredRule implements DiscountRule {

    @Override
    public String getName() {
        return "EXPIRED";
    }

    @Override
    public boolean applies(Product p, LocalDate today) {
        return p.daysUntilExpiry(today) <= 0;
    }

    @Override
    public double getDiscount(Product p, LocalDate today) {
        return 100.0;
    }
}

