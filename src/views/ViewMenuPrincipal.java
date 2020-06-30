package views;
import entities.QueryResults;
import interfaces.IEntidade;
import interfaces.IView;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Classe que implementa uma VieMenuPrincipal, que implementa a Interface IView.
 * Esta classe foi implementada para printar ao utilizador informações sobre o funcionamento do programa.
 */
public class ViewMenuPrincipal implements IView {

    /**
     * Método que mostra ao utilizador o menu principal
     */
    @Override
    public void show() {
        System.out.println("Bem-vindo!! Que deseja ver?");
        System.out.println("\t1  -> Lista ordenada alfabeticamente dos produtos nunca comprados.");
        System.out.println("\t2  -> Número total de vendas num certo mês e numero de clientes distintos que as fizeram.");
        System.out.println("\t3  -> Dado um código de Cliente determinar quantas compras fez, quantos produtos distintos comprou e quanto gastou no total");
        System.out.println("\t4  -> Dado um código de um produto determinar, mês a mês, quantas vezes foi comprado, por quantos clientes diferentes e o total faturado");
        System.out.println("\t5  -> Dado um código de Cliente determinar a lista de códigos de produtos que mais comprou");
        System.out.println("\t6  -> Determinar o conjunto dos X produtos mais vendidos todo o ano");
        System.out.println("\t7  -> Determinar, para cada filial, a lista dos três maiores compradores");
        System.out.println("\t8  -> Determinar os X clientes que compraram mais produtos diferentes");
        System.out.println("\t9  -> Dado um codigo de um Produto deternminar o conjunto dos X clientes que mais o compraram");
        System.out.println("\t10 -> Determinar mês a mês, e para cada filial, a faturação total de cada produto");
        System.out.println("\t11 -> Dados Estatísticos");
        System.out.println("\t12 -> Mudar de ficheiro");
        System.out.println("\t13 -> Carregar SGV");
        System.out.println("\t14 -> Gravar SGV");
        System.out.println("\t0  -> Sair");
    }

    /**
     * Método que "pede" algo ao utilizador.
     * Usamos este método, por exemplo, quando queremos pedir um inteiro ao utilizador.
     * @param s - string com o que queremos do utilizador.
     */
    public void show(String s) {
        System.out.println("Escreva o " + s + " :");
    }

    /**
     * Método que printa um tempo de execução recebido.
     * @param d - tempo a printar.
     */
    public void show(double d) {
        System.out.println("                    +-----------------------------------+");
        System.out.printf("                    | Tempo de execução: %.3f segundos |\n", d);
        System.out.println("                    +-----------------------------------+");
    }

    /**
     * Método muito simples, mas que usamos muito, simplesmente "pede" ao utilizador que prima enter.
     * Usámos para mostrar a informação de maneira organizada.
     */
    public void enter() {
        System.out.println("Enter para continuar :)");
    }

    /**
     * Método que printa os resultados obtidos na query 2
     * @param res - resultado obtido na query 2
     */
    public void printQuery2(int[] res) {
        System.out.println("--------------------------------------");
        for (int i = 0; i < 3 ; i++) {
            System.out.println("Filial " + (i+1));
            System.out.println("\tNº de vendas: " + res[i]);
            System.out.println("\tNº de clientes distintos: " + res[i+3]);
        }
        System.out.println("Total: ");
        System.out.println("\tNº de vendas: " + (res[0] + res[1] + res[2]));
        System.out.println("\tNº de clientes distintos: " + res[6]);
        System.out.println("--------------------------------------");
    }

