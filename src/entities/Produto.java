package entities;
import interfaces.IEntidade;

/**
 * Classe que implementa um Produto, que implementa a interface IEntidade.
 * Esta classe consiste em uma String que corresponde à identificação do Produto, duas letras e 4 inteiros (ex:AF1184).
 */
public class Produto implements IEntidade {
    // Variáveis de instância
    String id;

    /*##############################################################################################*/
    /**
     * Construtores da Classe Produto.
     * Declaração dos contrutores por omissão, parametrizado e de cópia.
     */

    public Produto(){
        this.id = "";
    }

    public Produto(String id) {
        this.id = id;
    }

    public Produto(Produto p){
        this.id = p.getId();
    }
    /*##############################################################################################*/
    /**
     * Devolve o id do Produto.
     * @return String que correspondo ao id do Produto.
     */
    public String getId() {
        return id;
    }

    /**
     * Atualiza o id do Produto.
     * @param id - novo valor do id
     */
    public void setId(String id) {
        this.id = id;
    }
    /*##############################################################################################*/
    /**
     * Método que valida um Produto.
     * Um produto é válido se o seu id tiver duas letras maiusculas seguidas de 4 algarismos.
     * @return true se for válido.
     * */
    @Override
    public boolean validar() {
        return this.getId().matches("[A-Z]{2}\\d{4}");
    }
    /*##############################################################################################*/
    /**
     * Metodo que devolve a representação em String do Produto.
     * @return String com a representação do produto.
     */
    @Override
    public String toString() {
        return "Produto {\n" +
                " - id = " + this.getId() + "\n" +
                "}";
    }

    /**
     * Metodo que faz uma cópia do objeto receptor da mensagem.
     * @return Produto clone do produto que recebe esta mensagem.
     */
    @Override
    public Produto clone() {
        return new Produto(this);
    }

    /**
     * Método usado para ditar a ordem entre dois Clientes.
     * @param entity Cliente recebido para comparar com esta instancia de Cliente.
     * @return 0 se forem iguais
     *         >0 se o entity for menor que this
     *         <0 se o entity for mais que this.
     */
    @Override
    public int compareTo(IEntidade entity) {
        Produto produto = (Produto)entity;
        return this.getId().compareTo(produto.getId());

    }

    /**
     * Metodo que determina se dois Clientes são iguais.
     * Para serem considerados iguais têm de ter ambos o mesmo id.
     * @param o - objeto para comparar com this
     * @return true se forem iguais.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return this.id != null ? this.id.equals(produto.getId()) : produto.getId() == null;
    }

    /**
     * Método que calcula a posição que this irá ter numa Hash Table.
     * @return número de Hash de this.
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}