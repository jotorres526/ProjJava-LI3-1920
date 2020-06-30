package views;
import interfaces.IVErros;

/**
 * Classe que implementa uma ViewErros, que implementa a Interface IVErros.
 * Esta classe foi implementada para printar erros ao utilizador.
 */
public class ViewErros implements IVErros {

    /**
     * Usamos este método para avisar o utilizador que o input que ele inseriu é inválido.
     * Neste caso inseriu um int inválido.
     * @param i - inteiro que o utilizador inseriu
     */
    public void show(int i) {
        System.out.println("Ups! A opção " + i + " é inválida!");
    }

    /**
     * Usamos este método para avisar o utilizador que o input que ele inseriu é inválido.
     * Neste caso inseriu uma String inválida.
     * @param s - String que o utilizador inseriu
     */
    public void show(String s) {
        System.out.println("Ups! O código " + s + " é inválido!");
    }
}