package projeto;

import java.io.IOException;
import java.util.Scanner;

public class Principal {


    public static void main(String[] args) throws IOException {

        ControleCameras controleCameras = new ControleCameras();
        ListaDePessoas listaDePessoas = new ListaDePessoas();
        String caminhoAmostras = "TodasAsAmostras.txt";
        String caminhoPessoas = "TodasPessoas.txt";
        Arquivo arq = new Arquivo(caminhoAmostras,caminhoPessoas);

        int menu = 0;
        int flagRemover = 0;
        while (menu == 0) {

           if(flagRemover != 0){

               System.out.print("Informe o Numero de Série da Camera que deseja remover: ");
               Scanner teclado = new Scanner(System.in);
               String NS = teclado.next();
               if(controleCameras.removerCamerasIP(NS)){
                   System.out.println("Camera removida");
               }
               else System.out.println("NS não encontrado para ser removido");

               flagRemover = 0;
           }



            //Sempre deve ser informado no inicio da execução

            controleCameras = arq.puxarArquivoCameras(controleCameras);
            listaDePessoas = arq.puxarArquivoPessoas(listaDePessoas);
            arq.atualizarArquivoControleDeCameras(controleCameras);

            System.out.println("-------------Controle de Amostras-------------");
            System.out.println("Escolha entre uma das opções");
            System.out.println("1- Inserir  uma Câmera no Armário");
            System.out.println("2- Remover uma Câmera no Armário");
            System.out.println("2- Buscar por uma câmera");
            System.out.println("3- Retirar uma Camera para empréstimo");
            System.out.println("4- Devolver uma Câmera");

            Scanner teclado = new Scanner(System.in);
            int opcao = teclado.nextInt();

            switch (opcao) {
                case 1:
                    subirTela();
                    String modelo = escolheModelo();
                    subirTela();
                    String numeroDeSerie = recebeNS();
                    subirTela();
                    String MAC = recebeMAC();
                    subirTela();

                    if(arq.salvarCamera(new CamerasIP(modelo,numeroDeSerie,MAC), controleCameras)) {
                        System.out.println("Camera adicionada ao Armário");
                    }
                    else System.out.println("Este NS ja está cadastrado!!  Não foi Adicionado ao armário");
                    break;

                case 2:
                    flagRemover++;
                    subirTela();
                    break;


                default:
                    System.out.println("Não foi inserida uma opcao valida");


            }
        }





    }



    private static void subirTela(){
        for (int i = 0; i <20 ; i++) {
            System.out.println("  ");
        }
    }
    private static String escolheModelo(){
        imprimeModelos();
        System.out.print("Selecione o Modelo da Câmera (pelo numero): ");
        Scanner teclado = new Scanner(System.in);
        int numero = teclado.nextInt();
        switch (numero){
            case 1 : return "VIP 5550 DZ IA";
            case 2 : return "VIP 5550 Z IA";
            case 3 : return "VIP 3430 D G2";
            case 4 : return "VIP 3430 B G2";
            case 5 : return "VIP 3230 D G2";
            case 6 : return "VIP 3230 B G2";
            case 7 : return "VIP 3240 D IA";
            case 8 : return "VIP 3240 IA";
            default: throw new IllegalArgumentException("Numero não Válido");
        }
    }
    private static void imprimeModelos(){
        System.out.println("1 - VIP 5550 DZ IA");
        System.out.println("2 - VIP 5550 Z IA");
        System.out.println("3 - VIP 3430 D G2");
        System.out.println("4 - VIP 3430 B G2");
        System.out.println("5 - VIP 3230 D G2");
        System.out.println("6 - VIP 3230 B G2");
        System.out.println("7 - VIP 3240 D IA");
        System.out.println("8 - VIP 3240  IA");
    }
    private static String recebeNS(){
        System.out.println("Informe o Numero de Série: ");
        Scanner teclado = new Scanner(System.in);
        return teclado.next();
    }
    private static String recebeMAC(){
        System.out.println("Informe o MAC: ");
        Scanner teclado = new Scanner(System.in);
        return teclado.next();
    }



}
