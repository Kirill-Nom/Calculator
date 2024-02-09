import java.util.Map;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }
    public static String calc (String input){
        // Делим строку
        String[] strings = input.split(" ");

        // strings[0] - "1" -> a
        // strings[1] - "+"
        // strings[2] - "2" -> b

        // abcd -> 4

        // Проверка
        if (strings.length != 3) {
            throw new RuntimeException("Неверный ввод");
        }

        boolean isFirstArabic = isRoman(strings[0]), isSecondArabic = isRoman(strings[2]);
        if (isFirstArabic != isSecondArabic) {
            throw new RuntimeException("Числа в разных представлениях");
        }

        // получаем переменные
        int a, b, c;

        if (isFirstArabic) {
            a = romanToArabic(strings[0]);
            b = romanToArabic(strings[2]);
        } else {
            a = Integer.parseInt(strings[0]);
            b = Integer.parseInt(strings[2]);
        }
        if(!(a >= 1 && b >= 1 && a <= 10 && b <= 10)) {
            throw new RuntimeException("Вы вышли за диапозон значений");
        }

        // выполняем действие
        if (strings[1].equals("+")) {
            c = a + b;
        } else if (strings[1].equals("*")) {
            c = a * b;
        } else if (strings[1].equals("/")) {
            c = a / b;
        } else if (strings[1].equals("-")) {
            c = a - b;
        } else {
            System.out.println("Неверный ввод");
            return null;
        }
        if (isFirstArabic) {
            if (c <= 0){
                throw new RuntimeException("В римской системе нет отрицательных чисел");
            }
            return arabicToRoman(c);
        } else {
            return String.valueOf(c);
        }
    }
    // проверка римских чисел
    private static boolean isRoman(String number) {
        for (char el : number.toCharArray()) {
            if (!romanToArabicNumbers.containsKey(el)){
                return false;
            }
        }
        return true;
    }
    private static int romanToArabic(String romanNumber) {
        // XLI
        // LX
        int result = 0;

        for (int i = 0; i < romanNumber.length() - 1; i++) {
            int current = romanToArabicNumbers.get(romanNumber.charAt(i)); // X
            int next = romanToArabicNumbers.get(romanNumber.charAt(i + 1)); // L
            if (current < next) {
                result -= current;
            } else {
                result += current;
            }
        }
        result += romanToArabicNumbers.get(romanNumber.charAt(romanNumber.length() - 1));

        return result;
    }

    private static String arabicToRoman(int number) {
        // 42 -> XL II
        // 4 -> I V

        int numberCopy = number;
        int count = 0;
        StringBuilder resultBuilder = new StringBuilder();
        while (numberCopy > 0) {
            int digit = numberCopy % 10;
            if (arabicToRomanNumbers.containsKey(digit)) {
                resultBuilder.insert(0, arabicToRomanNumbers.get(digit * pow(10, count)));
            } else if (arabicToRomanNumbers.containsKey(digit + 1)) {
                resultBuilder.insert(0, arabicToRomanNumbers.get((digit + 1) * pow(10, count)));
                resultBuilder.insert(0, arabicToRomanNumbers.get(pow(10, count)));
            } else {
                boolean isFiveSubstracted = false;
                if (digit > 5) {
                    isFiveSubstracted = true;
                    digit -= 5;
                }
                for (int i = 0; i < digit; i++) {
                    resultBuilder.insert(0, arabicToRomanNumbers.get(pow(10, count)));
                }
                if (isFiveSubstracted) {
                    resultBuilder.insert(0, arabicToRomanNumbers.get(5 * pow(10, count)));
                }
            }
            count++;
            numberCopy = numberCopy / 10;
        }

        return resultBuilder.toString();
    }
    //возводим в степень
    private static int pow(int a, int b) {
        int result = 1;
        for (; b > 0; b--) {
            result *= a;
        }
        return result;
    }

    private final static Map<Character, Integer> romanToArabicNumbers = Map.of(
            'I', 1,
            'V', 5,
            'X', 10,
            'L', 50,
            'C', 100
    );
    private final static Map<Integer, String> arabicToRomanNumbers = Map.of(
            0, "",
            1, "I",
            5, "V",
            10, "X",
            50, "L",
            100, "C"
    );
}