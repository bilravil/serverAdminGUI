package TableModel;


import java.sql.*; 
import java.util.*;
import javax.swing.table.*;

public class PatientTableModel extends AbstractTableModel  {

    // здесь мы будем хранить названия столбцов 
    private final ArrayList columnNames = new ArrayList(); 
    // список типов столбцов 
    private final ArrayList columnTypes = new ArrayList(); 
    // хранилище для полученных данных из базы данных 
    private final ArrayList data = new ArrayList(); 

    
    private final Connection conn ;
    
    // конструктор позволяет задать возможность редактирования 
    public PatientTableModel(boolean editable, Connection con) {
        this.editable = editable;
        this.conn = con;

    }
    
    private final boolean editable; 
    // количество строк 
    
    @Override
    public int getRowCount() {
        synchronized (data) {
        return data.size();
        } 
    } 
    // количество столбцов 
    @Override
    public int getColumnCount() {
        return columnNames.size();
    } 
    
    
    public void removeRow(int row) {
        data.remove(row);
    }
    // тип данных столбца 
    @Override
    public Class getColumnClass(int column) {
        return (Class)columnTypes.get(column);
    } 
    // название столбца 
    @Override
    public String getColumnName(int column) {
        return (String)columnNames.get(column);
    } 
    // данные в ячейке 
    @Override
    public Object getValueAt(int row, int column) {
        synchronized (data) {
        return ((ArrayList)data.get(row)).get(column); 
        } 
    } 
    // возможность редактирования 

    
    // замена значения ячейки 
    @Override
    public void setValueAt( Object value, int row, int column){

    }
    
    
    // получение данных из объекта ResultSet 
    public void setDataSource( ResultSet rs) throws Exception {
    // удаляем прежние данные 
        data.clear(); 
        columnNames.clear(); 
        columnTypes.clear(); 
        // получаем вспомогательную информацию о столбцах 
        ResultSetMetaData rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount(); 
        
        for ( int i=0; i < columnCount; i++) {
            // название столбца 
            columnNames.add(rsmd.getColumnName(i+1)); 
            // тип столбца 
            Class type =
            Class.forName(rsmd.getColumnClassName(i+1));
            columnTypes.add(type); 
        } 
        // сообщаем об изменениях в структуре данных 
        fireTableStructureChanged(); 
        // получаем данные 
        while ( rs.next() ) {
            // здесь будем хранить ячейки одной строки 
            ArrayList row = new ArrayList(); 
            for ( int i=0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class)
                row.add(rs.getString(i+1));              
            else
                row.add(rs.getObject(i+1));
                } 
            synchronized (data) {
                 data.add(row); 
                 
                 // сообщаем о прибавлении строки 
                 fireTableRowsInserted( data.size()-1, data.size()-1);
            }
            
           
        }
             //end while 
    }//end setDataSource 
}