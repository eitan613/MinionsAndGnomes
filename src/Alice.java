public class Alice extends Thread {

    private Bedroom bedroom;
    private Table table;
    private Bob bob;
    private Door door;

    public Alice(Bob bob, Table table, Bedroom bedroom){
        super("Alice");
        this.bob = bob;
        this.table = table;
        this.bedroom = bedroom;
    }

    @Override
    public void run() {
        bob.start();
        synchronized (this){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        letMinionsIn();
        synchronized (this){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        letGnomesIn();
        prepareDinner();
        eatDinner();
        goToRoom();
    }

    private void goToRoom() {
        bedroom.setAliceInRoom(true);
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

    private void prepareDinner() {
        try {
            System.out.println("Alice is preparing dinner.");
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void letGnomesIn() {
        door.letGnomesIn();
    }

    private void letMinionsIn() {
        door.letMinionsIn();
    }


    public void sayGoodBye(Minion minion) {
        saysGoodbye(minion.getName());
        System.out.println(minion.getName() + " says thank you Alice.");
    }

    public void sayGoodBye(Gnome gnome) {
        saysGoodbye(gnome.getName());
        System.out.println(gnome.getName() + " says Have a good day.");
    }

    private void saysGoodbye(String name) {
        int sleepTime = (int) (Math.random() * 1000);
        System.out.println("Alice is preparing lunch for " + name);
        try {
            sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Alice gives a kiss and lunch to " + name);
    }

    public void giveDoorHandle(Door door) {
        this.door = door;
    }
}
