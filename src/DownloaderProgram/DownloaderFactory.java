package DownloaderProgram;

public class DownloaderFactory {
	
	private static DownloaderFactory instance;
	
	public static DownloaderFactory getInstance() 
	  { 
	    if (instance == null)  
	    { 
	      //synchronized block to remove overhead 
	      synchronized (DownloaderFactory.class) 
	      { 
	        if(instance==null) 
	        { 
	          instance = new DownloaderFactory(); 
	        } 
	        
	      } 
	    } 
	    return instance; 
	  } 
	
	public IFileDownload getDownloader(String type) {
		type = type.toLowerCase();
		switch(type) {
			case "http":
				return new HttpFileDownloader();
			
			case "https":
				return new HttpsFileDownloader();
			
			case "ftp":
				return new FtpFileDownloader();
			
			case "sftp":
				return new SftpFileDownloader();
		}
		
		return null;
	}
}
