package DownloaderProgram;

class DownloaderThread extends Thread {
	
	private String fileLink;
	private String destination;
	private int retryCount;
	public boolean isAlive;
	
	public DownloaderThread(String fileLink, String destination, int retryCount) {
		this.fileLink = fileLink;
		this.retryCount = retryCount;
		this.destination = destination;
		isAlive = true;
	}
	
	
	@Override
	public void run() {
		try {
			 System.out.println("Downloading file from "+ fileLink + " ...................................................");
			 String protocol = this.fileLink.split("://")[0];
			 String[] arr = this.fileLink.split("/");
			 String fileName = arr[arr.length - 1];
			 this.destination = this.destination + fileName;
			 DownloaderFactory factory = DownloaderFactory.getInstance();
			 IFileDownload instance = factory.getDownloader(protocol);
			 int i = -1;
			 while(true) {
				 Boolean success = instance.DownloadFile(this.fileLink, this.destination);
				 if(success) {
					 System.out.println("File has been downloaded successfully from "+ fileLink + " at " + destination);
					 break;
				 }
				 else {
					 System.out.println("An Error Occured while file download from "+ fileLink);
				 }
				 i++;
				 if(this.retryCount > i) {
					 System.out.println("Retry ........" + (i + 1) );
				 }
				 else {
					 System.out.println("Maximum Retry limit reached");
					 break;
				 }
				 
			 }
		 }
		 catch (Exception ex) {
			System.out.println(ex.getMessage());
			
		}
		finally {
			isAlive = false;
		}
	}
	
}
