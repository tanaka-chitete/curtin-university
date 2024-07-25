public class TestClass {
    private final String testField;

    public TestClass(String testField) {
        this.testField = testField;
    }

    public String testMethod1(int n) {
        return testField.repeat(n);
    }

    public String testMethod2(int n) {
        return testField.repeat(2 * n);
    }

    public String testMethod3(int n) {
        return testField.repeat(3 * n);
    }

    public static String testMethod4(int n) {
        return "testField".repeat(4 * n);
    }
}
