import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import com.google.gson.Gson;

public class MemoryMappedFile {
    
    private String filePath;
    private RandomAccessFile randomAccessFile = null;
    private FileChannel fileChannel = null;
    private Gson gson = new Gson();
    private long writePosition = 0;
    
    
    public MemoryMappedFile(String filePath) {
        this.filePath = filePath;
    }
    
    
    public class IndexEntry {
        private long position;
        private int length;

        IndexEntry(long position, int length) {
            this.position = position;
            this.length = length;
        }
    }
    
    
    public void createMemoryMappedFile() {
        try {
            randomAccessFile = new RandomAccessFile(filePath, "rw");
            fileChannel = randomAccessFile.getChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void closeMemoryMappedFile() {
        try {
            fileChannel.close(); // Close the FileChannel
            randomAccessFile.close(); // Close the RandomAccessFile
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public <T> IndexEntry writePacketDataHolderToMemory(T obj) {
        try {
            // Serialize to JSON with gson
            String jsonString = gson.toJson(obj);
            
            // Write the JSON string to a memory-mapped file
            byte[] bytes = jsonString.getBytes();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 
                    writePosition, bytes.length);
            mappedByteBuffer.put(bytes);

            IndexEntry indexEntry = new IndexEntry(writePosition, bytes.length);
            writePosition += bytes.length;
            
            return indexEntry;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public <T> T readPacketDataHolderFromMemory(
            IndexEntry indexEntry, Class<T> clazz) {
        try {           
            // read JSON string from memory-mapped file
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 
                    indexEntry.position, indexEntry.length);
    
            byte[] bytes = new byte[indexEntry.length];
            mappedByteBuffer.get(bytes);

            // Deserialize from JSON
            T obj = gson.fromJson(new String(bytes), clazz);
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public long getWritePosition() {
        return this.writePosition;
    }

}
