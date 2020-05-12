package entities;

public class Connections<T> {
    private T father;
    private T son;


    public Connections(T son, T father) {
        this.father = father;
        this.son = son;
    }



    public T getFather() {
        return father;
    }


    public void setFather(T father) {
        this.father = father;
    }


    public T getSon() {
        return son;
    }

    public void setSon(T son) {
        this.son = son;
    }

    /**
    public String toString() {
        return "X" + son + " " + ":" + " " + "X" + father;
    }*/

}
