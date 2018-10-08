import javafx.beans.property.SimpleStringProperty;

public class Mitarbeiter {


    //Konstruktor fuer das Hinzufuegen
    Mitarbeiter(String mitarbeiterID, String vorname, String nachname, String gehalt, String abteilung, String hoehe, String auszahlungsdatum) {
        this.mitarbeiterID = new SimpleStringProperty(mitarbeiterID);
        this.vorname = new SimpleStringProperty(vorname);
        this.nachname = new SimpleStringProperty(nachname);
        this.gehalt = new SimpleStringProperty(gehalt);
        this.abteilung = new SimpleStringProperty(abteilung);
        this.hoehe = new SimpleStringProperty(hoehe);
        this.auszahlungsdatum = new SimpleStringProperty(auszahlungsdatum);
    }

    //Standard-Konstruktor
    Mitarbeiter() {
    }


    SimpleStringProperty mitarbeiterID = new SimpleStringProperty();
    SimpleStringProperty vorname = new SimpleStringProperty();
    SimpleStringProperty nachname = new SimpleStringProperty();
    SimpleStringProperty gehalt = new SimpleStringProperty();
    SimpleStringProperty abteilung = new SimpleStringProperty();
    SimpleStringProperty abteilungsname = new SimpleStringProperty();
    SimpleStringProperty stadt = new SimpleStringProperty();
    SimpleStringProperty land = new SimpleStringProperty();
    SimpleStringProperty hoehe = new SimpleStringProperty();
    SimpleStringProperty auszahlungsdatum = new SimpleStringProperty();


    //set
    void setMitarbeiterID(String mitarbeiterID) {
        this.mitarbeiterID.set(mitarbeiterID);
    }

    void setVorname(String vorname) {
        this.vorname.set(vorname);
    }

    void setNachname(String nachname) {
        this.nachname.set(nachname);
    }

    void setGehalt(String gehalt) {
        this.gehalt.set(gehalt);
    }

    void setAbteilung(String abteilung) { this.abteilung.set(abteilung); }

    void setAbteilungsname(String abteilungsname) {
        this.abteilungsname.set(abteilungsname);
    }

    void setStadt(String stadt) {
        this.stadt.set(stadt);
    }

    void setLand(String land) {
        this.land.set(land);
    }

    void setBonuszahlung(String bonuszahlung) {
        this.hoehe.set(bonuszahlung);
    }

    void setAuszahlungsdatum(String auszahlungsdatum) {
        this.auszahlungsdatum.set(auszahlungsdatum);
    }

    //get

    SimpleStringProperty getID() {
        return this.mitarbeiterID;
    }

    SimpleStringProperty getVorname() {
        return this.vorname;
    }

    SimpleStringProperty getNachname() {
        return this.nachname;
    }

    SimpleStringProperty getGehalt() {
        return this.gehalt;
    }

    SimpleStringProperty getAbteilung() {
        return this.abteilung;
    }

    SimpleStringProperty getAbteilungsname() {
        return abteilungsname;
    }

    SimpleStringProperty getStadt() {
        return stadt;
    }

    SimpleStringProperty getLand() {
        return land;
    }

    SimpleStringProperty getHoehe() {
        return hoehe;
    }

    SimpleStringProperty getAuszahlungsdatum() {

        return auszahlungsdatum;
    }

    String getEuro() {
        return gehalt.get();
    }

}
