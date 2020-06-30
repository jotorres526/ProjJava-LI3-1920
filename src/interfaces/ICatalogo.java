package interfaces;

import java.io.Serializable;
import java.util.Collection;

public interface ICatalogo extends Serializable {
    ICatalogo clone();

    void inserir(IEntidade entity);
    int tamanho();
    boolean existe(IEntidade entity);

    Collection<IEntidade> getCatalogo();
}
