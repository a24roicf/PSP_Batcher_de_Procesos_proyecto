package sharko.psp_batcher_de_procesos_proyecto;

/**
 *
 * @author Usuario
 */
import java.time.Instant;

public class Job {
    private String id;          // Identificador único
    private String name;        // Nombre
    private int priority;       // prioridad
    private int cpuCores;       // Núcleos solicitados
    private int memMb;          // Memoria solicitada en MB
    private long durationMs;    // Duración en milisegundos
    private State state;        // Estado del job
    private long arrivalTime;   // Tiempo de llegada
    private Long startTime;     // Inicio
    private Long endTime;       // Final

    public enum State {
        NEW, READY, WAITING, RUNNING, DONE, FAILED // Posibles estados del job
    }

    // 👇 Constructor vacío (requerido por SnakeYAML)
    public Job() {
        this.state = State.NEW;
        this.arrivalTime = Instant.now().toEpochMilli();
    }

    // 👇 Constructor completo (opcional, para uso manual)
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

    // --- Getters y Setters (importantes para SnakeYAML) ---
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCpuCores() {
        return cpuCores;
    }
    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public int getMemMb() {
        return memMb;
    }
    public void setMemMb(int memMb) {
        this.memMb = memMb;
    }

    public long getDurationMs() {
        return durationMs;
    }
    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
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
    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    // --- Representación en texto ---
    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", cpuCores=" + cpuCores +
                ", memMb=" + memMb +
                ", durationMs=" + durationMs +
                ", state=" + state +
                ", arrivalTime=" + arrivalTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
