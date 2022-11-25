package sample.mysql;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UtentiHasVoli {
    private StringProperty idVolo;
    private StringProperty idUtenti;
    private StringProperty indirizzo;
    private StringProperty citta;
    private StringProperty mezzo;
    private StringProperty nickname;
    private UtentiMySQL utentiMySQL;


    public UtentiHasVoli() {
        this( null, null, null, null, null, null);
    }

    public UtentiHasVoli(String idUtenti, String idVolo, String citta, String indirizzo, String mezzo, String nickname) {
        this.idUtenti = new SimpleStringProperty( idUtenti );
        this.idVolo = new SimpleStringProperty( idVolo );
        this.indirizzo = new SimpleStringProperty( indirizzo );
        this.citta = new SimpleStringProperty( citta );
        this.mezzo = new SimpleStringProperty( mezzo );
        this.nickname = new SimpleStringProperty( nickname );
    }

    public String getIdVolo() {
        return idVolo.get();
    }

    public void setIdVolo(String id) {
        this.idVolo.set( id );
    }

    public String getIdUtente() {
        return idUtenti.get();
    }

    public String getNickname(){return nickname.get(); }

    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public void setIdUtente(String id) {
        this.idUtenti.set( id );
    }

    public String getIndirizzoDestinazione() {
        return indirizzo.get();
    }

    public void setIndirizzoDestinazione(String indirizzo) {
        this.indirizzo.set( indirizzo );
    }

    public String getCitta() {
        return citta.get();
    }

    public void setCitta(String c) {
        this.citta.set( c );
    }

    public String getMezzo() {
        return mezzo.get();
    }

    public void setMezzo(String n) {
        this.mezzo.set( n );
    }
}
