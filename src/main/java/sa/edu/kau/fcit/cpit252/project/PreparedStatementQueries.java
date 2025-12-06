package sa.edu.kau.fcit.cpit252.project;

/**
 * Contains all prepared SQL statement queries for the DynamicDiscountManager.
 * Using prepared statements prevents SQL injection and improves performance.
 */
public class PreparedStatementQueries {

    // ==================== TABLE CREATION ====================

    public static final String CREATE_CATEGORY_TABLE =
        "CREATE TABLE IF NOT EXISTS category (" +
        "    id SERIAL PRIMARY KEY," +
        "    name VARCHAR(100) NOT NULL UNIQUE," +
        "    description TEXT" +
        ")";

    public static final String CREATE_ITEM_TABLE =
        "CREATE TABLE IF NOT EXISTS item (" +
        "    id SERIAL PRIMARY KEY," +
        "    name VARCHAR(200) NOT NULL," +
        "    description TEXT," +
        "    price DECIMAL(10, 2) NOT NULL," +
        "    stock_quantity INTEGER DEFAULT 0," +
        "    category_id INTEGER REFERENCES category(id) ON DELETE SET NULL," +
        "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
        ")";

    public static final String CREATE_DISCOUNT_TABLE =
        "CREATE TABLE IF NOT EXISTS discount (" +
        "    id SERIAL PRIMARY KEY," +
        "    name VARCHAR(100) NOT NULL," +
        "    description TEXT," +
        "    discount_type VARCHAR(20) NOT NULL," +  // PERCENTAGE, FIXED, TIERED
        "    discount_value DECIMAL(10, 2) NOT NULL," +
        "    min_quantity INTEGER DEFAULT 1," +
        "    max_quantity INTEGER," +
        "    start_date DATE," +
        "    end_date DATE," +
        "    is_active BOOLEAN DEFAULT TRUE," +
        "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
        ")";

    public static final String CREATE_ITEM_DISCOUNT_TABLE =
        "CREATE TABLE IF NOT EXISTS item_discount (" +
        "    id SERIAL PRIMARY KEY," +
        "    item_id INTEGER NOT NULL REFERENCES item(id) ON DELETE CASCADE," +
        "    discount_id INTEGER NOT NULL REFERENCES discount(id) ON DELETE CASCADE," +
        "    UNIQUE(item_id, discount_id)" +
        ")";

    // ==================== CATEGORY OPERATIONS ====================

    public static final String INSERT_CATEGORY =
        "INSERT INTO category (name, description) VALUES (?, ?)";

    public static final String SELECT_ALL_CATEGORIES =
        "SELECT * FROM category ORDER BY name";

    public static final String SELECT_CATEGORY_BY_ID =
        "SELECT * FROM category WHERE id = ?";

    public static final String SELECT_CATEGORY_BY_NAME =
        "SELECT * FROM category WHERE name = ?";

    public static final String UPDATE_CATEGORY =
        "UPDATE category SET name = ?, description = ? WHERE id = ?";

    public static final String DELETE_CATEGORY =
        "DELETE FROM category WHERE id = ?";

    // ==================== ITEM OPERATIONS ====================

    public static final String INSERT_ITEM =
        "INSERT INTO item (name, description, price, stock_quantity, category_id) " +
        "VALUES (?, ?, ?, ?, ?)";

    public static final String SELECT_ALL_ITEMS =
        "SELECT i.*, c.name as category_name " +
        "FROM item i " +
        "LEFT JOIN category c ON i.category_id = c.id " +
        "ORDER BY i.name";

    public static final String SELECT_ITEM_BY_ID =
        "SELECT i.*, c.name as category_name " +
        "FROM item i " +
        "LEFT JOIN category c ON i.category_id = c.id " +
        "WHERE i.id = ?";

    public static final String SELECT_ITEMS_BY_CATEGORY =
        "SELECT i.*, c.name as category_name " +
        "FROM item i " +
        "LEFT JOIN category c ON i.category_id = c.id " +
        "WHERE i.category_id = ? " +
        "ORDER BY i.name";

    public static final String SELECT_ITEMS_IN_STOCK =
        "SELECT i.*, c.name as category_name " +
        "FROM item i " +
        "LEFT JOIN category c ON i.category_id = c.id " +
        "WHERE i.stock_quantity > 0 " +
        "ORDER BY i.name";

    public static final String UPDATE_ITEM =
        "UPDATE item SET name = ?, description = ?, price = ?, stock_quantity = ?, " +
        "category_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    public static final String UPDATE_ITEM_STOCK =
        "UPDATE item SET stock_quantity = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    public static final String UPDATE_ITEM_PRICE =
        "UPDATE item SET price = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

    public static final String DELETE_ITEM =
        "DELETE FROM item WHERE id = ?";

