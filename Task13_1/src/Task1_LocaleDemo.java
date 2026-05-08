import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Task1_LocaleDemo {
    // Внутренняя таблица курсов (RUB -> другие валюты)
    private static final Map<String, Double> RATES_FROM_RUB = new HashMap<>();

    static {
        // Здесь можно задать реальные курсы (например, на текущую дату)
        RATES_FROM_RUB.put("USD", 0.013);
        RATES_FROM_RUB.put("EUR", 0.014);
        RATES_FROM_RUB.put("BYN", 0.034);
        RATES_FROM_RUB.put("RUB", 1.0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Меню выбора локали (на английском, чтобы было понятно до выбора)
        System.out.println("Choose locale:");
        System.out.println("1 - English (US)");
        System.out.println("2 - Русский (Россия)");
        System.out.println("3 - Français (France)");
        System.out.println("4 - Беларуская (Беларусь)");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        Locale locale;
        String targetCurrency;
        switch (choice) {
            case 1:
                locale = Locale.US;
                targetCurrency = "USD";
                break;
            case 2:
                locale = new Locale("ru", "RU");
                targetCurrency = "RUB";
                break;
            case 3:
                locale = Locale.FRANCE;
                targetCurrency = "EUR";
                break;
            case 4:
                locale = new Locale("be", "BY");
                targetCurrency = "BYN";
                break;
            default:
                locale = Locale.getDefault();
                targetCurrency = "USD";
        }

        // Загружаем ресурсы для выбранной локали
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        // Приветствие
        System.out.println("\n" + bundle.getString("greeting") + "\n");

        // 2. Ввод суммы в рублях (текст из ресурсов)
        System.out.print(bundle.getString("enter.price") + " ");
        double amountRUB = scanner.nextDouble();

        // 3. Конвертация в целевую валюту
        double rate = RATES_FROM_RUB.getOrDefault(targetCurrency, 1.0);
        double convertedAmount = (targetCurrency.equals("RUB")) ? amountRUB : amountRUB * rate;

        // 4. Форматирование в валюте локали и вывод (текст из ресурсов)
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        System.out.println(bundle.getString("converted.price") + " " + currencyFormat.format(convertedAmount));

        // 5. Демонстрация форматирования даты (используем локализованные заголовки)
        System.out.println("\n" + bundle.getString("date.header"));
        LocalDate today = LocalDate.now();

        // Россия
        Locale ruLocale = new Locale("ru", "RU");
        DateTimeFormatter ruFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", ruLocale);
        System.out.println(bundle.getString("date.russia") + " " + today.format(ruFormatter));

        // Франция
        Locale frLocale = Locale.FRANCE;
        DateTimeFormatter frFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", frLocale);
        System.out.println(bundle.getString("date.france") + " " + today.format(frFormatter));

        // США
        Locale usLocale = Locale.US;
        DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", usLocale);
        System.out.println(bundle.getString("date.usa") + " " + today.format(usFormatter));

        // Беларусь
        Locale beLocale = new Locale("be", "BY");
        DateTimeFormatter beFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", beLocale);
        System.out.println(bundle.getString("date.belarus") + " " + today.format(beFormatter));

        scanner.close();
    }
}