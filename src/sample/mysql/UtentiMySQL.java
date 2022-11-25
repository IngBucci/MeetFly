package sample.mysql;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class UtentiMySQL implements DAO<Utenti> {

    Utenti utente = new Utenti();

    public UtentiMySQL(){}
    private static ConnectionMyDatabase conn = new ConnectionMyDatabase();
    private static DAO dao = null;
    private static Logger logger = null;
    private String query;

    public static DAO getInstance(){
        if (dao == null){
            dao = new UtentiMySQL();
            logger = Logger.getLogger(UtentiMySQL.class.getName());
        }
        return dao;
    }

    public boolean utenteRegistrato(String email){

        query = "SELECT * FROM utenti WHERE email LIKE ?";
        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    }

    /**
     *  lista utenti per Avatar
     *
     */
    public Utenti selectUtente(String email, String password) {

        query = "SELECT * FROM utenti WHERE email LIKE ? and password LIKE ?";
        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                utente.setIdutenti(resultSet.getString( 1 ));
                utente.setNome(resultSet.getString(2));
                utente.setCognome(resultSet.getString(3));
                utente.setData(resultSet.getString(4));
                utente.setEmail(resultSet.getString(5));
                utente.setPassword(resultSet.getString(6));
                utente.setNickname(resultSet.getString(7));
                utente.setTelefono(resultSet.getString(8));
                utente.setEtà(resultSet.getString(9));
                utente.setSesso(resultSet.getString(10));

                return utente;
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return null;
    }

    public String selectNickname(String idUtente){
        String nick;
        query = "SELECT nickname FROM utenti WHERE idutenti LIKE ?";

        try (Connection connection = DriverManager.getConnection(conn.getURL(), conn.getUserName(), conn.getPwd());

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, idUtente);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nick = resultSet.getString("nickname");
                return nick;
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


    public void aggiornaPassword(String email, String password) throws DAOException {
        query = "UPDATE utenti SET password = '"+ password +"' WHERE email ='"+ email +"'";
        aggiornamento( query );

        System.out.println( "Password aggiornata." );
    }

    @Override
    public List<Utenti> select(Utenti a) throws DAOException {

        return null;
    }

    @Override
    public void update(Utenti a) throws DAOException {

        query = "UPDATE utenti SET nome = '" +a.getNome() +
                "', cognome = '" + a.getCognome() +
                "', data_nascita = '" +a.getData() +
                "', email = '"+ a.getEmail() +
                "', password = '"+ a.getPassword() +
                "', nickname = '"+ a.getNickname() +
                "', telefono = '"+ a.getTelefono() +
                "', età = '"+ a.getEtà() +
                "', sesso = '"+ a.getSesso() +

                "' WHERE idutenti = '" + a.getId() +"'";

        aggiornamento(query);

        System.out.println("Dati Personali Aggiornati");
    }

    @Override
    public void insert(Utenti a) throws DAOException {
       // verifica(a);
        String query = "INSERT INTO utenti (nome, cognome, data_nascita, email, password, nickname, telefono, età, sesso) VALUES  ('" +
                a.getNome() + "', '" + a.getCognome() + "', '" +
                a.getData() + "', '" + a.getEmail() + "', '" +
                a.getPassword() +"', '" +a.getNickname() + "', '"+
                a.getTelefono() +"', '"+ a.getEtà() +"', '" + a.getSesso() +"')";

        //logger.info("SQL: " + query);

        aggiornamento(query);
    }

    @Override
    public void delete(Utenti a) throws DAOException {

    }

}
