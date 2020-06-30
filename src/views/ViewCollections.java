package views;

import interfaces.IEntidade;
import interfaces.IVCollections;
import java.util.Collection;
import java.util.Iterator;

/**
 * Classe que implementa uma ViewCollections, que implementa a Interface IVCollections.
 * Esta classe foi implementada para uma melhor apresentação das collections ao utilizador.
 */
public class ViewCollections implements IVCollections {

    /**
     * Método faz os prints para o utilizador duma collection.
     * De maneira a ficar mais organizado nao mostra a lista toda, pois há alturas em que iriam
     * aparecer mais de 1000 produtos no monitor, o que iria ser dificil de entender para o utilizador.
     * Por isso nesta função apenas mostramos i + 20 elementos da coleção.
     * @param col - coleção que queremos printar para o utilizador
     * @param i - primeiro elemento que vamos printar.
     */
    public void show(Collection<IEntidade> col, int i) {
        int c = 0;
        Iterator<IEntidade> it = col.iterator();
        System.out.println("-----------------------------");
        while (it.hasNext() && c < i + 20){
            IEntidade e = it.next();
            if(c >= i){
                System.out.println("   >> " + e.getId());
            }
            c++;
        }
        System.out.println("-----------------------------");
        if(i >= 20) System.out.println(" A -> Página anterior");
        if(i + 20 < col.size()) System.out.println(" D -> Próxima página");
        System.out.println(" Q -> Fechar");
    }

    /**
     * Método que printa uma mensagem e um inteiro ao utilizador.
     * Só usamos esta função na query 1 para printar o resultado obtido.
     * @param s mensagem a printar.
     * @param i inteiro a printar.
     */
    public void show(String s, int i) {
        System.out.println("  " + s  + " " + i);
    }
}