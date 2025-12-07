package sa.edu.kau.fcit.cpit252.project.DB.Rule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class frozen implements GenerateRuleForProducts {
    
    @Override
    public boolean isApplicable(LocalDate currentDate, int productStock, int purchaseQuantity, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        return daysUntilExpiry <= 90 || productStock > 150;
    }
    
    @Override
    public int getDiscountPercentage() {
        return 0;
    }
    
    public int getDiscountPercentage(LocalDate currentDate, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        
        if (daysUntilExpiry <= 30) {
            return 20;
        } else if (daysUntilExpiry <= 60) {
            return 12;
        } else if (daysUntilExpiry <= 90) {
            return 8;
        } else {
            return 2;
        }
    }
}
