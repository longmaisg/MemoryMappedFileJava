import java.util.ArrayList;
import java.util.List;

import MemoryMappedFile.IndexEntry;

public class MemoryMappedFileExamples {

    public static class Person {
        private String name;
        private int age;

        public Person() {
            // Default constructor is needed for deserialization
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + '}';
        }
    }
    
    
    public static void main(String[] args) {
        try {
            // Create example objects
            List<Person> persons = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                persons.add(new Person(Integer.toString(i), i));
            }

            // File path for the memory-mapped file
            String filePath = "person.dat";
            
            // Create memory-mapped file object
            MemoryMappedFile memoryMappedFile = new MemoryMappedFile(filePath);
            
            // create memory-mapped fiel
            memoryMappedFile.createMemoryMappedFile();

            // Write the JSON string to a memory-mapped file
            System.out.println("Start writing objects to memory");
            List<IndexEntry> indexEntries = new ArrayList<>();
            int counter = 0;
            long startTime = System.currentTimeMillis();
            for (Person person : persons) {
                /*
                 * A buffer is created for each object.
                 * This code works best when each time we generate an object,
                 * we want to save that object immediately and release memory.
                 */
                IndexEntry indexEntry = memoryMappedFile.writePacketDataHolderToMemory(person);
                indexEntries.add(indexEntry);
                if (indexEntry == null) {
                    counter++;
                }
            }
            System.out.println("Spent time on writing (ms): " + (System.currentTimeMillis() - startTime));
            System.out.println("Last writePosition: " + memoryMappedFile.getWritePosition());
            if (counter > 0) {
                System.out.println("Fail writing counter: " + counter);
            }

            // Read the JSON string back from the memory-mapped file
            List<Person> personsFromFile = new ArrayList<>();
            counter = 0;
            startTime = System.currentTimeMillis();
            for (IndexEntry indexEntry : indexEntries) {
                /*
                 * Again, a buffer is created for each object.
                 * This code works best when we want only one object each time.
                 */
                if (indexEntry != null) {
                    Person person = memoryMappedFile.readPacketDataHolderFromMemory(indexEntry, Person.class);
                    personsFromFile.add(person);
                    if (person == null) {
                        counter++;
                    }
                } else {
                    personsFromFile.add(null);
                }
            }
            System.out.println("Spent time on reading (ms): " + (System.currentTimeMillis() - startTime));
            if (counter > 0) {
                System.out.println("Fail reading counter: " + counter);
            }

            // close memory-mapped file
            memoryMappedFile.closeMemoryMappedFile();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
