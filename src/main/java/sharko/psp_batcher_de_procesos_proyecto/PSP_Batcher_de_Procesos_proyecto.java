package sharko.psp_batcher_de_procesos_proyecto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author dam2_alu03@inf.ald
 */
public class PSP_Batcher_de_Procesos_proyecto {

    public static void main(String[] args) {
        
        String rutaCarpeta = "jobs/";                   //Ruta de los archivos yaml
        Queue<Job> jobsWaiting = new LinkedList<>();      //Cola de listas de trabajos esperando recursos
        //Map<Job,proceso> jobsRunning = new HashMap<>(); //Lista de trabajos en ejecucion asociado cada uno con el proceso correspondiente
        List<Job> jobs = new ArrayList<>();             //Lista para tener todos los archivos guardados
        File carpeta = new File(rutaCarpeta);           //Archivo dentro de la carpeta
        
        if (!carpeta.exists() || !carpeta.isDirectory()) {                  //Condicion para saber si la ruta existe y es directorio
            System.err.println("Carpeta no encontrada: " + rutaCarpeta);
        }
        
        //Bucle para recorrer todos los archivos.yaml
        for (File file : carpeta.listFiles((dir, name) -> name.endsWith(".yaml"))) {
            try (InputStream input = new FileInputStream(file)) {
                Yaml yaml = new Yaml();
                Job job = yaml.loadAs(input, Job.class);                //SnakeYAML para convertir el YAML en un objeto Job

                if (job != null) {                                      //Condicion para añadir los yaml como objetos
                    jobs.add(job);
                    System.out.println("Cargado job: " + job.getId() + " [" + job.getName() + "]");
                }
            } catch (Exception e) {
                System.err.println("Error al leer " + file.getName() + ": " + e.getMessage());
            }
        }
        
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
