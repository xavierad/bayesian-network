package entities;

public class Connections {
    private Integer father;
    private Integer son;


  public Connections(Integer son, Integer father) {
      this.father = father;
      this.son = son;
  }



  public Integer getFather() {
      return father;
  }


  public void setFather(Integer father) {
      this.father = father;
  }


  public Integer getSon() {
      return son;
  }

  public void setSon(Integer son) {
      this.son = son;
  }


  public String toString() {
      return "X" + son + " " + ":" + " " + "X" + father;
  }

}
