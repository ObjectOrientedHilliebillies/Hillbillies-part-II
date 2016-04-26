package hillbillies.model.tasks;

import java.util.Set;

import hillbillies.model.Scheduler;
import hillbillies.model.Unit;

public class Task { //implements Comparable<Task>{
	
	/**
	 * Initialize this new task with the given name and priority.
	 * @param name
	 * 		The name of this new task.
	 * @param priority
	 * 		The priority of this new task.
	 * @post The name of this new task is equal to the given name.
	 * 		| new.getName() == name
	 * @post The priority of this new task is equal to the given priority.
	 * 		| new.getPriority() == priority
	 */
	public Task(String name, int priority) {
		this.setName(name);
		this.setPriority(priority);
	}
	
	private Unit executor;
	
	public void setExecutor(Unit unit){
		this.executor = unit;
		unit.setTask(this);
	}
	
	public Unit getExecutor(){
		return this.executor;
	}
	
	
	private Integer priority;
	
	
	private void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	
	public Integer getPriority() {
		return this.priority;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName()  {
		return this.name;
	}
	
	public void addScheduler(Scheduler scheduler) {
		this.schedulers.add(scheduler);
	}
	
	private Set<Scheduler> schedulers;
	
	public Set<Scheduler> getSchedulers() {
		return this.schedulers;
	}
	
	
	/** 
	 * Check whether this task is equal to the given object.
	 * @return True if and only if the given object is effective,
	 * 		   if this task and the given object belong to the same class,
	 * 		   and if this task and the given object have the same name, priority.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other.getClass() != this.getClass())
			return false;
		Task otherTask = (Task)other; 
		return (this.getName() == otherTask.getName() 
					&& this.getPriority() == otherTask.getPriority());
	}

//	@Override
//	public int compareTo(Task otherTask) {
//		return(this.getPriority().compareTo(otherTask.getPriority()));
//	}
}
