package sharko.psp_batcher_de_procesos_proyecto;

/**
 *
 * @author Usuario
 */
import java.time.Instant;

public class Job {
    private final String id;          // Identificador único
    private final String name;        // Nombre
    private final int priority;       // prioridad
    private final int cpuCores;       // Núcleos solicitados
    private final int memMb;          // Memoria solicitada en MB
    private final long durationMs;    // Duración en milisegundos

    public enum State {
        NEW, READY, WAITING, RUNNING, DONE, FAILED
    }

    private State state;
    private final long arrivalTime;   // Tiempo de llegada
    private Long startTime;           // Inicio
    private Long endTime;             // Final

    public Job(String id, String name, int priority, int cpuCores, int memMb, long durationMs) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.cpuCores = cpuCores;
        this.memMb = memMb;
        this.durationMs = durationMs;
        this.state = State.NEW;
        this.arrivalTime = Instant.now().toEpochMilli();
    }

    // --- Getters y setters ---
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getPriority(){
        return priority;
    }
    public int getCpuCores() {
        return cpuCores;
    }
    public int getMemMb() {
        return memMb;
    }
    public long getDurationMs() {
        return durationMs;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public long getArrivalTime() {
        return arrivalTime;
    }
    public Long getStartTime() {
        return startTime;
    }
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    public Long getEndTime() {
        return endTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobID= " + id +
                " name= " + name +
                " priority= " + priority +
                " cpuCores= " + cpuCores +
                " memMb= " + memMb +
                " durationMs= " + durationMs +
                " state= " + state +
                " arrival= " + arrivalTime;
        
    };
}

