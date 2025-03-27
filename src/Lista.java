public class Lista {
    private String[] nomes;
    private int[] ids;
    private String[] motivos;
    // os três de cima são os principais no sentido de armazenar os dados dos pacientes
    private boolean[] atendidos; // mediante a solicitação de atendimento, o paciente é marcado como atendido
    private String[] historicoAtendidos; // armazena os pacientes atendidos
    private int tamanho; // controle do tamanho da lista
    private int proximoId; // próximo id, lembrar de decrementar caso seja desfeita a ultima operação se for de adição
    private int historicoTamanho; 
    // os 3 abaixo são para armazenar a última operação realizada p/ controle de desfazer
    private String ultimaOperacao;
    private String ultimoNome;
    private String ultimoMotivo;
    private boolean ultimoAtendido;

    public Lista(int capacidade) {
        this.nomes = new String[capacidade];
        this.ids = new int[capacidade];          
        this.motivos = new String[capacidade];
        // os 3 acimas são como se fossem apenas um, logo o mesmo tamanho
        this.atendidos = new boolean[capacidade]; // só pode ser atendido a capacidade da lista 
        this.historicoAtendidos = new String[capacidade]; 
        this.tamanho = 0; // sem pacientes, a principio
        this.proximoId = 1; // 1 e será incrementado a cada paciente adicionado
        this.historicoTamanho = 0;   
        this.ultimaOperacao = ""; // sem operações mas ela armaena a última
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
                ultimoNome = nomes[i];
                ultimoMotivo = motivos[i];
                ultimoAtendido = atendidos[i];
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
            historicoAtendidos[historicoTamanho] = "ID: " + ids[index] + ", Nome: " + nomes[index] + ", Motivo: " + motivos[index];
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
            proximoId--;
            System.out.println("Última adição desfeita.");
        } else if (operacao.equals("remover")) {
            if (tamanho >= nomes.length) {
                System.out.println("Lista cheia. Não é possível restaurar o paciente removido.");
                return;
            }
            nomes[tamanho] = ultimoNome;
            ids[tamanho] = id;
            motivos[tamanho] = ultimoMotivo;
            atendidos[tamanho] = ultimoAtendido;
            tamanho++;
            System.out.println("Última remoção desfeita.");
        }
        ultimaOperacao = "";
    }
}