    public static final String SEARCH_ITEMS_BY_NAME =
        "SELECT i.*, c.name as category_name " +
        "FROM item i " +
        "LEFT JOIN category c ON i.category_id = c.id " +
        "WHERE LOWER(i.name) LIKE LOWER(?) " +
        "ORDER BY i.name";

    // ==================== DISCOUNT OPERATIONS ====================

    public static final String INSERT_DISCOUNT =
        "INSERT INTO discount (name, description, discount_type, discount_value, " +
        "min_quantity, max_quantity, start_date, end_date, is_active) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_ALL_DISCOUNTS =
        "SELECT * FROM discount ORDER BY name";

    public static final String SELECT_DISCOUNT_BY_ID =
        "SELECT * FROM discount WHERE id = ?";

    public static final String SELECT_ACTIVE_DISCOUNTS =
        "SELECT * FROM discount " +
        "WHERE is_active = TRUE " +
        "AND (start_date IS NULL OR start_date <= CURRENT_DATE) " +
        "AND (end_date IS NULL OR end_date >= CURRENT_DATE) " +
        "ORDER BY name";

    public static final String UPDATE_DISCOUNT =
        "UPDATE discount SET name = ?, description = ?, discount_type = ?, " +
        "discount_value = ?, min_quantity = ?, max_quantity = ?, " +
        "start_date = ?, end_date = ?, is_active = ? WHERE id = ?";

    public static final String ACTIVATE_DISCOUNT =
        "UPDATE discount SET is_active = TRUE WHERE id = ?";

    public static final String DEACTIVATE_DISCOUNT =
        "UPDATE discount SET is_active = FALSE WHERE id = ?";

    public static final String DELETE_DISCOUNT =
        "DELETE FROM discount WHERE id = ?";

    // ==================== ITEM-DISCOUNT RELATIONSHIP ====================

    public static final String ASSIGN_DISCOUNT_TO_ITEM =
        "INSERT INTO item_discount (item_id, discount_id) VALUES (?, ?) " +
        "ON CONFLICT (item_id, discount_id) DO NOTHING";

    public static final String REMOVE_DISCOUNT_FROM_ITEM =
        "DELETE FROM item_discount WHERE item_id = ? AND discount_id = ?";

    public static final String SELECT_DISCOUNTS_FOR_ITEM =
        "SELECT d.* FROM discount d " +
        "INNER JOIN item_discount id ON d.id = id.discount_id " +
        "WHERE id.item_id = ? " +
        "AND d.is_active = TRUE " +
        "AND (d.start_date IS NULL OR d.start_date <= CURRENT_DATE) " +
        "AND (d.end_date IS NULL OR d.end_date >= CURRENT_DATE) " +
        "ORDER BY d.discount_value DESC";

    public static final String SELECT_ITEMS_WITH_DISCOUNT =
        "SELECT i.*, c.name as category_name FROM item i " +
        "LEFT JOIN category c ON i.category_id = c.id " +
        "INNER JOIN item_discount id ON i.id = id.item_id " +
        "WHERE id.discount_id = ? " +
        "ORDER BY i.name";

    // ==================== PRICE CALCULATION QUERIES ====================

    public static final String SELECT_ITEM_WITH_BEST_DISCOUNT =
        "SELECT i.*, c.name as category_name, " +
        "d.discount_type, d.discount_value, d.min_quantity, " +
        "CASE " +
        "    WHEN d.discount_type = 'PERCENTAGE' THEN i.price * (1 - d.discount_value / 100) " +
        "    WHEN d.discount_type = 'FIXED' THEN i.price - d.discount_value " +
        "    ELSE i.price " +
        "END as discounted_price " +
        "FROM item i " +
        "LEFT JOIN category c ON i.category_id = c.id " +
        "LEFT JOIN item_discount id ON i.id = id.item_id " +
        "LEFT JOIN discount d ON id.discount_id = d.id " +
        "    AND d.is_active = TRUE " +
        "    AND (d.start_date IS NULL OR d.start_date <= CURRENT_DATE) " +
        "    AND (d.end_date IS NULL OR d.end_date >= CURRENT_DATE) " +
        "WHERE i.id = ? " +
        "ORDER BY discounted_price ASC " +
        "LIMIT 1";

    // ==================== DROP TABLES ====================

    public static final String DROP_ITEM_DISCOUNT_TABLE =
        "DROP TABLE IF EXISTS item_discount";

    public static final String DROP_DISCOUNT_TABLE =
        "DROP TABLE IF EXISTS discount";

    public static final String DROP_ITEM_TABLE =
        "DROP TABLE IF EXISTS item";

    public static final String DROP_CATEGORY_TABLE =
        "DROP TABLE IF EXISTS category";

    private PreparedStatementQueries() {
        // Private constructor to prevent instantiation
    }
}
