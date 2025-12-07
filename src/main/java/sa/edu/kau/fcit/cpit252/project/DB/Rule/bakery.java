package sa.edu.kau.fcit.cpit252.project.DB.Rule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class bakery implements GenerateRuleForProducts {
    
    @Override
    public boolean isApplicable(LocalDate currentDate, int productStock, int purchaseQuantity, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        return daysUntilExpiry <= 3 || productStock > 30;
    }
    
    @Override
    public int getDiscountPercentage() {
        return 0;
    }
    
    public int getDiscountPercentage(LocalDate currentDate, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        
        if (daysUntilExpiry <= 1) {
            return 60;
        } else if (daysUntilExpiry <= 2) {
            return 45;
        } else if (daysUntilExpiry <= 3) {
            return 30;
        } else {
            return 10;
        }
    }
}
