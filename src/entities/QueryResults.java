package entities;
import interfaces.IEntidade;
import java.util.*;

/**
 * Classe que implementa um QueryResults.
 * Esta classe serve para "criar" classes uteis para a realização de certas queries.
 * Certas queries pedem-nos para devolver, por exemplo, listas e inteiros numa função e
 * isso não é possivel sem a criação destas classes, ou se é nós não sabemos/aprendemos.
 */
public class QueryResults {

    /**
     * Classe com um inteiro e uma coleção.
     * Usa-mos esta classa para a Query 1 e 2.
     */
    public static class IntAndColl {
        private Collection<IEntidade> list;
        private int total;

        public IntAndColl(Collection<IEntidade> listProds, int total) {
            setListProds(listProds);
            setTotal(total);
        }

        public Collection<IEntidade> getListProds() {
            return this.list;
        }

        public int getTotal() {
            return this.total;
        }

        public void setListProds(Collection<IEntidade> listProds) {
            this.list = new TreeSet<>(listProds);
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
    /*##############################################################################################*/
    /**
     * Classe com um inteiro, um float e uma coleção.
     * Usa-mos na Query 3
     */
    public static class IntFloatAndColl {
        private int numCompras;
        private Collection<IEntidade> setProds;
        private float gasto;

        public IntFloatAndColl(int numCompras, Collection<IEntidade> setProds, float gasto) {
            this.numCompras = numCompras;
            this.setProds = new TreeSet<>(setProds);
            this.gasto = gasto;
        }

        public int getNumCompras() {
            return numCompras;
        }

        public void setNumCompras(int numCompras) {
            this.numCompras = numCompras;
        }

        public Collection<IEntidade> getSetProds() {
            return new TreeSet<>(setProds);
        }

        public void setNumProds(Collection<IEntidade> setProds) {
            this.setProds = new TreeSet<>(setProds);
        }

        public float getGasto() {
            return gasto;
        }

        public void setGasto(float gasto) {
            this.gasto = gasto;
        }

        public void incDados(IntFloatAndColl q3) {
            this.numCompras += q3.getNumCompras();
            this.gasto += q3.getGasto();
            this.setProds.addAll(q3.getSetProds());
        }

    }
    /*##############################################################################################*/
    /**
     * Classe com dois arrays de inteiros e um array de floats.
     * Usa-mos na Query 4
     */
    public static class FloatArrAndTwoIntArr {
        private int[] numComprado;
        private int[] numClients;
        private float[] gasto;

        public FloatArrAndTwoIntArr(int[] numComprado, int[] numClients, float[] gasto) {
            this.numComprado = numComprado;
            this.numClients = numClients;
            this.gasto = gasto;
        }

        public int getNumComprado(int mes) {
            return numComprado[mes];
        }

        public void setNumComprado(int[] numComprado) {
            this.numComprado = numComprado;
        }

        public int getNumClients(int mes) {
            return numClients[mes];
        }

        public void setNumClients(int[] numClients) {
            this.numClients = numClients;
        }

        public float getGasto(int mes) {
            return gasto[mes];
        }

        public void setGasto(float[] gasto) {
            this.gasto = gasto;
        }
    }
    /*##############################################################################################*/
    /**
     * Classe com uma coleção de duplos. Cada duplo tem um produto e um int (Produto,int).
     * Usa-mos na Query 5
     */
    public static class ProdIntCol {
        private Collection<ProdAndInt> col;

        public ProdIntCol() {
            this.col = new TreeSet<>(Comparator.comparing(ProdAndInt::getQuant).reversed());
        }

        public ProdIntCol(Collection<ProdAndInt> col) {
            this.col = new ArrayList<>(col);
        }

        public Collection<ProdAndInt> getCol() {
            return new ArrayList<>(col);
        }

        public void setCol(Collection<ProdAndInt> col) {
            this.col = new ArrayList<>(col);
        }

        public void incOuAddQuery5Entry(ProdAndInt entry) {
            boolean existe = false;
            for(ProdAndInt q5 : this.col)
                if(q5.getProd().equals(entry.getProd())) {
                    existe = true;
                    q5.setQuant(q5.getQuant()+entry.getQuant());
                }
            if (!existe)
                this.col.add(entry);
        }

        @Override
        public String toString() {
            return "Query5{" +
                    "col=" + col +
                    '}';
        }
    }
    /*##############################################################################################*/
    /**
     *  Classe com uma IEntidade e um int.
     * Usa-mos na Query 5
     */
    public static class ProdAndInt {
        private IEntidade prod;
        private int quant;

        public ProdAndInt(IEntidade prod, int quant) {
            this.prod = prod.clone();
            this.quant = quant;
        }

        public IEntidade getProd() {
            return prod.clone();
        }

        public void setProd(IEntidade prod) {
            this.prod = prod.clone();
        }

        public int getQuant() {
            return quant;
        }

        public void setQuant(int quant) {
            this.quant = quant;
        }

        @Override
        public String toString() {
            return "Query5Entry{" +
                    "prod=" + prod +
                    ", quant=" + quant +
                    '}';
        }
    }
    /*##############################################################################################*/
    /**
     * Usamos esta classe para guardar os valores obtidos nas queries estastiticas.
     * Os nomes das variaveis já são muito explicitios.
     */
    public static class LastSalesData {
        String filename;
        String numVendasErradas;
        String numTotalProdutos;
        String numProdsUnicosComprados;
        String numProdsNaoComprados;
        String numTotalClientes;
        String numClientesCompradores;
        String numClientesNaoCompradores;
        String numComprasValorZero;
        String faturacaoTotal;
        float[][] comprasMes;
        int[] clientesDiferentesMes;

        public LastSalesData() {
            this.filename = "";
            this.numVendasErradas = "";
            this.numTotalProdutos = "";
            this.numProdsUnicosComprados = "";
            this.numProdsNaoComprados = "";
            this.numTotalClientes = "";
            this.numClientesCompradores = "";
            this.numClientesNaoCompradores = "";
            this.numComprasValorZero = "";
            this.faturacaoTotal = "";
        }

        public String getFilename() {
            return filename;
        }

        public String getNumVendasErradas() {
            return numVendasErradas;
        }

        public String getNumTotalProdutos() {
            return numTotalProdutos;
        }

        public String getNumProdsUnicosComprados() {
            return numProdsUnicosComprados;
        }

        public String getNumProdsNaoComprados() {
            return numProdsNaoComprados;
        }

        public String getNumTotalClientes() {
            return numTotalClientes;
        }

        public String getNumClientesCompradores() {
            return numClientesCompradores;
        }

        public String getNumClientesNaoCompradores() {
            return numClientesNaoCompradores;
        }

        public String getNumComprasValorZero() {
            return numComprasValorZero;
        }

        public String getFaturacaoTotal() {
            return faturacaoTotal;
        }

        public float[][] getComprasMes() {
            return comprasMes;
        }

        public int[] getClientesDiferentesMes() {
            return clientesDiferentesMes;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public void setNumVendasErradas(String numVendasErradas) {
            this.numVendasErradas = numVendasErradas;
        }

        public void setNumTotalProdutos(String numTotalProdutos) {
            this.numTotalProdutos = numTotalProdutos;
        }

        public void setNumProdsUnicosComprados(String numProdsUnicosComprados) {
            this.numProdsUnicosComprados = numProdsUnicosComprados;
        }

        public void setNumProdsNaoComprados(String numProdsNaoComprados) {
            this.numProdsNaoComprados = numProdsNaoComprados;
        }

        public void setNumTotalClientes(String numTotalClientes) {
            this.numTotalClientes = numTotalClientes;
        }

        public void setNumClientesCompradores(String numClientesCompradores) {
            this.numClientesCompradores = numClientesCompradores;
        }

        public void setNumClientesNaoCompradores(String numClientesNaoCompradores) {
            this.numClientesNaoCompradores = numClientesNaoCompradores;
        }

        public void setNumComprasValorZero(String numComprasValorZero) {
            this.numComprasValorZero = numComprasValorZero;
        }

        public void setFaturacaoTotal(String faturacaoTotal) {
            this.faturacaoTotal = faturacaoTotal;
        }

        public void setClientesDiferentesMes(int[] clientesDiferentesMes) {
            this.clientesDiferentesMes = clientesDiferentesMes;
        }

        public void setComprasMes(float[][] comprasMes) {
            this.comprasMes = comprasMes;
        }

        @Override
        public String toString() {
            return "LastSalesData{\n" +
                    "filename= " + filename + '\n' +
                    "numVendasErradas= " + numVendasErradas + '\n' +
                    "numTotalProdutos= " + numTotalProdutos + '\n' +
                    "prodsUnicosComprados= " + numProdsUnicosComprados +
                    "numProdsNaoComprados= " + numProdsNaoComprados + '\n' +
                    "numTotalClientes= " + numTotalClientes + '\n' +
                    "numClientesCompradores= " + numClientesCompradores + '\n' +
                    "numClientesNaoCompradores= " + numClientesNaoCompradores + '\n' +
                    "numComprasValorZero= " + numComprasValorZero + '\n' +
                    "faturacaoTotal= " + faturacaoTotal + '\n' +
                    '}';
        }
    }
}