package group11.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import group11.stream.input.BufferedInputStream;
import group11.stream.input.BufferedWithSizeInputStream;
import group11.stream.input.GenericInputStreamInterface;
import group11.stream.input.MappingInputStream;
import group11.stream.input.SystemInputStream;
import group11.stream.output.BufferedOutputStream;
import group11.stream.output.BufferedWithSizeOutputStream;
import group11.stream.output.GenericOutputStreamInterface;
import group11.stream.output.MappingOutputStream;
import group11.stream.output.SystemOutputStream;

public class Utility {

	private static final Integer MAXIMUM_RANDOM_NUMBER = 65536;
	private static final Integer UNSIGNED_MINIMUM_RANDOM_NUMBER = 32768;
	
	private static final String TEMP_DIRECTORY_FOR_MERGE_SORT = "tempDir";
	private static final String TEMP_FILE_NAME_PREFIX = "temp";
	
	private static final String BACK_FLASH_WINDOWS = "\\";

	/**
	 * Multi-way merge
	 * <p>
	 * From d sorted input streams of 32-bit integers, create single output
	 * stream in sorted order
	 * </p>
	 * 
	 * @param input:
	 *            an array of sorted stream
	 * @param sortedOrder:
	 *            put <code>true</code> if sort in ascending order,
	 *            <code>false</code> if sort in descending order
	 * @param output:
	 *            empty output stream instance
	 * @return
	 * @throws IOException
	 */
	public static GenericOutputStreamInterface merge(ArrayList<GenericInputStreamInterface> inputStreamArray,
			final boolean isAscending, String outputFile, ImplementationEnum outputImplementation, Integer bufferSize, Integer memCapacity) throws IOException {
		// initialize comparator for priority queue
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if ((isAscending && o1 > o2) || (!isAscending && o1 < o2)) {
					return 1;
				} else {
					if ((isAscending && o1 < o2) || (!isAscending && o1 > o2)) {
						return -1;
					} else
						return 0;
				}

			}
		};
		
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10, (Comparator<Integer>)comparator);
		// push elements into priority queue
		// get each element from each stream then merge
		
		// this list is used to store streams which has reached end
		List<Integer> endOfStreamList = new ArrayList<Integer>();
		while(endOfStreamList.size() < inputStreamArray.size()){			
			for (int i = 0; i < inputStreamArray.size(); i++) {
				// if the stream is not end, continue to read next element
				// if stream has reach end, skip this stream
				if(!endOfStreamList.contains(i)){
					GenericInputStreamInterface stream = inputStreamArray.get(i);

					Integer intTemp = stream.readNext();
					if(intTemp != null){					
						queue.add(intTemp);
					}
					if (stream.isEndOfStream()) {
						// close stream after reading
						stream.close();
						endOfStreamList.add(i);
					}
				}
			}
		}

		// pop element from queue to output stream
		GenericOutputStreamInterface outputStream = createOutputStreamInstance(outputImplementation, bufferSize, memCapacity);
		outputStream.create(outputFile);
		while (!queue.isEmpty()) {
			Integer element = queue.poll();
			outputStream.write(element);
			outputStream.write(" ");
		}
		outputStream.close();
		return outputStream;

	}

	/**
	 * Depend on the implementation user want to use, create stream instance
	 * @param implementationEnum
	 * @return {@link GenericInputStreamInterface}
	 */
	public static GenericInputStreamInterface createInputStreamInstance(ImplementationEnum implementationEnum, Integer bufferSize, Integer memCapacity){
		switch (implementationEnum) {
		case SYSTEM_CALL:
			return new SystemInputStream();
		case BUFFERED:
			return new BufferedInputStream();
		case BUFFERED_WITH_SIZE:
			return new BufferedWithSizeInputStream(bufferSize);
		case MAPPING:
			return new MappingInputStream(memCapacity);
		default:
			return new SystemInputStream();
		}
	}
	
	
	
	/**
	 * Depend on the implementation user want to use, create stream instance
	 * @param implementationEnum
	 * @return {@link GenericOutputStreamInterface}
	 */
	public static GenericOutputStreamInterface createOutputStreamInstance(ImplementationEnum implementationEnum, Integer bufferSize, Integer memCapacity){
		switch (implementationEnum) {
		case SYSTEM_CALL:
			return new SystemOutputStream();
		case BUFFERED:
			return new BufferedOutputStream();
		case BUFFERED_WITH_SIZE:
			return new BufferedWithSizeOutputStream(bufferSize);
		case MAPPING:
			return new MappingOutputStream(memCapacity);
		default:
			return new SystemOutputStream();
		}
	}

	
	/**
	 * Merge sort algorithm
	 * @param inputFile source file path
	 * @param availableMemory available memory used for algorithm (M)
	 * @param streamCount number of streams to merge in one pass (d)
	 * @param outputFile result file path
	 * @param isAscendingOrder sort order of numbers in result file
	 * @param readImplementation implementation number of input stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param readBufferSize if input stream is buffered stream with size, then specify buffer size
	 * @param readMemCapacity if input stream is mapping stream, then specify memory capacity used for mapping/unmapping element
	 * @param writeImplementation implementation number of output stream (1-SystemCall, 2-Buffer, 3-BufferWithSize, 4-Mapping)
	 * @param writeBufferSize if output stream is buffered stream with size, then specify buffer size
	 * @param writeMemCapacity if output stream is mapping stream then specify memory capacity used for mapping/unmapping element
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void mergeSort(String inputFile, Integer availableMemory, Integer streamCount, String outputFile, boolean isAscendingOrder, ImplementationEnum readImplementation, Integer readBufferSize, Integer readMemCapacity, ImplementationEnum writeImplementation, Integer writeBufferSize, Integer writeMemCapacity)
			throws FileNotFoundException, IOException {

		// 1.Read single and split into (size of file)/(memCapacity) arrays
		GenericInputStreamInterface inputStream = createInputStreamInstance(readImplementation, readBufferSize, readMemCapacity);
		inputStream.open(inputFile);
		ArrayList<String> referenceList = new ArrayList<String>();

		ArrayList<ArrayList<Integer>> intMatrix = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> intList = new ArrayList<Integer>();
		while (!inputStream.isEndOfStream()) {
			Integer element = inputStream.readNext();
			if(element != null){				
				intList.add(element);
			}
			// check size of each list at most memCapacity
			if (intList.size() == availableMemory) {
				intMatrix.add(intList);
				intList = new ArrayList<Integer>();
			}
		}

		if (intList.size() < availableMemory) {
			intMatrix.add(intList);
		}

		// 2. Sort each array and put to each output stream
		File tempDir = new File(TEMP_DIRECTORY_FOR_MERGE_SORT);
		if(!tempDir.exists()){
			tempDir.mkdir();
		}
		for (int i = 0; i < intMatrix.size(); i++) {
			ArrayList<Integer> temp = intMatrix.get(i);
			Collections.sort(temp);

			GenericOutputStreamInterface outputStream = createOutputStreamInstance(writeImplementation, writeBufferSize, writeMemCapacity);
			String txtFilePath = (TEMP_DIRECTORY_FOR_MERGE_SORT + BACK_FLASH_WINDOWS + TEMP_FILE_NAME_PREFIX + i);
			outputStream.create(txtFilePath);
			for (int j = 0; j < temp.size(); j++) {
				outputStream.write(temp.get(j));
				outputStream.write(" ");
			}
			outputStream.close();
			referenceList.add(txtFilePath);
		}

		// 3.Store reference of output stream

		// 4.Loop through references of output stream
		// 4.1 Get streamCount first streams
		// 4.2 Merge those streams into one single stream
		// 4.3 Put result at the end
		// 4.4 Loop until one single stream left
		int refSize = referenceList.size();
		while (referenceList.size() > 1) {
			refSize++;
			int toIndex = streamCount;
			if (referenceList.size() < streamCount) {
				toIndex = referenceList.size();
			}

			// 4.1 Get streamCount first streams
			List<String> processedRef = referenceList.subList(0, toIndex);
			ArrayList<GenericInputStreamInterface> streamList = new ArrayList<GenericInputStreamInterface>();
			// create arraylist of input streams

			while (processedRef.size() > 0) {
				GenericInputStreamInterface tempStream = createInputStreamInstance(readImplementation, readBufferSize, readMemCapacity);
				tempStream.open(processedRef.get(0));
				streamList.add(tempStream);
				processedRef.remove(0);
			}

			// 4.2 Merge those streams into one single stream
			String txtFilePath = (TEMP_DIRECTORY_FOR_MERGE_SORT + BACK_FLASH_WINDOWS + TEMP_FILE_NAME_PREFIX + refSize);
			Utility.merge(streamList, isAscendingOrder, txtFilePath, writeImplementation, writeBufferSize, writeMemCapacity);

			// remember to close stream after processing
			for (int m = 0; m < streamList.size(); m++) {
				streamList.get(m).close();
			}
			// 4.3 Put result at the end
			referenceList.add(txtFilePath);
		}

		// Copy result file from tempLab directory to another place
		GenericOutputStreamInterface outputStream = createOutputStreamInstance(writeImplementation, writeBufferSize, writeMemCapacity);
		outputStream.create(outputFile);
		GenericInputStreamInterface tempResStream = createInputStreamInstance(readImplementation, readBufferSize, readMemCapacity);
		tempResStream.open(referenceList.get(0));
		while (!tempResStream.isEndOfStream()) {
			Integer temp = tempResStream.readNext();
			outputStream.write(temp);
			outputStream.write(" ");
		}

		// Close stream
		tempResStream.close();
		outputStream.close();

		// Delete temporary directory
//		Utility.cleanUpDirectory(tempDirectory);

	}

//	private static void cleanUpDirectory(String directoryPath) {
//		File file = new File(directoryPath);
//		if (file.isDirectory()) {
//
//			// directory is empty, then delete it
//			if (file.list().length != 0) {
//				// list all the directory contents
//				String files[] = file.list();
//
//				for (String temp : files) {
//					// construct the file structure
//					File fileDelete = new File(file, temp);
//					fileDelete.delete();
//				}
//
//			}
//
//		}
//	}

	public static void generateRandomFile(Integer numberPerFile, Integer numberOfFiles, String outputDirectory, String filePrefix, String fileSuffix) throws IOException {
		Random random=new Random();
		for(int i =0; i< numberOfFiles; i++){
			String filePath = outputDirectory + BACK_FLASH_WINDOWS + filePrefix + i + "." + fileSuffix;
			GenericOutputStreamInterface outputStream = new SystemOutputStream();
			outputStream.create(filePath);
			for(int j = 0; j< numberPerFile; j++){				
				int randomNumber=(random.nextInt(MAXIMUM_RANDOM_NUMBER + UNSIGNED_MINIMUM_RANDOM_NUMBER) - UNSIGNED_MINIMUM_RANDOM_NUMBER);
				outputStream.write(randomNumber);
				outputStream.write(" ");
			}
			outputStream.close();
		}
	}
}
