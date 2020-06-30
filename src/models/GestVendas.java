package models;
import catalogs.CatalogoEntidades;
import catalogs.Faturacao;
import catalogs.Filial;
import entities.*;
import interfaces.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe que implementa o GestVendas, que implementa a interface IGestVendas.
 */
public class GestVendas implements IGestVendas {
    // Variáveis de instância
    private IFaturacao faturacao;
    private IFilial[] filials;
    private ICatalogo catProd;
    private ICatalogo catCli;
    private String filenameVendas;
    private int numVendasErradas;
    private int comprasNulas;

    /*##############################################################################################*/
    /**
     * Construtor por omissão de GestVendas
     */
    public GestVendas() {
        this.faturacao = new Faturacao();
        this.filials = new Filial[3];
        for(int i = 0; i < 3; i++) this.filials[i] = new Filial();
        this.catCli = new CatalogoEntidades();
        this.catProd = new CatalogoEntidades();
        this.filenameVendas = "";
        this.numVendasErradas = 0;
    }
    /*##############################################################################################*/

    /**
     * Método onde vai-se fazer o parsing e validação duma linha lida do ficheiro.
     * Se a venda for válida cria-se dois registos, um filial e um de faturação, para adicionar
     * ou incrementar, respetivamente, aos maps.
     * @param venda - String lida do ficheiro
     */
    public void lerVenda(String venda) {
        //Array com os campos: Produto Preco Unidades Tipo Cliente Mes Filial
        String[] campos = venda.split(" ");
        IEntidade cliente = new Cliente(campos[4]);
        IEntidade produto = new Produto(campos[0]);
        float price = Float.parseFloat(campos[1]);
        char type = campos[3].charAt(0);
        int quant = Integer.parseInt(campos[2]);
        int mes = Integer.parseInt(campos[5]);
        int filial = Integer.parseInt(campos[6]);
        if(this.catCli.existe(cliente) && this.catProd.existe(produto) && price >= 0 && price < 1000 && (type == 'N' || type == 'P') && quant >= 1 && quant <= 200 && mes >= 1 && mes <= 12 && filial >= 1 && filial <= 3) {
            IRegFilial rfil = new RegistoFilial(produto, quant, price, type, mes);
            IRegFat rfat = new RegistoFat(quant, price, type, mes, filial);
            this.filials[filial - 1].adicionar(cliente, rfil);
            this.faturacao.incrementa(produto, rfat);
            if(price == 0) this.comprasNulas++;
        } else
            this.numVendasErradas++;
    }

    //Le um ficheiro de um dado path
    //fileType pertence a {1, 2, 3}
        //1: Clientes
        //2: Produtos
        //3: Vendas

