package entities;
import interfaces.IEntidade;
import interfaces.IRegFilial;
import java.util.Objects;

/**
 * Classe que implementa um registo de filial, que implementa a interface IRegFil.
 * A RegistoFat contém todos os dados correspondentes à faturação um produto.
 */
public class RegistoFilial implements IRegFilial {
    //Variaveis de instancia
    private IEntidade prod;
    private int quant;
    private int mes;
    private float preco;
    private char np;

    /*##############################################################################################*/
    /**
     * Construtores da Classe Produto.
     * Declaração dos contrutores por omissão, parametrizado e de cópia.
     */


    public RegistoFilial(){
        this.prod = new Produto();
        this.quant = 0;
        this.preco = 0;
        this.np = ' ';
        this.mes = -1;
    }

    public RegistoFilial(IEntidade p, int quant, float preco, char np, int mes){
        setProd(p);
        this.quant = quant;
        this.preco = preco;
        this.np = np;
        this.mes = mes;
    }

    public RegistoFilial(IRegFilial rf){
        this.prod = rf.getProd();
        this.quant = rf.getQuant();
        this.preco = rf.getPreco();
        this.np = rf.getNp();
        this.mes = rf.getMes();
    }
    /*##############################################################################################*/
    /**
     * Getters e Setters
     */
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

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public char getNp() {
        return np;
    }

    public void setNp(char np) {
        this.np = np;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
    /*##############################################################################################*/
    /**
     * Método que determina se dois RegistoFilial são iguais.
     * Para serem considerados iguais têm de ter todas as variáveis de instância iguais.
     * @param o - objeto para comparar com this
     * @return true se forem iguais.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistoFilial that = (RegistoFilial) o;
        return quant == that.quant &&
                np == that.np &&
                mes == that.mes &&
                this.prod.equals(that.getProd());
    }

    /**
     * Metodo que devolve a representação em String do RegistoFilial.
     * @return String com a representação do registo.
     */
    @Override
    public String toString(){
        return "Registo Filial {" +
                this.prod.toString() +
                "Quantidade " + this.quant +
                "Tipo de compra" + this.np +
                "Mes de Compra" + (this.mes + 1) + "}";
    }

    /**
     * Metodo que faz uma cópia do objeto receptor da mensagem.
     * @return Produto clone do produto que recebe esta mensagem.
     */
    @Override
    public RegistoFilial clone(){
        return new RegistoFilial(this);
    }

    /**
     * Método usado para ditar a ordem entre dois RegistoFilial's.
     * @param r RegistoFilial recebido para comparar com esta instancia.
     * @return 0 se forem iguais
     *         >0 se o entity for menor que this
     *         <0 se o entity for mais que this.
     */
    @Override
    public int compareTo(IRegFilial r) {
        return this.prod.compareTo(r.getProd().clone());
    }

    /**
     * Método que calcula a posição que this irá ter numa Hash Table.
     * @return número de Hash de this.
     */
    @Override
    public int hashCode() {
        return Objects.hash(prod, quant, mes, preco, np);
    }

    /*################################################ QUERIES #######################################################*/

    /** Query 2
     * Método que verifica se o registoFilial corresponde a um determinado mes.
     * @param mes - mês a verificar.
     * @return true se este registo foi feito no mês recebido como argumento.
     */
    public boolean equalsMes(int mes) {
        return this.mes == mes;
    }

    /** Query 4
     * Método que, dado um produto, vê se o cliente o comprou num determinado mes.
     * @return true se o mês e o cliente recebidos como argumentos forem iguais ao cliente e ao mês
     * neste registo.
     */
    public boolean clientBoughtProductMonth(IEntidade p, int mes) {
        return this.prod.equals(p) && this.mes == mes;
    }

    /** Query 7
     *  Método que devolve o preço total que o cliente gastou com o produto tendo em conta a quantidade.
     * @return preço total
     */
    public float getPrecoTotal() {
        return this.preco * this.quant;
    }

    /**
     * Método que verifica se um produto é igual ao produto deste registo.
     * @param prod - produto que se quer verificar se é igual ao neste registo.
     * @return true se o produto recebido como argumento e o produto neste registo forem iguais.
     */
    public boolean hasProduto(IEntidade prod) {
        return this.prod.equals(prod);
    }
}