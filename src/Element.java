import java.util.ArrayList;
import java.util.List;

public class Element {
	private int number;
	private List<Integer> used = new ArrayList<Integer>();
	public Element(int number) {
		super();
		this.number = number;
		for(int i=1; i<=9; i++) {
			used.add(i);
		}
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public List<Integer> getUsed() {
		return used;
	}
	public void setUsed(List<Integer> used) {
		this.used = used;
	}
	
}
