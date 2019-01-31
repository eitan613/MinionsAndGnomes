public class Minion extends Thread{

    private Table table;
    private Door door;
    private Alice alice;

    public Minion(int i, Alice alice, Door door, Table table){
        super("Minion " + i);
        this.alice = alice;
        this.door = door;
        this.table = table;
    }

    @Override
    public void run(){
        goToWork();
        comeBackFromWork();
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

    private void comeBackFromWork() {
        boolean lastMinion = door.minionWaitingToEnter(this);
        waitAtDoor(lastMinion);
        int playTime = (int) (Math.random() * 2000);
        try {
            Thread.sleep(playTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eatDinner();
        int timeToSleep = (int) (Math.random() * 2000);
        System.out.println(getName() + " is going to sleep.");
        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + " fell asleep.");
    }

    private void goToWork() {
        int workTime = (int) (Math.random() * 2000);
        synchronized (alice){
            alice.sayGoodBye(this);
        }
        boolean lastMinion = door.minionWaitingToLeave(this);
        waitAtDoor(lastMinion);
        try {
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitAtDoor(boolean lastMinion) {
        if (!lastMinion){
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
