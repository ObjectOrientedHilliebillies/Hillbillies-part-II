package hillbillies.model;

public class Task { //implements Comparable<Task>{
	public Task(String name, int priority) {
		this.setName(name);
		this.setPriority(priority);
	}
	
	private Unit executor;
	
	public void setExecutor(Unit unit){
		this.executor = unit;
	}
	
	public Unit getExecutor(){
		return this.executor;
	}
	
	private void setPriority(int priority) {
		this.priority = priority;
	}
	
	private Integer priority;
	
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
