package hillbillies.model.task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

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
	
	public Task(String name, int priority, Statement statement, int[] pos) {
		this.setName(name);
		this.setPriority(priority);
		this.setStatement(statement);
		this.setPos(pos);
		//World world = this.getExecutor().getWorld();
		//Cube cube = world.getCube(pos);
		//this.setCube(cube);
	}
	
	private int[] pos = null;
	
	private void setPos(int[] pos) {
		this.pos = pos;
	}
	
	private int[] getPos() {
		return this.pos;
	}
	
	private Unit executor;
	
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
	
	public Unit getExecutor(){
		return this.executor;
	}
	
	private Integer priority;
	
	private void setPriority(int priority) {
		this.priority = priority;
		resortSchedulers();
	}
	
	public Integer getPriority() {
		return this.priority;
	}
	
	private void resortSchedulers(){
		for (Scheduler scheduler : schedulers){
			scheduler.sortManagedTasks();
		}
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	private String name;
	
	public String getName()  {
		return this.name;
	}
	
	private Cube cube;
	
	private void setCube(Cube cube) {
		cube.setStatement(this.statement);
		this.cube = cube;
	}
	
	public Cube getCube() {
		return this.cube;
	}
	
	public void addScheduler(Scheduler scheduler) {
		this.schedulers.add(scheduler);
	}
	
	private Set<Scheduler> schedulers = new HashSet<>();
	
	public Set<Scheduler> getSchedulers() {
		return this.schedulers;
	}
	
	public void reducePriority() {
		this.setPriority(this.getPriority()-2);
		resortSchedulers();
	}
	
	/**
	 * Returns whether this task is already being executed or not.
	 * @return True if the task is already being executed.
	 */
	public boolean isOccupied() {
		return this.isOccupied;
	}
	
	private boolean isOccupied;
	
	private Scheduler executingScheduler;
	
	public void setOccupied(Unit unit, Scheduler scheduler) {
		setExecutor(unit);
		executingScheduler = scheduler;
		this.isOccupied = true;
	}
	
	public void setAvailable() {
		this.isOccupied = false;
	}
	
	/**
	 * Compare this task amount with the other task.
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

	public void taskSucceeded() {
		System.out.println("TASK SUCCEEDED!!!");
		for (Scheduler scheduler: this.schedulers){
			scheduler.removeTask(this);
		}
		getExecutor().setTask(null);
	}
	
	public void taskFailed() {
		setAvailable();
		reducePriority();
		this.subTask = null;
		executingScheduler.taskFailed(this);
		getExecutor().setTask(null);
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	private Statement statement;
	private SubTask subTask = null;
	
	public getSubTask
	
	public void advanceProgram(double timeLeft){
		if (subTask == null){
			subTask = new SubTask(statement, cube, this, false);
		}
		double feedback = subTask.advance(timeLeft);
		if (feedback == -4){
			throw new IllegalArgumentException("Break was not in while");
		}
		if (subTask.advance(timeLeft) != -1){
			taskSucceeded();
		}
	}
	
	private HashMap<String, Expression> variables = new HashMap();
	
	public Expression<?> getValue(String name) {
		return variables.get(name);
	}
	
	public void setVariable(String name, Expression value) {
		System.out.println(name);
		System.out.println(value);
		System.out.println(variables);
		variables.put(name, value);
	}

}
