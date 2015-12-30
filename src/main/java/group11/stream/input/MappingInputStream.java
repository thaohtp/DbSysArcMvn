package group11.stream.input;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappingInputStream implements GenericInputStreamInterface{
	
	//This variable will store the current position in file. 
	Integer positioninfile = 0; //Create an array of Integers
	FileChannel fc;
	MappedByteBuffer mb;
	Integer length=20; //How much of the file want to be mapped?
	
	public MappingInputStream() {
	}
	public MappingInputStream(Integer capacity){
		this.length = capacity;
	}
	
	@SuppressWarnings("resource")
	public void open(String filepath) throws IOException{
		File file =new File(filepath);
		fc = new RandomAccessFile(file, "rw").getChannel(); 
		mb =  fc.map(
			        FileChannel.MapMode.READ_ONLY, 0, Math.min(fc.size(),length));
	}
	
	public Integer readNext() throws IOException{
		
		byte nextChar=0;
		StringBuffer content = new StringBuffer("");
		//Reads character by character		
		while (mb.hasRemaining() && ((char)nextChar) != ' '){
			nextChar = mb.get();
			positioninfile++;	//Saves the position in the array	
			content.append(((char)nextChar));
			if(!mb.hasRemaining()){		
				mb=fc.map(FileChannel.MapMode.READ_ONLY, positioninfile,
						Math.min(fc.size() - positioninfile, length));	
			}
		} 
		
		if(content.toString().trim().isEmpty())
		{
			return null;
		}
		return (int) Long.parseLong(content.toString().trim());
												
		}
		
		
	public void close() throws IOException {		
		mb.clear();
		fc.close();
}

	@Override
	public boolean isEndOfStream() throws IOException {
		
		if(mb.hasRemaining() ==true){
				return false;
			}
			else{
				return true;
			}
			
		}
		
	
}		
		
	