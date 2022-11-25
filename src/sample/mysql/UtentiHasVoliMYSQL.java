package sample.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static sample.mysql.UtentiMySQL.printSQLException;

public class UtentiHasVoliMYSQL {

    private String query;

    private static ConnectionMyDatabase conn = new ConnectionMyDatabase();

    public List<UtentiHasVoli> listaUtenti(String idVolo, String città, String indirizzo, Utenti utenti){
        ArrayList<UtentiHasVoli> lista = new ArrayList<>();

            if(città.isEmpty()){
                query = "SELECT * FROM utenti_has_voli WHERE voli_idvoli LIKE ?";
            }else{
                query = "SELECT * FROM utenti_has_voli WHERE citta LIKE ?";
            }
        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            if(città.isEmpty()){
                preparedStatement.setString(1, idVolo);
            }else{
                preparedStatement.setString(1, città);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(!resultSet.getString( 1 ).matches( utenti.getId() )){
                    lista.add(new UtentiHasVoli( resultSet.getString( 1 ),resultSet.getString(2),
                            resultSet.getString(3),resultSet.getString(4), resultSet.getString( 5 ),resultSet.getString( 6 )));
                }}
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return lista;
    }

    public void insert(Utenti utente, String idVolo) throws DAOException {
        query = "INSERT into utenti_has_voli(utenti_idutenti, voli_idvoli, nickname) VALUES ('"+ utente.getId() + "','"+ idVolo +"','"+ utente.getNickname() +"')";

        aggiornamento( query );
    }

    public void update(String id, String indirizzo, String citta, String mezzo ) throws DAOException, SQLException {
        query = "UPDATE utenti_has_voli SET indirizzo = '"+ indirizzo +"',citta = '"+ citta +"', mezzo = '"+ mezzo +"' WHERE utenti_idutenti = '"+ id+"'";

        Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.executeUpdate();
    }

    private int aggiornamento(String query) throws DAOException {
        try {
            Statement st = ConnectionMyDatabase.getStatement();

            int n = st.executeUpdate(query);
            ConnectionMyDatabase.closeStatement(st);

            return n;
        } catch (SQLException e) {
            throw new DAOException("In insert(): " + e.getMessage());
        }
    }

    public String utenteRegistrato(String idUtente){

        query = "SELECT * FROM utenti_has_voli WHERE utenti_idutenti LIKE ?";
        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, idUtente);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString( 2 );
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return null;
    }

    public void delete(String id) throws DAOException {
        query = "DELETE FROM utenti_has_voli WHERE utenti_idutenti='"+ id +"'";

        aggiornamento( query );
    }

    public String inidirizzo(String id) {
        query = "SELECT * FROM utenti_has_voli WHERE utenti_idutenti LIKE ?";

        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String indirizzo = resultSet.getString( 4 ) + ", " + resultSet.getString( 3 );
                return indirizzo;
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return null;
    }
}
