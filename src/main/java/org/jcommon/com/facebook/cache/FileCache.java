package org.jcommon.com.facebook.cache;

import java.io.File;

public class FileCache {
	private static FileCache instance;
	public static FileCache instance(){
		if(instance==null)
			instance = new FileCache();
		return instance;
	}
	
	private String file_root = System.getProperty("java.io.tmpdir");
	
	public void setFile_root(String file_root) {
		this.file_root = file_root;
	}
	
	public String getFile_root() {
		return file_root;
	}
	
	public File getFile(String file_id){
		if(file_id!=null){
			File directory = new File(file_root);

		    // get all the files from a directory
		    File[] fList = directory.listFiles();

		    for (File file : fList) {
		        if (file.isFile()) {
		            String file_name = file.getName();
		            if(file_name.startsWith(file_id) || file_name.endsWith(file_id))
		            	return file;
		        } 
		    }
		}
		return null;
	}
}
