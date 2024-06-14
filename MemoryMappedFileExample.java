import MemoryMappedFile.IndexEntry;

public class MemoryMappedFileExample {

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
            Person person1 = new Person("John", 30);
            Person person2 = new Person("Adam", 40);
            
            // File path for the memory-mapped file
            String filePath = "person.dat";
            
            // Create memory-mapped file object
            MemoryMappedFile memoryMappedFile = new MemoryMappedFile(filePath);
            
            // create memory-mapped fiel
            memoryMappedFile.createMemoryMappedFile();

            // Write the JSON string to a memory-mapped file
            IndexEntry indexEntryOfPerson1 = memoryMappedFile.writePacketDataHolderToMemory(person1);
            IndexEntry indexEntryOfPerson2 = memoryMappedFile.writePacketDataHolderToMemory(person2);
            System.out.println("Last writePosition: " + memoryMappedFile.getWritePosition());

            // Read the JSON string back from the memory-mapped file
            if (indexEntryOfPerson1 != null) {
                Person person1FromFile = memoryMappedFile.readPacketDataHolderFromMemory(indexEntryOfPerson1, Person.class);
                if (person1FromFile != null) {
                    System.out.println(person1FromFile.toString());
                }
            }
            
            if (indexEntryOfPerson2 != null) {
                Person person2FromFile = memoryMappedFile.readPacketDataHolderFromMemory(indexEntryOfPerson2, Person.class);
                if (person2FromFile != null) {
                    System.out.println(person2FromFile.toString());
                }
            }

            // close memory-mapped file
            memoryMappedFile.closeMemoryMappedFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
