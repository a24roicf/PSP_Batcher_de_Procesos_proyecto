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
}
