package sample.mysql;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Voli {
    private StringProperty id;
    private StringProperty aeroportoPartenza;
    private StringProperty aeroportoArrivo;
    private StringProperty orarioPartenza;
    private StringProperty orarioArrivo;


    /**
     * Costruttore di default
     */
    public Voli() {
        this(null, null, null, null,null);
    }

    public Voli(String aeroportoPartenza,String aeroportoArrivo, String orarioPartenza, String orarioArrivo, String idVolo){
        this.aeroportoArrivo = new SimpleStringProperty(aeroportoArrivo);
        this.aeroportoPartenza = new SimpleStringProperty(aeroportoPartenza);
        this.orarioArrivo = new SimpleStringProperty( orarioArrivo );
        this.orarioPartenza = new SimpleStringProperty( orarioPartenza );
        this.id = new SimpleStringProperty( idVolo );
    }

    public String getAeroportoPartenza() {
        return aeroportoPartenza.get();
    }
    public void setAeroportoPartenza(String aeroporto) {
        this.aeroportoPartenza.set(aeroporto);
    }

    public String getAeroportoArrivo(){return aeroportoArrivo.get(); }
    public void setAeroportoArrivo(String aeroporto){this.aeroportoArrivo.set(aeroporto);}

    public String getOrarioPartenza(){return orarioPartenza.get(); }

    public void setOrarioPartenza(String orarioPartenza){this.orarioPartenza.set(orarioPartenza);}

    public String getOrarioArrivo(){return orarioArrivo.get(); }

    public void setOrarioArrivo(String orarioArrivo){this.orarioArrivo.set(orarioArrivo);}

    public String getId(){ return id.get();}
    public void setId(String id){ this.id.set(id);}
}
