package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import ogp.framework.util.ModelException;

public class Scheduler {
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
	
	private void sortManagedTasks(){
		managedTasks.sort(null);
	}
	
	public void addTask(Task newTask) {
		this.managedTasks.add(newTask);
		sortManagedTasks();
		newTask.addScheduler(this);
	}
	
	private void removeTask(Task oldTask) {
		if (activeList.remove(oldTask)){
			oldTask.getExecutor().setTask(null);
			oldTask.setExecutor(null);
		}
		if (!managedTasks.remove(oldTask)){
			throw new IllegalArgumentException("Given task is not in scheduler.");
		}		
	}
	
	public void replace(Task oldTask, Task newTask){
		removeTask(oldTask);
		addTask(newTask);
	}
	
	private void ascribeTask(Unit unit) {
		
	}
	
	public boolean areTasksPartOf(Collection<Task> tasks) throws ModelException {
		return managedTasks.containsAll(tasks);
	}

}
