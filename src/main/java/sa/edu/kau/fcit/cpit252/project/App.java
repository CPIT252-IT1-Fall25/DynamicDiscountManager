package sa.edu.kau.fcit.cpit252.project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import sa.edu.kau.fcit.cpit252.project.DB.Database; 
import sa.edu.kau.fcit.cpit252.project.DB.Table;

public class App {
    public static void main(String[] args) {
        try (Connection connection = Database.getDBConnection(); Statement statement = connection.createStatement()){
            Table.TableBuilder userTableBuilder = new Table.TableBuilder("person")
                .ifNotExists()
                .addColumn("id", "SERIAL")
                .addColumn("name", "VARCHAR(100) NOT NULL")  
                .addColumn("email", "VARCHAR(100) NOT NULL") 
                .primaryKey("id");

            String createSQL = userTableBuilder.createTableSQL();
            System.out.println("createsql \n"  +createSQL);
            statement.execute(createSQL);
            String addColumnSQL = userTableBuilder.addColumnSQL("age", "INTEGER");
            System.out.println("addcolumn \n "+ addColumnSQL);
            statement.execute(addColumnSQL);

            Map<String, Object> userData = new HashMap<>();
            userData.put("name", "Ibrahim");
            userData.put("email", "Aljohani");
            
            String insertSQL = userTableBuilder.insertSQL(userData);
            System.out.println("Insertion Into sql \n" + insertSQL);
            
            try (PreparedStatement pstatement = connection.prepareStatement(insertSQL)) {
                pstatement.setString(1, "Ibrahim Aljohani");
                pstatement.setString(2, "Ibrahim@example.com");
                pstatement.executeUpdate();
            }
            
            String selectSQL = userTableBuilder.selectSQL("name = 'Ibrahim Aljohani'");
            System.out.println(selectSQL);
            
            try (ResultSet rs = statement.executeQuery(selectSQL)) {
                while (rs.next()) {
                    System.out.println("Found: ID=" + rs.getInt("id") + 
                                     ", Name=" + rs.getString("name") + 
                                     ", Email=" + rs.getString("email"));
                }
            }
            
            String updateSQL = userTableBuilder.updateSQL("age = 23", "name = 'Ibrahim Aljohani'");
            statement.executeUpdate(updateSQL);
            
            String deleteSQL = userTableBuilder.deleteSQL("id = 1");
            System.out.println(deleteSQL);
            statement.executeUpdate(deleteSQL);
            
            String dropSQL = userTableBuilder.dropTableSQL();
            System.out.println(dropSQL);
            statement.execute(dropSQL);

            

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        }
    }
    
}