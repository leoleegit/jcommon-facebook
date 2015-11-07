package org.jcommon.com.facebook.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jcommon.com.util.system.SystemListener;

public class Tester implements SystemListener{
	private static Tester instance;

	public static Tester instance() { return instance; }
	
	public static URL init_file_is = Tester.class.getResource("/facebook-log4j.xml");
	protected Logger logger = Logger.getLogger(getClass());
	static{
	    if (init_file_is != null)
	      DOMConfigurator.configure(init_file_is);
	}
	
	private static List<SystemListener> tester = new ArrayList<SystemListener>();
	
	public static void AddTester(SystemListener tester){ 
		Tester.tester.add(tester);
	}
	
	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		for(SystemListener sl : tester){
			sl.shutdown();
		}
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		AddTester(new GetAccessTokenTester());
		AddTester(new PageManagerTester());
		for(SystemListener sl : tester){
			sl.startup();
		}
	}

}
