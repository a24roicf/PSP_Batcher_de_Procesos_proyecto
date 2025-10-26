package sharko.psp_batcher_de_procesos_proyecto;

import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Usuario
 */
public class JobReciever {
    //Leer fichero
    
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
        return null;
    }
    //Procesar YAML
    public Job processYaml(InputStream input) throws Exception{
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(input);
        Job job = validateJobs(data);
        return job;
    }
}
