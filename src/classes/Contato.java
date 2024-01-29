package classes;

import java.util.ArrayList;

public class Contato {
    private Long id;
    private String nome;
    private String sobrenome;
    private int proximoTelefoneId;
    private ArrayList<Telefone> telefones;

    public Contato(Long id, String nome, String sobrenome) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefones = new ArrayList<>();
        this.proximoTelefoneId = 1;
    }

    public void adicionarTelefone(String ddd, String numero) {
        telefones.add(new Telefone(ddd, numero));
        proximoTelefoneId++;
    }

    public void removerTelefone(String ddd, String numero) {
        if (telefones != null) {
            telefones.removeIf(t -> t.getDdd().equals(ddd) && t.getNumero().equals(numero));
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s", nome, sobrenome);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public ArrayList<Telefone> getTelefones() {
        return telefones;
    }

    public int getProximoTelefoneId() {
        return proximoTelefoneId;
    }

    public void setProximoTelefoneId(int proximoTelefoneId) {
        this.proximoTelefoneId = proximoTelefoneId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

}
