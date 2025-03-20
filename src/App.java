import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Lista lista = new Lista(100);  
        Scanner scanner = new Scanner(System.in);
        int r = 1;

        do {
            System.out.println("________BEM VINDOS AO GERENCIAMENTO DE FILA DA CLÍNICA ______");
            System.out.println("\n _____________________________________________________________");
            System.out.println("01. Adicionar Paciente");
            System.out.println("02. Remover Paciente");
            System.out.println("03. Buscar Paciente");
            System.out.println("04. Listar Pacientes");
            System.out.println("05. Chamar Próximo Paciente");
            System.out.println("00. Sair");
            System.out.print("\n DIGITE SUA ESCOLHA: ");
            r = scanner.nextInt();
            scanner.nextLine();  
    
            switch(r) {
                case 1:
                    System.out.print("Digite o nome do paciente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o motivo da consulta: ");
                    String motivo = scanner.nextLine();
                    lista.adicionarPaciente(nome, motivo);
                    System.out.println("Paciente cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.print("Digite o ID do paciente a ser removido: ");
                    int idRemover = scanner.nextInt();
                    if (lista.removerPaciente(idRemover)) {
                        System.out.println("Paciente removido com sucesso!");
                    } else {
                        System.out.println("Paciente não encontrado!");
                    }
                    break;
                case 3:
                    System.out.print("Digite o ID do paciente para buscar: ");
                    int idBuscar = scanner.nextInt();
                    System.out.println(lista.buscarPaciente(idBuscar));
                    break;
                case 4:
                    lista.listarPacientes();
                    break;
                case 5:
                    lista.chamarProximoPaciente();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (r != 0);
        
        scanner.close();
    }
}
