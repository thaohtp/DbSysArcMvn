package group11.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import group11.util.ImplementationEnum;
import group11.util.Utility;

public class CommandTestSuite {

	public static void runGenerateFiles() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Enter number of generated number per file: ");
			String s = bufferRead.readLine();
			int numberPerFile = Integer.parseInt(s.trim());
			System.out.println("Enter number of generated files: ");
			s = bufferRead.readLine();
			int numberOfFiles = Integer.parseInt(s.trim());
			System.out.println("Enter directory path: ");
			s = bufferRead.readLine();
			String directory = s.trim();
			System.out.println("The file will be generated with name in format <Prefix><Sequence_number>.<suffix>");
			System.out.println("Enter file name prefix: ");
			s = bufferRead.readLine();
			String prefix = s.trim();
			System.out.println("Enter file name suffix: ");
			s = bufferRead.readLine();
			String suffix = s.trim();

			Utility.generateRandomFile(numberPerFile, numberOfFiles, directory, prefix, suffix);
			System.out.println("Task completed. Please check your file at " + directory);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void runReadWriteFileTestCase() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {

			System.out.println("What test do you want to run");
			System.out.println("\t 1. Read and print out single file (Test InputStream)");
			System.out.println("\t 2. Read and write single file (Test InputStream and OutputStream)");
			System.out.println(
					"\t 3. Read all K files in specific directory and create copied file. This testcase is used for testing opening K streams, reading/writing each stream, N times");
			System.out.println("\t Enter the test you wanna run (default 1): ");
			String s = bufferRead.readLine();
			int testcase = 0;
			if(s != null && s.compareTo("") != 0){
				testcase = Integer.parseInt(s.trim());
			}
			if (testcase == 3) {
				System.out.println("Sorry, this test is still in progress. Please choose another option" );
			} else {
				// Test case 2
				if (testcase == 2) {
					createReadWriteTest();
				} else {
					createReadTest();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void createReadTest() throws IOException{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter file path: ");
		String s = bufferRead.readLine();
		String filePath = s.trim();
		System.out.println("Enter implementation number you want to test: ");
		System.out.println("\t 1. Stream use system call: ");
		System.out.println("\t 2. Buffered stream: ");
		System.out.println("\t 3. Buffered stream with size: ");
		System.out.println("\t 4. Mapping stream: ");
		s = bufferRead.readLine();
		ImplementationEnum implEnum = ImplementationEnum.getImplementationEnum(Integer.parseInt(s.trim()));
		if (implEnum == ImplementationEnum.BUFFERED_WITH_SIZE) {
			System.out.println("\t -> Enter buffer size: ");
			s = bufferRead.readLine();
			Integer bufferSize = Integer.parseInt(s.trim());
			TestUtility.runReadSingleTestCase(filePath, implEnum, bufferSize, 0);
		} else {
			if (implEnum == ImplementationEnum.MAPPING) {
				System.out.println("\t -> Enter mapping capacity size: ");
				s = bufferRead.readLine();
				Integer memCapacity = Integer.parseInt(s.trim());
				TestUtility.runReadSingleTestCase(filePath, implEnum, 0, memCapacity);
			} else {
				TestUtility.runReadSingleTestCase(filePath, implEnum, 0, 0);
			}
		}
		System.out.println("Task completed!");
	}
	
	private static void createReadWriteKStreamTest() throws IOException{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter directory path of reading file: ");
		String s = bufferRead.readLine();
		String readingDir = s.trim();
		System.out.println("Enter directory path of copied files: ");
		s = bufferRead.readLine();
		String copiedDir = s.trim();
		System.out.println("Enter expected file path of file which store execution time: ");
		s = bufferRead.readLine();
		String execTimeDir = s.trim();
		System.out.println(
				"The file will be generated with name in format <Prefix><Sequence_time>_<Sequence_number>.<suffix>");
		System.out.println("Enter file name prefix: ");
		s = bufferRead.readLine();
		String prefix = s.trim();
		System.out.println("Enter file name suffix: ");
		s = bufferRead.readLine();
		String suffix = s.trim();
		System.out.println("Enter N times: ");
		s = bufferRead.readLine();
		int N = Integer.parseInt(s.trim());
		System.out.println("Enter implementation number you want to test: ");
		System.out.println("\t 1. Stream use system call: ");
		System.out.println("\t 2. Buffered stream: ");
		System.out.println("\t 3. Buffered stream with size: ");
		System.out.println("\t 4. Mapping stream: ");
		s = bufferRead.readLine();
		ImplementationEnum implEnum = ImplementationEnum.getImplementationEnum(Integer.parseInt(s.trim()));
		if (implEnum == ImplementationEnum.BUFFERED_WITH_SIZE) {
			System.out.println("\t -> Enter buffer size: ");
			s = bufferRead.readLine();
			Integer bufferSize = Integer.parseInt(s.trim());
		} else {
			if (implEnum == ImplementationEnum.MAPPING) {
				System.out.println("\t -> Enter mapping capacity size: ");
				s = bufferRead.readLine();
				Integer memCapacity = Integer.parseInt(s.trim());
			} else {
			}
		}
		System.out.println("Task completed! Please check your file at " + copiedDir);
	}
	
	private static void createReadWriteTest() throws IOException{
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter input file path: ");
		String s = bufferRead.readLine();
		String inputFilePath = s.trim();
		System.out.println("Enter output file path: ");
		s = bufferRead.readLine();
		String outputFilePath = s.trim();
		
		// get parameter for input stream
		System.out.println("Enter implementation number you want to test for READING: ");
		System.out.println("\t 1. Stream use system call: ");
		System.out.println("\t 2. Buffered stream: ");
		System.out.println("\t 3. Buffered stream with size: ");
		System.out.println("\t 4. Mapping stream: ");
		s = bufferRead.readLine();
		ImplementationEnum readImpl = ImplementationEnum.getImplementationEnum(Integer.parseInt(s.trim()));
		Integer readBufferSize = 0;
		Integer readMemCapacity = 0;
		if (readImpl == ImplementationEnum.BUFFERED_WITH_SIZE) {
			System.out.println("\t -> Enter buffer size: ");
			s = bufferRead.readLine();
			readBufferSize = Integer.parseInt(s.trim());
		} else {
			if (readImpl == ImplementationEnum.MAPPING) {
				System.out.println("\t -> Enter mapping capacity size: ");
				s = bufferRead.readLine();
				readMemCapacity = Integer.parseInt(s.trim());
			}
		}
		
		// get parameter for output stream
		System.out.println("Enter implementation number you want to test for WRITING: ");
		System.out.println("\t 1. Stream use system call: ");
		System.out.println("\t 2. Buffered stream: ");
		System.out.println("\t 3. Buffered stream with size: ");
		System.out.println("\t 4. Mapping stream: ");
		s = bufferRead.readLine();
		ImplementationEnum writeImpl = ImplementationEnum.getImplementationEnum(Integer.parseInt(s.trim()));
		Integer writeBufferSize = 0;
		Integer writeMemCapacity = 0;
		if (writeImpl == ImplementationEnum.BUFFERED_WITH_SIZE) {
			System.out.println("\t -> Enter buffer size: ");
			s = bufferRead.readLine();
			writeBufferSize = Integer.parseInt(s.trim());
		} else {
			if (writeImpl == ImplementationEnum.MAPPING) {
				System.out.println("\t -> Enter mapping capacity size: ");
				s = bufferRead.readLine();
				writeBufferSize = Integer.parseInt(s.trim());
			} 
		}
		TestUtility.runReadWriteSingleTestCase(inputFilePath, outputFilePath, readImpl, readBufferSize, readMemCapacity, writeImpl, writeBufferSize, writeMemCapacity);
		System.out.println("Task completed!Please check your file at " + outputFilePath);
	}

	public static void runMergeSortTestCase() {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String s;
		try {
			System.out.println("Enter file path: ");
			s = bufferRead.readLine();
			String filePath = s.trim();
			System.out.println("Enter expected file path of result file: ");
			s = bufferRead.readLine();
			String resultPath = s.trim();
			System.out.println("Enter expected file path of file which store execution time: ");
			s = bufferRead.readLine();
			String execTimeFilePath = s.trim();
			System.out.println("Is result sorted ascending? (Y or N): ");
			s = bufferRead.readLine();
			String sortOrder = s.trim().toLowerCase();
			boolean isAscending = sortOrder.compareTo("n") == 0 ? false : true;
			System.out.println("Enter number of available main memory: ");
			s = bufferRead.readLine();
			Integer M = Integer.parseInt(s.trim());
			System.out.println("Enter number of stream to merge in one pass: ");
			s = bufferRead.readLine();
			Integer d = Integer.parseInt(s.trim());

			// ask for implementation of reading
			Integer readBufferSize = 0;
			Integer readMemCapacity = 0;
			System.out.println("Enter implementation number you want to use for reading: ");
			System.out.println("\t 1. Stream use system call: ");
			System.out.println("\t 2. Buffered stream: ");
			System.out.println("\t 3. Buffered stream with size: ");
			System.out.println("\t 4. Mapping stream: ");
			s = bufferRead.readLine();
			ImplementationEnum readImplEnum = ImplementationEnum.getImplementationEnum(Integer.parseInt(s.trim()));

			if (readImplEnum == ImplementationEnum.BUFFERED_WITH_SIZE) {
				System.out.println("\t -> Enter buffer size for reading: ");
				s = bufferRead.readLine();
				readBufferSize = Integer.parseInt(s.trim());
			} else {
				if (readImplEnum == ImplementationEnum.BUFFERED_WITH_SIZE) {
					System.out.println("\t -> Enter mapping capacity size for reading: ");
					s = bufferRead.readLine();
					readMemCapacity = Integer.parseInt(s.trim());
				}
			}

			// ask for implementation of writing
			Integer writeBufferSize = 0;
			Integer writeMemCapacity = 0;
			System.out.println("Enter implementation number you want to use for writing: ");
			System.out.println("\t 1. Stream use system call: ");
			System.out.println("\t 2. Buffered stream: ");
			System.out.println("\t 3. Buffered stream with size: ");
			System.out.println("\t 4. Mapping stream: ");
			s = bufferRead.readLine();
			ImplementationEnum writeImplEnum = ImplementationEnum.getImplementationEnum(Integer.parseInt(s.trim()));
			if (writeImplEnum == ImplementationEnum.BUFFERED_WITH_SIZE) {
				System.out.println("\t -> Enter buffer size for writing: ");
				s = bufferRead.readLine();
				writeBufferSize = Integer.parseInt(s.trim());
			} else {
				if (writeImplEnum == ImplementationEnum.BUFFERED_WITH_SIZE) {
					System.out.println("\t -> Enter mapping capacity size for writing: ");
					s = bufferRead.readLine();
					writeMemCapacity = Integer.parseInt(s.trim());
				}
			}
			System.out.println("Enter number of time you want to run test: ");
			s = bufferRead.readLine();
			Integer nbRunTimes = Integer.parseInt(s.trim());
			TestUtility.runMergeSortTestCase(filePath, resultPath, execTimeFilePath, isAscending, M, d, readImplEnum,
					readBufferSize, readMemCapacity, writeImplEnum, writeBufferSize, writeMemCapacity, nbRunTimes);
			System.out.println("Task completed! Please check your file at " + resultPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
