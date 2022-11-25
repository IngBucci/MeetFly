package sample.mysql;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Utenti {
    private StringProperty idutenti;
    private StringProperty nome;
    private StringProperty cognome;
    private StringProperty data_nascita;
    private StringProperty email;
    private StringProperty password;
    private StringProperty nickname;
    private StringProperty telefono;
    private StringProperty età;
    private StringProperty sesso;

    /**
     * Costruttore di default
     */

    public Utenti() {
        this(null,null, null, null, null, null, null, null, null, null);
    }

    public Utenti(String id,String nome, String cognome,String data_nascita, String email, String password, String nick, String tel, String età, String sesso){
        this.idutenti = new SimpleStringProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.data_nascita = new SimpleStringProperty( data_nascita );
        this.email = new SimpleStringProperty( email );
        this.password = new SimpleStringProperty( password );
        this.nickname = new SimpleStringProperty( nick );
        this.telefono = new SimpleStringProperty(tel);
        this.età = new SimpleStringProperty(età);
        this.sesso = new SimpleStringProperty(sesso);

    }

    public String getNome() { return nome.get(); }
    public void setNome(String nome) { this.nome.set(nome); }

    public String getCognome(){return cognome.get(); }
    public void setCognome(String cognome){this.cognome.set(cognome);}

    public String getData(){return data_nascita.get(); }
    public void setData(String data){this.data_nascita.set(data);}

    public String getEmail(){return email.get(); }
    public void setEmail(String email){this.email.set(email);}

    public String getPassword(){return password.get(); }
    public void setPassword(String password){this.password.set(password);}

    public String getId(){ return idutenti.get(); }
    public void setIdutenti(String id){this.idutenti.set(id); }

    public String getNickname(){return nickname.get();}
    public void setNickname(String nickname) {
        this.nickname.set(nickname);
    }

    public String getTelefono() {
        return telefono.get();
    }
    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getEtà() {
        return età.get();
    }
    public void setEtà(String età) {
        this.età.set(età);
    }

    public String getSesso() {
        return sesso.get();
    }
    public void setSesso(String sesso) {
        this.sesso.set(sesso);
    }

    public static void main(String[] args) {}
}
