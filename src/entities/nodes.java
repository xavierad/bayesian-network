package entities;

public class nodes {
	private Integer father;
	private Integer son;
	
	
	public nodes(Integer father, Integer son) {
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
		return father + ", " + son;
	}

}
