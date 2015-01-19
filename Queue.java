
public class Queue {
	private QNode head, tail;
	
	public Queue(){
		head = null;
		tail = null;
	}
	
	public void enqueue(int v){
		if(head == null){
			QNode temp = new QNode(v, head);
			head = temp;
			tail = temp;
		}
		else {
			QNode temp = new QNode(v, null);
			tail.setNext(temp);
			tail = tail.getNext();
		}
	}
	
	public int dequeue(){
		if(head != null){
			int temp = head.getvertex();
			head = head.getNext();
			return temp;
		}
		return -1;
	}
	
	public boolean isEmpty(){
		if(head == null){
			return true;
		}
		else{
			return false;
		}
	}
}
