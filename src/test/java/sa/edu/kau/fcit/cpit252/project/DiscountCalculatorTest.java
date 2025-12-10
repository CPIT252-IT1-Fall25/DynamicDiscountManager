package sa.edu.kau.fcit.cpit252.project.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.discount.DiscountCalculator;
import sa.edu.kau.fcit.cpit252.project.observer.InventoryManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountCalculatorTest {
    private Product milk;
    private LocalDate baseDate;

    @BeforeEach
    void setup() {
        baseDate = LocalDate.of(2025, 1, 1);
        milk = new Product(1, "Milk", "Dairy", 45, LocalDate.of(2025, 1, 4), 3.99);
    }

    @Test
    void testExpiredItem() {
        Product expired = new Product(2, "Old Yogurt", "Dairy", 10, LocalDate.of(2024, 12, 31), 5.49);
        var sug = DiscountCalculator.suggest(expired, baseDate);
        assertEquals(100.0, sug.suggestedDiscount, "Expired items should get 100% discount");
        assertEquals("EXPIRED", sug.reason);
    }

    @Test
    void testCriticalExpiry() {
        LocalDate day3 = baseDate.plusDays(2);
        var sug = DiscountCalculator.suggest(milk, day3);
        assertEquals("CRITICAL", sug.reason);
        assertTrue(sug.suggestedDiscount >= 50.0, "Critical items need heavy discount");
    }

    @Test
    void testUrgentExpiry() {
        Product urgentItem = new Product(5, "Bread", "Bakery", 20, LocalDate.of(2025, 1, 10), 2.99);
        LocalDate day7 = baseDate.plusDays(6);
        var sug = DiscountCalculator.suggest(urgentItem, day7);
        assertEquals("URGENT", sug.reason);
        assertTrue(sug.suggestedDiscount >= 15.0);
    }

    @Test
    void testNoDiscountWhenFresh() {
        Product freshItem = new Product(6, "Cheese", "Dairy", 8, LocalDate.of(2025, 2, 20), 7.99);
        LocalDate day20 = baseDate.plusDays(19);
        var sug = DiscountCalculator.suggest(freshItem, day20);
        assertEquals(0.0, sug.suggestedDiscount, "Fresh items should have no discount");
    }

    @Test
    void testOverstockDiscount() {
        Product overstock = new Product(3, "Tomatoes", "Produce", 120, LocalDate.of(2025, 2, 15), 0.79);
        var sug = DiscountCalculator.suggest(overstock, baseDate);
        assertEquals("OVERSTOCK", sug.reason);
        assertEquals(5.0, sug.suggestedDiscount);
    }

    @Test
    void testDaysUntilExpiry() {
        assertEquals(3, milk.daysUntilExpiry(baseDate));
        assertEquals(2, milk.daysUntilExpiry(baseDate.plusDays(1)));
        assertEquals(0, milk.daysUntilExpiry(baseDate.plusDays(3)));
    }

    @Test
    void testHighRiskDetection() {
        LocalDate day3 = baseDate.plusDays(2);
        assertTrue(milk.isHighRisk(day3), "Milk with 1 day left and 45 stock = high risk");

        Product lowStock = new Product(4, "Cheese", "Dairy", 3, LocalDate.of(2025, 1, 4), 7.99);
        assertFalse(lowStock.isHighRisk(day3), "Low stock items are not high-risk");
    }

    @Test
    void testFinalPriceCalculation() {
        milk.appliedDiscount = 25.0;
        double expected = 3.99 * 0.75;
        assertEquals(expected, milk.getFinalPrice(), 0.01);
    }

    @Test
    void testInventoryObserver() {
        InventoryManager mgr = new InventoryManager(baseDate);
        mgr.addProduct(milk);

        TestObserver obs = new TestObserver();
        mgr.subscribe(obs);

        mgr.applyDiscount(1, 30.0);
        assertTrue(obs.discountApplied, "Observer should be notified of discount");

        mgr.advanceDay();
        assertTrue(obs.dayAdvanced, "Observer should be notified of day advance");
    }

    static class TestObserver implements sa.edu.kau.fcit.cpit252.project.observer.InventoryObserver {
        boolean highRiskDetected = false;
        boolean discountApplied = false;
        boolean dayAdvanced = false;

        @Override
        public void onHighRiskDetected(Product p, LocalDate today) {
            highRiskDetected = true;
        }

        @Override
        public void onDiscountApplied(Product p, double percentage) {
            discountApplied = true;
        }

        @Override
        public void onDayAdvance(int newDay, LocalDate date) {
            dayAdvanced = true;
        }
    }
}

