package unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import DownloaderProgram.SftpCredentials;

class SftpCredentialsTest {
	
	private SftpCredentials _credentials;
	private String _host;
	public SftpCredentialsTest() {
		_credentials = new SftpCredentials();
		_host = "test.rebex.net";
	}

	@Test
	void CredentialsCreatedSuccesfully() throws Exception {
		String configPath = System.getProperty("user.dir") + "\\config.properties";
		assertTrue(_credentials.setCrentials(configPath, _host));
	}
	
	
	@Test
	void CredentialsNotCreatedIfConfigNotFound() throws Exception {
		assertThrows(java.io.FileNotFoundException.class, () -> {
			String configPath = System.getProperty("user.dir") + "\\dummy\\config.properties";
			assertFalse(_credentials.setCrentials(configPath, _host));
	    });
	}

}
