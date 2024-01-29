package classes;

public class Telefone {

    private Long id;
    private String ddd;
    private String numero;

    public Telefone(String ddd, String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return ddd + ";" + numero;
    }
}

