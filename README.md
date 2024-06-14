# MemoryMappedFileJava
Memory-Mapped File to save objects into hard drive to release heap and RAM in Java

In Java, if you have millions of objects in the heap, it causes program to run slow + risk out of memory.

To solve this problem, we can use memory-mapped file. This method create a file channel that allows us to access directly into memory (hard drive).

Pros: 
- Don't worry anymore about heap or RAM
- Write and read objects directly to memory -> very fast

Cons:
- You have to do more work
- Limited writing cycles of hard drive
