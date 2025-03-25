import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextEditor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    writeToFile(scanner);
                    break;
                case 2:
                    readFromFile(scanner);
                    break;
                case 3:
                    running = false;
                    System.out.println("Вихід з редактора. До побачення!");
                    break;
                default:
                    System.out.println("Невірний вибір. Будь ласка, введіть число від 1 до 3.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== ТЕКСТОВИЙ РЕДАКТОР ===");
        System.out.println("1. Записати текст у файл");
        System.out.println("2. Прочитати вміст файлу");
        System.out.println("3. Вийти з програми");
        System.out.print("Ваш вибір: ");
    }

    private static int getUserChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void writeToFile(Scanner scanner) {
        System.out.print("\nВведіть ім'я файлу для запису: ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            System.out.println("Помилка: Ім'я файлу не може бути порожнім!");
            return;
        }

        System.out.println("Введіть текст (для завершення введіть 'кінець' на окремому рядку):");
        
        String[] lines = new String[1000];
        int lineCount = 0;
        
        while (lineCount < lines.length) {
            System.out.print("> ");
            String line = scanner.nextLine().trim(); // Добавляем trim() для удаления пробелов
            
            // Изменяем условие проверки команды выхода
            if ("кінець".equalsIgnoreCase(line)) {
                break;
            }
            
            lines[lineCount] = line;
            lineCount++;
        }

        try {
            File file = new File(fileName);
            
            if (file.exists() && !file.canWrite()) {
                System.out.println("Помилка: Немає прав для запису у файл!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int i = 0; i < lineCount; i++) {
                    writer.write(lines[i]);
                    writer.newLine();
                }
                writer.flush();
                
                System.out.println("\nУспішно записано " + lineCount + " рядків у файл: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Помилка запису: " + e.getMessage());
        }
    }

    private static void readFromFile(Scanner scanner) {
        System.out.print("\nВведіть ім'я файлу для читання: ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            System.out.println("Помилка: Ім'я файлу не може бути порожнім!");
            return;
        }

        File file = new File(fileName);
        
        if (!file.exists()) {
            System.out.println("Помилка: Файл не знайдено!");
            return;
        }
        
        if (!file.canRead()) {
            System.out.println("Помилка: Немає прав для читання файлу!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("\n=== ВМІСТ ФАЙЛУ ===");
            String line;
            int lineNumber = 1;
            
            while ((line = reader.readLine()) != null) {
                System.out.printf("%4d: %s%n", lineNumber++, line);
            }
            
            System.out.println("=== КІНЕЦЬ ФАЙЛУ ===");
        } catch (IOException e) {
            System.out.println("Помилка читання: " + e.getMessage());
        }
    }
}