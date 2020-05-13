package entities;

/**
 * This constructor helps in how we can save the nodes connections in a list.
 * That contains the constructorÂ´s getters and setters to access your fields.
 */
public class Connections<T> {
    private T father;
    private T son;

    /**
     * The constructor fields 
     * @param son node
     * @param father node
     */
    public Connections(T son, T father) {
        this.father = father;
        this.son = son;
    }


    /**
     * Function to get the Father field from the constructor
     * @return father, returns the father field
     */
    public T getFather() {
        return father;
    }

    /**
     * Function to modify a Father field in the constructor
     * @param father modifies the father field in the constructor 
     */
    public void setFather(T father) {
        this.father = father;
    }

    /**
     * Function to get the Son field from the constructor
     * @return son, returns the son field
     */
    public T getSon() {
        return son;
    }

    /**
     * Function to modify a Son field in the constructor
     * @param son modifies the son field in the constructor
     */
    public void setSon(T son) {
        this.son = son;
    }

}
