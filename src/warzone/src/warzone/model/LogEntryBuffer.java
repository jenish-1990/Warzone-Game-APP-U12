package warzone.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import warzone.service.GameEngine;
import warzone.state.*;

/**
 * This class represents the observable in the observer pattern. It is filled
 * with information about effect of the action that players take.
 * @author zexin
 *
 */
public class LogEntryBuffer extends Observable{
	private String d_message;
	private String d_time;
	private String d_order;
	private String d_result;
	private GamePhase d_phase;
	private GameContext d_gameContext;
	
	/**
	 * This method will print log messages and save them to the log file.
	 * @param p_result result of the action
	 * @param p_message message
	 */
	public void logAction(String p_result, String p_message) {
		this.d_message = p_message;
		this.d_result = p_result;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		d_time = df.format(new Date());
		this.d_phase = GameEngine.getGameEngine(d_gameContext).getPhase().getGamePhase();
		this.d_order = this.d_gameContext.getCurrentRouter().getCommand();
		this.notify(this);
	}
	
	/**
	 *  log Issue Order
	 * @param p_result result of logging Issue Order
	 * @param p_message  msg of logging Issue Order
	 * @param p_command  command which create the order
	 */
	public void logIssueOrder(String p_result, String p_message, String p_command) {
		this.d_message = p_message;
		this.d_result = p_result;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		d_time = df.format(new Date());
		this.d_phase = GamePhase.IssueOrder;
		this.d_order = p_command;
		this.notify(this);
	}
		
	/**
	 *  log executing Order
	 * @param p_result result of executing Order
	 * @param p_message  msg of executing Order
	 * @param p_order  the order
	 */
	public void logExecuteOrder(String p_result, String p_message, Order p_order) {
		this.d_message = p_message;
		this.d_result = p_result;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		d_time = df.format(new Date());
		this.d_phase = GamePhase.OrderExecution;
		this.d_order = p_order.getCommand();
		this.notify(this);
	}
	
	/**
	 * This is the constructor of the class.
	 * @param p_gameContext the game context
	 */
	public LogEntryBuffer(GameContext p_gameContext) {
		d_gameContext = p_gameContext;
		this.d_message = "";
	}
	@Override
	public String toString()
	{
		return String.format("%s %s :[Phase] %s [Order] %s [Message] %s", this.d_time, this.d_result, d_phase, this.d_order, this.d_message);	
	}
	
//	/**
//	 * This method will clear the message.
//	 */
//	public void clearMessage() {
//		this.d_message = "";
//	}
	
//	/**
//	 * This method will set the time as current moment.
//	 */
//	public LogEntryBuffer setTime() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		d_time = df.format(new Date());
//		return this;
//	}
//	
//	/**
//	 * This method will return the time that the command is issued.
//	 * @return the time that the command is issued
//	 */
//	public String getTime() {
//		return d_time;
//	}
//	
//	/**
//	 * This method will return the current order.
//	 * @return the current order
//	 */
//	public String getOrder() {
//		return d_order;
//	}
	
//	/**
//	 * This method will set the current order.
//	 * @param d_order the current order
//	 * @return LogEntryBuffer instance
//	 */
//	public LogEntryBuffer setOrder(String d_order) {
//		this.d_order = d_order;
//		return this;
//	}
	
//	/**
//	 * This method will show the result of the issued order.
//	 * @return the result of the issued order
//	 */
//	public String getResult() {
//		return d_result;
//	}
//	
//	/**
//	 * This method will set the result of the issued order.
//	 * @param d_result the result of the issued order
//	 * @return LogEntryBuffer instance
//	 */
//	public LogEntryBuffer setResult(String d_result) {
//		this.d_result = d_result;
//		return this;
//	}
//	
//	/**
//	 * This method will show the current phase.
//	 * @return the current phase
//	 */
//	public GamePhase getPhase() {
//		return d_phase;
//	}
	
//	/**
//	 * This method can set the current phase according to the class type of phase instance.
//	 * @param p_phase the current phase instance
//	 * @return LogEntryBuffer instance
//	 */
//	public LogEntryBuffer setPhase(Phase p_phase) {
//		if (p_phase instanceof MapEditor) {
//			this.d_phase = "MapEditor";
//		}
//		else if (p_phase instanceof Startup) {
//			this.d_phase = "Startup";
//		}
//		else if (p_phase instanceof GamePlay) {
//			this.d_phase = "GamePlay";
//		}
//		else if (p_phase instanceof IssueOrder) {
//			this.d_phase = "IssueOrder";
//		}
//		else if (p_phase instanceof OrderExecution) {
//			this.d_phase = "OrderExecution";
//		}
//		else if (p_phase instanceof Reinforcement) {
//			this.d_phase = "Reinforcement";
//		}
//		return this;
//	}
//	
//	/**
//	 * This method will show the message of the issued order.
//	 * @return the message of the issued order
//	 */
//	public String getMessage() {
//		return d_message;
//	}
	
//	/**
//	 * This method will set the message of the issued order.
//	 * @param d_message the message of the issued order
//	 * @return LogEntryBuffer instance
//	 */
//	public LogEntryBuffer setMessage(String d_message) {
//		this.d_message = d_message;
//		return this;
//	}
}
