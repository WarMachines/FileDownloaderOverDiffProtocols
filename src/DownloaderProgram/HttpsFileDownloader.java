package DownloaderProgram;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class HttpsFileDownloader extends IFileDownload {

	public HttpsFileDownloader() {
		this.protocol = "https";
	}
	
	@Override
	public Boolean DownloadFile(String source, String destination) {
		 try (BufferedInputStream inputStream = new BufferedInputStream(new URL(source).openStream());
		  FileOutputStream fileOS = new FileOutputStream(destination)) {
		    byte data[] = new byte[1024];
		    int byteContent;
		    while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
		    	fileOS.write(data, 0, byteContent);
		    }
		} catch (Exception ex) {
		    // handles IO exceptions
			System.out.println(ex.getMessage());
			this.RemoveIncompleteFile(destination);
			return false;
		}
		
		 return true;
	}
}
