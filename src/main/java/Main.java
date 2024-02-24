import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger sum3 = new AtomicInteger(0);
    public static AtomicInteger sum4 = new AtomicInteger(0);
    public static AtomicInteger sum5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(null, () -> sumIncrease(texts));
        Thread thread2 = new Thread(null, () -> sumSame(texts));
        Thread thread3 = new Thread(null, () -> sumPolendrom(texts));

        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.println("Красивых слов с длиной 3: " + sum3.get() + " шт \n"
                + "Красивых слов с длиной 4: " + sum4.get() + " шт \n"
                + "Красивых слов с длиной 5: " + sum5.get() + " шт \n");
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void addToLength(String word){
        int size = word.length();

        switch (size){
            case 3 :
                sum3.getAndIncrement();
                break;
            case 4 :
                sum4.getAndIncrement();
                break;
            case 5 :
                sum5.getAndIncrement();
                break;
        }
    }

    public static void sumPolendrom(String[] text) {

        for (String word : text) {
            if (word.equals(new StringBuilder(word).reverse().toString())) {
                if (isSame(word) == false){
                    addToLength(word);
                }
            }
        }
    }

    public static void sumSame(String [] text) {
        for (String word : text) {
            if (isSame(word)){
                addToLength(word);
            }
        }
    }

    public static void sumIncrease(String[] text) {
        for (String word : text) {
            boolean isIncreace = true;
            for (int i = 0; i < word.length() - 1; i++) {
                if (word.charAt(i) > word.charAt(i + 1)) {
                    isIncreace = false;
                    break;
                }
            }
            if (isIncreace && isSame(word) == false ) {
                addToLength(word);
            }
        }
    }
    public static boolean isSame(String text){

        char first = text.charAt(0);
        for (char c : text.toCharArray()) {
            if (first != c) {
                return false;
            }
        }
        return true;
    }
}