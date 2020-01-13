package DownloaderProgram;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.*;

public class SftpFileDownloader extends IFileDownload {
	
	
	public SftpFileDownloader() {
		this.protocol = "sftp";
	}
	

	@Override
	public Boolean DownloadFile(String source, String destination) {
		
		String host = getHostName(source);
		String fileToDownload = getFilePath(source, host);
    	FileOutputStream fileOS = null;
        try {
        	Session session = getSession(host, System.getProperty("user.dir") + "\\config.properties");
            ChannelSftp sftpChannel = getChannel(session);
            InputStream in =  sftpChannel.get(fileToDownload);
            fileOS = new FileOutputStream(destination);
		    byte data[] = new byte[1024];
		    int byteContent;
		    while ((byteContent = in.read(data, 0, 1024)) != -1) {
		    	fileOS.write(data, 0, byteContent);
		    }
		        
        sftpChannel.exit();
        session.disconnect();
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        	this.RemoveIncompleteFile(destination);
        	return false;
        }
        finally {
        	try {
				fileOS.close();
			} catch (IOException e) {}
        }
	 	return true;
	}
	
	public static String getHostName(String source) {
		String information = source.split("://")[1];
		return information.split("/")[0];
	}
	
	public static String getFilePath(String source, String host) {
		String information = source.split("://")[1];
		return information.replace(host, "");
	}
	
	public static Session getSession(String host, String configPath) throws Exception {
		try {
		SftpCredentials credentials = new SftpCredentials();
		credentials.setCrentials(configPath, host);
		JSch jsch = new JSch();
		Session session;
		session = jsch.getSession(credentials.Username,host, 22);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(credentials.Password);
        session.connect();
        return session;
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
	
	public static ChannelSftp getChannel(Session session) throws Exception {
		try {
			Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            return sftpChannel;
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
	}

}
