package sa.edu.kau.fcit.cpit252.project.DB.Rule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Pantry implements GenerateRuleForProducts {
    
    @Override
    public boolean isApplicable(LocalDate currentDate, int productStock, int purchaseQuantity, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        return daysUntilExpiry <= 30 || productStock > 50;
    }
    
    @Override
    public int getDiscountPercentage() {
        return 0;
    }
    
    public int getDiscountPercentage(LocalDate currentDate, LocalDate productExpiryDate) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(currentDate, productExpiryDate);
        
        if (daysUntilExpiry <= 7) {
            return 40;
        } else if (daysUntilExpiry <= 14) {
            return 25;
        } else if (daysUntilExpiry <= 30) {
            return 15;
        } else {
            return 5;
        }
    }
}
