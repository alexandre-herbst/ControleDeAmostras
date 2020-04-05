package projeto;

import java.io.IOException;

public class Principal {

    public static void main(String[] args) throws IOException {

        ControleCameras controleCameras = new ControleCameras();
        CamerasIP camera1 = new CamerasIP("VIP 5550 DZ IA", "ABC123", "5550DZIA" );
        CamerasIP camera2 = new CamerasIP("VIP 5850 B", "ABC321", "5850B" );
        CamerasIP camera3 = new CamerasIP("VIP 3240 D IA", "ABC111", "3240DIA" );
        CamerasIP camera5 = new CamerasIP("VIP 3240 D IA", "ABC112", "3240DIAa" );
        String caminho = "teste.txt";

        controleCameras.addCamera(camera1);
        controleCameras.addCamera(camera2);
        controleCameras.addCamera(camera3);
        controleCameras.addCamera(camera5);

        controleCameras.removerCamerasIP(camera2);

        System.out.println(controleCameras.toString());

    }
}