    /**
     * Método que lê os ficheiros e que começa a popular a filial, a faturação e os catalogos.
     * @param path - path do ficheiro
     * @param fileType 1: Clientes
     *                 2: Produtos
     *                 3: Vendas
     */
    public void readFile(String path, int fileType) {
        String line;
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            while((line = bf.readLine()) != null) {
                if (fileType == 1) {
                    IEntidade cliente = new Cliente(line);
                    if (cliente.validar()) {
                        this.catCli.inserir(cliente);
                        for(int i = 0; i < 3; i++) {
                            this.filials[i].insere(cliente);
                        }
                    }
                } else if (fileType == 2) {
                    IEntidade produto = new Produto(line);
                    if(produto.validar()) {
                        this.catProd.inserir(produto);
                        this.faturacao.initNodo(produto);
                    }
                } else if (fileType == 3) {
                    this.lerVenda(line);
                } else {
                    throw new IllegalArgumentException("fileType tem de ser um número 1, 2 ou 3");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que "decide" quais são os ficheiros que se vão ler para popular o programa.
     * @param path - path do ficheiro de vendas, se for  "" significa que se vai usar o ficheiro predefinido Venda_1M.txt
     */
    public void loadSGV(String path) {
        String dataDir = "./DataFiles/";
        this.readFile(dataDir + "Clientes.txt", 1);
        this.readFile(dataDir + "Produtos.txt", 2);
        if(path.equals("")) {
            this.readFile(dataDir + "Vendas_1M.txt", 3);
            this.filenameVendas = "Vendas_1M.txt";
        } else {
            this.readFile(path, 3);
            this.filenameVendas = path;
        }
    }

    /**
     * Método que guarda em ficheiro de objetos a "população" do programa.
     */
    public void guardaEstado(String nomeFicheiro) throws IOException {
        FileOutputStream fos = new FileOutputStream(nomeFicheiro + ".dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    /**
     * Método que recupera uma instância de GestVendas de um ficheiro de objetos.
     * @param nomeFicheiro nome do ficheiro onde está guardado um objeto do tipo GestVendas
     * @return objeto GestVendas inicializado.
     */
    public GestVendas carregaEstado(String nomeFicheiro) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(nomeFicheiro+ ".dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        GestVendas g = (GestVendas) ois.readObject();
        ois.close();
        return g;
    }
    /*##############################################################################################*/

    /** Query 1
     * Método que chama o método de instância da faturação getProductsNeverBought(), para obter a lista dos
     * produtos nunca comprados e depois calcula o seu tamanho(size()) para obter o seu respetivo total.
     * @return uma coleção feita para esta query (IntAndColl) com a lista dos produtos nunca comprados e o seu tamanho.
     */
    public QueryResults.IntAndColl listAndNumberOfProductsNeverBought() {
        Collection<IEntidade> list = this.faturacao.getProductsNeverBought();
        int tot = list.size();
        return new QueryResults.IntAndColl(list, tot);
    }

    /** Query 2
     * Método que devolve um array em que cada uma das posições corresponde a um valor que
     * vai ser necessário para a query 2
     * Posiçao:
     *         0 - numero de vendas na filial 1
     *         1 - numero de vendas na filial 2
     *         2 - numero de vendas na filial 3
     *         3 - clientes distintos na filial 1
     *         4 - clientes distintos na filial 2
     *         5 - clientes distintos na filial3
     *         6 - clientes distintos em ambas as filiais
     */
    public int[] totalSalesAndDifClientsMonth(int mes) {
        int[] ret = new int[7];
        QueryResults.IntAndColl principal = this.filials[0].getSetClientsBoughtMonth(mes);
        ret[0] = principal.getTotal();
        ret[3] = principal.getListProds().size();
        QueryResults.IntAndColl secundaria = this.filials[1].getSetClientsBoughtMonth(mes);
        ret[1] = secundaria.getTotal();
        ret[4] = secundaria.getListProds().size();
        for(IEntidade e : secundaria.getListProds()){
            principal.getListProds().add(e);
        }
        secundaria = this.filials[2].getSetClientsBoughtMonth(mes);
        ret[2] = secundaria.getTotal();
        ret[5] = secundaria.getListProds().size();
        for(IEntidade e : secundaria.getListProds()){
            principal.getListProds().add(e);
        }
        ret[6] = principal.getListProds().size();

        return ret;
    }

    /** Query 3
     * Método que dado um cliente determina, para cada mês, quantaas compras fez quantos produtos
     * distintos comprou e qunato gastou no total.
     * @return uma matriz de floats, em que a linha 0 corresponde ao numero de compras por mes, a linha 1
     * ao numero de compras distintas por mês e a 2 ao total gasto por mês.
     */
    public float[][] clientSalesDifProdsAndTotalSpent(IEntidade c) {
        float[][] query3 = new float[12][3];
        for(int mes = 0; mes < 12; mes++) {
            QueryResults.IntFloatAndColl totalQuery = this.filials[0].dadosMensaisCliente(c, mes+1);
            for (int i = 1; i <= 2; i++)
                totalQuery.incDados(this.filials[i].dadosMensaisCliente(c, mes + 1));
            query3[mes][0] = totalQuery.getNumCompras();
            query3[mes][1] = totalQuery.getSetProds().size();
            query3[mes][2] = totalQuery.getGasto();
        }
        return query3;
    }

    /** Query 4
     * Método que dado um código de um produto existente, determina, mês a mês, quantas vezes foi
     * comprado, por quantos clientes diferentes e o total faturado
     * @return uma classe que criamos para esta query que tem um array de floats que vai corresponder
     * ao total faturado por mês e 2 arrays de ints, um para quantas vezes o produto foi comprado, por mes
     * e outro para o numero de clientes diferentes por mês.
     */
    public QueryResults.FloatArrAndTwoIntArr productSalesDiffClientsAndFatPerMonth(IEntidade prod) {
        int[] numComprado = new int[12], numClients = new int[12];
        float[] gasto = new float[12];
        for (int mes = 0; mes < 12; mes++) {
            Collection<IEntidade> auxCol = new TreeSet<>();
            float[] resFat = this.faturacao.getInfoProdutoMonth(prod, mes+1);
            numComprado[mes] = (int) resFat[0];
            gasto[mes] = resFat[1];
            for(int filial = 0; filial < 3; filial++)
                auxCol.addAll(this.filials[filial].clientesCompraramProdMes(prod, mes+1));
            numClients[mes] = auxCol.size();
        }
        return new QueryResults.FloatArrAndTwoIntArr(numComprado, numClients, gasto);
    }

    /** Query 5
     * Método que dado um código de um cliente determina a lista de códigos de produtos que mais
     * comprou, por ordem decrescente de quantidade.
     */
    public QueryResults.ProdIntCol clientMostBoughtProds(IEntidade client) {
        QueryResults.ProdIntCol q5 = this.filials[0].getSetOrdProdsCliente(client);
        for(int i = 1; i <= 2; i++)
            for(QueryResults.ProdAndInt entry : this.filials[i].getSetOrdProdsCliente(client).getCol())
                q5.incOuAddQuery5Entry(entry);
         return q5;
    }

    /** Query 6
     * Método que determina o conjunto dos X produtos mais vendidos.
     * @param n - o tamanho da lista a retornar
     */
    public Collection<Map.Entry<IEntidade, Integer>> xMostSelledProds(int n) {
        Collection<Map.Entry<IEntidade, Integer>> newCol = new ArrayList<>();
        Collection<IEntidade> col = this.faturacao.topNSoldProducts(n);
        for(IEntidade prod : col) {
            Collection<IEntidade> set = this.filials[0].clientesCompraramProd(prod);
            for(int i = 1; i <= 2; i++)
                set.addAll(this.filials[i].clientesCompraramProd(prod));
            newCol.add(Map.entry(prod, set.size()));
        }
        return newCol;
    }

    /** Query 7
     * Método que determina, para cada filial, a lista dos três maiores compradores em termos de
     * dinheiro faturado.
     */
    public IEntidade[] threeMostBuyersPerFilial() {
        IEntidade[] ret = new IEntidade[9];
        int c = 0;
        for (int i = 0; i < 3; i++) {
            Collection<IEntidade> col = this.filials[i].top3ClientesMaisCompraram();
            for(IEntidade e : col) {
                ret[c] = e.clone();
                c++;
            }
        }
        return ret;
    }

    /** Query 8
     * Método que determina os códigos dos X clientes que compraram mais produtos diferentes,
     * sendo o critério de ordenação a ordem decrescente do número de produtos
     * @param n - tamanho da lista a devolver.
     */
    public List<Map.Entry<IEntidade, Integer>> xClientsBoughtMoreDiffProds(int n) {
        Comparator<Map.Entry<IEntidade, Integer>> comp = (x, y) -> {
            int r = y.getValue().compareTo(x.getValue());
            if(r == 0) return x.getKey().compareTo(y.getKey());
            return r;
        };
        Map<IEntidade, Collection<IEntidade>> mapfilial = this.filials[0].getTopNClientsBoughtDifProds();
        for(int i = 1; i <= 2; i++) {
            Map<IEntidade, Collection<IEntidade>> map = this.filials[i].getTopNClientsBoughtDifProds();
            for (Map.Entry<IEntidade, Collection<IEntidade>> entry : map.entrySet())
                if (mapfilial.containsKey(entry.getKey()))
                    mapfilial.get(entry.getKey()).addAll(entry.getValue());
                else mapfilial.put(entry.getKey(), entry.getValue());
        }
        return mapfilial.entrySet().stream()
                .map(x -> Map.entry(x.getKey(), x.getValue().size()))
                .sorted(comp)
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Método retorna a quantiade de produtos comprados numa collection.
     */
    private int allProdQuant(Collection<IRegFilial> reg) {
        return reg.stream().map(IRegFilial::getQuant).reduce(Integer::sum).orElse(0);
    }

    /**
     * Método que retorna um map com um cliente e a quantidade de um produto que ele comprou
     * numa dada filial
     */
    public Map<IEntidade, Integer> filialToQuantMap(IEntidade prod, int fil) {
        Map<IEntidade, Collection<IRegFilial>> filByProd = this.filials[fil].clientesRegistosComProd(prod);
        return filByProd.entrySet()
                .stream()
                .map(x -> Map.entry(x.getKey(), allProdQuant(x.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    /** Query 9
     * Método que dado um código de um produto determina o conjunto dos X clientes
     * que mais o compraram.
     */
    public Collection<IEntidade> topNClientes(IEntidade prod, int limit) {
        if(this.catProd.existe(prod) || limit == 0) {
            Comparator<Map.Entry<IEntidade, Integer>> comp = (x, y) -> {
                int r = y.getValue().compareTo(x.getValue());
                if(r == 0) return x.getKey().compareTo(y.getKey());
                return r;
            };
            Map<IEntidade, Integer> fil1, fil2, fil3;
            //Começar por criar uma lista de clientes que comprar o prod e quanto compraram por filial
            fil1 = this.filialToQuantMap(prod, 0);
            fil2 = this.filialToQuantMap(prod, 1);
            fil3 = this.filialToQuantMap(prod, 2);
            //agregar as 3 filiais num big result
                //Percorrer a fil 2 e a fil 3
            Map<IEntidade, Integer> merged = Stream.of(fil1, fil2, fil3)
                    .map(Map::entrySet)
                    .flatMap(Set::stream)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));

            //Aqui o fil1 ja deve ter os clientes com a quantidade total de compras do produto X
            //Agora basta ordenar de forma decrescente; introduzir um limite e calcular o total faturado

            return merged.entrySet()
                    .stream()
                    .sorted(comp)
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        } else return null;
    }

    /**
     * Método que calcula quanto um cliente gastou num produto
     */
    public float gastoEmProd(IEntidade cliente, IEntidade prod) {
        float r = 0;
        for (int i = 0; i < 3; i++)
            r += this.filials[i].gastoEmProduto(cliente, prod);
        return r;
    }

    public List<Map.Entry<IEntidade, Float>> topNClientesComGasto(IEntidade prod, int limit) {
        return this.topNClientes(prod, limit).stream()
                .map(x -> Map.entry(x, this.gastoEmProd(x, prod)))
                .collect(Collectors.toList());
    }


    /** Query 10
     * Método que determina mês a mês, e para cada mês filial a filial, a faturação total com um produto dado.
     */
    public float[][] productTotalFat(IEntidade prod) {
        float[][] ret = new float[4][12];
        float[][] res = this.faturacao.getFatMesFilialProd(prod);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                if(i == 0) {
                    ret[i][j] += res[0][j] + res[1][j] + res[2][j];
                } else {
                    ret[i][j] = res[i-1][j];
                }
            }
        }
        return ret;
    }

    /** Queries Estatisticas
     * Método que devolve a collection dos clientes que compraram em todas as filiais
     */
    public Collection<IEntidade> clientesCompradores(){
        Collection<IEntidade> fil1, fil2, fil3;
        fil1 = this.filials[0].clientesCompradores();
        fil2 = this.filials[1].clientesCompradores();
        fil3 = this.filials[2].clientesCompradores();
        return fil1.stream()
                   .filter(x -> fil2.contains(x) && fil3.contains(x))
                   .collect(Collectors.toList());
    }

    /** Queries Estatisticas
     * Método que devolve as compras feitas e o total faturado, por mês, em todas as filiais.
     */
    public float[][] comprasMes() {
        float[][] ret = new float[2][12];
        for (int i = 0; i < 3; i++) {
            int[][] r = this.filials[i].comprasMesTotal();
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 12; k++) {
                    ret[j][k] += r[j][k];
                }
            }
        }
        return ret;
    }

    /** Queries Estatisticas
     * Método que calcula o número total de clientes distintos que realizaram compras por mês.
     */
    public int[] clientesDiferentesMes() {
        int[] res = new int[12];
        List<Set<IEntidade>> ret = this.filials[0].clientesMes();
        for (int i = 0; i < 2; i++) {
            List<Set<IEntidade>> r = this.filials[i].clientesMes();
            for (int j = 0; j < 12; j++) {
                ret.get(i).addAll(r.get(i));
            }
        }
        for (int i = 0; i < 12; i++) {
            res[i] = ret.get(i).size();
        }
        return res;
    }

    /*----------------------------------------Queries estatisticas----------------------------------------_*/

    /** Queries Estatisticas
     * Método que calcula todas as queries estatisticas e guarda as numa classe feita com este
     * proposito para depois mostrar ao utilizador.
     */
    public QueryResults.LastSalesData lastSalesData() {
        QueryResults.LastSalesData r = new QueryResults.LastSalesData();

        int totalProds = this.catProd.tamanho();
        int prodsComprados = this.faturacao.allBoughtProducts().size();
        int totalClientes = this.catCli.tamanho();
        int numCompradores = this.clientesCompradores().size();
        int numNaoCompradores = totalClientes - numCompradores;

        r.setFilename(this.filenameVendas);
        r.setNumVendasErradas(Integer.toString(this.numVendasErradas));
        r.setNumTotalProdutos(Integer.toString(totalProds));
        r.setNumTotalClientes(Integer.toString(totalClientes));

        r.setNumProdsUnicosComprados(Integer.toString(prodsComprados));
        r.setNumProdsNaoComprados(Integer.toString(totalProds - prodsComprados));

        r.setNumTotalClientes(Integer.toString(totalClientes));
        r.setNumClientesCompradores(Integer.toString(numCompradores));
        r.setNumClientesNaoCompradores(Integer.toString(numNaoCompradores));

        r.setNumComprasValorZero(Integer.toString(this.comprasNulas));
        r.setFaturacaoTotal(String.format("%.2f", this.faturacao.totalFaturado()));

        r.setClientesDiferentesMes(this.clientesDiferentesMes());
        r.setComprasMes(this.comprasMes());
        return r;
    }
}