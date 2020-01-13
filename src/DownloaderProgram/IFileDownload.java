package DownloaderProgram;
import java.io.File;

public abstract class IFileDownload {
	
	public String protocol = "";
	public abstract Boolean DownloadFile(String source, String destination);
	
	public boolean RemoveIncompleteFile(String file) {
		File f = new File(file); 
        if(f.exists()) {
        	if(f.delete()) 
            { 
                System.out.println("Partial File deleted successfully"); 
                return true;
            } 
            else
            { 
                System.out.println("Failed to delete the partial file"); 
            }
        }
        return false;
	}
	
	public String getProtocol() {
		return this.protocol;
	}
}
