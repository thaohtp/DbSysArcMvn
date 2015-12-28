package group11.test;

import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import group11.stream.input.GenericInputStreamInterface;
import group11.stream.input.SystemInputStream;
import group11.stream.output.GenericOutputStreamInterface;
import group11.stream.output.MappingOutputStream;
import group11.stream.output.SystemOutputStream;
import group11.util.ImplementationEnum;
import group11.util.Utility;

public class Test {
	
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {

		/*
		 * String file_ruta1 = "D:\\Test.txt"; GenericInputStreamInterface input
		 * = new BufferedWithSizeInputStream(); System.out.println(
		 * "Total execution time: " + TimerTest.readTest(input, file_ruta1) +
		 * " ms"); input.close();
		 * 
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

		// String file_ruta6 = "D:\\Test.txt";
		// GenericInputStreamInterface input6 = new MappingInputStream();
		// System.out.println("Total execution time: " +
		// TimerTest.readTest(input6, file_ruta6) + " ms");
		// /*String file_ruta4 = "C:\\test2.txt";
		// SystemInputStream SIS = new SystemInputStream();
		// SIS.open(file_ruta4);
		// SIS.fread();
		// */

		// BELOW CODE IS USED FOR TEST MULTI-WAY MERGE

//		 String file1 = "D:\\test.txt";
//		 String file2 = "D:\\test2.txt";
//		 GenericInputStreamInterface istream1 = new SystemInputStream();
//		 istream1.open(file1);
//		 GenericInputStreamInterface istream2 = new SystemInputStream();
//		 istream2.open(file2);
//		
//		
//		 GenericOutputStreamInterface ostream = new SystemOutputStream();
//		 String output = "D:\\output.txt";
//		 ostream.create(output);
//		
//		 ArrayList<GenericInputStreamInterface> isArray = new ArrayList<GenericInputStreamInterface>();
//		 isArray.add(istream1);
//		 isArray.add(istream2);
////		 Utility.merge(isArray, false, ostream);
//		 Utility.merge(isArray, true, output, ImplementationEnum.SYSTEM_CALL, 0, 0);
//		
//		 SystemInputStream testStream = new SystemInputStream();
//		 System.out.println("Test: ");
//		 TestUtility.readTest(testStream,output);

//		Utility.mergeSort("D:\\merge.txt", 3, 2, "D:\\result.txt");
		Utility.mergeSort("D:\\merge.txt", 10, 2, "D:\\result.txt", true, ImplementationEnum.MAPPING, 0, 0, ImplementationEnum.MAPPING, 0, 0);

//		Utility.generateRandomFile(100, 3, "D:\\tempLab\\", "generate", "txt");
//		runGenerateFiles();
//		runReadWriteFileTest();
//		TimerTest.runMergeSortTestCase("D:\\tempLab\\generate0.txt", "D:\\tempLab\\result.txt", "D:\\tempLab\\exec.txt", 30, 3, 1, 0, 0, 1, 0, 0, 10);
//		TestUtility.runMergeSortTestCase("D:\\tempLab\\generate0.txt", "D:\\result.txt", "D:\\tempLab\\exec.txt", true, 10, 3, ImplementationEnum.SYSTEM_CALL, 0, 0, ImplementationEnum.SYSTEM_CALL, 0, 0, 20);
	}

}
