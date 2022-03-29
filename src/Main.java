
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

	public static void main(String[] args) {
		
		Thread hilo=null;	
		int numHilos=0;
		int counter=0;
		List<Thread> listaHilos=new ArrayList<Thread>();
		List<ClassB> listaClassB=new ArrayList<ClassB>();
		List<ClassB2> listaCB=new ArrayList<ClassB2>();		
		Scanner sc=new Scanner(System.in);		 		
		String opcion=null;
		
		try {
			do {
				System.out.println("\nIntroducir un valor para el Counter: ");
				counter=sc.nextInt();
				while(counter<=0) {
					System.out.println("Error, el valor a introducir debe ser mayor de 0.");
					System.out.println("\nIntroducir un valor para el Counter: ");
					counter=sc.nextInt();
				}
				System.out.println("Introducir número de hilos a crear: ");
				numHilos=sc.nextInt();
				while(numHilos<=0) {
					System.out.println("Error, el valor a introducir debe ser mayor de 0.");
					System.out.println("Introducir número de hilos a crear: ");
					numHilos=sc.nextInt();
				}
				
				System.out.println("Introducir un monitor a utilizar o 'exit' para terminar.");
				System.out.println("1. Notify\n2. NotifyAll\n3. Exit");
				opcion=sc.next();			
					
				switch(opcion) {		
				case "1":					
					ClassA classA=new ClassA(counter);
					
					for(int i=0;i<numHilos;i++) {
						ClassB classB=new ClassB(classA);
						listaClassB.add(classB);
					}
					for(int f=0;f<listaClassB.size();f++) {
						if(f==listaClassB.size()-1) {
							listaClassB.get(f);
							listaClassB.get(f).setNext(listaClassB.get(0));
						}else {
							listaClassB.get(f).setNext(listaClassB.get(f+1));
						}
					}
					for(ClassB cb : listaClassB) {
						hilo=new Thread(cb);
						hilo.start();
						listaHilos.add(hilo);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							System.out.println("Error, hilo interrumpido en el sleep(500) de la opción 1 de la clase Main");
						}
					}
					synchronized(listaClassB.get(0)){
						listaClassB.get(0).notify();						
					}				
					while(!classA.isFinished());
					if(classA.isFinished()) {	
						classA.listarHilos();
						classA.comprobacion(listaHilos);
					}
					break;
					
				case "2":	
					AtomicInteger counterAI=new AtomicInteger(counter);			
					ClassA2 classA_plus=new ClassA2(counterAI);
					
					for(int i=0;i<numHilos;i++) {					
						ClassB2 classB_lista=new ClassB2(classA_plus);
						listaCB.add(classB_lista);
					}
					for(ClassB2 cb : listaCB) {
						cb.setLista(listaCB);
					}
					for(ClassB2 cb : listaCB) {
						hilo=new Thread(cb);
						hilo.start();
						listaHilos.add(hilo);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							System.out.println("Error, hilo interrumpido en el sleep(500) de la opción 2 de la clase Main");
						}
					}
					synchronized(listaCB){
						listaCB.notifyAll();
					}
					
					while(!classA_plus.isFinished());
					if(classA_plus.isFinished()) {		
						classA_plus.listarHilos();
						classA_plus.comprobacion(listaHilos);
					}					
					break;
				
				case "3":
					System.out.println("La ejecución se ha terminado. Adios!!.");
					break;
					
				default:			
					System.out.println("Error, el valor introducido no es correcto.");
					break;	
				}
					
				System.out.println("\nContinuar con la ejecución?\n1.sí\n2.no");			
				String seguir=sc.next();
				String op=seguir.toLowerCase();
				
				switch(op) {
					case "1":
						break;
						
					case "2":
						System.out.println("La ejecución se ha terminado. Adios!!.");
						opcion="3";
						return;
						
					default:
						System.out.println("Error, el valor introducido no es correcto.");
						System.out.println("\nDesea seguir con la ejecución?\n1.sí\n2.no");			
						seguir=sc.next();
						op=seguir.toLowerCase();					
						break;	
				}
			} while(!opcion.equals("3"));
			
		} catch(InputMismatchException e) {
			System.out.println("Error, el valor introducido no es un digito.");
		}
		sc.close();
	}
}
