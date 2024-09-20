import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        List<Thread> threads = new ArrayList<>();
        int threadsNumber = 1000;
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int rRepits = howManyRRotate(route);

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(rRepits)) {
                        Integer saved = sizeToFreq.get(rRepits) + 1;
                        sizeToFreq.remove(rRepits);
                        sizeToFreq.put(rRepits, saved);
                    } else {
                        sizeToFreq.put(rRepits, 1);
                    }
                }


            }));

        }
        for (Thread thread : threads) {
            thread.start();

        }
        for (Thread thread : threads) {
            thread.join();
        }

        //вывод на экран самого чатсого повторения ключа и всех остальных
        int mostPolularkey = 0;
        int biggestRepits = 0;
        for (Integer key : sizeToFreq.keySet()) {
            int reps = sizeToFreq.get(key);
            if (reps > biggestRepits) {
                biggestRepits = reps;
                mostPolularkey = key;
            }
        }
        sizeToFreq.remove(mostPolularkey, biggestRepits);
        System.out.println("Most popular key is " + mostPolularkey + ". There are " + biggestRepits + " repeats.");
        System.out.println("Also there :");
        sizeToFreq.forEach((key, value) -> System.out.println("Key: " + key + " Value: " + value));


    }

    //функция для генерации маршрута
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    //функция для подсчета кол-ва r в маршруте
    public static int howManyRRotate(String input) {
        char c = 'R';
        int rRepits = 0;
        for (int counter = 0; counter < input.length(); counter++) {
            if (input.charAt(counter) == c) {
                rRepits++;
            }
        }
        return rRepits;

    }
}
