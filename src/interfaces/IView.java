package interfaces;

import entities.QueryResults;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IView {
    void show();
    void show(double time);
    void show(String s);
    void enter();
    void printQuery2(int[] res);
    void printQuery3(float[][] res);
    void printQuery4(QueryResults.FloatArrAndTwoIntArr q4);
    void printQuery5(QueryResults.ProdIntCol listProds, int i);
    void printQuery6(Collection<Map.Entry<IEntidade, Integer>> res, int i);
    void printQuery7(IEntidade[] res);
    void printQuery8(Collection<Map.Entry<IEntidade, Integer>> q8);
    void printQuery9(List<Map.Entry<IEntidade, Float>> map, int lim);
    void printQuery10(float[][] res, int i, IEntidade prod);
    void printQueriesEstatisticas(QueryResults.LastSalesData res);
}
