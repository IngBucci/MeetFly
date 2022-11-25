package sample.mysql;

import java.sql.*;

public class ConnectionMyDatabase {

    public final static String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
    public final static String HOST = "localhost";
    public final static String USERNAME = "utenti";
    public final static String PWD = "utenti";
    public final static String SCHEMA = "progetto";
    public final static String PARAMETERS = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public final static String URL = "jdbc:mysql://"+HOST+":3306/"+SCHEMA+PARAMETERS;

    //private String driverName = "com.mysql.cj.jdbc.Driver";
    private String host = "localhost";
    private String userName = "utenti";
    private String pwd = "utenti";
    private String schema = "progetto";
    private String parameters = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private String url = "jdbc:mysql://"+host+":3306/"+schema+parameters;

    public String getURL(){
        return url;
    }
    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getSchema() {
        return schema;
    }


    public void setURL(String url){
        this.url = url;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    static{
        try {
            Class.forName(DRIVERNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static ConnectionMyDatabase impostazioniInizialiDatabase = null;

    public static ConnectionMyDatabase getImpostazioniInizialiDatabase(){
        if (impostazioniInizialiDatabase == null){
            impostazioniInizialiDatabase = getDefaultImpostazioniDatabase();
        }
        return impostazioniInizialiDatabase;
    }

    public static ConnectionMyDatabase getDefaultImpostazioniDatabase(){
        ConnectionMyDatabase impostazioniMySQL = new ConnectionMyDatabase();
        impostazioniMySQL.host = HOST;
        impostazioniMySQL.userName = USERNAME;
        impostazioniMySQL.schema = SCHEMA;
        impostazioniMySQL.pwd = PWD;
        return impostazioniMySQL;
    }

    public static void setDefaultImpostazionidatabase(ConnectionMyDatabase impostazioni){
        impostazioniInizialiDatabase = impostazioni;
    }

    public static Statement getStatement() throws SQLException{
        if (impostazioniInizialiDatabase == null){
            impostazioniInizialiDatabase = getDefaultImpostazioniDatabase();
        }
        System.out.println( "Connesione riuscita se non ci sono errori dopo." );
        return DriverManager.getConnection("jdbc:mysql://" + impostazioniInizialiDatabase.host  + "/" + impostazioniInizialiDatabase.schema + PARAMETERS, impostazioniInizialiDatabase.userName, impostazioniInizialiDatabase.pwd).createStatement();
    }

    public static void closeStatement(Statement st) throws SQLException{
        st.getConnection().close();
        st.close();
    }
}
