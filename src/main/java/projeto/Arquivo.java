package projeto;



import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Arquivo {

    private File arquivoDeCameras;
    private File arquivoDePessoas;


    public Arquivo(String arquivoCameras, String arquivoPessoas){

        this.arquivoDeCameras = new File(getClass().getClassLoader().getResource(arquivoCameras).getFile());
        this.arquivoDePessoas = new File(getClass().getClassLoader().getResource(arquivoPessoas).getFile());

    }





    public void lerArquivo(String arquivo) {
//      File arquivo = new File(getClass().getClassLoader().getResource("produtos.txt").getFile());

        // Para pegar o arquivo quando estiver dentro um .jar
        InputStream is = getClass().getResourceAsStream("/resources/" + arquivo);

        // Para pegar o arquivo quando estiver executando pela IDE
        if (is == null){
            is = getClass().getClassLoader().getResourceAsStream(arquivo);
        }
        try {
            Scanner leitor = new Scanner(is);

            // varrendo o conteúdo do arquivo linha por linha
            while (leitor.hasNextLine()) {
                System.out.println(leitor.nextLine());
            }

        } catch (Exception e) {
            System.err.println("Erro ao tentar ler o arquivo: " + e.toString());
        }
    }


















    public ControleCameras puxarArquivoCameras(ControleCameras listaDeCameras, String arquivo) throws FileNotFoundException {

            listaDeCameras = new ControleCameras();

            InputStream is = getClass().getResourceAsStream("/resources/" + arquivo);

            if (is == null){
            is = getClass().getClassLoader().getResourceAsStream(arquivo);
            }

            Scanner leitor = new Scanner(is);

            // varrendo o conteúdo do arquivo linha por linha
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();
                listaDeCameras.addCamera(montaCamera(linha));
            }

            return  listaDeCameras;
        }

    public ListaDePessoas puxarArquivoPessoas(ListaDePessoas listaDePessoas, String arquivo ) throws FileNotFoundException {
        listaDePessoas = new ListaDePessoas();

        InputStream is = getClass().getResourceAsStream("/resources/" + arquivo);

        if (is == null){
            is = getClass().getClassLoader().getResourceAsStream(arquivo);
        }


        Scanner leitor = new Scanner(is);

        while (leitor.hasNextLine()) {
            String linha = leitor.nextLine();
            listaDePessoas.inserirPessoa(montaPessoa(linha));
        }
        return listaDePessoas;
    }

    //NOK
    public void atualizarArquivoControleDeCameras(ControleCameras controleCameras) throws IOException {


        FileWriter arq = new FileWriter(arquivoDeCameras.getAbsolutePath());
        PrintWriter gravarArq = new PrintWriter(arq);
        String arquivoNovo = controleCameras.toString();

        gravarArq.printf(arquivoNovo);

        arq.close();


    }

    /**
     * @param modo = 1 imprime arquivo de cameras
     *             = 2 imprime arquivo de pessoas
     */
    public  String imprimeConteudoArquivo(int modo) {
        try {
            switch (modo) {
                case 1:
                   Scanner leitor = new Scanner(arquivoDeCameras);
                    StringBuilder sb = new StringBuilder();
                    while (leitor.hasNext()) {
                        String linha = leitor.nextLine();
                        sb.append(linha).append('\n');
                    }
                    return sb.toString();

                case 2:
                     Scanner leitor2 = new Scanner(arquivoDePessoas);
                    StringBuilder sb2 = new StringBuilder();
                    while (leitor2.hasNext()) {
                        String linha = leitor2.nextLine();
                        sb2.append(linha).append('\n');
                    }
                    return sb2.toString();

                default:
                    throw new IllegalStateException("Valor invalido " + modo);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public ControleCameras salvarCamera(CamerasIP camerasIP, ControleCameras controleCameras) throws IOException   {//throws IOException {

           FileWriter fwArquivo = null;
           BufferedWriter bw;

               // se true, ele concatena, se false ele cria ou zera o arquivo
               fwArquivo = new FileWriter(arquivoDeCameras, true); //parametro do tipo FILE
               bw = new BufferedWriter(fwArquivo);
               bw.write(camerasIP.toString() + "\n");
               bw.close();
               fwArquivo.close();


       controleCameras.addCamera(camerasIP);
       System.out.println("Camera adicionada ao Armário");
       return controleCameras;
    }

    public ListaDePessoas salvarPessoa(Pessoa pessoa, ListaDePessoas listaDePessoas) throws IOException {
        FileWriter fwArquivo = null;
        BufferedWriter bw;

        if (!arquivoDePessoas.exists()) {
            fwArquivo = new FileWriter(arquivoDePessoas, !arquivoDePessoas.exists()); //parametro do tipo FILE
            bw = new BufferedWriter(fwArquivo);
            bw.write(pessoa.toString() + "\n");
            bw.close();
            fwArquivo.close();


        } else {
            // se true, ele concatena, se false ele cria ou zera o arquivo
            fwArquivo = new FileWriter(arquivoDePessoas, true); //parametro do tipo FILE
            bw = new BufferedWriter(fwArquivo);
            bw.write(pessoa.toString() + "\n");
            bw.close();
            fwArquivo.close();
        }

        listaDePessoas.inserirPessoa(pessoa);
        return listaDePessoas;


    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString(StandardCharsets.UTF_8.name());

    }

    private CamerasIP montaCamera(String linha){

        String modelo = linha.substring(linha.indexOf('[') + 1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String numeroDeSerie = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String MAC = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+1,linha.lastIndexOf(']')+1);


        String local = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+1,linha.lastIndexOf(']')+1);

        String responsavel = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));


        CamerasIP camerasIP = new CamerasIP(modelo,numeroDeSerie,MAC);
        camerasIP.setResponsavel(responsavel);
        camerasIP.setLocal(local);
        return camerasIP;
    }
    private Pessoa montaPessoa(String linha){

        String nome = linha.substring(linha.indexOf('[') + 1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String matricula = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));
        linha = linha.substring(linha.indexOf(']')+ 1,linha.lastIndexOf(']')+1);

        String email = linha.substring(linha.indexOf('[')+1,linha.indexOf(']'));

        return new Pessoa(nome, matricula, email);

    }







}