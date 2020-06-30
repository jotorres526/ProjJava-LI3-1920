package catalogs;
import interfaces.ICatalogo;
import interfaces.IEntidade;
import java.util.Collection;
import java.util.TreeSet;

/**
 * Classe que implementa um CatalogoEntidades, que implementa a interface ICatalogo.
 * Esta classe consiste numa collection de IEntidades.
 * Decidimos usar esta classe tanto para o Catalogo de Produtos como para o Catalodo de Clientes
 * Uma vez que vão ser iguais a unica coisa que diferem é no qye guardam, unma guarda clientes e outras produtos.
 * Mas como ambas sao IEntidades, problema resolvido.
 */
public class CatalogoEntidades implements ICatalogo {
    private final Collection<IEntidade> catalogo;

    /*##############################################################################################*/
    /**
     * Construtores da Classe CatalogoEntidades.
     * Declaração dos contrutores por omissão e de cópia.
     */

    public CatalogoEntidades() {
        this.catalogo = new TreeSet<>();
    }

    public CatalogoEntidades(CatalogoEntidades cp) {
        this.catalogo = cp.getCatalogo();
    }
    /*##############################################################################################*/

    /**
     * Getter do catálogo.
     * @return cópia de this.catalogo
     */
    public Collection<IEntidade> getCatalogo() {
        Collection<IEntidade> r = new TreeSet<>();
        for(IEntidade x : this.catalogo) {
            r.add(x.clone());
        }
        return r;
    }
    /*##############################################################################################*/
    /**
     * Método que insere uma cópia da Entidade, recebida como argumento, no this.catalogo
     * @param entity - entidade a inserir no this.catalogo
     */
    @Override
    public void inserir(IEntidade entity) {
        this.catalogo.add(entity.clone());
    }

    /**
     * Método que calcula o tamanho de this.catalogo.
     * @return retorna o tamanho de this.catalogo
     */
    @Override
    public int tamanho() {
        return this.catalogo.size();
    }

    /**
     * Método que verifica se existe uma certa entidade, recebida como argumento, em this.catalogo.
     * @param entity - entidade que se vai procurar.
     * @return true se a entidade existir e for válida.
     */
    @Override
    public boolean existe(IEntidade entity) {
        return entity.validar() && this.catalogo.contains(entity);
    }
    /*##############################################################################################*/
    /**
     * Metodo que faz uma cópia do objeto receptor da mensagem.
     * @return Produto clone do produto que recebe esta mensagem.
     */
    @Override
    public ICatalogo clone() {
        return new CatalogoEntidades(this);
    }
}