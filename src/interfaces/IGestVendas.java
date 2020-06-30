package interfaces;

import entities.QueryResults;
import models.GestVendas;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IGestVendas extends Serializable {
    void loadSGV(String path);

    QueryResults.IntAndColl listAndNumberOfProductsNeverBought();
    int[] totalSalesAndDifClientsMonth(int mes);
    float[][] clientSalesDifProdsAndTotalSpent(IEntidade c);

    QueryResults.FloatArrAndTwoIntArr productSalesDiffClientsAndFatPerMonth(IEntidade prod);
    QueryResults.ProdIntCol clientMostBoughtProds(IEntidade client);
    Collection<Map.Entry<IEntidade, Integer>> xMostSelledProds(int n);

    IEntidade[] threeMostBuyersPerFilial();
    Collection<Map.Entry<IEntidade, Integer>> xClientsBoughtMoreDiffProds(int n);
    float[][] productTotalFat(IEntidade prod);

    List<Map.Entry<IEntidade, Float>> topNClientesComGasto(IEntidade prod, int limit);
    QueryResults.LastSalesData lastSalesData();

    void guardaEstado(String nomeFicheiro) throws IOException;
    GestVendas carregaEstado(String nomeFicheiro) throws IOException, ClassNotFoundException;

    }