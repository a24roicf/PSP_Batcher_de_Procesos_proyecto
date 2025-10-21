package sharko.psp_batcher_de_procesos_proyecto;

/**
 *
 * @author Usuario
 */
public class ResourceManager {
    private int totalCpuCores;
    private int totalMemMb;
    private int availableCpuCores;
    private int availableMemMb;

    //Constructor de la clase
    public ResourceManager(int totalCpuCores, int totalMemMb) {
        this.totalCpuCores = totalCpuCores;                     //total cpu cores
        this.totalMemMb = totalMemMb;                           //total memoria
        this.availableCpuCores = totalCpuCores;                 //cpu cores disponible
        this.availableMemMb = totalMemMb;                       //memoria disponible
    }
    
    //Booleano de si puede iniciar el job o no
    public boolean canRun(Job job) {
        return job.getCpuCores() <= availableCpuCores && job.getMemMb() <= availableMemMb;
    }

    //Reserva recursos cuando un job inicia
    public void allocate(Job job) {
        availableCpuCores -= job.getCpuCores();
        availableMemMb -= job.getMemMb();
        System.out.println("Recursos asignados a"+ job.getName()+" → CPU: "+job.getCpuCores()+", MEM: "+job.getMemMb());
    }

    //Libera recursos cuando un job termina o falla
    public void release(Job job) {
        availableCpuCores += job.getCpuCores();
        availableMemMb += job.getMemMb();
        System.out.println("Recursos liberados por "+job.getName()+" → CPU: "+job.getCpuCores()+", MEM: "+job.getMemMb()+"MB");
    }

    //Imprimir estado en consola
    public void printStatus() {
        System.out.println("Recursos disponibles → CPU: "+availableCpuCores+"/"+totalCpuCores+", MEM: "+availableMemMb+"/"+totalMemMb+ "MB");
    }
}
