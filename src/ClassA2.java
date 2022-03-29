import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassA2 {private AtomicInteger counter=new AtomicInteger();
private Set<Long> threadIds=new HashSet<Long>();


public ClassA2(AtomicInteger counter) {
	this.counter=counter;
}

public synchronized void EnterAndWait() {
	
	Thread hilo=Thread.currentThread();
	Long idHilo=hilo.getId();
	System.out.println("El hilo que está ejecutando es: "+idHilo);
	threadIds.add(idHilo);	
	counter.getAndDecrement();
    System.out.println("Nuevo valor del contador: "+counter);
	System.out.println("El hilo que está acabando de ejecutar es: "+idHilo+"\n");		
}

public boolean isFinished() {
	
	if(counter.get()==0) {
		return true;
	}
	return false;
}

public void listarHilos() {
	
	System.out.println("\nIDENTIFICADORES DE LOS HILOS EJECUTADOS");
	for(Long id : threadIds) {
		String listar=String.valueOf(id);
		System.out.println("Hilo "+listar);
	}
	System.out.println(" ");
}

public void comprobacion(List<Thread> listaHilos) {
	
	List<Thread> listaThread=listaHilos;
	Long idHilo=null;
	
	System.out.println("COMPROBACIÓN DE LOS HILOS QUE HAN ACCEDIDO A ENTERANDWAIT()");
	for(Long id : threadIds) {
		Long idThread=id;			
		for ( int j=0;j<listaThread.size();j++) {
			Thread hilo=listaThread.get(j);
			idHilo=hilo.getId();
			if (idThread==idHilo) {
				System.out.println("El hilo "+idHilo+" ha ejecutado el método EnterAndWait ().");
			}
		}		
	}
	

}
}
