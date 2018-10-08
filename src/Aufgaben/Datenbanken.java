package Aufgaben;
import java.sql.*;

public class Datenbanken {
    private static final String hostname = "egmontix";
    private static final String port = "1521";
    private static final String sid = "dev3";
    private static final String username = "praktikant";
    private static final String password = "praktikant";
    private static final String url = "jdbc:oracle:thin:@" + hostname + ":" + port + ":" + sid;

    public static void main(String[] args) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Konnte nicht geladen werden");
        }

        try {
            Connection con = java.sql.DriverManager.getConnection(url, username, password);
            con.close();
        } catch (SQLException ex) {
            System.out.println("Fehler beim Laden");
        }
        mitarbeiterTabelleAnzeigen();
    }


    public static void mitarbeiterTabelleAnzeigen() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement stmt = null;
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MITARBEITERUEBERSICHT");
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();

            System.out.print(meta.getColumnName(1) + " " + meta.getColumnName(2) + " "
                    + meta.getColumnName(3) + " " + meta.getColumnName(4) + " "
                    + meta.getColumnName(5) + " " +  meta.getColumnName(6) + " "
                    + meta.getColumnName(7) + " " + meta.getColumnName(8) + " "
                    + meta.getColumnName(9) + " " + meta.getColumnName(10));
            System.out.println();
            while (rs.next()) {

                for (int i = 1; i <= columns; i++) {
                    String value = rs.getString(i);
                    System.out.print(value + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Fehler");
        }
    }
}
