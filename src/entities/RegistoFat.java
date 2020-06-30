package entities;
import interfaces.IRegFat;
import interfaces.IRegFilial;

import java.util.Arrays;

/**
 * Classe que implementa um registo de faturação, que implementa a interface IRegFat.
 * A RegistoFat contém todos os dados correspondentes à faturação um produto.
 */
public class RegistoFat implements IRegFat {
    // Variáveis de instância
    private int quant;
    private final float[][] fatMensalN;
    private final float[][] fatMensalP;
    private final int[][] compraP;
    private final int[][] compraN;

    /*##############################################################################################*/
    /**
     * Construtores da Classe Produto.
     * Declaração dos contrutores por omissão, parametrizado e de cópia.
     */

    public RegistoFat() {
        this.quant = 0;
        this.fatMensalN = new float[3][12];
        this.fatMensalP = new float[3][12];
        this.compraP = new int[3][12];
        this.compraN = new int[3][12];
    }

    public RegistoFat(int quant, float[][] fatMensalN, float[][] fatMensalP, int[][] compraP, int[][] compraN) {
        this.quant = quant;
        this.fatMensalN = fatMensalN;
        this.fatMensalP = fatMensalP;
        this.compraP = compraP;
        this.compraN = compraN;
    }

    public RegistoFat(RegistoFat rf) {
        this.quant = rf.getQuant();
        this.fatMensalP = rf.getFatMensalP();
        this.fatMensalN = rf.getFatMensalN();
        this.compraP = rf.getCompraP();
        this.compraN = rf.getCompraN();
    }

    /**
     * Este último construtor é usado para, durante a leitura do ficheiro de vendas, criar um
     * RegistoFat auxiliar para incrementar no valor original na faturação.
     */
    public RegistoFat(int quant, float price, char type, int mes, int filial) {
        this();
        this.quant = quant;
        if (type == 'P') {
            this.fatMensalP[filial - 1][mes - 1] = price * quant;
            this.compraP[filial - 1][mes - 1]++;
        }
        else {
            this.fatMensalN[filial - 1][mes - 1] = price * quant;
            this.compraN[filial - 1][mes - 1]++;
        }
    }
    /*##############################################################################################*/
    /**
     * Getters
     */

    public int getQuant() {
        return this.quant;
    }

    public float[][] getFatMensalN() {
        return this.fatMensalN;
    }

    public float[][] getFatMensalP() {
        return this.fatMensalP;
    }

    public float getFatMensalN(int filial, int mes){
        return this.fatMensalN[filial][mes];
    }

    public float getFatMensalP(int filial, int mes){
        return this.fatMensalP[filial][mes];
    }

    public int[][] getCompraP() {
        return this.compraP;
    }

    public int[][] getCompraN() {
        return this.compraN;
    }

    public int getCompraP(int filial, int mes) {
        return this.compraP[filial][mes];
    }

