package org.jcommon.com.facebook;

import java.util.*;

import org.apache.log4j.Logger;
import org.jcommon.com.util.http.HttpRequest;
import org.jcommon.com.util.system.SystemListener;
import org.jcommon.com.util.thread.ThreadManager;

public class QueueRequestManager implements SystemListener {
	private long monitor_frequency;
	private boolean run;
	private Timer timer_graph = null;
	
	private List<String> threads = new ArrayList<String>();
	private LinkedList<HttpRequest> queues = new LinkedList<HttpRequest>();
	private Logger logger = Logger.getLogger(getClass());

	public QueueRequestManager(long monitor_frequency){
		this.monitor_frequency = monitor_frequency;
	}
	
	public boolean isStarted(){
		return run;
	}
	
	public void addQueue1(HttpRequest re){
		queues.add(re);
	}
	
	public void addThread1(String thread){            
		if(!threads.contains(thread)){
			threads.add(thread);
			restart();
		}
	}
	
	public boolean removeThread(String thread){
		if(threads.contains(thread)){
			restart();
			return threads.remove(thread);
		}
		return false;
	}
	
	private void restart(){
		if (this.timer_graph != null) {
	        try {
		        this.timer_graph.cancel();
		        this.timer_graph = null;
	        } catch (Exception e) {
	    	    logger.error("", e);
	        }
	    }
		startup();
	}
	
	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		logger .info("...");
		run = false;
		if (this.timer_graph != null) {
	        try {
		        this.timer_graph.cancel();
		        this.timer_graph = null;
	        } catch (Exception e) {
	    	    logger.error("", e);
	        }
	    }
		queues.clear();
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		logger .info("...");
		run = true;
		long frequency = threads.size()>0 ? monitor_frequency * threads.size() : monitor_frequency;
		timer_graph =  org.jcommon.com.util.thread.TimerTaskManger.instance().schedule("QueueRequestManager-Graph", new TimerTask(){
	    	public void run(){
	    		if(!run)return;
	    	    
	    		for(;queues.size()>0;){
	    			HttpRequest re = queues.removeFirst();
	    	    	String thread  = re.getAttibute("Thread")!=null?(String) re.getAttibute("Thread"):null;
	    	    	if(thread!=null && threads.contains(thread)){
	    	    		ThreadManager.instance().execute(re);
	    	    		break;
	    	    	}
	    		}
	    	}
	    }, 10000L, frequency);
	}
   
}
