package fr.knux14.snapdesk;

import java.util.concurrent.TimeUnit;

import com.habosa.javasnap.Snapchat;

public class ThreadAutoUpdate extends Thread {

	private MainFrame mf;
	private Snapchat scAccount;
	
	public ThreadAutoUpdate(MainFrame mf) {
		this.mf = mf;
		this.scAccount = mf.scAccount;
	}
	
	@Override
	public void run() {
		while (mf.update) {
			try {
				sleep(TimeUnit.MINUTES.toMillis(1));
				if (scAccount.refresh()) {
					mf.refresh();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
