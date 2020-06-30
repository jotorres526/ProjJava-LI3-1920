package interfaces;

import java.io.Serializable;

public interface IRegFilial extends Comparable<IRegFilial>, Serializable {
    IEntidade getProd();
    float getPrecoTotal();
    int getQuant();
    int getMes();
    float getPreco();
    char getNp();

    boolean equalsMes(int mes);
    boolean clientBoughtProductMonth(IEntidade p, int mes);
    boolean hasProduto(IEntidade prod);

    IRegFilial clone();
    int compareTo(IRegFilial r);
}