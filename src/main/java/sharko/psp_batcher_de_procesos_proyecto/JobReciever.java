package sharko.psp_batcher_de_procesos_proyecto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Usuario
 */
public class JobReciever {
    //Leer fichero
    public List<Job> reciveJobs(Path dir){
        List<Job> jobs = new ArrayList<>();
        String rutaCarpeta = "jobs/";
        File carpeta = new File(rutaCarpeta);                                           //Archivo dentro de la carpeta
        
        if (!carpeta.exists() || !carpeta.isDirectory()) {                              //Condicion para saber si la ruta existe y es directorio
            System.err.println("Carpeta no encontrada: " + rutaCarpeta);
            return jobs;
        }
        for (File file : carpeta.listFiles((d, name) -> name.endsWith(".yaml"))) {
            try (InputStream input = new FileInputStream(file)) {
                Job job = processYaml(input);
                System.out.println("Job valido: " + job.getId() + " (" + file.getName() + ")");
            } catch (Exception e) {
                System.err.println("Error al procesar " + file.getName() + ": " + e.getMessage());
            }
        }
        return jobs;
    }
    //Leer desde URL
    
    //Validad campos
    public Job validateJobs(Map<String, Object> data) throws Exception{
        //data.forEach((k, v) -> System.out.println(k + ": " + v)); Comprobar lo que lee en consola
        if (data.containsKey("id") && data.get("id") instanceof String && data.get("id") != null) {
            
        }else{
            throw new Exception("El campo ID no esta correcto");
        }
        if (data.containsKey("name") && data.get("name") instanceof String && data.get("name") != null) {
                
        }else{
            throw new Exception("El campo Name no esta correcto");
        }
        if (data.containsKey("priority") && data.get("priority") instanceof Integer && data.get("priority") != null) {
            int priority = (Integer) data.get("priority");
            if (priority >= 0 && priority <= 4) {
                
            }else{
                throw new Exception("El campo Priority no esta correcto");
            }
        }else{
            throw new Exception("El campo Priority no esta correcto");
        }
        if (data.containsKey("cpuCores") && data.get("cpuCores") instanceof Integer && data.get("cpuCores") != null) {
            int cpuCores = (Integer) data.get("cpuCores");
            if (cpuCores >= 1){
                
            }else{
                throw new Exception("El campo cpuCores no esta correcto");
            }
        }else{
            throw new Exception("El campo cpuCores no esta correcto");
        }
        if (data.containsKey("memMb") && data.get("memMb") instanceof Integer && data.get("memMb") != null) {
            int memMb = (Integer) data.get("memMb");
            if (memMb >= 0) {
                
            }else{
                throw new Exception("El campo memMb no esta correcto");
            }
        }else{
            throw new Exception("El campo memMb no esta correcto");
        }
        if(data.containsKey("durationMs") && data.get("durationMs") instanceof Integer && data.get("durationMs") != null){
            int durationMs = (Integer) data.get("durationMs");
            if (durationMs >= 0) {
                
            }else{
                throw new Exception("El campo durationMs no esta correcto");
            }
        }else{
            throw new Exception("El campo durationMs no esta correcto");
        }
        //MODIFICAR DAOD QUE SALE NULO
        //Job job = new Job(id,name,priority,cpuCores, memMb, durationMs);

        return null;
    }
    //Procesar YAML
    public Job processYaml(InputStream input) throws Exception {
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(input);
        return validateJobs(data);
    }
}