    /**
     * Método que printa os resultados obtidos na query 3
     * @param res - resultado obtido na query 3
     */
    public void printQuery3(float[][] res) {
        for (int mes = 0; mes < 12; mes++) {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Para o mes " + (mes+1) + ": ");
            System.out.println("Fez " + (int) res[mes][0] + " compras" +
                               "| Comprou " + res[mes][1] + " produtos diferentes " +
                               "| Gastou " + res[mes][2] + " euros");
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    /**
     * Método que printa os resultados obtidos na query 4
     * @param res - resultado obtido na query 4
     */
    public void printQuery4(QueryResults.FloatArrAndTwoIntArr res) {
        for(int mes = 0; mes < 12; mes++) {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Para o mes " + (mes+1) + ": ");
            System.out.println("Foi comprado " + res.getNumComprado(mes) + " vezes" +
                               "| " + res.getNumClients(mes) + " clientes compraram o produto" +
                               "| Total faturado: " + res.getGasto(mes));
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Método que printa os resultados obtidos na query 5
     * @param listProds - resultado obtido na query 5
     * @param i - primeiro elemento da listProds que queremos printar
     */
    public void printQuery5(QueryResults.ProdIntCol listProds, int i) {
        int c = 0;
        System.out.println("Lista de Produtos: ");
        for(QueryResults.ProdAndInt entry : listProds.getCol()) {
            if(c >= i && c < i +20)
                System.out.println("Produto: " + entry.getProd().getId() + "     Quantidade: " + entry.getQuant());
            c++;
        }
        System.out.println("----------------------------");
        if(i >= 20) System.out.println("'A' -> Para trás");
        if(i < listProds.getCol().size()) System.out.println("'D' -> Para a Frente ");
        System.out.println("'Q' -> Sair");
    }

    /**
     * Método que printa os resultados obtidos na query 6
     * @param res - resultado obtido na query 6
     * @param x - valor que o utilizador deu para fazer a query 6
     */
    public void printQuery6(Collection<Map.Entry<IEntidade, Integer>> res, int x) {
        System.out.println(x + " produtos que mais se venderam neste ano: ");
        for(Map.Entry<IEntidade, Integer> entry : res) {//Map.Entry<IEntidade, Integer> entry : res.entrySet()) {
            System.out.println(" >> " + entry.getKey());
            System.out.println("\tNúmero de clientes distintos que compraram " + entry.getValue());
            System.out.println("--------------------------------------------------------------");
        }
    }

    /**
     * Método que printa os resultados obtidos na query 7
     * @param res - resultado obtido na query 7
     */
    public void printQuery7(IEntidade[] res) {
        System.out.println("Top 3 Compradores!!");
        System.out.println("Filial 1 :");
        System.out.println("\t>" + res[0].getId());
        System.out.println("\t>" + res[1].getId());
        System.out.println("\t>" + res[2].getId());
        System.out.println("Filial 2 :");
        System.out.println("\t>" + res[3].getId());
        System.out.println("\t>" + res[4].getId());
        System.out.println("\t>" + res[5].getId());
        System.out.println("Filial 3 :");
        System.out.println("\t>" + res[6].getId());
        System.out.println("\t>" + res[7].getId());
        System.out.println("\t>" + res[8].getId());
        System.out.println("-----------------------");
    }

    /**
     * Método que printa os resultados obtidos na query 8
     * @param res - resultado obtido na query 8
     */
    public void printQuery8(Collection<Map.Entry<IEntidade, Integer>> res) {
        System.out.println("Lista de Clientes: ");
        for(Map.Entry<IEntidade, Integer> entry : res) {
            System.out.println(" >> " + entry.getKey());
            System.out.println("\tNúmero de produtos distintos: " + entry.getValue());
            System.out.println("--------------------------------------------------------------");
        }
    }

    /**
     * Método que printa os resultados obtidos na query 9
     * @param map - resultado obtido na query 9
     * @param lim - valor que o utilizador deu para fazer a query 9
     */
    public void printQuery9(List<Map.Entry<IEntidade, Float>> map, int lim) {
        System.out.println("Top " + lim + " compradores de dado produto: ");
        int i = 1;
        for(Map.Entry<IEntidade, Float> entry : map) {
            System.out.println(i + ": Cliente: " + entry.getKey().getId() +  " gastou " + entry.getValue());
            i++;
        }
    }

    /**
     * Método que printa os resultados obtidos na query 10
     * @param res - resultado obtido na query 10
     * @param i - int que o utilizador deu para fazer a query 10
     * @param prod - produto que o utilizador deu para fazer a query 10
     */
    public void printQuery10(float[][] res, int i, IEntidade prod) {
        if(i > 0) System.out.println("Faturação por mês na Filial " + (i+1) + " do produto " + prod.getId());
        else System.out.println("Faturçao Total por mês do produto " + prod.getId());
        System.out.println("Janeiro   >> " + res[i][0]);
        System.out.println("Fevereiro >> " + res[i][1]);
        System.out.println("Março     >> " + res[i][2]);
        System.out.println("Abril     >> " + res[i][3]);
        System.out.println("Maio      >> " + res[i][4]);
        System.out.println("Junho     >> " + res[i][5]);
        System.out.println("Julho     >> " + res[i][6]);
        System.out.println("Agosto    >> " + res[i][7]);
        System.out.println("Setembro  >> " + res[i][8]);
        System.out.println("Outubro   >> " + res[i][9]);
        System.out.println("Novembro  >> " + res[i][10]);
        System.out.println("Dezembro  >> " + res[i][11]);
        System.out.println("---------------------------------");
        if(i == 1) System.out.println("A -> Ver no Total");
        if(i > 1) System.out.println("'A' -> Ver a Filial " + (i - 1));
        if(i < 3) System.out.println("'D' -> Ver a Filial " + (i + 1));
        System.out.println("'Q' -> Sair");
    }

    /**
     * Método que printa os resultados obtidos nas queries estastisticas
     * @param res - resultado obtido nas queries estastisticas
     */
    public void printQueriesEstatisticas(QueryResults.LastSalesData res) {
        System.out.println("Queries Estatisticas:");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nome do último ficheiro de vendas lido: " + res.getFilename() + "   |");
        System.out.println("\t-----------------------------------------------------------\n");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº de vendas lidas inválidas: " + res.getNumVendasErradas() + "                    |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº total de produtos: " + res.getNumTotalProdutos() + "                            |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº total de produtos comprados: " + res.getNumProdsUnicosComprados() + "                  |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº total de produtos não comprados: " + res.getNumProdsNaoComprados() + "                 |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº total de clientes: " + res.getNumTotalClientes() + "                             |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº total de clientes compradores: " + res.getNumClientesCompradores() + "                 |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº total de clientes não compradores: " + res.getNumClientesNaoCompradores() + "                 |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Total faturado: " + res.getFaturacaoTotal() + "                          |");
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº de compras por mês:                                  |");
        float[][] r = res.getComprasMes();
        for (int j = 0; j < 9; j++)
            System.out.printf("\t| Mês %d: %.00f                                          |\n", j+1, r[0][j]);
        for(int j = 9; j < 12; j++)
            System.out.printf("\t| Mês %d: %.00f                                         |\n", j+1, r[0][j]);
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Faturação total por mês:                                |");
        for (int j = 0; j < 9; j++)
            System.out.printf("\t| Mês %d: %.02f                                    |\n", j+1, r[1][j]);
        for (int j = 9; j < 12; j++)
            System.out.printf("\t| Mês %d: %.02f                                   |\n", j+1, r[1][j]);
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\t| Nº de clientes distintos que realizaram compras por mês:|");
        int[] ret = res.getClientesDiferentesMes();
        for (int i = 0; i < 9; i++)
            System.out.printf("\t| Mês %d: %d                                            |\n", i+1, ret[i]);
        for(int i = 9; i < 12; i++)
            System.out.printf("\t| Mês %d: %d                                           |\n", i+1, ret[i]);
        System.out.println("\t-----------------------------------------------------------");
        System.out.println("\n");
    }
}