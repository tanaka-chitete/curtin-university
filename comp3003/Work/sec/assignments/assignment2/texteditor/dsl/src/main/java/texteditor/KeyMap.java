package texteditor;

public class KeyMap 
{
    private final String keyBinding;
    private final String operation;
    private final String operand;
    private final String position;

    public KeyMap(String keyBinding, String operation, String operand, String position)
    {
        this.keyBinding = keyBinding;
        this.operation = operation;
        this.operand = operand;
        this.position = position;
    }

    public String getKeyBinding()
    {
        return keyBinding;
    }

    public String getOperation()
    {
        return operation;
    }

    public String getOperand()
    {
        return operand;
    }

    public String getPosition()
    {
        return position;
    }

    @Override
    public String toString()
    {
        return String.format("KeyMap[keyBinding=%s, operation=%s, operand=%s, position=%s]",
                             keyBinding, operation, operand, position);
    }
}
