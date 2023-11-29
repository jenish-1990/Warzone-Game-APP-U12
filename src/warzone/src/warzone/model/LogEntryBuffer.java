package warzone.model;

import warzone.service.GameEngine;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the observable in the observer pattern. It is filled
 * with information about effect of the action that players take.
 * @author zexin
 *
 */
public class LogEntryBuffer extends Observable implements Serializable {
	/**
	 * message
	 */
	private String d_message;
	/**
	 * time stamp
	 */
	private String d_time;
	/**
	 * order
	 */
	private String d_order;
	/**
	 * result
	 */
	private String d_result;
	/**
	 * phase
	 */
	private GamePhase d_phase;
	/**
	 * game context
	 */
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

	/**
	 * print log to string
	 * @return string
	 */
	@Override
	public String toString()
	{
		return String.format("%s %s :[Phase] %s [Order] %s [Message] %s", this.d_time, this.d_result, d_phase, this.d_order, this.d_message);	
	}

}
