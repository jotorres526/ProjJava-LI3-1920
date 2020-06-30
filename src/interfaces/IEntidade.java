package interfaces;

import java.io.Serializable;

public interface IEntidade extends Comparable<IEntidade>, Serializable {
    boolean validar();
    String getId();
    IEntidade clone();
    int compareTo(IEntidade entity);
}
