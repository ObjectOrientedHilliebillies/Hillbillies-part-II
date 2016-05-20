package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import be.kuleuven.cs.som.annotate.*;
import hillbillies.model.task.Task;

public class Scheduler implements Iterable<Task> {
	
	/**
	 * Initialize this new scheduler with the given faction.
	 * 
	 * @param faction
	 * 		The faction for this new scheduler
	 * 
	 * @post
	 * 		If the given faction is a valid faction, the faction of this new
	 * 		scheduler is equal to the given faction and the scheduler of the 
	 * 		faction is equal to this scheduler.
	 * 		| new.getFaction() == faction
	 * 		| new.faction.getScheduler() == this
	 */
	public Scheduler(Faction faction) {
		this.setFaction(faction);
		this.getFaction().setScheduler(this);
	}
	
	/**
	 * Set the faction of this scheduler to the given faction.
	 * 
	 * @param faction
	 * 		The new faction for this scheduler
	 * 
	 * @post the faction of this scheduler is equal to the given faction
	 * 		| new.getFaction == faction
	 */
	@Raw
	private void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	/**
	 * Returns the faction of this scheduler.
	 */
	@Basic @Raw
	private Faction getFaction() {
		return this.faction;
	}
	
	/**
	 * Variable registering the faction of this scheduler.
	 */
	private Faction faction;
	
	/**
	 * List registering the active tasks of this scheduler
	 */
	private ArrayList<Task> activeList = new ArrayList<>();
	
	/**
	 * Returns a list with the active tasks of this scheduler
	 */
	@Basic @Raw
	public ArrayList <Task> getActiveList() {
		return this.activeList;
	}
	
	/**
	 * List registering all tasks of this scheduler.
	 */
	private ArrayList<Task> managedTasks = new ArrayList<>();
	
	/**
	 * Returns a list with all tasks of this scheduler.
	 */
	@Basic @Raw
	private ArrayList<Task> getManagedTasks(){
		return this.managedTasks;
	}
	
	/**
	 * Sort the managed tasks by descending priority
	 * 
	 * @effect The priority of each task in the managed task list is lower then the
	 * 		priority of the previous task.
	 * 		| managedTasks.get(i).getPriority() > managedTasks.get(i+1).getPriority()
	 */
	public void sortManagedTasks(){
		managedTasks.sort(null);
	}
	
	/**
	 * Add the given task to the managed tasks
	 * 
	 * @param newTask
	 * 		The new task to be added
	 * 
	 * @post the new task is added
	 * 		| new.getManagedTasks.contains(newTask) == true
	 * @effect the managed tasks are sorted
	 * 		| managedTasks.get(i).getPriority() > managedTasks.get(i+1).getPriority()
	 */
	public void addTask(Task newTask) {
		this.managedTasks.add(newTask);
		sortManagedTasks();
		newTask.addScheduler(this);
	}
	
	/**
	 * Remove the given task from the menaged task list
	 * 
	 * @param oldTask
	 * 		The old task to be removed.
	 * 
	 * @post The old task is no longer in the managed tasks list or the active list
	 * 		| new.getManagedTasks.contains(oldTask) == false
	 * 		| new.getActiveList.contains(oldTask) == false
	 * 
	 * @throws IllegalArgumentException
	 * 		oldTask is not in the managed tasks
	 */
	public void removeTask(Task oldTask) {
		if (activeList.remove(oldTask)){
			managedTasks.remove(oldTask);
		}else if(!managedTasks.remove(oldTask)){
			throw new IllegalArgumentException("Given task is not in scheduler.");
		}		
	}
	
	/**
	 * Replace the given old task with the given new task
	 * 
	 * @param oldTask
	 * 		The task to be replaced
	 * @param newTask
	 * 		The task to be added
	 * 
	 * @post managed tasks does no longer contain the old task
	 * 		| new.getManagedTasks().contains(oldTask) == false
	 * 
	 * @post managed tasks contains the new task
	 * 		| new.getManagedTasks().contains(newTask) == true
	 */
	public void replace(Task oldTask, Task newTask){
		removeTask(oldTask);
		addTask(newTask);
	}
	
	/**
	 * Set the given task to failed
	 * 
	 * @param task
	 * 		The failed task
	 * 
	 * @post the given task is no longer in the active task list
	 * 		| new.getActiveList().contains(task) == false
	 * 
	 * @effect the managed task list is sorted
	 * 		| managedTasks.get(i).getPriority() > managedTasks.get(i+1).getPriority()
	 */
	public void taskFailed(Task task){
		this.getActiveList().remove(task);
		sortManagedTasks();
	}
	
	/**
	 * Iterator for the managed task list
	 */
	@Override
	public Iterator<Task> iterator() {
	        Iterator<Task> it = new Iterator<Task>() {

	            private int currentIndex = 0;
	            
	            /**
	             * Returns true if the iteration has more elements.
	             */
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
