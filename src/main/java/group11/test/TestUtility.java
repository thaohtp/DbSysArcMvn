package group11.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import group11.stream.input.GenericInputStreamInterface;
import group11.stream.output.GenericOutputStreamInterface;
import group11.stream.output.SystemOutputStream;
import group11.util.ImplementationEnum;
import group11.util.Utility;

/**
 * This class is used for recording execution time of a test case
 * 
 * @author JML
 *
 */
public class TestUtility {

	/**
	 * Test reading file by input stream
	 * 
	 * @param input
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Long readTest(GenericInputStreamInterface input, String filePath) throws IOException {
		long startTime = System.currentTimeMillis();
		try {
			input.open(filePath);
			try {
				while (!input.isEndOfStream()) {
					int number = input.readNext();
					System.out.println(number);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	/**
	 * Test writing file by output stream
	 * 
	 * @param output
	 * @param filePath
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static Long writeTest(GenericOutputStreamInterface output, String filePath, String data) throws IOException {
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
	 * 
	 * @param filePath
	 *            source file path
	 * @param implementation
	 *            implementation number of input stream (1-SystemCall, 2-Buffer,
	 *            3-BufferWithSize, 4-Mapping)
	 * @param bufferedSize
	 *            if input stream is buffered stream with size, then specify
	 *            buffer size
	 * @param mapCapacity
	 *            if input stream is mapping stream with size, then specify
	 *            memory capacity used for mapping/unmapping element
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Long runReadSingleTestCase(String filePath, ImplementationEnum implementation, Integer bufferedSize,
			Integer mapCapacity) throws FileNotFoundException, IOException {
		GenericInputStreamInterface inputStream = Utility.createInputStreamInstance(implementation, bufferedSize,
				mapCapacity);
		Long execTime = readTest(inputStream, filePath);
		inputStream.close();
		System.out.println("Execution time: " + execTime + "ms");
		return execTime;

	}

	/**
	 * Run merge sort single test case
	 * 
	 * @param readFilePath
	 *            source file
	 * @param resultFilePath
	 *            result file
	 * @param isAscending
	 *            sort order of numbers in result file
	 * @param availableMem
	 *            available memory
	 * @param nbStreamPerPass
	 *            number of streams to merge in one pass
	 * @param readImplemenation
	 *            implementation number of input stream (1-SystemCall, 2-Buffer,
	 *            3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize
	 *            if input stream is buffered stream with size, then specify
	 *            buffer size
	 * @param readMemCapacity
	 *            if input stream is mapping stream, then specify memory
	 *            capacity used for mapping/unmapping element
	 * @param writeImplementation
	 *            implementation number of output stream (1-SystemCall,
	 *            2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize
	 *            if output stream is buffered stream with size, then specify
	 *            buffer size
	 * @param writeMemCapacity
	 *            if output stream is mapping stream, then specify capacity used
	 *            for mapping/unmapping element
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Long runMergeSortSingleTestCase(String readFilePath, String resultFilePath, Boolean isAscending,
			Integer availableMem, Integer nbStreamPerPass, ImplementationEnum readImplemenation, Integer readBufferSize,
			Integer readMemCapacity, ImplementationEnum writeImplementation, Integer writeBufferSize,
			Integer writeMemCapacity) throws FileNotFoundException, IOException {
		Long startTime = System.currentTimeMillis();
		Utility.mergeSort(readFilePath, availableMem, nbStreamPerPass, resultFilePath, isAscending, readImplemenation,
				readBufferSize, readMemCapacity, writeImplementation, writeBufferSize, writeMemCapacity);
		Long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + (endTime - startTime) + "ms");
		return (endTime - startTime);
	}

	/**
	 * Run merge sort test case
	 * 
	 * @param readFilePath
	 *            source file
	 * @param resultFilePath
	 *            result file
	 * @param execTimeFilePath
	 *            file which stores execution time
	 * @param isAscending
	 *            sort order of numbers in result file
	 * @param availableMem
	 *            available memory
	 * @param nbStreamPerPass
	 *            number of streams to merge in one pass
	 * @param readImplemenation
	 *            implementation number of input stream (1-SystemCall, 2-Buffer,
	 *            3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize
	 *            if input stream is buffered stream with size, then specify
	 *            buffer size
	 * @param readMemCapacity
	 *            if input stream is mapping stream, then specify memory
	 *            capacity used for mapping/unmapping element
	 * @param writeImplementation
	 *            implementation number of output stream (1-SystemCall,
	 *            2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize
	 *            if output stream is buffered stream with size, then specify
	 *            buffer size
	 * @param writeMemCapacity
	 *            if output stream is mapping stream, then specify capacity used
	 *            for mapping/unmapping element
	 * @param nbRunTimes
	 *            number of running times
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void runMergeSortTestCase(String readFilePath, String resultFilePath, String execTimeFilePath,
			Boolean isAscending, Integer availableMem, Integer nbStreamPerPass, ImplementationEnum readImplemenation,
			Integer readBufferSize, Integer readMemCapacity, ImplementationEnum writeImplementation,
			Integer writeBufferSize, Integer writeMemCapacity, Integer nbRunTimes)
					throws FileNotFoundException, IOException {
		SystemOutputStream execTimeStream = new SystemOutputStream();
		execTimeStream.create(execTimeFilePath);
		for (int i = 0; i < nbRunTimes; i++) {
			// write execution to disk
			System.out.print("Iteration " + (i + 1) + " - ");
			Long execTime = runMergeSortSingleTestCase(readFilePath, resultFilePath, isAscending, availableMem,
					nbStreamPerPass, readImplemenation, readBufferSize, readMemCapacity, writeImplementation,
					writeBufferSize, writeMemCapacity);
			execTimeStream.write(execTime.toString());
			execTimeStream.write(" ");
		}
		execTimeStream.close();
	}

	/**
	 * Test reading and writing K streams for N times
	 * 
	 * @param readFileDirectory
	 *            <p>
	 *            Directory path of input files. If this is file path, the
	 *            program will read one single file
	 *            </p>
	 * @param writeFileDirectory
	 *            <p>
	 *            Directory path of output files
	 *            </p>
	 * @param execTimeFilePath
	 *            <p>
	 *            Directory path of file which stores execution time
	 *            </p>
	 * @param readImpl
	 *            implementation number of input stream (1-SystemCall, 2-Buffer,
	 *            3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize
	 *            if input stream is buffered stream with size, then specify
	 *            buffer size
	 * @param readMapCapacity
	 *            if input stream is mapping stream with size, then specify
	 *            memory capacity used for mapping/unmapping element
	 * @param writeImpl
	 *            implementation number of output stream (1-SystemCall,
	 *            2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize
	 *            if output stream is buffered stream with size, then specify
	 *            buffer size
	 * @param writeMapCapacity
	 *            if output stream is mapping stream with size, then specify
	 *            memory capacity used for mapping/unmapping element
	 * @param nbRunTimes
	 *            <p>
	 *            Number of times running
	 *            </p>
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void runReadWriteKParallelFilesTestCase(String readFileDirectory, String writeFileDirectory,
			String execTimeFilePath, ImplementationEnum readImpl, Integer readBufferSize, Integer readMapCapacity,
			ImplementationEnum writeImpl, Integer writeBufferSize, Integer writeMapCapacity, Integer nbRunTimes)
					throws FileNotFoundException, IOException {
		SystemOutputStream execTimeStream = new SystemOutputStream();
		execTimeStream.create(execTimeFilePath);
		for (int i = 0; i < nbRunTimes; i++) {
			// write execution to time disk
			System.out.print("Iteration " + (i + 1) + " - ");
			Long execTime = runReadWriteKFilesSingleTestCase(readFileDirectory, writeFileDirectory, readImpl,
					readBufferSize, readMapCapacity, writeImpl, writeBufferSize, writeMapCapacity);
			execTimeStream.write(execTime.toString());
			execTimeStream.write(" ");
		}
		execTimeStream.close();
	}

	/**
	 * Test reading and writing single file
	 * 
	 * @param inputFilePath
	 *            source file path
	 * @param readImpl
	 *            implementation number of input stream (1-SystemCall, 2-Buffer,
	 *            3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize
	 *            if input stream is buffered stream with size, then specify
	 *            buffer size
	 * @param readMapCapacity
	 *            if input stream is mapping stream with size, then specify
	 *            memory capacity used for mapping/unmapping element
	 * @param writeImpl
	 *            implementation number of output stream (1-SystemCall,
	 *            2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize
	 *            if output stream is buffered stream with size, then specify
	 *            buffer size
	 * @param writeMapCapacity
	 *            if output stream is mapping stream with size, then specify
	 *            memory capacity used for mapping/unmapping element
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Long runReadWriteSingleTestCase(String inputFilePath, String outputFilePath,
			ImplementationEnum readImpl, Integer readBufferSize, Integer readMapCapacity, ImplementationEnum writeImpl,
			Integer writeBufferSize, Integer writeMapCapacity) throws FileNotFoundException, IOException {
		Long startTime = System.currentTimeMillis();
		GenericInputStreamInterface inputStream = Utility.createInputStreamInstance(readImpl, readBufferSize,
				readMapCapacity);
		inputStream.open(inputFilePath);
		GenericOutputStreamInterface outputStream = Utility.createOutputStreamInstance(writeImpl, writeBufferSize,
				writeMapCapacity);
		outputStream.create(outputFilePath);
		while (!inputStream.isEndOfStream()) {
			Integer temp = inputStream.readNext();
			if (temp != null) {
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

	/**
	 * Test reading and writing K streams parallel
	 * 
	 * @param readFileDirectory
	 *            <p>
	 *            Directory path of input files. If this is file path, the
	 *            program will read one single file
	 *            </p>
	 * @param writeFileDirectory
	 *            <p>
	 *            Directory path of output files
	 *            </p>
	 * @param readImpl
	 *            implementation number of input stream (1-SystemCall, 2-Buffer,
	 *            3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize
	 *            if input stream is buffered stream with size, then specify
	 *            buffer size
	 * @param readMapCapacity
	 *            if input stream is mapping stream with size, then specify
	 *            memory capacity used for mapping/unmapping element
	 * @param writeImpl
	 *            implementation number of output stream (1-SystemCall,
	 *            2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize
	 *            if output stream is buffered stream with size, then specify
	 *            buffer size
	 * @param writeMapCapacity
	 *            if output stream is mapping stream with size, then specify
	 *            memory capacity used for mapping/unmapping element
	 * @return
	 * @throws IOException
	 */
	private static Long runReadWriteKFilesSingleTestCase(String readFileDirectory, String writeFileDirectory,
			ImplementationEnum readImpl, Integer readBufferSize, Integer readMapCapacity, ImplementationEnum writeImpl,
			Integer writeBufferSize, Integer writeMapCapacity) throws IOException {
		Long startTime = System.currentTimeMillis();
		File readDir = new File(readFileDirectory);
		ArrayList<GenericInputStreamInterface> inputStreamList = new ArrayList<GenericInputStreamInterface>();
		ArrayList<GenericOutputStreamInterface> outputStreamList = new ArrayList<GenericOutputStreamInterface>();
		if (readDir.isDirectory()) {
			File[] fileList = readDir.listFiles();
			for (File file : fileList) {
				if (!file.isDirectory()) {
					GenericInputStreamInterface inputStream = Utility.createInputStreamInstance(readImpl,
							readBufferSize, readMapCapacity);
					inputStream.open(file.getAbsolutePath());
					String outputFilePath = writeFileDirectory + "\\" + file.getName();
					GenericOutputStreamInterface outputStream = Utility.createOutputStreamInstance(writeImpl,
							writeBufferSize, writeMapCapacity);
					outputStream.create(outputFilePath);
					inputStreamList.add(inputStream);
					outputStreamList.add(outputStream);
				}
			}

		} else {
			GenericInputStreamInterface inputStream = Utility.createInputStreamInstance(readImpl, readBufferSize,
					readMapCapacity);
			inputStream.open(readDir.getAbsolutePath());
			String outputFilePath = writeFileDirectory + "\\" + readDir.getName();
			GenericOutputStreamInterface outputStream = Utility.createOutputStreamInstance(writeImpl, writeBufferSize,
					writeMapCapacity);
			outputStream.create(outputFilePath);
			inputStreamList.add(inputStream);
			outputStreamList.add(outputStream);
		}

		List<Integer> endOfStreamList = new ArrayList<Integer>();
		while (endOfStreamList.size() < inputStreamList.size()) {
			for (int i = 0; i < inputStreamList.size(); i++) {

				// if the stream is not end, continue to read next element
				// if stream has reach end, skip this stream

				if (!endOfStreamList.contains(i)) {
					Integer intTemp = inputStreamList.get(i).readNext();
					// System.out.println("Esto es lo que lee"+intTemp);
					if (intTemp != null) {
						outputStreamList.get(i).write(intTemp);
						// System.out.println("Esto es lo que escribe"+intTemp);
						outputStreamList.get(i).write(" ");
					}
					if (inputStreamList.get(i).isEndOfStream()) {
						inputStreamList.get(i).close();
						outputStreamList.get(i).close();
						endOfStreamList.add(i);
					}
				}

			}
		}
		Long endTime = System.currentTimeMillis();

		System.out.println("Execution time: " + (endTime - startTime) + "ms");
		return endTime - startTime;
	}
}
