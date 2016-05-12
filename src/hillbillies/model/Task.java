package hillbillies.model;

import java.util.Set;

import hillbillies.model.statements.Statement;

public class Task implements Comparable<Task>{
	
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
	public Task(String name, int priority, Statement activity) {
		this.setName(name);
		this.setPriority(priority);
		this.setActivity(activity);
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
	
	private void setActivity(Statement activity) {
		this.activity = activity;
	}
	
	private Statement activity;
	
	private Statement getActivity() {
		return this.activity;
	}
	
	public void addScheduler(Scheduler scheduler) {
		this.schedulers.add(scheduler);
	}
	
	private Set<Scheduler> schedulers;
	
	public Set<Scheduler> getSchedulers() {
		return this.schedulers;
	}
	
	/**
	 * Compare this money amount with the other money amount.
	 * 
	 * @param other
	 * 		The other task
	 * @return The result is equal to the  comparison of the priority
	 * 			of this task and the other task.
	 * @throws ClassCastException
	 * 			The other task is not effective.
	 */
	@Override
	public int compareTo(Task other) {
		if (other == null){
			throw new ClassCastException();
		}
		return getPriority().compareTo(other.getPriority())
	}
}
