public class Table {
    private boolean[]chairs = new boolean[5];

    public Table(){
        for (int i = 0; i < 5; i++) {
            chairs[i] = true;
        }
    }

    public synchronized boolean openSeat(){
        for (int i = 0; i < 5; i++) {
            if (chairs[i])
                return true;
        }
        return false;
    }
    public synchronized void seatOne(){
        for (int i = 0; i < 5; i++) {
            if (chairs[i]){
                chairs[i] = false;
                break;
            }
        }
    }
    public synchronized void unseatOne(){
        for (int i = 0; i < 5; i++) {
            if (!chairs[i]){
                chairs[i] = true;
                break;
            }
        }
    }
}
