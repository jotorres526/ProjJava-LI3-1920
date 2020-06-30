package interfaces;

import java.io.Serializable;
import java.util.Collection;

public interface IFaturacao extends Serializable {
    void incrementa(IEntidade entity, IRegFat regFat);
    void initNodo(IEntidade entity);
    double totalFaturado();
    Collection<IEntidade> topNSoldProducts(int n);

    Collection<IEntidade> getProductsNeverBought();
    float[] getInfoProdutoMonth(IEntidade p, int mes);
    float[][] getFatMesFilialProd(IEntidade prod);

    IFaturacao clone();
    Collection<IEntidade> allBoughtProducts();
}