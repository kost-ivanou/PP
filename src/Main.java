import java.io.*;
import java.util.Scanner;
import com.xml.SAXExample;
public class Main {
    public static void main(String[] args) {
        StringBuilder str = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите:\n 1. Считка и запись текстового файла" +
                "\n 2. Чтение xml файла");
        int n = scanner.nextInt();
        switch (n) {
            case 1:
                try (FileReader fin = new FileReader("input.txt")) {
                    int c;
                    while ((c = fin.read()) != -1) {
                        str.append((char) c);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                String strUpd = str.toString().toUpperCase();
                try (FileWriter fout = new FileWriter("output.txt", false)) {
                    fout.write(strUpd);
                    System.out.println("запись завершена успешно");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                new SAXExample();
                break;
            default:
                System.out.println("вы ввели неправильную цифру");
                break;
        }
    }
}