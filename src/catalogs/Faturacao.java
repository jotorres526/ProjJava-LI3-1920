package catalogs;
import entities.RegistoFat;
import interfaces.IEntidade;
import interfaces.IFaturacao;
import interfaces.IRegFat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que implementa  Faturacao, que implementa a interface IFaturacao.
 * Nesta classe, são armazenados todos os produtos num HashMap, em que a cada Produto está associado um RegistoFat.
 */
public class Faturacao implements IFaturacao {
    // Variável de instância
        //Tem um Produto como key e um RegistoFaturacao como valor
    private final Map<IEntidade, IRegFat> faturacao;

    /*##############################################################################################*/
    /**
     * Construtores da Classe Faturacao.
     * Declaração dos contrutores por omissão e de cópia.
     */

    public Faturacao() {
        this.faturacao = new HashMap<>();
    }

    public Faturacao(Faturacao f) {
        this.faturacao = f.getFaturacao();
    }
    /*##############################################################################################*/
    /**
     * Getter.
     * @return cópia de this.faturacao.
     */
    public Map<IEntidade, IRegFat> getFaturacao() {
        Map<IEntidade, IRegFat> ret = new HashMap<>();
        for(Map.Entry<IEntidade, IRegFat> entry : this.faturacao.entrySet()) {
            IEntidade keyClone = entry.getKey().clone();
            IRegFat valueClone = entry.getValue().clone();
            ret.put(keyClone, valueClone);
        }
        return ret;
    }
    /*##############################################################################################*/

    /**
     * Método que procura um produto no map e que incrementa o seu registoFat conforme o
     * registo passado como argumento.
     * @param entity - entidade a procurar
     * @param regFat - registo com os valores a incrementar
     */
    @Override
    public void incrementa(IEntidade entity, IRegFat regFat) {
        this.faturacao.get(entity).incrementar(regFat);
    }

    /**
     * Método que inicializa e insere um nodo no mapa.
     * Para inicializar o nodo põe-se o produto na key e incializa-se um registoFat para os value.
     * @param entity - produto para a key.
     */
    @Override
    public void initNodo(IEntidade entity) {
        this.faturacao.put(entity.clone(), new RegistoFat());
    }
    /*##############################################################################################*/
    /**
     * Metodo que determina se duas faturações são iguais.
     * Para serem considerados iguais têm de ter os maps iguais.
     * @param o - objeto para comparar com this
     * @return true se forem iguais.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faturacao faturacao = (Faturacao) o;
        return Objects.equals(this.faturacao, faturacao.faturacao);
    }

    /**
     * Método que calcula a posição que this irá ter numa Hash Table.
     * @return número de Hash de this.
     */
    @Override
    public int hashCode() {
        return Objects.hash(faturacao);
    }

    /**
     * Metodo que devolve a representação em String da Faturacao.
     * @return String com a representação do faturacao.
     */
    @Override
    public String toString() {
        return "Faturacao{" +
                "faturacao=" + faturacao +
                '}';
    }

    /**
     * Metodo que faz uma cópia do objeto receptor da mensagem.
     * @return Faturacao clone da instancia que recebe esta mensagem.
     */
    @Override
    public Faturacao clone() {
        return new Faturacao(this);
    }

    /*################################################ QUERIES #######################################################*/
    /** Query 1
     * Método que percorre os registos todos e devolve os produtos que não foram comprados.
     * @return uma lista com os produtos que não foram comprados.
     */
    public List<IEntidade> getProductsNeverBought() {
        return this.faturacao.entrySet().stream()
                .filter(x -> x.getValue().isProductNeverBought())
                .map(x -> x.getKey().clone())
                .collect(Collectors.toList());
    }

    /** Query 4
     * Método que dado um produto e um mes, determina quantas vezes foi comprado.
     * @param mes - mês em que se vai determinar.
     * @param p - produto que se vai ver quantas vezes foi comprado no mes.
     * @return um array em que a primeira posição corresponde ao numero de vendas e a segunda ao
     * total faturado com o 'p' em 'mes'.
     */
    public float[] getInfoProdutoMonth(IEntidade p, int mes) {
        float[] res = new float[2];
        IRegFat registo = this.faturacao.get(p);
        res[0] = registo.getNumRegVendaProdutoMonth(mes);
        res[1] = registo.getFatProdutoMonth(mes);
        return res;
    }

    /** Query 6
     * Método que retorna uma lista dos N produtos mais comprados em número de unidades vendidas.
     * @param n - tamanho da lista que se quer retornar.
     * @return uma lista com os 'n' produtos mais comprados.
     */
    public Collection<IEntidade> topNSoldProducts(int n) {
        Comparator<Map.Entry<IEntidade, IRegFat>> byQuant = Comparator.comparing(x -> x.getValue().getQuant());
        return this.faturacao.entrySet().stream()
                                        .sorted(byQuant.reversed())
                                        .limit(n)
                                        .map(x -> x.getKey().clone())
                                        .collect(Collectors.toList());
    }

    /** Query 10
     * Método que para cada mes e para cada filial, determina a faturação total de cada produto.
     * @param prod - produto a calcular.
     * @return matriz 3 por 12 onde as linhas correspondem a uma filial e as colunas ao mês. Em que, por exemplo,
     * na posiçao [1][4] obtemos o total faturado por este produto na filial 2 no mês 5.
     */
    public float[][] getFatMesFilialProd(IEntidade prod) {
        IRegFat registo = this.faturacao.get(prod);
        if(registo != null) return registo.getFatFilialMes();
        else return new float[3][12];
    }

    /** Queries Interativas
     * Método que calcula o total faturado.
     * @return total faturado.
     */
    public double totalFaturado(){
        return this.faturacao.values()
                .stream()
                .map(IRegFat::faturado)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    /**
     * Método que devolve uma lista dos produtos que foram comprados,
     * ou seja, os que a faturação é maior que 0.
     * @return lista dos produtos comprados.
     */
    public Collection<IEntidade> allBoughtProducts() {
        return this.faturacao.entrySet().stream()
                .filter(x -> !x.getValue().isProductNeverBought())
                .map(x -> x.getKey().clone())
                .collect(Collectors.toList());
    }
}