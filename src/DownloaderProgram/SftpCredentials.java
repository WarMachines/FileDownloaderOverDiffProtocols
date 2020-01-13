package DownloaderProgram;
import java.io.FileInputStream;
import java.util.Properties;

public class SftpCredentials {
	
	public String Username;
	public String Password;
	
	public boolean setCrentials(String configPath, String host) throws Exception {
		FileInputStream fis = null;
		try {
			 Properties prop = new Properties();
			 fis = new FileInputStream(configPath);
			 prop.load(fis);
			 String[] ftpServerDetails = prop.getProperty(host).split(" ");
			 this.Username = ftpServerDetails[0];
			 this.Password = ftpServerDetails[1];
			 fis.close();
		 }
		 catch(Exception ex) {
			 System.out.println(ex.getMessage());
			 throw ex;
		 }
		return true;
	}
}
