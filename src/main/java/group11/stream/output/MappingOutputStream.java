package group11.stream.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import group11.stream.output.GenericOutputStreamInterface;



public class MappingOutputStream implements GenericOutputStreamInterface {
	IntBuffer writer;
	MappedByteBuffer stream;
	FileChannel fc;
//	static int length = 0x8FFFFFF; //128MB
	Integer length=200;
	String writec;
	@SuppressWarnings("resource")
	public void create(String filePath) throws IOException {
		File file = null;
		try {
			// create file output stream
			file = new File(filePath);
//			//Delete the file; we will create a new file
//	        file.delete();
			// create a new OutputStreamWriter
			fc = new RandomAccessFile(file, "rw").getChannel();
			stream = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);

		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
	}

	public void write(char c) throws IOException {
		try {
			
			writec = Character.toString(c);
			stream.put(writec.getBytes());
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void write(String data) throws IOException {
		try {
			for (char c : data.toCharArray()) {
				writec = Character.toString(c);
				stream.put(writec.getBytes());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void write(Integer data) throws IOException {
		try {
			writec = Integer.toString(data);
			stream.put(writec.getBytes());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void close() throws FileNotFoundException {
		// flush the stream
		 try {
			 fc.close();
		 } catch (IOException e) {
		
		 e.printStackTrace();
		 }

	}
	
}
