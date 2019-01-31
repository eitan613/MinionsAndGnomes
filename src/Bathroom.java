import java.util.concurrent.Semaphore;

public enum Bathroom {

    INSTANCE;

    private Semaphore semaphore = new Semaphore(1,true);

    public void useBathroom(Thread t){
        try {
            semaphore.acquire();
            int bathroomTime = (int)(Math.random() * 1_000);
            System.out.println(t.getName() + " is using the bathroom.");
            Thread.sleep(bathroomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(t.getName() + " is done using the bathroom.");
        }
    }
}
