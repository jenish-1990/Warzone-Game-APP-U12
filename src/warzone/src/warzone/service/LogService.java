package warzone.service;

import java.io.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import warzone.model.GameContext;
import warzone.model.Observable;
import warzone.model.Observer;

/**
 * This class is responsible to create print log messages
 * using information in LogEntryBuffer
 * @author alay
 *
 */
public class LogService implements Observer, Serializable {
	
	/**
	 * Log file Name
	 */
	private String d_fileName ;
	
	
	/**
	 * construstor of the log service
	 */
	public LogService() {
		d_fileName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ".log";
	}
	
	/**
	 * This method will print log message into console.
	 */
	@Override
	public void update(Observable p_observable) {
		String l_log = p_observable.toString();
		System.out.println(l_log);
		
		if (GameContext.getGameContext().getIsLog()) {
			write2LogFile(l_log);
		}
	}
	
	/**
	 * This method will write the message into log file if the log function is configured
	 * in the configuration file
	 * @param p_content the content of the log message
	 */
	private void write2LogFile(String p_content) {
		//create file writer
		FileWriter l_fw = null;
		try {
			File l_dir = new File(GameContext.getGameContext().getLogfolder());
			if (!l_dir.isDirectory()) {
				l_dir.mkdir();
			}
			File l_f = new File(l_dir, d_fileName);
			if (!l_f.isFile()) {
				l_f.createNewFile();
			}
	        l_fw = new FileWriter(l_f, true);
		} catch (IOException e) {
	        e.printStackTrace();
		}
		//create print writer
		PrintWriter l_pw = new PrintWriter(l_fw);
		l_pw.println(p_content);
		l_pw.flush();
		try {
		        l_fw.flush();
		        l_pw.close();
		        l_fw.close();
		} catch (IOException e) {
		        e.printStackTrace();
		}
	}
}
