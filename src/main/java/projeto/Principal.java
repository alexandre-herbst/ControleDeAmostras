package projeto;

public class Principal {

    public static void main(String[] args) {

        ControleCameras controleCameras = new ControleCameras();
        CamerasIP camera1 = new CamerasIP("VIP 5550 DZ IA", "ABC123", "5550DZIA" );
        CamerasIP camera2 = new CamerasIP("VIP 5850 B", "ABC321", "5850B" );
        CamerasIP camera3 = new CamerasIP("VIP 3240 D IA", "ABC111", "3240DIA" );

        controleCameras.addCamera(camera1);
        controleCameras.addCamera(camera2);
        controleCameras.addCamera(camera3);

        System.out.println(controleCameras.toString());

        CamerasIP camera4 =  controleCameras.procuraPorNS("ABC321");
        System.out.println(camera4.toString());

        controleCameras.removerCamerasIP(camera3);
        controleCameras.removerCamerasIP("ABC321");

        System.out.println(controleCameras.toString());

    }
}
