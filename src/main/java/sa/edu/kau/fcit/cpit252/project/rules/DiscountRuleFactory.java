package sa.edu.kau.fcit.cpit252.project.rules;

public class DiscountRuleFactory {
    
    public static DiscountRule createRule(String type) {
        switch (type.toUpperCase()) {
            case "EXPIRED":
                return new ExpiredRule();
            case "CRITICAL":
                return new CriticalRule();
            case "URGENT":
                return new UrgentRule();
            case "SOON":
                return new SoonRule();
            case "OVERSTOCK":
                return new OverstockRule();        
            case "CATEGORY":
                return new CategoryRule();
            default:
                throw new IllegalArgumentException("Unknown rule type: " + type);
        }
    }
}
