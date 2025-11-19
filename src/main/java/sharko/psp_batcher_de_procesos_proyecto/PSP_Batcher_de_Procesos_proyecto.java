package sharko.psp_batcher_de_procesos_proyecto;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author dam2_alu03@inf.ald
 */
public class PSP_Batcher_de_Procesos_proyecto {

    public static void main(String[] args) {
        
        String rutaCarpeta = "jobs/";                       //Ruta de los archivos yaml
        List<Job> jobs = new ArrayList<>();                 //Lista de trabajos
        Queue<Job> readyQueue = new LinkedList<>();         //Lista de cola preparado
        Queue<Job> waitingQueue = new LinkedList<>();       //Lista de cola en espera
        List<Job> runningJobs = new ArrayList<>();          //Lista de cola en proceso
        File carpeta = new File(rutaCarpeta);               //Archivo dentro de la carpeta
        
        JobReciever jr = new JobReciever();                 //Creacion de objeto que se encargara de leer los YAML
        jobs = jr.reciveJobs(Path.of(rutaCarpeta));         //Llamar al metodo para cargar los jobs desde YAML
        
        // Mostrar resumen de los jobs cargados
        System.out.println("Total de jobs cargados: " + jobs.size());
        for (Job job : jobs) {                              //Bucle para recorrer los jobs
            System.out.println(job);
        }
        
        //Prueba de Jobs con el ResourceManager
        ResourceManager resources = new ResourceManager(8, 16000);              //Simulacion de un sistema de 8 nucleos y 16GB de RAM
        for (Job job : jobs) {                                                  //Bucle para recorrer jobs
            if (resources.canRun(job)) {                                        //Comprobar si hay recursos para ejecutar los jobs
                job.setState(Job.State.READY);                                  //Si hay recursos el estado pasa a READY
                readyQueue.add(job);                                            //Añadir a la cola de ready
            } else {
                job.setState(Job.State.WAITING);                                //Si no hay recursos pasa a waiting
                waitingQueue.add(job);                                          //añadir a la cola de waiting
            }
        }
        
        //Menu interactivo consola
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona una politica de planificacion");
        System.out.println("1. FCFS (First Come, First Served)");
        System.out.println("2. Round Robin");
        System.out.print("Opcion: ");
        int opcion = sc.nextInt();

        String politica = (opcion == 2) ? "RR" : "FCFS";
        int quantum = 200; // en milisegundos (solo para RR) Cada Job "ocupa" la CPU solo ese intervalo y luego cede el turno.
    
        while (!readyQueue.isEmpty()) {                                         //Bucle mientras haya trabajos para ejecutar
            if (politica.equals("FCFS")) {
                Job job = readyQueue.poll();                                    //Extrae el primer job de la cola
                if (resources.canRun(job)) {                                    //Comprueba si hay recursos disponibles
                    resources.allocate(job);                                    //Reserva los recursos necesarios
                    job.setState(Job.State.RUNNING);                            //Cambia el estado del job a running
                    job.setStartTime(System.currentTimeMillis());               //Registra el tiempo de inicio
                    runningJobs.add(job);                                       //Añade el job a la lista ejecutandose
                    System.out.println("Ejecutando (FCFS): " + job.getName());  //Mostrar en consola el job que esta activo
                    job.setState(Job.State.DONE);                               //Cambiarle el estado del job a DONE
                    resources.release(job);                                     //Libera recursos del job
                    while (!waitingQueue.isEmpty()) {                           //Bucle de trabajos esperandos al no tener recursos
                        Job w = waitingQueue.peek();                            //Va al primer Job de la cola de espera
                        if (resources.canRun(w)) {                              //Comprueba si hay recursos disponibles para ejecutar
                            waitingQueue.poll();                                //Si lo hay se quita de la cola de WAITING
                            w.setState(Job.State.READY);                        //Cambia el estado del job a READY
                            readyQueue.add(w);                                  //Lo agregamos a la cola correspondiente
                        } else {
                            break;                                              //Si no hay recursos se sale del bucle
                        }
                    }
                } else {
                    job.setState(Job.State.WAITING);                            //Sin recursos pasa a waiting
                    waitingQueue.add(job);                                      //Añadir el job a la cola de espera
                }
            } else {                                                            //Si eliges el metodo Round Robin
                Job job = readyQueue.poll();                                    //Extrae el primer job de la cola
                if (resources.canRun(job)) {                                    //Comprueba si hay recursos dispo
                    resources.allocate(job);                                    //Reserva los recursos necesarios
                    job.setState(Job.State.RUNNING);                            //Cambia el estado del job a running
                    job.setStartTime(System.currentTimeMillis());               //Registra el tiempo de inicio
                    runningJobs.add(job);                                       //Añade el job a la lista ejecutandose
                    long ejecucion = Math.min(quantum, job.getRemainingTime()); //Valor para tiempo de ejecucion
                    System.out.println("Ejecutando (RR): " + job.getName() + " por " + quantum + " ms");

                    // Simulación del consumo de quantum sin ejecutar por ahora
                    job.setRemainingTime(job.getRemainingTime() - ejecucion);
                    resources.release(job);                                     //Libera los recursos usados por el job
                    runningJobs.remove(job);                                    //Se quita de la lista running
                    
                    if (job.getRemainingTime() > 0) {
                        job.setState(Job.State.READY);                              //Vuelve a esta listo tras su turno
                        readyQueue.add(job);                                        //Vuelve al final de la cola (cola circular)
                    } else {
                        job.setState(Job.State.DONE);                           //Pasa a ser un job finalizado
                        job.setEndTime(System.currentTimeMillis());             //Guarda el tiempo cuando finalizo el job
                        System.out.println(job.getName()+" finalizado.");
                    }
                } else {
                    job.setState(Job.State.WAITING);                            //Si no hay recursos pasa a waiting
                    waitingQueue.add(job);                                      //Se añade a la cola de espera
                }
            }
            resources.printStatus();                                            //Imprime los recursos disponibles
        }

        System.out.println("Todos los jobs procesados");                        //Vista desde consola para saber el estado de los jobs
        for (Job job : jobs) {
            System.out.println(job.getName() + " Estado final: " + job.getState());
        }
        sc.close();                                                             //Cerrar escaner
    }
}
