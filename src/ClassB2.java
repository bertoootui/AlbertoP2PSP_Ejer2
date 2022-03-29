
import java.util.List;


public class ClassB2 implements Runnable{
	private ClassA2 classAplus;

private ClassB2 next;
private List<ClassB2> lista;
private boolean acceder=true;
private Thread hilo=Thread.currentThread();

public ClassB2(ClassA2 classAplus) {
	
	this.classAplus=classAplus;
}

public List<ClassB2> getLista() {
	return lista;
}

public void setLista(List<ClassB2> lista) {
	this.lista=lista;
}

public ClassB2 getNext() {
	return next;
}

public void setNext(ClassB2 next) {
	this.next=next;
}

@Override
public void run() {
	
	while(acceder) {
		synchronized(lista){	
			try {	
				lista.wait();	
			} catch (InterruptedException e) {					
				hilo.interrupt();
				System.out.println("Error, hilo interrumpido.");
			}
			if (classAplus.isFinished()) {
				acceder=false;
			} else {
				classAplus.EnterAndWait();
			}
			lista.notifyAll();
		}
	}		
}
}


