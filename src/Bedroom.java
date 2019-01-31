public class Bedroom {
    private boolean lights = false;
    private boolean aliceInRoom = false;
    private boolean bobInRoom = false;
    private Object[] couch = new Object[2];

    public void setAliceInRoom(boolean b){
        aliceInRoom = b;
        if (aliceInRoom || bobInRoom)
            lights = true;
        else
            lights = false;
    }
    public void setBobInRoom(boolean b){
        bobInRoom = b;
        if (aliceInRoom || bobInRoom)
            lights = true;
        else
            lights = false;
    }

    public void reading(Object o){
        if (couch[0] == null)
            couch[0] = o;
        else
            couch[1] = o;
    }
}
