package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import hillbillies.model.tasks.Task;
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
	
	private ArrayList<Task> activeList = new ArrayList<>();
	
	
	Comparator<Task> comparator = new PriorityComparator();
	private  PriorityQueue<Task> scheduledTaskQue = new PriorityQueue<>(comparator);
	
	public void addTask(Task newTask) {
		this.scheduledTaskQue.add(newTask);
		newTask.addScheduler(this);
	}
	
	private void removeTask(Task oldTask) {
		if (activeList.remove(oldTask)){
			oldTask.getExecutor().setTask(null);
			oldTask.setExecutor(null);
		} else if (!scheduledTaskQue.remove(oldTask)){
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
		ArrayList<Task> allTasks = this.getScheduledTasks();
		allTasks.addAll(this.getActiveTasks());
		return allTasks.containsAll(tasks);
	}

}
