package interfaces;

import entities.QueryResults;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Set;

public interface IFilial extends Serializable {
    Map<IEntidade, Collection<IRegFilial>> getFilial();

    void adicionar(IEntidade entity, IRegFilial regFilial);
    void insere(IEntidade entity);

    Collection<IEntidade> clientesCompradores();
    QueryResults.IntAndColl getSetClientsBoughtMonth(int mes);

    Collection<IEntidade> top3ClientesMaisCompraram();
    QueryResults.ProdIntCol getSetOrdProdsCliente(IEntidade c);

    Map<IEntidade, Collection<IRegFilial>> clientesRegistosComProd(IEntidade prod);
    QueryResults.IntFloatAndColl dadosMensaisCliente(IEntidade c, int mes);

    Collection<IEntidade> clientesCompraramProdMes(IEntidade prod, int mes);
    Collection<IEntidade> clientesCompraramProd(IEntidade prod);

    float gastoEmProduto(IEntidade cliente, IEntidade prod);
    Map<IEntidade, Collection<IEntidade>> getTopNClientsBoughtDifProds();

    int[][] comprasMesTotal();
    List<Set<IEntidade>> clientesMes();

    IFilial clone();;
}
