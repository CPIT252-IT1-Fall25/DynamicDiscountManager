package sa.edu.kau.fcit.cpit252.project.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table {
    private final String tableName;
    private final List<String> columns;
    private final List<String> constraints;

    private Table(TableBuilder builder) {
        this.tableName = builder.tableName;
        this.columns = new ArrayList<>(builder.columns);
        this.constraints = new ArrayList<>(builder.constraints);
    }

    public String getTableName() {
        return tableName;
    }
    
    public List<String> getColumns() {
        return columns;
    }
    
    public List<String> getConstraints() {
        return constraints;
    }

    public static class TableBuilder {
        private String tableName;
        private List<String> columns;
        private List<String> constraints;
        private boolean ifNotExists;
    
    public TableBuilder(String tablename){
        this.tableName=tablename;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
         this.ifNotExists = false;
    }
    public TableBuilder ifNotExists(){
            this.ifNotExists = true;
            return this;
        }
    public TableBuilder primaryKey(String columnName ){
        constraints.add("PRIMARY KEY ("+ columnName+")");
        return this;

    }
    public TableBuilder addColumn(String name, String type){
        columns.add(name + " " + type);
        return this;
    }
    
    public TableBuilder addColumn(String name, String type, String constraint){
        columns.add(name + " " + type + " " + constraint);
        return this;
    }
     public TableBuilder foreignKey(String column, String fromTable, String fromColumn) {
            constraints.add("FOREIGN KEY (" + column + ") REFERENCES " + fromTable + "(" + fromColumn + ")");
            return this;
       
        }
         public Table build(){
           return new Table(this);
        }
        
        //these commands were created with claude 4 given the table builder above and that i want to create sql commands
public String createTableSQL(){
            Table table = build();
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE ");
            
            if (ifNotExists) {
                sql.append("IF NOT EXISTS ");
            }
            
            sql.append(table.tableName).append(" (\n");

            for(int i = 0; i < table.columns.size(); i++){
                sql.append("    ").append(table.columns.get(i));
                if (i < table.columns.size() - 1 || !table.constraints.isEmpty()) {
                    sql.append(",");
                }
                sql.append("\n");
            }
            
            for(int i = 0; i < table.constraints.size(); i++){
                sql.append("    ").append(table.constraints.get(i));
                if (i < table.constraints.size() - 1) {
                    sql.append(",");
                }
                sql.append("\n");
            }
            
            sql.append(")");
            return sql.toString();
        }
        public String dropTableSQL() {
            return "DROP TABLE IF EXISTS " + tableName;
        }
        
        public String addColumnSQL(String columnName, String dataType) {
            return "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + dataType;
        }
        
        public String dropColumnSQL(String columnName) {
            return "ALTER TABLE " + tableName + " DROP COLUMN " + columnName;
        }
        
        public String insertSQL(Map<String, Object> values) {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ").append(tableName).append(" (");
            
            String[] columnNames = values.keySet().toArray(new String[0]);
            sql.append(String.join(", ", columnNames));
            sql.append(") VALUES (");
            
            for (int i = 0; i < columnNames.length; i++) {
                sql.append("?");
                if (i < columnNames.length - 1) sql.append(", ");
            }
            sql.append(")");
            
            return sql.toString();
        }
        
        public String selectSQL() {
            return "SELECT * FROM " + tableName;
        }
        
        public String selectSQL(String whereClause) {
            return "SELECT * FROM " + tableName + " WHERE " + whereClause;
        }
        
        public String updateSQL(String setClause, String whereClause) {
            return "UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause;
        }
        
        public String deleteSQL(String whereClause) {
            return "DELETE FROM " + tableName + " WHERE " + whereClause;
        }
    }
}