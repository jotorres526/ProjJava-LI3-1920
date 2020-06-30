package interfaces;

import java.io.Serializable;

public interface IRegFat extends Serializable {
    int getQuant();
    int getNumRegVendaProdutoMonth(int mes);
    float getFatProdutoMonth(int mes);
    float[][] getFatFilialMes();

    void incrementar(IRegFat regFat);
    boolean isProductNeverBought();
    double faturado();

    IRegFat clone();
}
