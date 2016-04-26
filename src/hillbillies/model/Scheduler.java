package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ogp.framework.util.ModelException;

public class Scheduler {
	public Scheduler(Faction faction) {
		this.setFaction(faction);
	}
	
	private void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	private Faction getFaction() {
		return this.faction;
	}
	
	private Faction faction;
	
	private ArrayList<Task> scheduledList = new ArrayList<>();
	private ArrayList<Task> activeList = new ArrayList<>();
	
	private ArrayList<Task> getScheduledTasks() {
		return this.scheduledList;
	}
	
	private ArrayList<Task> getActiveTasks() {
		return this.activeList;
	}
	
	public void addTask(Task newTask) {
//		int pos = Collections.binarySearch(taskList, newTask.getPriority());
		if (this.scheduledList.size() == 0){
			scheduledList.add(newTask);
		}
		else {
			for (int i=0; i!=scheduledList.size(); i++){
				if (scheduledList.get(i).getPriority() < newTask.getPriority()){
					scheduledList.add(i, newTask);
					return;
				}
				if (scheduledList.get(i) == newTask){ //FIXME 
					return;
				}
			}
			scheduledList.add(newTask);	
		}
	}
	
	private void removeTask(Task oldTask) {
		int index = activeList.indexOf(oldTask);
		if (index != -1){
			oldTask.getExecutor().setTask(null);
			oldTask.setExecutor(null);
			activeList.remove(index);
			return;
		}
		index = scheduledList.indexOf(oldTask);
		if (index != -1){
			scheduledList.remove(index);
			return;
		}
		
		throw new IllegalArgumentException("Given task is not in scheduler.");		
	}
	
	public void replace(Task oldTask, Task newTask){
		removeTask(oldTask);
		addTask(newTask);
	}
	
	private void ascribeTask(Unit unit) {
		
	}
	
	public boolean areTasksPartOf(Collection<Task> tasks) throws ModelException {
		ArrayList<Task> allTasks = this.getScheduledTasks();
		allTasks.addAll(this.getActiveTasks());
		return allTasks.containsAll(tasks);
	}

}
