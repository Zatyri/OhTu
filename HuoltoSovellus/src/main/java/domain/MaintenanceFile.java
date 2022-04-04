package domain;

import java.util.ArrayList;

public class MaintenanceFile {
        private ArrayList<MaintenanceTask> tasks;
        

        /**
         * @return the tasks
         */
        public ArrayList<MaintenanceTask> getTasks() {
            return tasks;
        }

        /**
         * @param task the tasks to add to list
         */
        public void addTask(MaintenanceTask task) {
            tasks.add(task);
        }
}
