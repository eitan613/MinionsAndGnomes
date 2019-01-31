public class Bob extends Thread {

    private Table table;
    private Bedroom bedroom;

    public Bob(Table table, Bedroom bedroom){
        super("Bob");
        this.table = table;
        this.bedroom = bedroom;
    }

    @Override
    public void run(){
        goToWork();
        synchronized (this){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        comeBackHome();
        eatDinner();
        goToRoom();
    }

    private void goToRoom() {
        bedroom.setBobInRoom(true);
        bedroom.reading(this);
        System.out.println(getName() + " is reading.");
        int timeToSleep = (int) (Math.random() * 2000);
        System.out.println(getName() + " is going to sleep.");
        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + " fell asleep.");
    }

    private void eatDinner() {
        if (table.openSeat()){
            table.seatOne();
            System.out.println(getName() + " is eating dinner.");
            int eatTime = (int) (Math.random() * 2000);
            try {
                sleep(eatTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.unseatOne();
            System.out.println(getName() + " is done eating dinner.");
        }
        else {
            int waitTime = (int) (Math.random() * 2000);
            try {
                sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eatDinner();
        }
    }

    private void comeBackHome() {
        System.out.println("Bob came back home.");
    }

    private void goToWork() {
        System.out.println("Bob gets a kiss and lunch from Alice.");
        System.out.println("Bob goes to work as an accountant.");
        int workTime = (int) (Math.random() * 2000);
        try {
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
