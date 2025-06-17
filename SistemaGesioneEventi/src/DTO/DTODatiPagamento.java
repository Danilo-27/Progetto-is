package DTO;

public class DTODatiPagamento {
    private String numeroCarta;
    private String nomeTitolare;
    private String cognomeTitolare;
    private String dataScadenza; // Formato: YY/MM

    public DTODatiPagamento() {
    }

    public DTODatiPagamento(String numeroCarta, String nomeTitolare, String cognomeTitolare, String dataScadenza) {
        this.numeroCarta = numeroCarta;
        this.nomeTitolare = nomeTitolare;
        this.cognomeTitolare = cognomeTitolare;
        this.dataScadenza = dataScadenza;
    }


    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getNomeTitolare() {
        return nomeTitolare;
    }

    public void setNomeTitolare(String nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }

    public String getCognomeTitolare() {
        return cognomeTitolare;
    }

    public void setCognomeTitolare(String cognomeTitolare) {
        this.cognomeTitolare = cognomeTitolare;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

}
