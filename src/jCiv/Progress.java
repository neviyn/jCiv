package jCiv;

public class Progress {
	
	private int[] progress;
	
	public Progress(int numofnations) {
		
		progress = new int[numofnations];
		
		for(int i = 0; i < progress.length; i++) {
			progress[i] = 0;
		}
	}
	
	public void incrementProgress(int nation) {
		progress[nation]++;
	}
	
	public void decrementProgress(int nation) {
		progress[nation]--;
	}
	
	public int getProgress(int nation) {
		return progress[nation];
	}
}
