package sa.edu.kau.fcit.cpit252.project.DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
    private static String url = "jdbc:postgresql://localhost:5432/DynamicDiscountDB";
    private static String userName = "admin";
    private static String password = "password";
    private static Connection DBConnection;

    //singleton
    public static Connection getDBConnection()throws SQLException{
        if(DBConnection != null)
            return DBConnection;
        else{
             return CreateConnection();

        }
        
    }
    private static Connection CreateConnection(){
         try {
            Class.forName("org.postgresql.Driver");
            DBConnection =  DriverManager.getConnection(url,userName,password);

             
         } catch (ClassNotFoundException e) {
            System.out.println("class error " + e);
         } 
         catch(SQLException e){
            System.err.println("SQL Error "+ e);

         }
         return DBConnection;

        }

}