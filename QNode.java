
public class QNode {
	private int vertex;
	private QNode next;
	
	public QNode(int v, QNode nxt){
		vertex = v;
		next = nxt;
	}
	
	public int getvertex(){
		return vertex;
	}
	
	public QNode getNext(){
		return next;
	}
	
	public void setNext(QNode q){
		next = q;
	}
}
