package group11.test;

import java.io.IOException;

import group11.stream.input.GenericInputStreamInterface;
import group11.stream.input.MappingInputStream;
import group11.stream.output.GenericOutputStreamInterface;
import group11.stream.output.MappingOutputStream;
import group11.util.ImplementationEnum;

public class Test {
	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
//		//Creates an array of InputStreams
//		ArrayList<GenericInputStreamInterface> listofstreams = new ArrayList<GenericInputStreamInterface>(); 
//		//Create an array of OutputStreams
//	    ArrayList<GenericOutputStreamInterface> listofoutstreams = new ArrayList<GenericOutputStreamInterface>(); 
//	    
//	    
//	    List<Integer> endOfStreamList = new ArrayList<Integer>();
//	    Integer N = 50;
//	    Integer k = 3;
//	    for (int i=0;i<k;i++){//To create the k input/output streams
//	     GenericInputStreamInterface input = new SystemInputStream(); 
//	     GenericOutputStreamInterface output = new SystemOutputStream();
//	     String filePath = "D:\\tempLab\\result"+i+".txt";
//	     listofstreams.add(input);
//	     listofoutstreams.add(output);
//	     listofoutstreams.get(i).create(filePath);
//	     String filePath2 = "D:\\tempLab\\generate"+i+".txt"; 
// 		listofstreams.get(i).open(filePath2); 
//	    }
//	    for (int j=0;j<N;j++){ //number of time in this case "N" times. 
//	    while(endOfStreamList.size()<listofstreams.size()){
//	    	for (int i=0;i<listofstreams.size();i++){
//	    		
//	    		// if the stream is not end, continue to read next element
//				// if stream has reach end, skip this stream
//	    		
//	    		if(!endOfStreamList.contains(i)){
//	    			Integer intTemp = listofstreams.get(i).readNext();
//	    			//System.out.println("Esto es lo que lee"+intTemp);
//	    			if(intTemp != null){
//	    			listofoutstreams.get(i).write(intTemp);
//	    			//System.out.println("Esto es lo que escribe"+intTemp);
//	    			listofoutstreams.get(i).write(" ");
//	    			}
//	    			if(listofstreams.get(i).isEndOfStream()){
//	    				listofstreams.get(i).close();
//	    				listofoutstreams.get(i).close();
//	    				endOfStreamList.add(i);
//	    			}
//	    		}
//	    		
//	    	}
//	    }
//	    }
	    
//	    // Test manuallyO
	    String readingDir = "D:\\tempLab\\read\\";
	    String copiedDir = "D:\\tempLab\\write";
	    // file to store execution time
	    String execFilePath = "D:\\tempLab\\executionTime.txt";
	    Integer nbRunTimes = 20;
	    Integer mapCapacity = 100;
	    Integer bufferSize = 0;

	    ImplementationEnum readImplemetation = ImplementationEnum.SYSTEM_CALL;
	    ImplementationEnum writeImplementation = ImplementationEnum.SYSTEM_CALL;
	    TestUtility.runReadWriteKParallelFilesTestCase(readingDir, copiedDir, execFilePath, readImplemetation, bufferSize, mapCapacity, writeImplementation, bufferSize, mapCapacity, nbRunTimes);
	    
	    // if you test merge sort, use TestUtility.runMergeSortTestCase
	    String readingFile = "D:\\tempLab\\read\\generate1.txt";
	    String writeFile = "D:\\tempLab\\write\\result.txt";
	    boolean isAscending = true;
	    Integer availableMem = 100;
	    Integer numberOfStreamPerPass = 3;

	    ImplementationEnum readImplemetation2 = ImplementationEnum.MAPPING;
	    ImplementationEnum writeImplementation2 = ImplementationEnum.MAPPING;
	    TestUtility.runMergeSortTestCase(readingFile, writeFile, execFilePath, isAscending, availableMem, numberOfStreamPerPass, readImplemetation2, bufferSize, mapCapacity, writeImplementation2, bufferSize, mapCapacity, nbRunTimes);
		
