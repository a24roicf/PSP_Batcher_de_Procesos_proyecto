# PSP Batcher de Procesos proyecto
## Clases
#### PSP_Batcher_de_Procesos_proyecto
Esta seria la clase main donde converjen el resto de clases para hacer funcial el proyecto
#### Job
Clase donde se encuentran los estados de cada job, las variables que debera que tener el archivo YAML para poder leer cada uno de los archivos y los getters y setters correspondientes
#### JobReciever
Clase para comprobar el contenido de cada YAML, los lee, valida los campos obligatorios e ignora los datos extra y procesa el YAML
#### ResourceManager
Clase que controla el estado de cada job, si puede iniciarse, reserva recursos y los libera y tambien imprime un estado en consola.
### Autor
Roi Conles Ferro
