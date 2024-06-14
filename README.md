# MemoryMappedFileJava
Memory-Mapped File to save objects into hard drive to release heap and RAM in Java

In Java, if you have millions of objects in the heap, it causes program to run slow + risk out of memory.

To solve this problem, we can use memory-mapped file. This method create a file channel that allows us to access directly into memory.

Pros: 
- Don't worry anymore about heap or RAM
- Write and read objects directly to/from memory -> very fast

Cons:
- You have to do more work
- You should consider limited writing cycles of hard drive

Here I give 2 examples of how to use memory-mapped file:
- Example of just 2 objects
- Example of millions of objects

Note that in the examples, I create buffer each time writing or reading an object. This use case is popular in practice, because we don't wait for all objects before writing it to file.

Feel free to comment! Stay hungry, stay foolish!