    public int getCompraN(int filial, int mes) {
        return this.compraN[filial][mes];
    }
    /*##############################################################################################*/
    /**
     * Método que incrementa o valor do RegFat que receber a mensagem.
     * Soma os valores dos dois registos.
     * @param reg - registo com os valores a incrementar em this
     */
    public void incrementar(IRegFat reg) {
        RegistoFat r = (RegistoFat) reg;
        this.quant += r.getQuant();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 12; j++) {
                this.fatMensalP[i][j] += r.getFatMensalP(i, j);
                this.fatMensalN[i][j] += r.getFatMensalN(i, j);
                this.compraP[i][j] += r.getCompraP(i, j);
                this.compraN[i][j] += r.getCompraN(i, j);
            }
        }
    }

    /**
     * Método que percorre a matriz das comprasP e calcula o total faturado pelo modo P.
     * @return total faturado pelo modo Promoção.
     */
    private int getTotalCompraP() {
        int res = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 12; j++)
                res += this.compraP[i][j];
        return res;
    }

    /**
     * Método que percorre a matriz das comprasN e calcula o total faturado pelo modo N.
     * @return total faturado pelo modo Normal.
     */
    private int getTotalCompraN() {
        int res = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 12; j++)
                res += this.compraN[i][j];
        return res;
    }
    /*##############################################################################################*/
    /**
     * Método que determina se dois RegistoFat são iguais.
     * Para serem considerados iguais têm de ter os mesmos valores em todas as posições
     * dos arrays e a mesma quantidade.
     * @param o - objeto para comparar com this
     * @return true se forem iguais.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistoFat that = (RegistoFat) o;
        return Arrays.equals(fatMensalN, that.fatMensalN) &&
                Arrays.equals(fatMensalP, that.fatMensalP) &&
                Arrays.equals(compraP, that.compraP) &&
                Arrays.equals(compraN, that.compraN) &&
                this.quant == that.getQuant();
    }

    /**
     * Método que calcula a posição que this irá ter numa Hash Table.
     * @return número de Hash de this.
     */
    @Override
    public int hashCode() {
        int result = Arrays.hashCode(fatMensalN);
        result = 31 * result + Arrays.hashCode(fatMensalP);
        result = 31 * result + Arrays.hashCode(compraP);
        result = 31 * result + Arrays.hashCode(compraN);
        return result;
    }

    /**
     * Metodo que devolve a representação em String do RegistoFat.
     * @return String com a representação do registo.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Registo Faturacao {\n").
                append("Quant: ").append(this.quant);
        for(int i = 0; i < 3; i++) {
            sb.append("\n\tFilial ").append(i).append(" {\n");
            sb.append("\t\tFaturacao N: ").append(Arrays.toString(this.fatMensalN[i]));
            sb.append("\n\t\tFaturacao P: ").append(Arrays.toString(this.fatMensalP[i]));
            sb.append("\n\t\tCompras N: ").append(Arrays.toString(this.compraN[i]));
            sb.append("\n\t\tCompras P: ").append(Arrays.toString(this.compraP[i]));
        }
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * Metodo que faz uma cópia do objeto receptor da mensagem.
     * @return Produto clone do produto que recebe esta mensagem.
     */
    @Override
    public RegistoFat clone() {
        return new RegistoFat(this);
    }
    /*################################################ QUERIES #######################################################*/
    /** Query 1
     * Método que verifica se o produto com este registo nunca foi comprado.
     * @return true se o produto com este registo nunca foi comprado.
     */
    public boolean isProductNeverBought() {
        return getTotalCompraN() == 0 && getTotalCompraP() == 0;
    }

    /** Query 4
     *  Método que devolve o número de vezes que o produto com este registo foi comprado num determinado mês.
     * @param mes - vai se ver quantas vezes o produto com este registo foi comprado neste mês.
     * @return quantas vezes o produto com este registo foi comprado no mês.
     */
    public int getNumRegVendaProdutoMonth(int mes) {
        int num = 0;
        for (int i = 0; i < 3; i++)
            num += this.compraN[i][mes-1] + this.compraP[i][mes-1];
        return num;
    }

    /** Query 4
     * Método que determina a faturacao total num determinado mês.
     * @param mes - vai se ver o total faturado neste mês.
     * @return total faturado no mês.
     */
    public float getFatProdutoMonth(int mes) {
        float num = 0;
        for (int i = 0; i < 3; i++)
            num += this.fatMensalN[i][mes-1] + this.fatMensalP[i][mes-1];
        return num;
    }

    /** Query 10
     * Método que para cada mes e para cada filial determina o total faturado.
     * @return uma matriz de floats em que cada linha corresponde a uma filial e cada coluna a um mês.
     * Ou seja, na posiçao [1][3] vamos obter o total faturado pela filial 2(1+1), no mês de Abril(3+1).
     */
    public float[][] getFatFilialMes() {
        float[][] ret = new float[3][12];
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 12; j++) {
                ret[i][j] = this.fatMensalN[i][j] + this.fatMensalP[i][j];
            }
        }
        return ret;
    }

    /**
     * Método que calcula o total faturado neste registo, ou seja,
     * percorre as duas matrizes (fatMensalP e fatMensalN) e soma os valores todos.
     * @return total faturado neste registo.
     */
    public double faturado() {
        double r = 0;
        for(int i = 0; i < 3; i++)
            for (int j = 0; j < 12; j++)
                r += this.fatMensalN[i][j] + this.fatMensalP[i][j];
        return r;
    }
}