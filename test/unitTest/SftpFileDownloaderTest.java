package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import DownloaderProgram.SftpFileDownloader;

class SftpFileDownloaderTest {

	private String _url;
	private String _configPath;
	private String _configPathIncomplete;
	
	public SftpFileDownloaderTest() {
		_url = "sftp://test.rebex.net/test/file1";
		_configPath = System.getProperty("user.dir") + "\\config.properties";
		_configPathIncomplete = System.getProperty("user.dir") + "\\testConfig.properties";
	}
	
	@Test
	void GetHostName() {
		String hostName = SftpFileDownloader.getHostName(_url);
		assertEquals("test.rebex.net", hostName);
	}
	
	
	@Test
	void GetFilePath() {
		String hostName = SftpFileDownloader.getHostName(_url);
		String filePath = SftpFileDownloader.getFilePath(_url, hostName);
		assertEquals("/test/file1", filePath);
	}
	
	@Test
	void GetSessionSuccess() throws Exception {
		String host = SftpFileDownloader.getHostName(_url);
		Session session = SftpFileDownloader.getSession(host, _configPath);
		assertNotNull(session);
	}
	
	@Test
	void GetSessionFailedInValidConfigPath() throws Exception {
		assertThrows(java.io.FileNotFoundException.class, () -> {
			String host = SftpFileDownloader.getHostName(_url);
			SftpFileDownloader.getSession(host, "//imaginaryPath");
	    });
	}
	
	@Test
	void GetSessionFailedIfPasswordMissingInConfig() throws Exception {
		assertThrows(java.lang.ArrayIndexOutOfBoundsException.class, () -> {
			String host = SftpFileDownloader.getHostName(_url);
			SftpFileDownloader.getSession(host, _configPathIncomplete);
	    });
	}
	
	@Test
	void GetSessionFailedIfUserNameMissingInConfig() throws Exception {
		assertThrows(com.jcraft.jsch.JSchException.class, () -> {
			String url = _url.replace("test", "test1");
			String host = SftpFileDownloader.getHostName(url);
			SftpFileDownloader.getSession(host, _configPathIncomplete);
	    });
		
	}
	
	@Test
	void GetSessionFailedIfDomainIsIncorrect() throws Exception {
		assertThrows(com.jcraft.jsch.JSchException.class, () -> {
			String url = _url.replace("test", "test1");
			String host = SftpFileDownloader.getHostName(url);
			SftpFileDownloader.getSession(host, _configPathIncomplete);
	    });
		
	}
	
	@Test
	void GetChannelSuccess() throws Exception {
		String host = SftpFileDownloader.getHostName(_url);
		Session session = SftpFileDownloader.getSession(host, _configPath);
		ChannelSftp channel = SftpFileDownloader.getChannel(session);
		assertNotNull(channel);
	}
	
	@Test
	void GetChannelUnSuccessIfSessionIsNull() throws Exception {
		assertThrows(java.lang.NullPointerException.class, () -> {
			Session session = null;
			SftpFileDownloader.getChannel(session);
	    });
	}
}

