
public class Main {

	public static void main(String[] args) {
		UI ui = new UI();
		try {
			ui.start();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("AI failed to make move.");
		}
	}
}
