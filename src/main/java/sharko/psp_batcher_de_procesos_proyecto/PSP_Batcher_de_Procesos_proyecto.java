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
        for (Job job : jobs) {
            System.out.println(job);
        }
        
        //Prueba de Jobs con el ResourceManager
        ResourceManager resources = new ResourceManager(8, 16000);
        for (Job job : jobs) {
            if (resources.canRun(job)) {
                job.setState(Job.State.READY);
                readyQueue.add(job);
            } else {
                job.setState(Job.State.WAITING);
                waitingQueue.add(job);
            }
        }
        
        //Menu interactivo consola
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona una política de planificación");
        System.out.println("1. FCFS (First Come, First Served)");
        System.out.println("2. Round Robin");
        System.out.print("Opcion: ");
        int opcion = sc.nextInt();

        String politica = (opcion == 2) ? "RR" : "FCFS";
        int quantum = 200; // en milisegundos (solo para RR) Cada Job "ocupa" la CPU solo ese intervalo y luego cede el turno.

        System.out.println("\nPlanificador seleccionado: " + (politica.equals("FCFS") ? "First Come First Served" : "Round Robin (" + quantum + " ms)"));
    
        while (!readyQueue.isEmpty()) {
            if (politica.equals("FCFS")) {
                Job job = readyQueue.poll();
                if (resources.canRun(job)) {
                    resources.allocate(job);
                    job.setState(Job.State.RUNNING);
                    job.setStartTime(System.currentTimeMillis());
                    runningJobs.add(job);
                    System.out.println("→ Ejecutando (FCFS): " + job.getName());
                } else {
                    job.setState(Job.State.WAITING);
                    waitingQueue.add(job);
                }
            } else {
                Job job = readyQueue.poll();
                if (resources.canRun(job)) {
                    resources.allocate(job);
                    job.setState(Job.State.RUNNING);
                    job.setStartTime(System.currentTimeMillis());
                    runningJobs.add(job);
                    System.out.println("→ Ejecutando (RR): " + job.getName() + " por " + quantum + " ms");

                    // Simulación del consumo de quantum sin ejecutar por ahora
                    job.setState(Job.State.READY);
                    readyQueue.add(job);                                        // Vuelve al final de la cola (cola circular)
                    resources.release(job);
                    runningJobs.remove(job);
                } else {
                    job.setState(Job.State.WAITING);
                    waitingQueue.add(job);
                }
            }
            resources.printStatus();                                            //Imprime los recursos disponibles
        }

        System.out.println("Todos los jobs procesados");                        //Vista desde consola para saber el estado de los jobs
        for (Job job : jobs) {
            System.out.println(job.getName() + "Estado final: " + job.getState());
        }
        sc.close();                                                             //Cerrar escaner
    }
}
