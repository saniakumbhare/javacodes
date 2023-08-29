public class Lock {
    private int x, y, z; 
    private int currentNumber; 
    private boolean open; 
    
    public Lock(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.currentNumber = 0;
        this.open = false;
    }
    
    public void setCombination(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void turnKnobClockwiseUntil(int number) {
        for (int i = currentNumber; i != number; i = (i + 1) % 40) {
            System.out.println("Turning knob clockwise, number is now " + i);
        }
        currentNumber = number;
    }
    
    public void turnKnobCounterclockwiseUntil(int number) {
        for (int i = currentNumber; i != number; i = (i - 1 + 40) % 40) {
            System.out.println("Turning knob counterclockwise, number is now " + i);
        }
        currentNumber = number;
    }
    
    public void close() {
        open = false;
    }
    
    public void attemptToOpen() {
        if (currentNumber == z) {
            open = true;
            System.out.println("Lock opened!");
        } else {
            System.out.println("Incorrect combination.");
        }
    }
    
    public boolean isOpen() {
        return open;
    }
    
    public int getCurrentNumber() {
        return currentNumber;
    }

public static void main(String[] args) {
    Lock lock = new Lock(17, 26, 9);
    
    lock.turnKnobClockwiseUntil(lock.getCurrentNumber() + 1);
    lock.turnKnobClockwiseUntil(lock.x);
    lock.turnKnobCounterclockwiseUntil(lock.y);
    lock.turnKnobClockwiseUntil(lock.z);
    lock.attemptToOpen();
    
}
}