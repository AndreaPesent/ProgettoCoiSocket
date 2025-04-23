public class Prodotto
{
    private String nome;
    private double prezzo;
    private String descrizione;
    private int quantita;
    public Prodotto(String nome, String descrizione, int quantita, double prezzo)
    {
        this.nome=nome;
        this.descrizione=descrizione;
        this.quantita=quantita;
        this.prezzo=prezzo;
    }
    public String getNome()
    {
        return nome;
    }
    public String getDescrizione()
    {
        return descrizione;
    }
    public int getQuantita()
    {
        return quantita;
    }
    public double getPrezzo()
    {
        return prezzo;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public void setDescrizione(String descrizione)
    {
        this.descrizione = descrizione;
    }
    public void setQuantita(int quantita)
    {
        this.quantita = quantita;
    }
    public void setPrezzo(double prezzo)
    {
        this.prezzo = prezzo;
    }
    public String toString()
    {
        return "Nome prodotto: " + nome + " Descrizione: " +descrizione + " Quantità rimasta: " + quantita + " Prezzo: " + prezzo;
    }
}
