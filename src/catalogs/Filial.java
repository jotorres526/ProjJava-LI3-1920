package catalogs;
import entities.QueryResults;
import interfaces.IEntidade;
import interfaces.IFilial;
import interfaces.IRegFilial;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que implementa Filial, que implementa a interface IFilial.
 * Nesta classe, são armazenados todos os clientes num HashMap, em que a cada cliente está associado uma
 * collection de registos filiais.
 */
public class Filial implements IFilial {
    // Variável de instância
    private final Map<IEntidade, Collection<IRegFilial>> filial;

    /*##############################################################################################*/
    /**
     * Construtores da Classe Faturacao.
     * Declaração dos contrutores por omissão e de cópia.
     */

    public Filial() {
        this.filial = new HashMap<>();
    }

    public Filial(Filial f) {
        this.filial = f.getFilial();
    }
    /*##############################################################################################*/
    /**
     * Getter.
     * @return cópia de this.filial.
     */
    public Map<IEntidade, Collection<IRegFilial>> getFilial() {
        Map<IEntidade, Collection<IRegFilial>> r = new HashMap<>();
        for (Map.Entry<IEntidade, Collection<IRegFilial>> entry : this.filial.entrySet()) {
            IEntidade c = entry.getKey().clone();
            Collection<IRegFilial> collection = entry.getValue().stream().map(IRegFilial::clone).collect(Collectors.toList());
            r.put(c, collection);
        }
        return r;
    }
    /*##############################################################################################*/
    /**
     * Método que adiciona um registo filial à collection de um cliente no mapa.
     * @param entity - cliente a que vai ser adicionado um registo.
     * @param regFilial - registo a adicionar à coleçao do cliente.
     */
    @Override
    public void adicionar(IEntidade entity, IRegFilial regFilial) {
        this.filial.get(entity).add(regFilial.clone());
    }

    /**
     * Método que iniciliza e insere uma entrada a this.filial.
     * @param entity - key da entrada a adicionar.
     */
    @Override
    public void insere(IEntidade entity) {
        this.filial.put(entity.clone(), new ArrayList<>());
    }
    /*##############################################################################################*/
    /**
     * Metodo que determina se duas filiais são iguais.
     * Para serem considerados iguais têm de ter os maps iguais.
     * @param o - objeto para comparar com this
     * @return true se forem iguais.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filial filial1 = (Filial) o;
        return Objects.equals(filial, filial1.filial);
    }

    /**
     * Método que calcula a posição que this irá ter numa Hash Table.
     * @return número de Hash de this.
     */
    @Override
    public int hashCode() {
        return Objects.hash(filial);
    }

    /**
     * Metodo que devolve a representação em String da Filial.
     * @return String com a representação da Filial.
     */
    @Override
    public String toString() {
        return "Filial{" +
                "filial=" + filial +
                '}';
    }

    /**
     * Metodo que faz uma cópia do objeto receptor da mensagem.
     * @return Faturacao clone da instancia que recebe esta mensagem.
     */
    @Override
    public Filial clone() {
        return new Filial(this);
    }
    /*################################################ QUERIES #######################################################*/

    /** Query 2
     * Método que verifica se um cliente comprou algo num determinado mes.
     * @param collection - collection onde se vai verificar.
     * @param mes - mes a verificar.
     * @return numero de registos da 'collection' feitos em 'mes'
     */
    private int clientBoughtAnyByMonth(Collection<IRegFilial> collection, int mes) {
        int ret = 0;
        for (IRegFilial rf : collection)
            if (rf.equalsMes(mes)) ret++;
        return ret;
    }

    /** Query 2
     * Método que devolve um set dos clientes que compraram algo num determinado mes.
     * @param mes - mês usado para criar a lista
     * @return um set e um int, o set corresponde à lista dos clientes que compraram no 'mes'
     * e o inteiro corresponde ao número de vendas feitas nesse 'mes'
     */
    public QueryResults.IntAndColl getSetClientsBoughtMonth(int mes) {
        int num = 0;
        int r;
        Collection<IEntidade> setClients = new TreeSet<>();
        for (Map.Entry<IEntidade, Collection<IRegFilial>> entry : this.filial.entrySet())
            if ((r = clientBoughtAnyByMonth(entry.getValue(), mes)) > 0) {
                num += r;
                setClients.add(entry.getKey());
            }
        return new QueryResults.IntAndColl(setClients, num);
    }

