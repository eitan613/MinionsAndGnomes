import java.util.LinkedList;
import java.util.Queue;

public class Door {

    private final Alice alice;
    private Queue<Gnome> gnomeQueue = new LinkedList<>();
    private Queue<Minion> minionQueue = new LinkedList<>();
    private boolean minionsLeft = false;
    private boolean gnomesLeft = false;
    private boolean minionsEntered = false;
    private boolean gnomesEntered = false;

    public Door(Alice alice) {
        this.alice = alice;
    }

    public synchronized boolean gnomeWaitingToLeave(Gnome gnome) {
        gnomeQueue.add(gnome);
        if (gnomeQueue.size() == 7) {
            if (!minionsLeft)
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            while (!gnomeQueue.isEmpty()) {
                gnome = gnomeQueue.remove();
                synchronized (gnome) {
                    gnome.notify();
                    System.out.println(gnome.getName() + " went to work at the mine.");
                }
            }
            gnomesLeft = true;
        }
        return gnomesLeft;
    }

    public synchronized boolean minionWaitingToLeave(Minion minion) {
        minionQueue.add(minion);
        if (minionQueue.size() == 10) {
            while (!minionQueue.isEmpty()) {
                synchronized (minionQueue.peek()) {
                    minionQueue.peek().notify();
                    System.out.println(minionQueue.poll().getName() + " went to work at the deli.");
                }
            }
            minionsLeft = true;
            notify();
        }
        return minionsLeft;
    }

    public synchronized boolean minionWaitingToEnter(Minion minion) {
        minionQueue.add(minion);
        if (minionQueue.size() == 10) {
            synchronized (alice) {
                alice.giveDoorHandle(this);
                alice.notify();
            }
            minionsEntered = true;
        }
        return minionsEntered;
    }


    public boolean gnomeWaitingToEnter(Gnome gnome) {
        gnomeQueue.add(gnome);
        if (gnomeQueue.size() == 7){
            if (!minionsEntered)
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            synchronized (alice){alice.notify();}
            synchronized (gnome){
                try {
                    gnome.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            gnomesEntered = true;
        }
        return gnomesEntered;
    }

    public void letMinionsIn() {
        while (!minionQueue.isEmpty()) {
            synchronized (minionQueue.peek()) {
                minionQueue.peek().notify();
                System.out.println(minionQueue.poll().getName() + " went inside to play.");
            }
        }
        synchronized (this){notify();}
    }

    public void letGnomesIn() {
        Gnome gnome;
        while (!gnomeQueue.isEmpty()){
            gnome = gnomeQueue.remove();
            synchronized (gnome){
                gnome.notify();
                System.out.println(gnome.getName() + " entered to play outside.");
            }
        }
    }
}
