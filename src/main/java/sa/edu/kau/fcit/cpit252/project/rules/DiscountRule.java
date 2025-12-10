package sa.edu.kau.fcit.cpit252.project.rules;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import java.time.LocalDate;

public interface DiscountRule {

    String getName();

    boolean applies(Product p, LocalDate today);

    double getDiscount(Product p, LocalDate today);

    default String getReason() {
        return getName();
    }
}

