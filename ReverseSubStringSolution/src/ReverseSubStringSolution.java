public class ReverseSubStringSolution {
    public static void main(String[] args) {
        reverseSubStrings(null);
        reverseSubStrings("");
        reverseSubStrings("aBD(jnb)asdf");
        reverseSubStrings("abd(jnb)asdf");
        reverseSubStrings("abdjnbasdf");
        reverseSubStrings("dd(df)a(ghhh)");
    }

    private static void reverseSubStrings(String originalString) {

        if (!validationSteps(originalString)) {
            System.out.println("Input string is invalid.");
            return;
        }

        StringBuilder result = new StringBuilder();
        boolean reverseConditionStarted = false;
        StringBuilder stringToBeReversed = new StringBuilder();

        for (int index = 0; index < originalString.length(); index++) {
            if (originalString.charAt(index) != '(' && originalString.charAt(index) != ')') {
                if (reverseConditionStarted) {
                    stringToBeReversed.append(originalString.charAt(index));
                } else {
                    result.append(originalString.charAt(index));
                }
            } else if (originalString.charAt(index) == '(') {
                reverseConditionStarted = true;
                result.append(originalString.charAt(index));
            } else if (originalString.charAt(index) == ')') {
                reverseConditionStarted = false;
                result.append(stringToBeReversed.reverse());
                stringToBeReversed = new StringBuilder("");
                result.append(originalString.charAt(index));
            }
        }

        System.out.println("Result is: " + result);
    }

    private static boolean validationSteps(String originalString) {
        if (originalString == null
                || originalString.length() < 1 || originalString.length() > 2000) {
            return false;
        }
        for (char c : originalString.toCharArray()) {
            if (c != '(' && c != ')'
                    && !Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }
}