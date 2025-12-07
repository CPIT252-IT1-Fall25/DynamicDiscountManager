package sa.edu.kau.fcit.cpit252.project.DB.Rule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class dairy implements GenerateRuleForProducts {
    
    @Override
    public boolean isApplicable(LocalDate currentDate, int productStock, int purchaseQuantity, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        return daysUntilExpiry <= 7 || productStock > 40;
    }
    
    @Override
    public int getDiscountPercentage() {
        return 0;
    }
    
    public int getDiscountPercentage(LocalDate currentDate, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        
        if (daysUntilExpiry <= 2) {
            return 50;
        } else if (daysUntilExpiry <= 4) {
            return 35;
        } else if (daysUntilExpiry <= 7) {
            return 25;
        } else if (daysUntilExpiry <= 14) {
            return 15;
        } else {
            return 5;
        }
    }
}
