package sa.edu.kau.fcit.cpit252.project;

import java.time.LocalDate;

public interface GenerateRuleForProducts {
    
    boolean isApplicable(LocalDate currentDate, int productStock, int purchaseQuantity, LocalDate productExpiryDate);
    
    int getDiscountPercentage();
    
    LocalDate getStartDate();
    
    LocalDate getEndDate();
    
    int getMinAmount();
    
    int getMaxAmount();
    
    int getMinStock();
    
    boolean isExpiryDateApplicable(LocalDate productExpiryDate, LocalDate currentDate);
    
    String getDescription();
    
    default double calculateDiscountedPrice(double originalPrice) {
        if (!isApplicable(LocalDate.now(), 0, 1, null)) {
            return originalPrice;
        }
        double discountAmount = originalPrice * (getDiscountPercentage() / 100.0);
        return originalPrice - discountAmount;
    }
}