    /** Query 3
     * Método que dado um cliente e para cada mês, determina o número de compras que fez, quanto gastou e a quantos
     * produtos diferentes comprou.
     * @return um set com os produtos que o cliente comprou no mês dado, um int com o numero de compras feitas nesse mês
     * e um float para o total gasto.
     */
    public QueryResults.IntFloatAndColl dadosMensaisCliente(IEntidade c, int mes) {
        Collection<IRegFilial> collection = this.filial.get(c);
        if (collection == null) return new QueryResults.IntFloatAndColl(0, new TreeSet<>(), 0);
        int num_compras = 0;
        float gasto = 0;
        Collection<IEntidade> newSet = new TreeSet<>();
        for(IRegFilial reg : collection)
            if (reg.equalsMes(mes)) {
                newSet.add(reg.getProd().clone());
                gasto += reg.getPrecoTotal();
                num_compras++;
            }
        return new QueryResults.IntFloatAndColl(num_compras, newSet, gasto);
    }

    /** Query 4
     * Método que verifica se um Cliente, a partir da sua Collection de registos,
     * comprou um produto num determinado mes.
     * @return true se o cliente comprou o produto, recebido como argumento, no mês,
     * também recebido como argumento.
     */
    private boolean clientCompraProdMes(Collection<IRegFilial> collection, IEntidade prod, int mes) {
        for (IRegFilial register : collection)
            if (register.clientBoughtProductMonth(prod, mes))
                return true;
        return false;
    }

    /** Query 4
     * Método que devolve um Set dos clientes que compraram um produto num certo mes.
     * Tanto o produto como o mês são passados como argumentos.
     * @return set com os clientes que compraram o 'prod' no 'mes'.
     */
    public Collection<IEntidade> clientesCompraramProdMes(IEntidade prod, int mes) {
        return this.filial.entrySet().stream()
                .filter(x -> clientCompraProdMes(x.getValue(), prod, mes))
                .map(x -> x.getKey().clone())
                .collect(Collectors.toSet());
    }


    /** Query 5
     *  Método que devolve um set de produtos de um determinado cliente ordenado decrescentemente pela quantidade e,
     * se as quantidades forem iguais, ordena alfabeticamente
     */
    public QueryResults.ProdIntCol getSetOrdProdsCliente(IEntidade c) {
        QueryResults.ProdIntCol q5Final = new QueryResults.ProdIntCol();
        Collection<IRegFilial> col = this.filial.get(c);
        for(IRegFilial registo : col) {
            QueryResults.ProdAndInt q5Entry = new QueryResults.ProdAndInt(registo.getProd(), registo.getQuant());
            q5Final.incOuAddQuery5Entry(q5Entry);
        }
        return q5Final;
    }

    /** Query 6
     * Método que dado uma Collection de RegistosFilial e um produto, verifica se o cliente comprou o produto.
     */
    public static boolean clientBoughtProduct(Collection<IRegFilial> collection, IEntidade prod) {
        for (IRegFilial registo : collection)
            if (prod.equals(registo.getProd()))
                return true;
        return false;
    }

    /** Query 6
     * Método que para um produto, retorna um Set de clientes que o compraram.
     */
    public Collection<IEntidade> clientesCompraramProd(IEntidade prod) {
        return this.filial.entrySet().stream()
                .filter(x -> clientBoughtProduct(x.getValue(), prod))
                .map(x -> x.getKey().clone())
                .collect(Collectors.toSet());
    }

    /** Query 7
     * Método que retorna a lista dos 3 Clientes que mais compraram nesta filial.
     */
    public Collection<IEntidade> top3ClientesMaisCompraram() {
        Comparator<Map.Entry<IEntidade, Collection<IRegFilial>>> comp = (x, y) -> {
            float quant1 = calculaFatTotal(x.getValue());
            float quant2 = calculaFatTotal(y.getValue());
            int res = 0;
            res = Float.compare(quant1, quant2);
            if (res == 0) res = x.getKey().clone().compareTo(y.getKey().clone());
            return res;
        };
        return this.filial.entrySet().stream()
                                     .sorted(comp.reversed())
                                     .map(x -> x.getKey().clone())
                                     .limit(3)
                                     .collect(Collectors.toList());
    }

