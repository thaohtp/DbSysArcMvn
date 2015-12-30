package group11.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import group11.util.ImplementationEnum;
import group11.util.Utility;

@State(Scope.Thread)
public class JmhBenchmarkTestSuite {
	
	private static final String TEST_DIRECTORY = "test_data";
//	private static final Integer BUFFER_SIZE = 50;
//	private static final Integer MEMORY_CAPACITY = 50;
	private static final String BACK_FLASH_WINDOWS = "\\";
	
	// Set default parameters for all classes
	private static final boolean DEFAULT_ASCENDING = true;
	private static final Integer DEFAULT_AVAILABLE_MEMORY = 10;
	private static final Integer DEFAULT_NB_STREAM_PER_PASS = 3;

	@Benchmark @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public static void runMergeSortTestCaseWithoutTime() throws FileNotFoundException, IOException{
		String readFilePath = TEST_DIRECTORY + BACK_FLASH_WINDOWS + "merge_sort_data" + BACK_FLASH_WINDOWS + "generate0.txt";
		String resultFilePath = TEST_DIRECTORY + BACK_FLASH_WINDOWS + "merge_sort_data" + BACK_FLASH_WINDOWS + "merge_sort_result.txt";
		Utility.mergeSort(readFilePath, DEFAULT_AVAILABLE_MEMORY, DEFAULT_NB_STREAM_PER_PASS, resultFilePath, DEFAULT_ASCENDING, ImplementationEnum.SYSTEM_CALL, 0, 0, ImplementationEnum.SYSTEM_CALL, 0, 0);
		
	}
}
