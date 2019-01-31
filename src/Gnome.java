public class Gnome extends Thread{

    private Bob bob;
    private Table table;
    private Alice alice;
    private Door door;

    public Gnome(int i, Alice alice, Door door, Bob bob, Table table){
        super("Gnome " + i);
        this.alice = alice;
        this.door = door;
        this.bob = bob;
        this.table = table;
    }

    @Override
    public void run(){
        goToWork();
        comeHomeFromWork();
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

    private void comeHomeFromWork() {
        boolean lastGnome = door.gnomeWaitingToEnter(this);
        waitAtDoor(lastGnome);
        if (lastGnome){
            synchronized (bob){
                bob.notify();
            }
        }
        int playTime = (int) (Math.random() * 2000);
        try {
            Thread.sleep(playTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eatDinner();
        Bathroom.INSTANCE.useBathroom(this);
        int timeToSleep = (int) (Math.random() * 2000);
        System.out.println(getName() + " is going to sleep.");
        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + " fell asleep.");
    }

    private void waitAtDoor(boolean lastGnome) {
        if (!lastGnome) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void goToWork() {
        int workTime = (int) (Math.random() * 2000);
        synchronized (alice){
            alice.sayGoodBye(this);
        }
        boolean lastGnome = door.gnomeWaitingToLeave(this);
        waitAtDoor(lastGnome);
        if (lastGnome)
            alice.start();
        try {
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
