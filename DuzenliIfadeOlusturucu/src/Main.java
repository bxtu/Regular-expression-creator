import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Alfabeyi girin (örneğin, {a,b}): ");
        String alfabe = scanner.nextLine();
        String[] alfabeDizisi = alfabe
                .replaceAll("\\s+", "")
                .replaceAll("[{}]", "")
                .split(",");

        System.out.print("Düzenli ifadeyi girin: ");
        String duzenliIfade = scanner.nextLine();

        // Düzenli ifade alfabeden üretilebilir mi kontrol et
        if (duzenliIfadeTest(duzenliIfade, alfabeDizisi)) {
            System.out.print("Kaç adet L dilinde kelime görmek istersiniz?: ");
            int kelimeSayisi = scanner.nextInt();

            System.out.println("Düzenli ifade alfabe S'den üretilebilir. İşte kelimeleriniz:");
            ArrayList<String> kelimeler = kelimeUret(duzenliIfade, alfabeDizisi, kelimeSayisi);
            for (String kelime : kelimeler) {
                System.out.println("L={" + kelime + "}");
            }
            scanner.nextLine();
            System.out.print("Kontrol edilmek istenen kelimeyi girin: ");
            String kontrolKelimesi = scanner.nextLine();
            if (dilIleEslesiyorMu(kontrolKelimesi, duzenliIfade, alfabeDizisi)) {
                System.out.println("Bu kelime L diline aittir.");
            } else {
                System.out.println("Bu kelime L diline ait değildir.");
            }
        } else {
            System.out.println("Düzenli ifade verilen alfabeden üretilemez.");
        }

        scanner.close();
    }

    private static boolean duzenliIfadeTest(String regex, String[] alfabe) {
        for (String karakter : alfabe) {
            if (!regex.contains(karakter)) {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<String> kelimeUret(String regex, String[] alfabe, int kelimeSayisi) {
        ArrayList<String> kelimeler = new ArrayList<>();
        Pattern desen = Pattern.compile(regex);

        for (int kelimeUzunlugu = 1; kelimeler.size() < kelimeSayisi; kelimeUzunlugu++) {
            uret("", kelimeUzunlugu, alfabe, desen, kelimeler);
        }

        return kelimeler;
    }

    private static void uret(String prefix, int remainingLength, String[] alfabe, Pattern desen, ArrayList<String> kelimeler) {
        if (remainingLength == 0) {
            if (desen.matcher(prefix).matches() && !kelimeler.contains(prefix)) {
                kelimeler.add(prefix);
            }
            return;
        }

        for (int i = 0; i < alfabe.length; i++) {
            String newPrefix = prefix + alfabe[i];
            uret(newPrefix, remainingLength - 1, alfabe, desen, kelimeler);
        }
    }

    private static boolean dilIleEslesiyorMu(String kelime, String regex, String[] alfabe) {
        Pattern desen = Pattern.compile("^[" + String.join("", alfabe) + "]+$");
        Matcher eslesme = desen.matcher(kelime);
        if (!eslesme.matches()) {
            return false;
        }
        return kelime.matches("^" + regex + "$");
    }
}
