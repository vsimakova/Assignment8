import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Assignment8Application {

	public static void main(String[] args) {

		Assignment8 a = new Assignment8();
		
		//create list of integers of size 1000
		List<Integer> allNumbers = Collections.synchronizedList(new ArrayList<>());
		
		//threading
        ExecutorService executor = Executors.newCachedThreadPool();

        //create list of 1000 tasks
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        
        for (int i = 0; i < 1000; i++) {
            CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> a.getNumbers(), executor)
                    .thenAccept(numbers -> allNumbers.addAll(numbers));
            tasks.add(task);    
        }

        while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000)

        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

		Map<Integer, Integer> output = new HashMap<>();
		
		for (Integer num : allNumbers) {
			output.put(num, output.getOrDefault(num, 0) + 1);
		}
		
		System.out.println(output);
	}

}
