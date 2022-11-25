package sample.mysql;


import java.sql.*;
import java.util.ArrayList;

public class VoliMySQL {

    Voli voli = new Voli();
    ArrayList idVoli = new ArrayList( );
    private static ConnectionMyDatabase conn = new ConnectionMyDatabase();
    private String query;


    public ArrayList select() throws SQLException {
        query = "SELECT * FROM voli ";

        Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(query);

        while (resultSet.next()) {
            voli.setId( resultSet.getString(1) );
            voli.setAeroportoPartenza( resultSet.getString( 2 ) );
            voli.setAeroportoArrivo(resultSet.getString(3));
            voli.setOrarioPartenza(resultSet.getString(4));
            voli.setOrarioArrivo(resultSet.getString(5));
            idVoli.add( voli.getId() );
        }
        return idVoli;
    }

    public String selectAeroporto(String codice){
        query ="SELECT aeroporto FROM codici_aeroporto WHERE codice LIKE ?";
        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, codice);
            ResultSet resultSet = preparedStatement.executeQuery();
            String aereoporto;
            if (resultSet.next()) {
                aereoporto = resultSet.getString( 1 );
                return aereoporto;
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return null;
    }

        public Voli selectVolo(String idVoli) throws SQLException {

        query = "SELECT * FROM voli WHERE idvoli LIKE ? ";

        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, idVoli);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                voli.setId( resultSet.getString( 1 ) );
                voli.setAeroportoPartenza(resultSet.getString(2));
                voli.setAeroportoArrivo(resultSet.getString(3));
                voli.setOrarioPartenza(resultSet.getString(4));
                voli.setOrarioArrivo(resultSet.getString(5));
                return voli;
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return null;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }


    private void aggiornamento(String query) throws DAOException{
        try {
            Statement st = ConnectionMyDatabase.getStatement();

            int n = st.executeUpdate(query);
            ConnectionMyDatabase.closeStatement(st);

        } catch (SQLException e) {
            throw new DAOException("In insert(): " + e.getMessage());
        }
    }
}
