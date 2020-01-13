package DownloaderProgram;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

 public class Program {
	 public static void main(String[] args) throws InterruptedException {
		 String destination = "";
		 int retryCount = 0;
		 int threadCount = 1;
		 try {
			 Properties prop = new Properties();
			 FileInputStream fis= new FileInputStream(System.getProperty("user.dir") + "\\config.properties");
			 prop.load(fis);
			 destination = prop.getProperty("destinationPath");
			 threadCount =  Integer.parseInt(prop.getProperty("threadCount"));
			 retryCount = Integer.parseInt(prop.getProperty("retryCount"));
		 }
		 catch(Exception ex) {
			 destination = System.getProperty("user.dir");
			 retryCount = 0;
		 }
		 
		 ArrayList<String> fileLinks = getDownloadLinks();
		 ArrayList<DownloaderThread> threadList = new ArrayList<DownloaderThread>();
		 int threads = 0;
		 if(fileLinks.size() > 0) {
				 for(int i = 0; i < fileLinks.size(); i++) {
					 if(threads < threadCount) {
						 DownloaderThread thread = new DownloaderThread(fileLinks.get(i), destination, retryCount);
						 threads++;
						 threadList.add(thread);
						 thread.start();
					 }
					 else {
						 i--;
						 while(threads == threadCount) {
							 for(int j = 0; j < threadList.size(); j++) {
								 if(threadList.get(j).isAlive) {
									 Thread.sleep(1000);
									 continue;
								 }
								 threads--;
								 threadList.remove(threadList.get(j));
							 }
						 }
					 }
				 }
				
			 	 for(int j = 0; j < threadList.size(); j++) {
					 threadList.get(j).join();
				 }
		 } 
	 }
	 
	 public static ArrayList<String> getDownloadLinks() {
		 ArrayList<String> fileLinks = new ArrayList<String>();
		 BufferedReader reader;
			try {	 
				FileInputStream fis = null;
				Properties prop = new Properties();
				fis = new FileInputStream(System.getProperty("user.dir") + "\\config.properties");
				prop.load(fis);
				String file = prop.getProperty("DownloadFile");
				fis.close();
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				while (line != null) {
					fileLinks.add(line);
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		return fileLinks;
	}
	 
 }
