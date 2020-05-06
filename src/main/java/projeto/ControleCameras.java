package projeto;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ControleCameras {

    private HashMap<String, ArrayList<CamerasIP>> controle = new HashMap<>();
    private ArrayList<CamerasIP> listaAmostras = new ArrayList<>();
    private ArrayList<String> listaModelos = new ArrayList<>();



    /**
     * Este método irá adicionar uma câmera nova no controle.
     * Verifica Numero de Série e MAC duplicados.
     *
     * @param cameraIP = camera a ser adicionada.
     * @return false = não foi possível inserir a amostra no controle
     *  true = camera inserida no controle de amostras
     */

    public boolean addCamera(CamerasIP cameraIP)    {

        if (controle.containsKey(cameraIP.getModelo())){
            ArrayList<CamerasIP> listaCamera = controle.get(cameraIP.getModelo());

            // Validar Dados
            for (CamerasIP camera : listaCamera) {

                int flag = 0;

                //Valida NS
                if(cameraIP.getSerialNumber().equals(camera.getSerialNumber())){
                    System.out.println(camera.getModelo() + " - Esse numero de serie já está registrado:  " + camera.getSerialNumber());
                    flag++;
                }

                //Valida MAC
                if(cameraIP.getMAC().equals(camera.getMAC())){
                    System.out.println(camera.getModelo() + " - Esse MAC já está registrado ou está clonado:  " + camera.getMAC());
                    flag++;
                }
                if (flag > 0){

                    return  false;
                }
            }

            listaCamera.add(cameraIP);
            controle.put(cameraIP.getModelo(), listaCamera);

            return true;
        }


        else {
            ArrayList<CamerasIP> novoModelo = new ArrayList<>();
            novoModelo.add(cameraIP);
            controle.put(cameraIP.getModelo(), novoModelo );
            return true;
        }
    }

    public  boolean removerCamerasIP(String numeroDeSerie){ //Necessario remover camera pelo NS

         listaModelos = retornaTodosOsModelos();
        for (String modelo : listaModelos) {
            listaAmostras = retornaListaDoModelo(modelo);
            for (CamerasIP cameraip : listaAmostras) {
                if(cameraip.getSerialNumber().equals(numeroDeSerie)){
                    listaAmostras.remove(cameraip);
                    return true;
                }
            }
        }
        return false;

    }

    public CamerasIP buscarCamera(String numeroDeSerie){

        listaModelos = retornaTodosOsModelos();
        for (String modelo : listaModelos) {
            listaAmostras = retornaListaDoModelo(modelo);
            for (CamerasIP cameraip : listaAmostras) {
                if(cameraip.getSerialNumber().equals(numeroDeSerie)){
                    return cameraip;
                }
            }
        }
        throw new IllegalArgumentException("Numero de série não encontrado");
    }

    public CamerasIP bucarCameraMAC(String MAC){

        listaModelos = retornaTodosOsModelos();
        for (String modelo : listaModelos) {
            listaAmostras = retornaListaDoModelo(modelo);
            for (CamerasIP cameraip : listaAmostras) {
                if(cameraip.getMAC().equals(MAC)){
                    return cameraip;
                }
            }
        }
        throw new IllegalArgumentException("MAC não encontrado");
    }


    public CamerasIP retornaGenerica(String modelo){

        for (CamerasIP camerasIP : retornaListaDoModelo(modelo)) {
            if(camerasIP.getResponsavel().equals("livre")){
                return camerasIP;
            }
        }
        throw new IllegalArgumentException("Essa câmera não possui amostas livres");
    }

    public void alterarResponsavel(String numSerie, String resposavel){
        CamerasIP camerasIP = buscarCamera(numSerie);
        camerasIP.setResponsavel(resposavel);
    }

    public boolean verificaCamera(String numeroDeSerie){

        listaModelos = retornaTodosOsModelos();
        for (String modelo : listaModelos) {
            listaAmostras = retornaListaDoModelo(modelo);
            for (CamerasIP cameraip : listaAmostras) {
                if(cameraip.getSerialNumber().equals(numeroDeSerie)){
                    return true;
                }
            }
        }
       return false;
    }

    public  ArrayList<String> retornaTodosOsModelos(){
        listaModelos = new ArrayList<>();
        listaModelos.addAll(controle.keySet());
        return listaModelos;
    }

    public  ArrayList<CamerasIP> retornaListaDoModelo(String modelo){
        if(controle.containsKey(modelo)){
            return controle.get(modelo);
        }
        else  throw new IllegalArgumentException("Modelo não cadastrado");

    }

    public ArrayList<CamerasIP> retornaListaDoResposavel(Pessoa resposavel){

        ArrayList<CamerasIP> listaDoResponsavel = new ArrayList<>();

        ArrayList<String> modelos = retornaTodosOsModelos();

        for (String modelo : modelos) {
            ArrayList<CamerasIP> camerasIPS = retornaListaDoModelo(modelo);
            for (CamerasIP amostra : camerasIPS) {
                if(amostra.getResponsavel().equals(resposavel.getNome())){
                    listaDoResponsavel.add(amostra);
                }
            }
        }
        return  listaDoResponsavel;
    }

    public String imprimeListaDoModelo(String modelo){
        listaAmostras = retornaListaDoModelo(modelo);
        StringBuilder sb = new StringBuilder();

        for (CamerasIP camerasIP : listaAmostras) {
            sb.append("Modelo:  [").append(camerasIP.getModelo())
                    .append("]   Numero de Serie: [").append(camerasIP.getSerialNumber())
                    .append("]   MAC:  [").append(camerasIP.getMAC())
                    .append("]   Local: [").append(camerasIP.getLocal())
                    .append("]   Responsavel: [").append(camerasIP.getResponsavel()).append("]").append('\n');
        }
        return sb.toString();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (String modelos : controle.keySet()) {
            sb.append(imprimeListaDoModelo(modelos));
        }
        return sb.toString();
    }

}
