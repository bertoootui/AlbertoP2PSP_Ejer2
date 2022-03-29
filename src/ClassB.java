
public class ClassB implements Runnable {

	private ClassA classA;
	private ClassB next;
	private boolean acceder=true;
	
	public ClassB(ClassA classA) {
		
		this.classA=classA;
	}

	public void setNext(ClassB next) {
		
		this.next=next;
	}

	@Override
	public void run() {

		while(acceder) {
			synchronized(this) {
				try {					
					wait();
				} catch (InterruptedException e) {
					System.out.println("Error, hilo interrumpido.");
				}
			}
			if (classA.isFinished()) {
				acceder=false;
			} else {
				classA.EnterAndWait();
			}			
			synchronized (next) {
				next.notify();
			}
		}
	}
}
