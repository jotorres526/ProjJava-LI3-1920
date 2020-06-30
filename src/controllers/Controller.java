package controllers;
import application.Crono;
import application.Input;
import entities.Cliente;
import entities.Produto;
import entities.QueryResults;
import interfaces.*;
import models.GestVendas;
import views.ViewErros;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Classe que implementa o Controller, que implementa a interface IController
 * Classe que controla o fluxo do programa, recebe as ordens do utilizador processa os dados no modelo e
 * utiliza a view para comunicar com o utilizador.
 */
public class Controller implements IController {
    private IGestVendas model;
    private final IView view;
    private final IVErros erros;
    private final IVCollections col;
    private final double tempoExec;

    /**
     * Construtor parametrizado da classe Controller
     */
    public Controller(IGestVendas model, IView view, IVErros erros, IVCollections collections, double tempoExec){
        this.model = model;
        this.view = view;
        this.erros = erros;
        this.col = collections;
        this.tempoExec = tempoExec;
    }

    /**
     * Método principal da classe. Local onde o fluxo do programa é controlado.
     * Mostramos o menu ao utilizador e conforme as suas decisões o programa vai "correndo"
     */
    @Override
    public void start() {
        // Aqui mostramos ao utilizador o tempo que demorou para o programa "arrancar"
        this.mostraTempo(this.tempoExec);

        // Aqui mostramos o menu de opções ao utilizador e lê-se input do utilizador
        int opcao;
        do {
            double time;
            int mes, i;
            boolean valido;
            IEntidade entidade;
            String cod;

            this.view.show();
            opcao = Input.lerInt();

            switch(opcao) {
                case 1:
                    Crono.start();
                    QueryResults.IntAndColl result = model.listAndNumberOfProductsNeverBought();
                    time = Crono.stop();
                    this.mostraTempo(time);
                    paginacaoCollections(result.getListProds());
                    this.col.show("Número de produtos que nao foram compradas em nenhuma filial:", result.getTotal());
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 2:
                    valido = false;
                    do {
                        this.view.show("Mês");
                        mes = Input.lerInt();
                        if(mes > 0 && mes < 13) valido = true;
                        else this.erros.show(mes);
                    } while (!valido);
                    Crono.start();
                    int[] r = this.model.totalSalesAndDifClientsMonth(mes);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQuery2(r);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 3:
                    valido = false;
                    do {
                        this.view.show("código do Cliente");
                        cod = Input.lerString();
                        entidade = new Cliente(cod.toUpperCase());
                        if(entidade.validar()) valido = true;
                        else new ViewErros().show(cod);
                    } while(!valido);
                    Crono.start();
                    float[][] res = this.model.clientSalesDifProdsAndTotalSpent(entidade);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQuery3(res);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 4:
                    valido = false;
                    do {
                        this.view.show("Código do Produto");
                        cod = Input.lerString();
                        entidade = new Produto(cod.toUpperCase());
                        if(entidade.validar()) valido = true;
                        else new ViewErros().show(cod);
                    } while(!valido);
                    Crono.start();
                    QueryResults.FloatArrAndTwoIntArr query4 = this.model.productSalesDiffClientsAndFatPerMonth(entidade);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQuery4(query4);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 5:
                    valido = false;
                    do {
                        this.view.show("Código do Cliente");
                        cod = Input.lerString();
                        entidade = new Cliente(cod.toUpperCase());
                        if(entidade.validar()) valido = true;
                        else new ViewErros().show(cod);
                    } while(!valido);
                    Crono.start();
                    QueryResults.ProdIntCol listProds = this.model.clientMostBoughtProds(entidade);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    i = 0;
                    while(i != -1) {
                        this.view.printQuery5(listProds, i);
                        String s1 = Input.lerString();
                        if (s1.toUpperCase().equals("A") && i >= 20) i -= 20;
                        else if (s1.toUpperCase().equals("D") && i + 20 < listProds.getCol().size()) i += 20;
                        else if (s1.toUpperCase().equals("Q")) i = -1;
                        else this.erros.show(s1);
                    }
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 6:
                    do {
                        this.view.show("X");
                        i = Input.lerInt();
                    } while(i <= 0);
                    Crono.start();
                    Collection<Map.Entry<IEntidade, Integer>> map = model.xMostSelledProds(i);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQuery6(map, i);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 7:
                    Crono.start();
                    IEntidade[] ar = this.model.threeMostBuyersPerFilial();
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQuery7(ar);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 8:
                    valido = false;
                    do {
                        this.view.show("inteiro");
                        i = Input.lerInt();
                        if(i > 0) valido = true;
                        else this.erros.show(i);
                    } while (!valido);
                    Crono.start();
                    Collection<Map.Entry<IEntidade, Integer>> q8 = this.model.xClientsBoughtMoreDiffProds(i);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQuery8(q8);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 9:
                    valido = false;
                    do {
                        this.view.show("código do Produto");
                        cod = Input.lerString();
                        entidade = new Produto(cod.toUpperCase());
                        if(entidade.validar()) valido = true;
                        else new ViewErros().show(cod);
                    } while(!valido);
                    do {
                        this.view.show("Limite");
                        i = Input.lerInt();
                    } while (i <= 0);
                    Crono.start();
                    List<Map.Entry<IEntidade, Float>> printable = this.model.topNClientesComGasto(entidade, i);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQuery9(printable, i);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 10:
                    valido = false;
                    do {
                        this.view.show("código do Produto");
                        cod = Input.lerString();
                        entidade = new Produto(cod.toUpperCase());
                        if(entidade.validar()) valido = true;
                        else new ViewErros().show(cod);
                    } while(!valido);
                    Crono.start();
                    float[][] ret = model.productTotalFat(entidade);
                    time = Crono.stop();
                    i = 0;
                    this.mostraTempo(time);
                    while (i != -1) {
                        this.view.printQuery10(ret, i, entidade);
                        String s = Input.lerString();
                        if (s.toUpperCase().equals("A") && i > 0) i--;
                        else if (s.toUpperCase().equals("D") && i < 3) i++;
                        else if (s.toUpperCase().equals("Q")) i = -1;
                        else this.erros.show(s);
                    }
                    break;
                //-----------------------------------------------------------------------------------------//
                case 11:
                    Crono.start();
                    QueryResults.LastSalesData data = this.model.lastSalesData();
                    time = Crono.stop();
                    this.mostraTempo(time);
                    this.view.printQueriesEstatisticas(data);
                    this.view.enter();
                    Input.lerString();
                    break;
                //-----------------------------------------------------------------------------------------//
                case 12:
                    this.view.show("path");
                    String path = Input.lerString();
                    Crono.start();
                    this.model = new GestVendas();
                    this.model.loadSGV(path);
                    time = Crono.stop();
                    this.mostraTempo(time);
                    break;
                //-----------------------------------------------------------------------------------------//
                case 13:
                    this.view.show("nome do ficheiro");
                    String filename = Input.lerString();
                    Crono.start();
                    try {
                        this.model.carregaEstado(filename);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    time = Crono.stop();
                    this.mostraTempo(time);
                    break;
                //-----------------------------------------------------------------------------------------//
                case 14:
                    this.view.show("nome do ficheiro");
                    String file = Input.lerString();
                    Crono.start();
                    try {
                        this.model.guardaEstado(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    time = Crono.stop();
                    this.mostraTempo(time);
                    break;
                case 0:
                    break;
                default:
                    this.erros.show(opcao);
                    break;
            }
        } while(opcao != 0);
    }

    /**
     * Método que trata da paginação das Collections de maneira a ser mais fácil para o utilizador ver
     * a coleção inteira. Funciona como uma espécie de livro em que apresenta-se 20 elementos da coleção
     * por página e o utilizador pode "mudar de página" ou "fechar o livro"
     * @param col - coleçao a paginar.
     */
    private void paginacaoCollections(Collection<IEntidade> col) {
        int i = 0;
        do {
            this.col.show(col, i);
            String lido = Input.lerString();
            switch (lido.toUpperCase()) {
                case "D":
                    if (i + 20 <= col.size()){
                        i += 20;
                    }else this.erros.show(lido);
                    break;
                case "A":
                    if (i - 20 >= 0){
                        i -= 20;
                    } else this.erros.show(lido);
                    break;
                case "Q":
                    i = -1;
                    break;
                default:
                    this.erros.show(lido);
                    break;
            }
        } while (i != -1);
    }

    /**
     * Uma vez que usamos estas três linhas de código muitas vezes ao longo do código start() criamos esta função
     * para evitar tanta repetição, diminuir o tamanho do código e para ficar mais organizado.
     */
    private void mostraTempo(double time) {
        this.view.show(time);
        this.view.enter();
        Input.lerString();
    }
}