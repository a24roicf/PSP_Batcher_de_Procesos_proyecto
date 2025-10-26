package sharko.psp_batcher_de_procesos_proyecto;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author dam2_alu03@inf.ald
 */
public class PSP_Batcher_de_Procesos_proyecto {

    public static void main(String[] args) {
        
        String rutaCarpeta = "jobs/";                       //Ruta de los archivos yaml
        Queue<Job> jobsWaiting = new LinkedList<>();        //Cola de listas de trabajos esperando recursos
        //Map<Job,proceso> jobsRunning = new HashMap<>();   //Lista de trabajos en ejecucion asociado cada uno con el proceso correspondiente
        List<Job> jobs = new ArrayList<>();                 //Lista para tener todos los archivos guardados
        File carpeta = new File(rutaCarpeta);               //Archivo dentro de la carpeta
        
        JobReciever jr = new JobReciever();
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
                resources.allocate(job);
                job.setState(Job.State.RUNNING);
                System.out.println("Ejecutando job: " + job.getName());
            } else {
                job.setState(Job.State.WAITING);
                System.out.println("En espera: " + job.getName());
            }
            resources.printStatus();
        }
    }
}
