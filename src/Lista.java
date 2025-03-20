import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lista {
    private String[] nomes;
    private int[] ids;
    private String[] motivos;
    private boolean[] atendidos;
    private String[] historicoAtendidos;
    private int tamanho;
    private int proximoId;
    private int historicoTamanho;
    private String ultimaOperacao;

    public Lista(int capacidade) {
        this.nomes = new String[capacidade];
        this.ids = new int[capacidade];
        this.motivos = new String[capacidade];
        this.atendidos = new boolean[capacidade];
        this.historicoAtendidos = new String[capacidade];
        this.tamanho = 0;
        this.proximoId = 1;
        this.historicoTamanho = 0;
        this.ultimaOperacao = "";
    }

    public boolean adicionarPaciente(String nome, String motivo) {
        if (tamanho >= nomes.length) {
            System.out.println("Lista cheia. Não é possível adicionar mais pacientes.");
            return false;
        }
        nomes[tamanho] = nome;
        ids[tamanho] = proximoId;
        motivos[tamanho] = motivo;
        atendidos[tamanho] = false;
        proximoId++;
        tamanho++;
        ultimaOperacao = "adicionar:" + (proximoId - 1);
        return true;
    }

    public boolean removerPaciente(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (ids[i] == id) {
                adicionarAoHistorico(i);
                ultimaOperacao = "remover:" + ids[i];
                for (int j = i; j < tamanho - 1; j++) {
                    nomes[j] = nomes[j + 1];
                    ids[j] = ids[j + 1];
                    motivos[j] = motivos[j + 1];
                    atendidos[j] = atendidos[j + 1];
                }
                tamanho--;
                return true;
            }
        }
        return false;
    }

    private void adicionarAoHistorico(int index) {
        if (historicoTamanho < historicoAtendidos.length) {
            String dataAtendimento = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            historicoAtendidos[historicoTamanho] = "ID: " + ids[index] + ", Nome: " + nomes[index] + ", Motivo: " + motivos[index] + ", Data: " + dataAtendimento;
            historicoTamanho++;
        }
    }

    public String buscarPaciente(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (ids[i] == id) {
                return "ID: " + ids[i] + ", Nome: " + nomes[i] + ", Motivo: " + motivos[i] + ", Atendido: " + (atendidos[i] ? "Sim" : "Não");
            }
        }
        return "Paciente não encontrado.";
    }

    public void listarPacientes() {
        if (tamanho == 0) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (int i = 0; i < tamanho; i++) {
            System.out.println("ID: " + ids[i] + ", Nome: " + nomes[i] + ", Motivo: " + motivos[i] + ", Atendido: " + (atendidos[i] ? "Sim" : "Não"));
        }
    }

    public void chamarProximoPaciente() {
        if (tamanho == 0) {
            System.out.println("Nenhum paciente na fila.");
            return;
        }
        System.out.println("Chamando paciente: " + nomes[0] + " (ID: " + ids[0] + ")");
        atendidos[0] = true;
        removerPaciente(ids[0]);
    }

    public void listarHistoricoAtendimentos() {
        if (historicoTamanho == 0) {
            System.out.println("Nenhum atendimento registrado no histórico.");
            return;
        }
        System.out.println("Histórico de atendimentos:");
        for (int i = 0; i < historicoTamanho; i++) {
            System.out.println(historicoAtendidos[i]);
        }
    }

    public void desfazerUltimaOperacao() {
        if (ultimaOperacao.isEmpty()) {
            System.out.println("Nenhuma operação para desfazer.");
            return;
        }
        String[] partes = ultimaOperacao.split(":");
        String operacao = partes[0];
        int id = Integer.parseInt(partes[1]);

        if (operacao.equals("adicionar")) {
            removerPaciente(id);
            System.out.println("Última adição desfeita.");
        } else if (operacao.equals("remover")) {
            System.out.println("Não é possível desfazer uma remoção neste sistema.");
        }
        ultimaOperacao = "";
    }
}
