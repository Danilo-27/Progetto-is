package DTO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTOEvento è una classe Data Transfer Object utilizzata per rappresentare i dati relativi a un evento.
 * La classe include informazioni come il titolo, la descrizione, la data, l'ora, il luogo, il costo,
 * la capienza e il numero di biglietti venduti.
 *
 * È progettata per semplificare il trasferimento di dati tra i livelli di un'applicazione o tra sistemi diversi.
 */
public class DTOEvento {
    /**
     * Rappresenta il titolo dell'evento.
     * Questo campo memorizza una stringa che descrive il nome o il titolo assegnato all'evento.
     */
    private String titolo;
    /**
     * Rappresenta una descrizione dettagliata dell'evento.
     * Può includere informazioni aggiuntive per fornire un contesto più ampio sull'evento.
     */
    private String descrizione;
    /**
     * Rappresenta la data in cui si svolge l'evento.
     */
    private LocalDate data;
    /**
     * Rappresenta l'orario in cui si svolge l'evento.
     */
    private LocalTime ora;
    /**
     * Rappresenta il luogo in cui si svolgerà l'evento.
     */
    private String luogo;
    /**
     * Rappresenta il costo dell'evento, espresso come intero.
     * Può indicare il prezzo di partecipazione o un costo associato all'evento.
     */
    private int costo;
    /**
     * Rappresenta la capienza massima di partecipanti per l'evento.
     */
    private int capienza;
    /**
     * Indica il numero di partecipanti attuali all'evento.
     */
    private int partecipanti;
    /**
     * Rappresenta il numero di biglietti già venduti per l'evento.
     */
    private int bigliettiVenduti;

    /**
     * Costruttore predefinito della classe DTOEvento.
     * Inizializza un'istanza vuota dell'oggetto senza impostare alcun valore
     * per gli attributi della classe.
     */
    public DTOEvento() {
    }

    /**
     * Costruttore della classe DTOEvento che inizializza un'istanza con tutti gli attributi relativi
     * a un evento, inclusi dati informativi, logistici e operativi.
     *
     * @param titolo Il titolo dell'evento.
     * @param descrizione La descrizione dettagliata dell'evento.
     * @param data La data in cui si svolge l'evento.
     * @param ora L'orario di inizio dell'evento.
     * @param luogo Il luogo in cui si tiene l'evento.
     * @param costo Il costo del biglietto per l'evento.
     * @param capienza Il numero massimo di partecipanti ammessi all'evento.
     * @param bigliettiVenduti Il numero di biglietti già venduti per l'evento.
     */
    public DTOEvento(String titolo, String descrizione, LocalDate data, LocalTime ora, String luogo, int costo, int capienza,int bigliettiVenduti) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.ora = ora;
        this.luogo = luogo;
        this.costo = costo;
        this.capienza = capienza;
        this.bigliettiVenduti = bigliettiVenduti;
    }

    /**
     * Imposta il titolo dell'evento.
     *
     * @param titolo Il titolo da assegnare all'evento. Non può essere null o vuoto.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Imposta la descrizione dell'evento.
     *
     * @param descrizione La descrizione dell'evento da assegnare. Deve fornire
     *                    informazioni dettagliate e significative relative
     *                    all'evento.
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Imposta la data relativa all'evento.
     *
     * @param data La data da assegnare all'evento. Deve essere un'entità di tipo LocalDate
     *             rappresentante una data valida.
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Imposta l'ora dell'evento.
     *
     * @param ora L'ora dell'evento da assegnare, rappresentata come un oggetto di tipo LocalTime.
     */
    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    /**
     * Imposta il valore del luogo associato all'evento.
     *
     * @param luogo Una stringa che rappresenta il luogo in cui si terrà l'evento.
     */
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    /**
     * Imposta il valore del costo associato all'evento.
     *
     * @param costo Il costo dell'evento espresso come valore intero. Deve essere
     *              maggiore o uguale a zero per rappresentare un importo valido.
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * Imposta il valore della capienza massima per l'evento.
     *
     * @param capienza La capienza massima del luogo dove si terrà l'evento.
     *                 Deve essere un valore intero positivo che rappresenta
     *                 il numero massimo di partecipanti consentiti.
     */
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    /**
     * Imposta il numero di partecipanti relativi a un evento.
     *
     * @param partecipanti Il numero di partecipanti all'evento. Deve essere un valore intero
     *                     non negativo che rappresenta il conteggio dei partecipanti.
     */
    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    /**
     * Restituisce il titolo associato all'evento.
     *
     * @return Il titolo dell'evento come una stringa.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Restituisce la descrizione dell'evento.
     *
     * @return La descrizione dell'evento come stringa.
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce la data associata all'evento.
     *
     * @return La data dell'evento come oggetto di tipo {@link LocalDate}.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Restituisce l'orario dell'evento.
     *
     * @return Un oggetto LocalTime che rappresenta l'orario dell'evento.
     */
    public LocalTime getOra() {
        return ora;
    }

    /**
     * Restituisce il luogo associato all'evento rappresentato dall'istanza corrente.
     *
     * @return Una stringa che rappresenta il luogo dell'evento.
     */
    public String getLuogo() {
        return luogo;
    }

    /**
     * Restituisce il costo associato all'evento.
     *
     * @return Il costo dell'evento come valore intero.
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Restituisce la capienza massima prevista per l'evento.
     *
     * @return Un valore intero che rappresenta la capienza massima dell'evento.
     */
    public int getCapienza() {
        return capienza;
    }

    /**
     * Restituisce il numero di partecipanti associati all'evento.
     *
     * @return Il numero di partecipanti come valore intero.
     */
    public int getPartecipanti() {
        return partecipanti;
    }

    /**
     * Restituisce il numero totale di biglietti venduti per un evento.
     *
     * @return Il numero di biglietti venduti, rappresentato come un valore intero.
     */
    public int getBigliettiVenduti() {
        return bigliettiVenduti;
    }

    /**
     * Imposta il numero di biglietti venduti per un evento.
     *
     * @param bigliettiVenduti Il numero di biglietti venduti da assegnare all'evento.
     */
    public void setBigliettiVenduti(int bigliettiVenduti) {
        this.bigliettiVenduti = bigliettiVenduti;
    }
}