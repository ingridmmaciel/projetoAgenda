import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import classes.Contato;
import classes.Telefone;

public class Agenda {

    private static Long proximoId = 1L;

    public static void main(String[] args) throws IOException {
        ArrayList<Contato> agenda = new ArrayList<>();
        Scanner ler = new Scanner(System.in);
        int opcao;

        importar(agenda);

        do {
            System.out.println("##################\n" +
                    "##### AGENDA #####\n" +
                    "##################\n");
            System.out.printf("[ 1 ] Adicionar Contato\n");
            System.out.printf("[ 2 ] Remover Contato\n");
            System.out.printf("[ 3 ] Listar Contatos\n");
            System.out.printf("[ 4 ] Editar Contato\n");
            System.out.printf("[ 0 ] Sair\n");
            System.out.printf("\nOpção Desejada: ");

            opcao = ler.nextInt();

            switch (opcao) {
                case 1:
                    incluir(agenda, ler);
                    break;
                case 2:
                    excluir(agenda, ler);
                    break;
                case 3:
                    listar(agenda);
                    break;
                case 4:
                    editar(agenda, ler);
                    break;
            }

            System.out.printf("\n\n");

        } while (opcao != 0);

        exportar(agenda);
    }

    public static void importar(ArrayList<Contato> agenda) {
        try {
            FileReader arq = new FileReader("src/agenda.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            while (linha != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 3) {
                    Contato contato = new Contato(
                            Long.parseLong(dados[0]),
                            dados[1],
                            dados[2]
                    );
                    for (int i = 3; i < dados.length; i += 2) {
                        if (i + 1 < dados.length) {
                            contato.adicionarTelefone(dados[i], dados[i + 1]);
                        } else {
                            System.err.println("Erro: Linha mal formatada no arquivo.");
                        }
                    }
                    agenda.add(contato);
                    proximoId = Math.max(proximoId, contato.getId() + 1);
                } else {
                    System.err.println("Erro: Linha mal formatada no arquivo.");
                }
                linha = lerArq.readLine();
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.", e.getMessage());
        }
    }

    public static void exportar(ArrayList<Contato> agenda) throws IOException {
        FileWriter arq = new FileWriter("src/agenda.txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        for (Contato contato : agenda) {
            gravarArq.printf("%d;%s;%s", contato.getId(), contato.getNome(), contato.getSobrenome());

            ArrayList<Telefone> telefones = contato.getTelefones();
            for (Telefone telefone : telefones) {
                gravarArq.printf(";%s", telefone);
            }

            gravarArq.printf("%n");
        }
        gravarArq.close();
    }

    public static void incluir(ArrayList<Contato> agenda, Scanner ler) {
        String nome, sobrenome, telefone, ddd;

        System.out.printf("\nInforme o nome do contato:\n");
        ler.nextLine();
        nome = ler.nextLine();

        System.out.printf("\nInforme o sobrenome do contato:\n");
        sobrenome = ler.nextLine();

        if (contatoExiste(agenda, nome, sobrenome)) {
            System.out.println("Contato já existe na agenda.");
            return;
        }

        Contato novoContato = new Contato(proximoId++, nome, sobrenome);

        System.out.println("Deseja adicionar telefone(s)? (s/n)");
        char resposta = ler.next().charAt(0);

        ler.nextLine();

        if (resposta == 's' || resposta == 'S') {
            while (true) {
                System.out.printf("\nInforme o DDD do telefone do contato (ou digite 'sair' para encerrar):\n");
                ddd = ler.nextLine();

                if (ddd.equalsIgnoreCase("sair")) {
                    break;
                }

                if (ddd.length() == 2 && ddd.matches("\\d+")) {
                    System.out.printf("\nInforme o número do telefone do contato:\n");
                    telefone = ler.nextLine();

                    if (telefoneExiste(novoContato.getTelefones(), ddd, telefone)) {
                        System.out.println("Telefone já cadastrado para este contato.");
                        continue;
                    }

                    novoContato.adicionarTelefone(ddd, telefone);
                } else {
                    System.out.println("Erro: DDD inválido. Deve conter exatamente dois números.");
                }
            }
        }

        agenda.add(novoContato);
        System.out.println("Contato adicionado com sucesso.");
    }

    public static void excluir(ArrayList<Contato> agenda, Scanner ler) {
        long id;

        listar(agenda);

        System.out.printf("\nInforme o ID a ser excluído:\n");
        id = ler.nextLong();

        try {
            agenda.removeIf(contato -> contato.getId() == id);
            System.out.println("Contato removido com sucesso.");
        } catch (Exception e) {
            System.out.printf("\nErro: ID inválido (%s).\n\n", e.getMessage());
        }
    }

    public static void listar(ArrayList<Contato> agenda) {
        System.out.printf("\nListando os itens da Agenda:\n");
        for (Contato contato : agenda) {
            System.out.printf("ID %d- %s\n", contato.getId(), contato);
            ArrayList<Telefone> telefones = contato.getTelefones();
            for (Telefone telefone : telefones) {
                System.out.printf("    Telefone: %s\n", telefone);
            }
        }
        System.out.printf("---------------------------------------\n");
    }

    public static void editar(ArrayList<Contato> agenda, Scanner ler) {
        listar(agenda);

        System.out.printf("\nInforme o ID do contato a ser editado:\n");
        long id = ler.nextLong();

        if (id >= 0 && id < proximoId) {
            Contato contato = agenda.stream().filter(c -> c.getId() == id).findFirst().orElse(null);

            if (contato != null) {
                System.out.println("O que deseja editar?");
                System.out.println("[1] Nome");
                System.out.println("[2] Sobrenome");
                System.out.println("[3] Telefone");
                int opcaoEdicao = ler.nextInt();

                switch (opcaoEdicao) {
                    case 1:
                        System.out.println("Informe o novo nome:");
                        String novoNome = ler.next();
                        contato.setNome(novoNome);
                        break;
                    case 2:
                        System.out.println("Informe o novo sobrenome:");
                        String novoSobrenome = ler.next();
                        contato.setSobrenome(novoSobrenome);
                        break;
                    case 3:
                        System.out.println("Informe o DDD do telefone a ser editado:");
                        String dddEditar = ler.next();
                        System.out.println("Informe o número do telefone a ser editado:");
                        String numeroEditar = ler.next();

                        if (telefoneExiste(contato.getTelefones(), dddEditar, numeroEditar)) {
                            System.out.println("Telefone já cadastrado para este contato.");
                        } else {
                            System.out.println("Informe o novo DDD do telefone:");
                            String novoDDD = ler.next();
                            System.out.println("Informe o novo número do telefone:");
                            String novoNumero = ler.next();
                            contato.adicionarTelefone(novoDDD, novoNumero);
                            System.out.println("Telefone editado com sucesso.");
                        }
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else {
                System.out.println("Contato não encontrado.");
            }
        } else {
            System.out.println("ID inválido.");
        }
    }

    private static boolean contatoExiste(ArrayList<Contato> agenda, String nome, String sobrenome) {
        return agenda.stream().anyMatch(c -> c.getNome().equalsIgnoreCase(nome) && c.getSobrenome().equalsIgnoreCase(sobrenome));
    }

    private static boolean telefoneExiste(ArrayList<Telefone> telefones, String ddd, String numero) {
        return telefones.stream().anyMatch(t -> t.getDdd().equals(ddd) && t.getNumero().equals(numero));
    }
}
