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

    // Modifique o método adicionarTelefone para usar o próximo ID de telefone
    public void adicionarTelefone(String ddd, String numero) {
        telefones.add(new Telefone(ddd, numero));
        proximoTelefoneId++;
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

    // Adicione o método setter para o próximo ID de telefone
    public void setProximoTelefoneId(int proximoTelefoneId) {
        this.proximoTelefoneId = proximoTelefoneId;
    }

    // Adicione os métodos setters para o nome e sobrenome
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

}
