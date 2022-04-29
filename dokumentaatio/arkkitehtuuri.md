## Rakenne

MaintenanceFileService luokka toimii ohjelman keskuksena.
MaintenanceFileService luokka luo huolto-ohjelman MaintenanceFile ja MaintenanceTask luokkien avulla.
Käyttöliittymä kommunikoi pelkästään MaintenanceFileServicen kanssa ja saa sen kautta tietoa huolto-ohjemasta (MaintenanceFile).
DatabaseController sisältää kaiken tietokantaan liittyvän logiikan. MaintenaceFileService on ainoa luokka joka on yhteydessä DatabaseControlleriin.

 ```mermaid
 classDiagram
 	MaintenanceFileService <- MaintenanceFile
	MaintenanceFileService <- OneTimeTask
	MaintenanceFileService <- RecurringTask
	OneTimeTask <- MaintenanceTask
	RecurringTask <- MaintenanceTask
	MaintenanceFileService<->ui
	DatabaseController<->MaintenanceFileService
 
 ```
## Luokkakaavio

```mermaid
classDiagram
	MaintenanceFileService <|-- MaintenanceFile
	MaintenanceFileService <|-- OneTimeTask
	MaintenanceFileService <|-- RecurringTask
	OneTimeTask <|-- MaintenanceTask : extends
	RecurringTask <|-- MaintenanceTask : extends
	MaintenanceFile <|-- MaintenanceTask
	class MaintenanceFile{
		+ArrayList<MaintenanceTask> tasks
		}
	class MaintenanceTask{
		+UUID id
		+String name
		+LocalDate creationDate
		+LocalDate comletedOnDate
		+LocalDate dueDate;
		+Boolean isCompleted;
		}
	class RecurringTask{
		+int recurringIntervalMonths
		}
	class MaintenanceFileService{
		+MaintenanceFile maintenanceFile
		-initializeMaintenanceFile()
		-getMaintenanceFile()
		-createTask()
		-getTasks()
		-deleteTasks()
		}

```

## Käynnistyksen sekvenssikaavio


```mermaid
sequenceDiagram
	UserInterface->>MaintenanceFileService:getDefaultMaintenanceFile()
	MaintenanceFileService->>DatabaseController:getDefaultMaintenanceFile()
	DatabaseController->>Database:sql query
	Database->>DatabaseController:ResultSet
	DatabaseController->>MaintenanceFileService:String[]
	MaintenanceFileService->>MaintenanceFile:New MaintenanceFile()
	MaintenanceFile->>MaintenanceFileService:MaintenanceFile
	MaintenanceFileService->>DatabaseController:getMaintenanceFileTasks()
	DatabaseController->>Database:sql query
	Database->>DatabaseController:ResultSet
	DatabaseController->>MaintenanceFileService:ResultSet
	MaintenanceFileService->>MaintenanceFile:getTasks()
	MaintenanceFile->>MaintenanceFileService:tasks
	MaintenanceFileService->>UserInterface:MaintenanceFile
```