    /**
     * Método que calcula o total faturado numa collection de registos filial.
     * Percorre a coleção e faz o somatório da quantidade x preço.
     */
    private float calculaFatTotal(Collection<IRegFilial> col) {
        float quant = 0;
        for (IRegFilial registo1 : col)
            quant += registo1.getPrecoTotal();
        return quant;
    }

    /** Query 8
     * Método que determina o número de produtos diferentes numa collection recebida como argumento.
     */
    private static Collection<IEntidade> getDifProductsByClient(Collection<IRegFilial> col) {
        return col.stream()
                .map(x -> x.getProd().clone())
                .collect(Collectors.toSet());
    }

    /** Query 8
     * Método que devolve um map com os N clientes que compraram mais produtos diferentes associados
     * ao número de produtos que compraram .
     */
    public Map<IEntidade, Collection<IEntidade>> getTopNClientsBoughtDifProds() {
        return this.filial.entrySet().stream()
                .map(x -> Map.entry(x.getKey(), getDifProductsByClient(x.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /** Query 9
     * Método que percorre a collection de registos filiais do cliente recebido como argumento e
     * filtra-os devolvendo apenas os em que o cliente recebe o produto recebido como argumento.
     */
    private Collection<IRegFilial> registosProdutoPorCli(IEntidade prod, IEntidade cliente) {
        return this.filial.get(cliente)
                .stream()
                .filter(x -> x.hasProduto(prod))
                .collect(Collectors.toSet());
    }

    /** Query 9
     * Método que devolve um map com clientes e todos os registos que têm relacionados com o
     * dado produto.
     */
    public Map<IEntidade, Collection<IRegFilial>> clientesRegistosComProd(IEntidade prod) {
        return this.filial.keySet()
                .stream()
                .map(x -> Map.entry(x.clone(), registosProdutoPorCli(prod, x)))
                .filter(x -> !x.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Método que devolve o total que um cliente dado gastou num certo produto, também dado.
     */
    @Override
    public float gastoEmProduto(IEntidade cliente, IEntidade prod) {
        return this.filial.get(cliente).stream()
                .filter(x -> x.hasProduto(prod))
                .map(IRegFilial::getPrecoTotal)
                .reduce(Float::sum)
                .orElse((float) 0);
    }

    /** Queries Estatisticas
     * Método que devolve o numero de clientes que realizaram pelo menos uma compra.
     */
    public Collection<IEntidade> clientesCompradores() {
        return this.filial.entrySet().stream()
                                     .filter(x -> !x.getValue().isEmpty())
                                     .map(x -> x.getKey().clone())
                                     .collect(Collectors.toList());
    }

    // Estatisticas 1.2
        //-> total[1][mes] = quantidade de compras
        //-> total[2][mes] = faturaçao mensal

    /** Queries Estatisticas
     * Método que calcula o total de compras feitas e o total faturado nesta filial, por mês
     * @return um array em que os elementos na primeira linha correspondem à quantidade de produtos
     * comprados nesta filial por mês e na segunda o faturado por mês.
     */
    public int[][] comprasMesTotal() {
        int[][] total = new int[2][12];
        Collection<Collection<IRegFilial>> col = this.filial.values();
        for(Collection<IRegFilial> c : col) {
            for(IRegFilial r : c){
                int mes = r.getMes() - 1;
                total[0][mes] += r.getQuant();
                total[1][mes] += r.getPrecoTotal();
            }
        }
        return total;
    }

    /**
     * Método que percorre todas as entradas da filial e devolve uma List com 12 entradas
     * em que cada uma das entradas terá um set com os clientes que compraram em cada mês
     * O objetivo é depois descobrir o total de de clientes distinto que realizaram c
     * ompras por mês.
     */
    public List<Set<IEntidade>> clientesMes() {
        List<Set<IEntidade>> ret = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            ret.add(new TreeSet<>());
        }
        for(Map.Entry<IEntidade, Collection<IRegFilial>> c : this.filial.entrySet()) {
            for(IRegFilial r : c.getValue()) {
                ret.get(r.getMes() - 1).add(c.getKey());
            }
        }
        return ret;
    }
}