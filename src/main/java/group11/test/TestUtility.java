package group11.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import group11.stream.input.GenericInputStreamInterface;
import group11.stream.output.GenericOutputStreamInterface;
import group11.stream.output.SystemOutputStream;
import group11.util.ImplementationEnum;
import group11.util.Utility;

/**
 * This class is used for recording execution time of a test case
 * @author JML
 *
 */
public class TestUtility {
	
	
	/**
	 * Test reading file by input stream
	 * @param input
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static Long readTest(GenericInputStreamInterface input, String filePath) throws IOException{
		long startTime = System.currentTimeMillis();
	      try {
			input.open(filePath);
			try {
				while(!input.isEndOfStream()){
					  int number = input.readNext();
					  System.out.println(number);
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      input.close();
	      long endTime = System.currentTimeMillis();
	      return endTime - startTime;
   }
	
	/**
	 * Test writing file by output stream
	 * @param output
	 * @param filePath
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public static Long writeTest(GenericOutputStreamInterface output, String filePath, String data) throws IOException{
		long startTime = System.currentTimeMillis();
		output.create(filePath);
		for (char c : data.toCharArray()) {
		       output.write(c);
		}
		output.close();
		long endTime = System.currentTimeMillis();
	    return endTime - startTime;
	}
	
	/**
	 * Test reading single file
	 * @param filePath source file path
	 * @param implementation implementation number of input stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param bufferedSize if input stream is buffered stream with size, then specify buffer size
	 * @param mapCapacity if input stream is mapping stream with size, then specify memory capacity used for mapping/unmapping element
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Long runReadSingleTestCase(String filePath, ImplementationEnum implementation, Integer bufferedSize, Integer mapCapacity) throws FileNotFoundException, IOException{
		GenericInputStreamInterface inputStream = Utility.createInputStreamInstance(implementation, bufferedSize, mapCapacity);
		Long execTime = readTest(inputStream, filePath);
		inputStream.close();
		System.out.println("Execution time: " + execTime + "ms");
		return execTime;
		
	}
	
	public static void runReadWriteKStreamTestCase(String readingDirectory, String copiedDirectory, String execTimeFilePath, String prefix, String suffix, Integer nTime, Integer bufferSize, Integer mapCapacity){
		// Should we put in thread
	}
	
	/**
	 * Run merge sort single test case
	 * @param readFilePath source file
	 * @param resultFilePath result file
	 * @param isAscending sort order of numbers in result file
	 * @param availableMem available memory
	 * @param nbStreamPerPass number of streams to merge in one pass
	 * @param readImplemenation implementation number of input stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize if input stream is buffered stream with size, then specify buffer size
	 * @param readMemCapacity if input stream is mapping stream, then specify memory capacity used for mapping/unmapping element
	 * @param writeImplementation implementation number of output stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize if output stream is buffered stream with size, then specify buffer size
	 * @param writeMemCapacity if output stream is mapping stream, then specify capacity used for mapping/unmapping element
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Long runMergeSortSingleTestCase(String readFilePath, String resultFilePath, Boolean isAscending, Integer availableMem, Integer nbStreamPerPass, ImplementationEnum readImplemenation,Integer readBufferSize, Integer readMemCapacity, ImplementationEnum writeImplementation, Integer writeBufferSize, Integer writeMemCapacity) throws FileNotFoundException, IOException{
		Long startTime = System.currentTimeMillis();
		Utility.mergeSort(readFilePath, availableMem, nbStreamPerPass, resultFilePath, isAscending, readImplemenation, readBufferSize, readMemCapacity, writeImplementation, writeBufferSize, writeMemCapacity);
		Long endTime = System.currentTimeMillis();		
		System.out.println("Execution time: " + (endTime - startTime) + "ms");
		return (endTime - startTime);
	}
	
	/**
	 * Run merge sort test case
	 * @param readFilePath source file
	 * @param resultFilePath result file
	 * @param execTimeFilePath file which stores execution time
	 * @param isAscending sort order of numbers in result file
	 * @param availableMem available memory
	 * @param nbStreamPerPass number of streams to merge in one pass
	 * @param readImplemenation implementation number of input stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize if input stream is buffered stream with size, then specify buffer size
	 * @param readMemCapacity if input stream is mapping stream, then specify memory capacity used for mapping/unmapping element
	 * @param writeImplementation implementation number of output stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize if output stream is buffered stream with size, then specify buffer size
	 * @param writeMemCapacity if output stream is mapping stream, then specify capacity used for mapping/unmapping element
	 * @param nbRunTimes number of running times
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void runMergeSortTestCase(String readFilePath, String resultFilePath, String execTimeFilePath, Boolean isAscending, Integer availableMem, Integer nbStreamPerPass, ImplementationEnum readImplemenation,Integer readBufferSize, Integer readMemCapacity, ImplementationEnum writeImplementation, Integer writeBufferSize, Integer writeMemCapacity, Integer nbRunTimes) throws FileNotFoundException, IOException{
		SystemOutputStream execTimeStream = new SystemOutputStream();
		execTimeStream.create(execTimeFilePath);
		for(int i =0; i< nbRunTimes; i++){			
//			Long startTime = System.currentTimeMillis();
//			Utility.mergeSort(readFilePath, availableMem, nbStreamPerPass, resultFilePath, isAscending, readImplemenation, readBufferSize, readMemCapacity, writeImplementation, writeBufferSize, writeMemCapacity);
//			Long endTime = System.currentTimeMillis();		
//			System.out.println((i+1) + ".Execution time: " + (endTime - startTime) + "ms");
			// write execution to disk
			System.out.print("Iteration " + (i+1) + " - ");
			Long execTime = runMergeSortSingleTestCase(readFilePath, resultFilePath, isAscending, availableMem, nbStreamPerPass, readImplemenation, readBufferSize, readMemCapacity, writeImplementation, writeBufferSize, writeMemCapacity);
			execTimeStream.write(execTime.toString());
			execTimeStream.write(" ");
		}
		execTimeStream.close();
	}
	
	public static void runKStreamReadTestCase(String readFileDirectory, String execTimeFilePath, ImplementationEnum implementation, Integer readBuffer, Integer readCapacity){
		
	}
	
	/**
	 * Test reading and writing single file
	 * @param inputFilePath source file path
	 * @param readImpl implementation number of input stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize if input stream is buffered stream with size, then specify buffer size
	 * @param readMapCapacity if input stream is mapping stream with size, then specify memory capacity used for mapping/unmapping element
	 * @param writeImpl implementation number of output stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize if output stream is buffered stream with size, then specify buffer size
	 * @param writeMapCapacity if output stream is mapping stream with size, then specify memory capacity used for mapping/unmapping element
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Long runReadWriteSingleTestCase(String inputFilePath, String outputFilePath, ImplementationEnum readImpl, Integer readBufferSize, Integer readMapCapacity, ImplementationEnum writeImpl, Integer writeBufferSize, Integer writeMapCapacity) throws FileNotFoundException, IOException{
		Long startTime =  System.currentTimeMillis();
		GenericInputStreamInterface inputStream = Utility.createInputStreamInstance(readImpl, readBufferSize, readMapCapacity);
		inputStream.open(inputFilePath);
		GenericOutputStreamInterface outputStream = Utility.createOutputStreamInstance(writeImpl, writeBufferSize, writeMapCapacity);
		outputStream.create(outputFilePath);
		while(!inputStream.isEndOfStream()){
			Integer temp = inputStream.readNext();
			if(temp != null){
				outputStream.write(temp);
				outputStream.write(" ");
			}
		}
		inputStream.close();
		outputStream.close();
		Long endTime = System.currentTimeMillis();
		
		System.out.println("Execution time: " + (endTime - startTime) + "ms");
		return endTime - startTime;
		
	}
}

