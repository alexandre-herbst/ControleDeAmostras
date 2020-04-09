package projeto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ControleCameras {

    private HashMap<String, ArrayList<CamerasIP>> controle = new HashMap<>();
    private ArrayList<CamerasIP> listaAmostras;




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
            return listaCamera.add(cameraIP);
        }

        else {
            ArrayList<CamerasIP> novoModelo = new ArrayList<>();
            novoModelo.add(cameraIP);
            controle.put(cameraIP.getModelo(), novoModelo );
            return true;
        }
    }

    public  void removerCamerasIP(CamerasIP camerasIP){
        listaAmostras = retornaLista(camerasIP.getModelo());
        listaAmostras.removeIf(camera -> camera.getSerialNumber().equals(camerasIP.getSerialNumber()));
    }

    public CamerasIP buscarCamera(String modelo, String numeroDeSerie){

        listaAmostras = retornaLista(modelo);
        for (CamerasIP camera : listaAmostras) {
            if(camera.getSerialNumber().equals(numeroDeSerie)){
                return camera;
            }
        }
        throw new IllegalArgumentException("Numero de série não encontrado");
    }

    private ArrayList<CamerasIP> retornaLista(String modelo){
        if(controle.containsKey(modelo)){
            return controle.get(modelo);
        }
        else  throw new IllegalArgumentException("Modelo não cadastrado");

    }

    public String imprimeListaDoModelo(String modelo){
        listaAmostras = retornaLista(modelo);
        StringBuilder sb = new StringBuilder();

        for (CamerasIP camerasIP : listaAmostras) {
            sb.append("Modelo:  [").append(camerasIP.getModelo())
                    .append("]   Numero de Serie: [").append(camerasIP.getSerialNumber())
                    .append("]   MAC:  [").append(camerasIP.getMAC())
                    .append("]   Local: [").append(camerasIP.getLocal()).append("]").append('\n');
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
