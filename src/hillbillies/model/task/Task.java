package hillbillies.model.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Cube;
import hillbillies.model.Scheduler;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.model.expressions.Expression;
import hillbillies.model.expressions.positionExpressions.CubeExpression;
import hillbillies.model.expressions.positionExpressions.LiteralPosition;
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
	public Task(String name, int priority, Statement statement) {
		this.setName(name);
		this.setPriority(priority);
		this.setStatement(statement);
	}
	
	/**
	 * Initialize this new task with the given parameters.
	 * @param name
	 * 		The name of this new task.
	 * @param priority
	 * 		The priority of this new task.
	 * @param statement
	 * 		The statement this task will execute.
	 * @param pos
	 * 		The selected positions for this task.
	 * @post The name of this new task is equal to the given name.
	 * 		| new.getName() == name
	 * @post The priority of this new task is equal to the given priority.
	 * 		| new.getPriority() == priority
	 * @post The statement of this new task is equal to the given statement.
	 * 		| new.getStatement() == statement
	 * @post The position of this new task is equal to the given pos.
	 * 		|new.getPos() == pos
	 */
	public Task(String name, int priority, Statement statement, int[] pos) {
		this.setName(name);
		this.setPriority(priority);
		this.setStatement(statement);
		this.setPos(pos);
		//World world = this.getExecutor().getWorld();
		//Cube cube = world.getCube(pos);
		//this.setCube(cube);
	}
	
	/**
	 * Variable registering the selected positions for this task.
	 */
	private int[] pos = null;
	
	/**
	 * Set the pos of this unit to the given pos.
	 * @param pos
	 * 		The new pos for this task.
	 * 
	 * @post the pos of this task is equal to the given pos.
	 * 		| new.getPos() == pos
	 */
	@Raw
	private void setPos(int[] pos) {
		this.pos = pos;
	}
	
	/**
	 * Return the pos of this task.
	 */
	@Basic @Raw
	private int[] getPos() {
		return this.pos;
	}
	
	/**
	 * Variable registering the executor of this task.
	 */
	private Unit executor;
	
	/**
	 * Set the Executor of this task to the given unit
	 * 
	 * @param unit
	 * 		The unit to execute this task.
	 * 
	 * @post the executor of this task is equal to the given unit
	 * 		| new.getExecutor() == unit
	 */
	@Raw
	public void setExecutor(Unit unit){
		this.executor = unit;
		if (unit != null){
			unit.setTask(this);	
			World world = unit.getWorld();
			if (this.getPos() != null) {
				Cube cube = world.getCube(this.getPos());
				CubeExpression cubeE = new LiteralPosition(pos[0], pos[1], pos[2]);
				this.setCube(cube);
			}
		}
	}
	
	/**
	 * Returns the executor of this task
	 */
	@Basic @Raw
	public Unit getExecutor(){
		return this.executor;
	}
	
	/**
	 * Variable registering the priority of this task.
	 */
	private Integer priority;
	
	/**
	 * Set the priority of this task to the given priority.
	 * 
	 * @param priority
	 * 		The new priority for this task
	 * 
	 * @post The new priority of this task is equal to the given priority.
	 * 		| new.getPriority() == priority
	 */
	@Raw
	private void setPriority(int priority) {
		this.priority = priority;
		resortSchedulers();
	}
	
	/**
	 * Returns the priority of this task.
	 */
	@Basic @Raw
	public Integer getPriority() {
		return this.priority;
	}
	
	/**
	 * Resort the tasks in all schedulers in descending priority order
	 * 
	 * @effect all tasks in all schedulers are ordered by descending priority.
	 * 		| forAll scheduler in schedulers
	 * 		|	scheduler.get(i).getPriority() > scheduler.get(i+1).getPriority()
	 */
	private void resortSchedulers(){
		for (Scheduler scheduler : schedulers){
			scheduler.sortManagedTasks();
		}
	}
	
	/**
	 * Set the name of this task to the given name.
	 * 
	 * @param name
	 * 		The new name for this task.
	 * 
	 * @post The new name of this task is equal to the given name.
	 * 		| new.getName() == name
	 */
	@Raw
	private void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Variable registering the name of this task.
	 */
	private String name;
	
	/**
	 * Return the name of this task.
	 */
	@Basic @Raw
	public String getName()  {
		return this.name;
	}
	
	/**
	 * Variable registering the cube of this task.
	 */
	private Cube cube;
	
	/**
	 * Set the cube of this task to the given cube
	 * 
	 * @param cube
	 * 		The new cube for this task
	 * 
	 * @post the cube of this task is equal to the given cube.
	 * 		| new.getCube() == cube
	 */
	@Raw
	private void setCube(Cube cube) {
		cube.setStatement(this.statement);
		this.cube = cube;
	}
	
	/**
	 * Returns the cube of this task
	 */
	@Basic @Raw
	public Cube getCube() {
		return this.cube;
	}
	
	/**
	 * Add a new scheduler to this task
	 * 
	 * @param scheduler
	 * 		The new scheduler to be added.
	 * 
	 * @post the given scheduler is part of the schedulers of this task.
	 * 		| this.getSchedulers.contains(scheduler)
	 */
	public void addScheduler(Scheduler scheduler) {
		this.schedulers.add(scheduler);
	}
	
	/**
	 * Set registering the schedulers of this task
	 */
	private Set<Scheduler> schedulers = new HashSet<>();
	
	/**
	 * Returns a set containing the schedulers of this task
	 */
	@Basic @Raw
	public Set<Scheduler> getSchedulers() {
		return this.schedulers;
	}
	
	/**
	 * Reduces the priority of this task
	 * 
	 * @post the priority of this task is half the earlier priority.
	 * 		| new.getPriority() == old.getPriority/2 
	 */
	public void reducePriority() {
		this.setPriority(this.getPriority()-2);
		resortSchedulers();
	}
	
	/**
	 * Returns whether this task is already being executed or not.
	 * 
	 * @return True if the task is already being executed.
	 */
	public boolean isOccupied() {
		return this.isOccupied;
	}
	
	/**
	 * Variable registering whether this task is being executed or not.
	 */
	private boolean isOccupied;
	
	/**
	 * Variable registering which scheduler is currently executing this task.
	 */
	private Scheduler executingScheduler;
	
	/**
	 * Set this task to be executed by the given unit and scheduler
	 * 
	 * @param unit
	 * 		The unit executing this task.
	 * @param scheduler
	 * 		The scheduler in which the unit has found this task.
	 * 
	 * @post the given unit is executing this task
	 * 		| new.getExecutor() == unit
	 * @post this task is occupied
	 * 		| new.isOccupied() == true;
	 * @post the given scheduler is set as executing scheduler.
	 * 		| new.executingScheduler == scheduler
	 */
	public void setOccupied(Unit unit, Scheduler scheduler) {
		setExecutor(unit);
		executingScheduler = scheduler;
		this.isOccupied = true;
	}
	
	/**
	 * Set this task as available.
	 * 
	 * @post this task is available
	 * 		| new.isOccupied() == false
	 */
	public void setAvailable() {
		this.isOccupied = false;
	}

	/**
	 * Compare this task with the other task.
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
		return getPriority().compareTo(other.getPriority());
	}
	
	/**
	 * Set this task as succeded
	 * 
	 * @effect this task is removed from all schedulers
	 * 		| forAll scheduler in schedulers
	 * 		| 	scheduler.contains(this) == false
	 */
	public void taskSucceeded() {
		System.out.println("TASK SUCCEEDED!!!");
		for (Scheduler scheduler: this.schedulers){
			scheduler.removeTask(this);
		}
		getExecutor().setTask(null);
	}
	
	/**
	 * Set this task as failed
	 * 
	 * @post this task is available again
	 * 		| new.isOccupied() == false
	 * @post the priority of this task is reduced
	 * 		| new.getPriority() == old.getPriority()/2
	 * @post the executor of this task is null
	 * 		| getExecutor.getTask() == null 
	 * 		| new.getExecutor() == null
	 * @post this task is removed from the active list in its scheduler
	 * 		| new.getScheduler.getActiveList.contains(this) == false
	 * @post the subtask of this task is set to null
	 * 		| new.subTask == null
	 * @effect the tasks are resorted in all schedulers
	 * 		| this.resortSchedulers()
	 */
	public void taskFailed() {
		setAvailable();
		reducePriority();
		this.subTask = null;
		executingScheduler.taskFailed(this);
		getExecutor().setTask(null);
		this.setExecutor(null);
	}
	
	/**
	 * Set the statement of this task to the given statement
	 * 
	 * @param statement
	 * 		The new statement for this task
	 * 
	 * @post The statement of this task is equal to the given statement
	 * 		| new.getStatement() == statement
	 */
	@Raw
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	/**
	 * Variable registering the statement of this task
	 */
	private Statement statement;
	
	/**
	 * Variable registering the subtask of this task.
	 */
	private SubTask subTask = null;
	

	public SubTask getSubTask() {
		return this.subTask;
	}

	/**
	 * Advance the program with the given time left
	 * 
	 * @param timeLeft
	 * 		The time this task has left for executing its statement
	 * 
	 * @effect This task will be advanced by the executor.
	 * 
	 * @effect If the textfile is finished, this task is finished.
	 */
	public void advanceProgram(double timeLeft){
		if (subTask == null){
			subTask = new SubTask(statement, cube, this, false);
		}
		double feedback = subTask.advance(timeLeft);
		if (feedback == SubTask.finishedOnBreak){
			throw new IllegalArgumentException("Break was not in while");
		}
		if (feedback != SubTask.hasToBeExecutedAgain){
			taskSucceeded();
		}
	}
	
	/**
	 * Map registering the variables for this task with their values.
	 */
	private HashMap<String, Expression> variables = new HashMap();
	
	/**
	 * Returns the values of the variable with the given name.
	 * 
	 * @param name
	 * 		The name of the retrieved variable
	 */
	@Basic @Raw
	public Expression<?> getValue(String name) {
		return variables.get(name);
	}
	
	/**
	 * Add a variable with given name and value for this task.
	 * 
	 * @param name
	 * 		The name for the new variable
	 * @param value
	 * 		The value for the new variable
	 * 
	 * @post the given variable is assigned to this task
	 * 		| new.getValue(name) == value
	 */
	public void setVariable(String name, Expression value) {
		variables.put(name, value);
	}
	
	/**
	 * Return whether this task is well formed or not.
	 * @return
	 */
	public boolean isWellFormed() {		
		return (!statement.doYouHaveABreak());
	}
}
