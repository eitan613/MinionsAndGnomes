public class DayAtHome {

    public static void main(String[] args) {
        DayAtHome dayAtHome = new DayAtHome();
    }

    private Table table = new Table();
    private Alice alice;
    private Bedroom bedroom = new Bedroom();
    private Bob bob = new Bob(table,bedroom);

    public DayAtHome() {
        alice = new Alice(bob,table,bedroom);
        Door door = new Door(alice);
        Minion[] minions = new Minion[10];
        Gnome[] gnomes = new Gnome[7];
        for (int i = 0; i < minions.length; i++) {
            minions[i] = new Minion(i, alice,door,table);
            minions[i].start();
            if (i < 7) {
                gnomes[i] = new Gnome(i, alice,door,bob,table);
                gnomes[i].start();
            }

        }
    }
}
