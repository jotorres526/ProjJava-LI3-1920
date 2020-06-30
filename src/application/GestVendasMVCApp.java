package application;
import controllers.Controller;
import interfaces.*;
import models.GestVendas;
import views.ViewCollections;
import views.ViewErros;
import views.ViewMenuPrincipal;

public class GestVendasMVCApp {

    private static IGestVendas createData() {
        IGestVendas igv = new GestVendas();
        igv.loadSGV("");
        return igv;
    }

    public static void main(String[] args) {
        Crono.start();
        IGestVendas model = createData();
        double time = Crono.stop();
        IView view = new ViewMenuPrincipal();
        IVErros erros = new ViewErros();
        IVCollections collections = new ViewCollections();
        IController controller = new Controller(model, view, erros, collections, time);
        controller.start();
        System.exit(0);
    }
}