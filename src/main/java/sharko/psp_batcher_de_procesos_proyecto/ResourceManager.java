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
        return job.getCpuCores() <= availableCpuCores &&
               job.getMemMb() <= availableMemMb;
    }

    //Reserva recursos cuando un job inicia
    public void allocate(Job job) {
        availableCpuCores -= job.getCpuCores();
        availableMemMb -= job.getMemMb();
        System.out.printf("Recursos asignados a %s → CPU: %d, MEM: %dMB%n",
                job.getName(), job.getCpuCores(), job.getMemMb());
    }

    //Libera recursos cuando un job termina o falla
    public void release(Job job) {
        availableCpuCores += job.getCpuCores();
        availableMemMb += job.getMemMb();
        System.out.printf("Recursos liberados por %s → CPU: %d, MEM: %dMB%n",
                job.getName(), job.getCpuCores(), job.getMemMb());
    }

    //Estado
    public void printStatus() {
        System.out.printf("Recursos disponibles → CPU: %d/%d, MEM: %d/%d MB%n",
                availableCpuCores, totalCpuCores, availableMemMb, totalMemMb);
    }
}
