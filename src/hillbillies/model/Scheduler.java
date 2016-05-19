package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import ogp.framework.util.ModelException;

public class Scheduler implements Iterable<Task> {
	
	public Scheduler(Faction faction) {
		this.setFaction(faction);
		this.getFaction().setScheduler(this);
	}
	
	private void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	private Faction getFaction() {
		return this.faction;
	}
	
	private Faction faction;
	
	private ArrayList<Task> activeList = new ArrayList<>();
	
	private ArrayList<Task> managedTasks = new ArrayList<>();
	
	private ArrayList<Task> getManagedTasks(){
		return this.managedTasks;
	}
	
	public void sortManagedTasks(){
		managedTasks.sort(null);
	}
	
	public void addTask(Task newTask) {
		this.managedTasks.add(newTask);
		sortManagedTasks();
		newTask.addScheduler(this);
	}
	
	public void removeTask(Task oldTask) {
		if (activeList.remove(oldTask)){
			managedTasks.remove(oldTask);
		}else if(!managedTasks.remove(oldTask)){
			throw new IllegalArgumentException("Given task is not in scheduler.");
		}		
	}
	
	public void replace(Task oldTask, Task newTask){
		removeTask(oldTask);
		addTask(newTask);
	}
	
	public void taskFailed(Task task){
		activeList.remove(task);
		sortManagedTasks();
	}
	
	@Override
	public Iterator<Task> iterator() {
	        Iterator<Task> it = new Iterator<Task>() {

	            private int currentIndex = 0;

	            @Override
	            public boolean hasNext() {
	                return currentIndex < managedTasks.size() 
	                		&& managedTasks.get(currentIndex) != null;
	            }

	            @Override
	            public Task next() {
	                return managedTasks.get(currentIndex++);
	            }

	            @Override
	            public void remove() {
	                throw new UnsupportedOperationException();
	            }
	        };
	        return it;
	    }
	
	public boolean areTasksPartOf(Collection<Task> tasks){
		return managedTasks.containsAll(tasks);
	}
	
	public Task ascribeTask(Unit unit) {
		Task mostImportantTask = null;
		for (Task task : getManagedTasks()){
			if (!task.isOccupied()){
				if (mostImportantTask == null
						|| (mostImportantTask.getPriority() < task.getPriority())){
					mostImportantTask = task;
				}
			}
		}
		if (mostImportantTask != null){
			this.activeList.add(mostImportantTask);
			mostImportantTask.setOccupied(unit, this);
		}
		return mostImportantTask;
	}
}
