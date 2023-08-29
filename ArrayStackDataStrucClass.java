public class ArrayStackDataStrucClass<T> implements ArrayStackADT<T> {
    private final int MAX_STACK_SIZE = 50;
    private int top;
    private T[] stack;

    public ArrayStackDataStrucClass() {
        top = 0;
        stack = (T[]) new Object[MAX_STACK_SIZE];
    }

    public ArrayStackDataStrucClass(int stackSize) {
        top = 0;
        stack = (T[]) new Object[stackSize];
    }

    public ArrayStackDataStrucClass(ArrayStackDataStrucClass<T> otherStack) {
        top = otherStack.top;
        stack = (T[]) new Object[otherStack.stack.length];
        System.arraycopy(otherStack.stack, 0, stack, 0, top);
    }

    public void initializeStack() {
        top = 0;
    }

    public boolean isEmptyStack() {
        return (top == 0);
    }

    public boolean isFullStack() {
        return (top == stack.length);
    }

    public void push(T newItem) throws StackOverflowException {
        if (isFullStack()) {
            throw new StackOverflowException("Stack is full. Cannot push item.");
        }
        stack[top] = newItem;
        top++;
    }

    public T peek() throws StackUnderflowException {
        if (isEmptyStack()) {
            throw new StackUnderflowException("Stack is empty. Cannot peek item.");
        }
        return stack[top - 1];
    }

    public void pop() throws StackUnderflowException {
        if (isEmptyStack()) {
            throw new StackUnderflowException("Stack is empty. Cannot pop item.");
        }
        top--;
        stack[top] = null;
    }
}