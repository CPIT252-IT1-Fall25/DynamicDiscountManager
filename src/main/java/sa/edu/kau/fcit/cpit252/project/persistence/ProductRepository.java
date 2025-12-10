package sa.edu.kau.fcit.cpit252.project.persistence;

import sa.edu.kau.fcit.cpit252.project.domain.Product;
import sa.edu.kau.fcit.cpit252.project.DB.Database;
import sa.edu.kau.fcit.cpit252.project.DB.Table;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    public void initializeSchema(Connection conn) throws SQLException {
        Table.TableBuilder builder = new Table.TableBuilder("products")
            .ifNotExists()
            .addColumn("id", "SERIAL")
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
        Map<String, Object> values = new HashMap<>();
        values.put("name", p.name);
        values.put("category", p.category);
        values.put("stock", p.stock);
        values.put("expiry_date", p.expiryDate.toString());
        values.put("base_price", p.basePrice);
        values.put("applied_discount", p.appliedDiscount);

        Table.TableBuilder builder = new Table.TableBuilder("products");
        String sql = builder.insertSQL(values);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.name);
            pstmt.setString(2, p.category);
            pstmt.setInt(3, p.stock);
            pstmt.setString(4, p.expiryDate.toString());
            pstmt.setDouble(5, p.basePrice);
            pstmt.setDouble(6, p.appliedDiscount);
            pstmt.executeUpdate();
        }
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
}

