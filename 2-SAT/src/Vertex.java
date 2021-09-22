import java.util.Objects;

public class Vertex {
    /** nom du sommet */
    String name;

    /** Constructeur de sommet */
    public Vertex(String label) {

        this.name = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    /** Méthode d'affichage */
    @Override
    public String toString() {
        return name;
    }
}
