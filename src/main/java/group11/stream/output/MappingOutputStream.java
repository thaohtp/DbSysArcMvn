package group11.stream.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappingOutputStream implements GenericOutputStreamInterface {
	IntBuffer writer;
	MappedByteBuffer stream;
	FileChannel fc;
	// static int length = 0x8FFFFFF; //128MB
	// MIN_VALUE = 4
	Integer length = 5;
	String writec;
	Integer position = 0;

	public MappingOutputStream() {

	}

	public MappingOutputStream(Integer capacity) {
		this.length = capacity;
	}

	@SuppressWarnings("resource")
	public void create(String filePath) throws IOException {
		File file = null;
		try {
			// create file output stream
			file = new File(filePath);
			// //Delete the file; we will create a new file
			file.delete();
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
			// This if condition is used to unmap element when stream is full
			// because sometimes even hasRemaining is true
			// but the left capacity is only 1 and the number requires 2-3 position -> lead to BufferOverFlow
			// so we need to ensure left capacity is always >= 3
			if (stream.capacity() - stream.position() < 3) {
				position += stream.position();
				stream =fc.map(FileChannel.MapMode.READ_WRITE, position, length);
			}
			writec = Character.toString(c);
			stream.put(writec.getBytes());
			System.out.println("X " + stream.position() + "-" + position + "-" + stream.capacity());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void write(String data) throws IOException {
		
		try {
			for (char c : data.toCharArray()) {
				// This if condition is used to unmap element when stream is full
				// because sometimes even hasRemaining is true
				// but the left capacity is only 1 and the number requires 2-3 position -> lead to BufferOverFlow
				// so we need to ensure left capacity is always >= 3
				if (stream.capacity() -stream.position() < 3) {
					position += stream.position();
					stream =fc.map(FileChannel.MapMode.READ_WRITE, position, length);
				}
				writec = Character.toString(c);
				stream.put(writec.getBytes());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void write(Integer data) throws IOException {
		if(data != null){			
			this.write(data.toString());
		}
		/*try {
			// This if condition is used to unmap element when stream is full
			// because sometimes even hasRemaining is true
			// but the left capacity is only 1 and the number requires 2-3 position -> lead to BufferOverFlow
			// so we need to ensure left capacity is always >= 3
			if (stream.capacity() -stream.position() < 3) {
				position += stream.position();
				stream =fc.map(FileChannel.MapMode.READ_WRITE, position, length);
			}
			writec = Long.toString(data);
			stream.put(writec.getBytes());
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		*/
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
