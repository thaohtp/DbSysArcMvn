

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openjdk.jmh.runner.RunnerException;

import group11.test.CommandTestSuite;

public class Main {
	public static void main(String[] args) throws IOException, RunnerException {
		System.out.println("---------------------------------------------------------");
		System.out.println("------- WELCOME TO MERGE SORT PROJECT OF GROUP 11 -------");
		System.out.println("---------------------------------------------------------");
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String s = null;
		Integer task;
		do {
			System.out.println("1. Generate test file data consisting random integers");
			System.out.println("2. Test read/write file");
			System.out.println("3. Test merge sort integers");
			System.out.println("0. EXIT");
			do{ 
				System.out.println("Please enter the number of task you want to do: ");
				s = bufferRead.readLine();
			} while (s == null || s.compareTo("") == 0); 
			task = Integer.parseInt(s.trim());
			switch (task) {
			case 1:
				CommandTestSuite.runGenerateFiles();
				break;
			case 2:
				CommandTestSuite.runReadWriteFileTestCase();
				break;
			case 3:
				CommandTestSuite.runMergeSortTestCase();
				break;
			default:
				return;
			}
			System.out.println("Do you want to continue (Y or N)? ");
			s = bufferRead.readLine().trim();
		} while (s.compareToIgnoreCase("Y") == 0);
		
		// BENCHMARK FRAMEWORK INTEGRATION
//		Options options = new OptionsBuilder()
//				.include(JmhBenchmarkTestSuite.class.getSimpleName()).threads(1)
//				.forks(1).shouldFailOnError(true).shouldDoGC(true).build();
//		new Runner(options).run();
	}
}