		/*ArrayList<GenericInputStreamInterface> listofstreams = new ArrayList<GenericInputStreamInterface>(); //Creates an array of InputStreams
	    ArrayList<GenericOutputStreamInterface> listofoutstreams = new ArrayList<GenericOutputStreamInterface>(); //Create an array of OutputStreams
		Integer N = 1;
	    Integer k = 2;
	    for (int i=0;i<k;i++){//To create the k input/output streams
	     GenericInputStreamInterface input = new SystemInputStream(); 
	     GenericOutputStreamInterface output = new SystemOutputStream();
	     listofstreams.add(input);
	     listofoutstreams.add(output);
	    }
	    
	    for (int j=0;j<N;j++){ //number of time in this case "N" times.
	    	for (int i=0;i<k;i++){ //Read k stream of k different file
	    
	    		String file_ruta1 = "D:\\Tests\\Step2\\test"+i+".txt"; 
	    		String file_ruta2 = "D:\\Tests\\Step2\\write\\test"+i+".txt"; 
	    		listofstreams.get(i).open(file_ruta1);
	    		listofoutstreams.get(i).create(file_ruta2);
	    			while(!listofstreams.get(i).isEndOfStream()){
	    				int number = listofstreams.get(i).readNext();
	    				//System.out.println(number);
	    				listofoutstreams.get(i).write(number);
	    				listofoutstreams.get(i).write(" ");
	    			}
	      
	      
	    			System.out.println("---File: "+ i);
	    
	    			listofstreams.get(i).close();
	    			listofoutstreams.get(i).close();
	    	}
	   
	    System.out.println("---time: "+ j);
	    }
	    //listofstreams.clear();
	  
	    
	 }
	 */
//		String ruta1="D:\\tempLab\\read\\generate1.txt";
//		TestUtility.runReadWriteSingleTestCase(ruta1, "D:\\result.txt", ImplementationEnum.MAPPING, 0, 100, ImplementationEnum.MAPPING, 0, 100);
//		GenericInputStreamInterface input2	= new MappingInputStream(); 
//		GenericOutputStreamInterface output2 = new MappingOutputStream(100);
//		output2.create("D:\\result.txt");
//		 input2.open(ruta1);
//		  while(!input2.isEndOfStream()){
//			  Integer number = input2.readNext();
//			  if(number != null){
//				  output2.write(number);
//				  output2.write(" ");
//				  System.out.println(number);
//			  }
//			  
//		  }
//		  output2.close();
//		  input2.close();
	
//		String ruta1="D:\\Tests\\Step2\\test9.txt";
//		GenericInputStreamInterface input2	= new MappingInputStream(); 
//		 input2.open(ruta1);
//		  while(!input2.isEndOfStream()){
//			  int number = input2.readNext();
//			  System.out.println(number);
//			  
//		  }
//		  
//		  input2.close();
		
		/*  GenericInputStreamInterface input2	= new SystemInputStream(); 
		 input2.open(file_ruta1);
		  while(!input2.isEndOfStream()){
			  int number = input2.readNext();
			  System.out.println(number);
			  
		  }*/
			
		
		  //input2.close();
		  
		  //String file_ruta2 = "D:\\Tests\\test1.txt"; 
		
		//  input.open(file_ruta2);
		  //while(!input.isEndOfStream()){
			//  int number = input.readNext();
			  //System.out.println(number);
		 // }
		  
		
		  
		 
		  
		  /* 
		 * String file_ruta5 = "D:\\Test.txt"; GenericInputStreamInterface
		 * input2 = new BufferedInputStream(); System.out.println(
		 * "Total execution time: " + TimerTest.readTest(input2, file_ruta1) +
		 * " ms"); input2.close(); /* String file_ruta2 = "D:\\test2.txt";
		 * SystemOutputStream output = new SystemOutputStream();
		 * System.out.println("Total execution time: " +
		 * TimerTest.writeTest(output, file_ruta2, "3 2 1") + " ms");
		 * 
		 * String file_ruta3 = "D:\\test3.txt"; BufferedOutputStream output2 =
		 * new BufferedOutputStream(); System.out.println(
		 * "Total execution time: " + TimerTest.writeTest(output2, file_ruta3,
		 * "3 2 1") + " ms");
		 * 
		 * String file_ruta4 = "D:\\test4.txt"; BufferedOutputStream output3 =
		 * new BufferedOutputStream(); System.out.println(
		 * "Total execution time: " + TimerTest.writeTest(output3, file_ruta4,
		 * "3 2 1") + " ms");
		 */

		
		// /*String file_ruta4 = "C:\\test2.txt";
		// SystemInputStream SIS = new SystemInputStream();
		// SIS.open(file_ruta4);
		// SIS.fread();
		// */

		// BELOW CODE IS USED FOR TEST MULTI-WAY MERGE
/*
		 String file1 = "D:\\test.txt";
		 String file2 = "D:\\test2.txt";
		 GenericInputStreamInterface istream1 = new SystemInputStream();
		 istream1.open(file1);
		 GenericInputStreamInterface istream2 = new SystemInputStream();
		 istream2.open(file2);
		
		
		 GenericOutputStreamInterface ostream = new SystemOutputStream();
		 String output = "D:\\output.txt";
		 ostream.create(output);
		
		 ArrayList<GenericInputStreamInterface> isArray = new ArrayList<GenericInputStreamInterface>();
		 isArray.add(istream1);
		 isArray.add(istream2);
//		 Utility.merge(isArray, false, ostream);
		 Utility.merge(isArray, true, output, ImplementationEnum.SYSTEM_CALL, 0, 0);
		
		 SystemInputStream testStream = new SystemInputStream();
		 System.out.println("Test: ");
		 TestUtility.readTest(testStream,output);

//		Utility.mergeSort("D:\\merge.txt", 3, 2, "D:\\result.txt");
		Utility.mergeSort("D:\\merge.txt", 10, 2, "D:\\result.txt", true, ImplementationEnum.SYSTEM_CALL, 0, 0, ImplementationEnum.SYSTEM_CALL, 0, 0);

//		Utility.generateRandomFile(100, 3, "D:\\tempLab\\", "generate", "txt");
//		runGenerateFiles();
//		runReadWriteFileTest();
//		TimerTest.runMergeSortTestCase("D:\\tempLab\\generate0.txt", "D:\\tempLab\\result.txt", "D:\\tempLab\\exec.txt", 30, 3, 1, 0, 0, 1, 0, 0, 10);
 */


//		TestUtility.runMergeSortTestCase("D:\\merge.txt", "D:\\merge.txt", "D:\\tempLab\\exec.txt", true, 3, 3, ImplementationEnum.SYSTEM_CALL, 0, 0, ImplementationEnum.SYSTEM_CALL, 0, 0, 2);
	}	
}

	
