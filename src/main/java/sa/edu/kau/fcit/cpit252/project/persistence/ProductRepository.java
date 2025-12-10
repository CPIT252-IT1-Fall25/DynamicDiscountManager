package sa.edu.kau.fcit.cpit252.project.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.edu.kau.fcit.cpit252.project.DB.Table;
import sa.edu.kau.fcit.cpit252.project.domain.Product;

public class ProductRepository {

    public void initializeSchema(Connection conn) throws SQLException {
        Table.TableBuilder builder = new Table.TableBuilder("products")
            .ifNotExists()
            .addColumn("id", "SERIAL")
            .addColumn("product_id", "VARCHAR(50) NOT NULL")
            .addColumn("name", "VARCHAR(200) NOT NULL")
            .addColumn("category", "VARCHAR(100) NOT NULL")
            .addColumn("stock", "INTEGER NOT NULL")
            .addColumn("expiry_date", "DATE NOT NULL")
            .addColumn("base_price", "DECIMAL(10, 2) NOT NULL")
            .addColumn("applied_discount", "DECIMAL(5, 2) DEFAULT 0")
            .primaryKey("id");

        String sql = builder.createTableSQL();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void saveProduct(Product p, Connection conn) throws SQLException {
        // Use explicit SQL to ensure parameter order matches PreparedStatement indices
        String sql = "INSERT INTO products (product_id, name, category, stock, expiry_date, base_price, applied_discount) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.productId);
            pstmt.setString(2, p.name);
            pstmt.setString(3, p.category);
            pstmt.setInt(4, p.stock);
            pstmt.setDate(5, Date.valueOf(p.expiryDate));
            pstmt.setDouble(6, p.basePrice);
            pstmt.setDouble(7, p.appliedDiscount);
            pstmt.executeUpdate();
        }
    }

    public int saveProductAndGetId(String productId, String name, String category, int stock, LocalDate expiryDate, double basePrice, Connection conn) throws SQLException {
        String sql = "INSERT INTO products (product_id, name, category, stock, expiry_date, base_price, applied_discount) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, productId);
            pstmt.setString(2, name);
            pstmt.setString(3, category);
            pstmt.setInt(4, stock);
            pstmt.setDate(5, Date.valueOf(expiryDate));
            pstmt.setDouble(6, basePrice);
            pstmt.setDouble(7, 0.0);
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to insert product, no generated ID obtained");
    }

    public void updateProductDiscount(int productId, double discount, Connection conn) throws SQLException {
        Table.TableBuilder builder = new Table.TableBuilder("products");
        String sql = builder.updateSQL("applied_discount = " + discount, "id = " + productId);

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public void updateProductStock(int productId, int newStock, Connection conn) throws SQLException {
        Table.TableBuilder builder = new Table.TableBuilder("products");
        String sql = builder.updateSQL("stock = " + newStock, "id = " + productId);

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public List<Product> loadAllProducts(Connection conn) throws SQLException {
        List<Product> result = new ArrayList<>();
        Table.TableBuilder builder = new Table.TableBuilder("products");
        String sql = builder.selectSQL();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("product_id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("stock"),
                    LocalDate.parse(rs.getString("expiry_date")),
                    rs.getDouble("base_price")
                );
                p.appliedDiscount = rs.getDouble("applied_discount");
                result.add(p);
            }
        }
        return result;
    }

    public Product loadProductById(int id, Connection conn) throws SQLException {
        Table.TableBuilder builder = new Table.TableBuilder("products");
        String sql = builder.selectSQL("id = " + id);

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("stock"),
                    LocalDate.parse(rs.getString("expiry_date")),
                    rs.getDouble("base_price")
                );
                p.appliedDiscount = rs.getDouble("applied_discount");
                return p;
            }
        }
        return null;
    }

    public void deleteAllProducts(Connection conn) throws SQLException {
        Table.TableBuilder builder = new Table.TableBuilder("products");
        String sql = builder.dropTableSQL();

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void deleteProductById(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteProductByBarcode(String productId, Connection conn) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productId);
            pstmt.executeUpdate();
        }
    }
}

