package unitTest;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import DownloaderProgram.DownloaderFactory;
import DownloaderProgram.IFileDownload;

class DownloaderFactoryTest {

	private DownloaderFactory _downloadFactory;
	
	
	@Test
	void IsObjectOfHttpDownloader() {
		String protocol = "http";
		_downloadFactory = DownloaderFactory.getInstance();
		IFileDownload instance = _downloadFactory.getDownloader(protocol);
		assertTrue(instance.getProtocol() == protocol);
	}
	
	@Test
	void IsObjectOfHttpsDownloader() {
		String protocol = "https";
		_downloadFactory = DownloaderFactory.getInstance();
		IFileDownload instance = _downloadFactory.getDownloader(protocol);
		assertTrue(instance.getProtocol() == protocol);
	}
	
	@Test
	void IsObjectOfFtpDownloader() {
		String protocol = "ftp";
		_downloadFactory = DownloaderFactory.getInstance();
		IFileDownload instance = _downloadFactory.getDownloader(protocol);
		assertTrue(instance.getProtocol() == protocol);
	}
	
	@Test
	void IsObjectOfSftpDownloader() {
		String protocol = "sftp";
		_downloadFactory = DownloaderFactory.getInstance();
		IFileDownload instance = _downloadFactory.getDownloader(protocol);
		assertTrue(instance.getProtocol() == protocol);
	}
	
	@Test
	void ReturnNullIfProtocolNotInFactory() {
		String protocol = "xyz";
		_downloadFactory = DownloaderFactory.getInstance();
		IFileDownload instance = _downloadFactory.getDownloader(protocol);
		assertNull(instance);
	}

}
