package entities;
import interfaces.IEntidade;

/**
 * Classe que implementa um Cliente, que implementa a Interface IEntidade.
 * Esta classe consiste em uma String que corresponde à identificação do Cliente, uma letra e 4 inteiros (ex:L4891).
 */
public class Cliente implements IEntidade {
    //Variaveis de instância
    String id;

    /*##############################################################################################*/
    /**
     * Construtores da Classe Cliente.
     * Declaração dos contrutores por omissão, parametrizado e de cópia.
     */
    public Cliente() {
        this.id = "";
    }

    public Cliente(String id) {
        this.id = id;
    }

    public Cliente(Cliente c) {
        this.id = c.id;
    }
    /*##############################################################################################*/

    /**
     * Devolve o id do Cliente.
     * @return String que correspondo ao id do Cliente.
     */
    public String getId() {
        return id;
    }

    /**
     * Atualiza o id do Cliente.
     * @param id - novo valor do id
     */
    public void setId(String id) {
        this.id = id;
    }

    /*##############################################################################################*/

    /**
     * Método que verifica se este cliente é válido.
     * Para ser considerado válido o primeiro elemento do id tem de ser uma letra maiúscula
     * seguida de 4 algarismos.
     * @return true se for válido
     */
    @Override
    public boolean validar() {
        return this.getId().matches("[A-Z]\\d{4}");
    }
    /*##############################################################################################*/

    /**
     * Metodo que devolve a representação em String do Cliente.
     * @return String com a representação do cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "id = '" + this.getId() + '\'' +
                '}';
    }

    /**
     * Metodo que faz uma cópia do objeto receptor da mensagem.
     * @return Cliente clone do cliente que recebe esta mensagem.
     */
    @Override
    public Cliente clone() {
        return new Cliente(this);
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
        Cliente cliente = (Cliente)entity;
        return this.id.compareTo(cliente.getId());
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
        Cliente cliente = (Cliente) o;
        return this.id != null ? this.id.equals(cliente.getId()) : cliente.getId() == null;
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