# MemoryMappedFileJava
Memory-Mapped File to save objects into hard drive to release heap and RAM in Java

In Java, if you have millions of objects in the heap, it causes program to run slow + risk out of memory.

To solve this problem, we can use memory-mapped file. This method create a file channel that allows us to access directly into memory.

## Pros: 
- Don't worry anymore about heap or RAM
- Write and read objects directly to/from memory -> very fast
- Reduce heap -> faster execution

## Cons:
- You have to do more work
- You should consider limited writing cycles of hard drive

Here I give 2 examples of how to use memory-mapped file:
- Example of just 2 objects
- Example of millions of objects

Note that in the examples, I create buffer each time writing or reading an object. This use case is popular in practice, because we don't wait for all objects before writing it to file.

Here is the results on my mac:

- 100.000 objects:
- 
Start writing objects to memory

Spent time on writing (ms): 918  => 1s

Last writePosition: 2777780  => 2.8 MB

Spent time on reading (ms): 1050  => 1s


- 1.000.000 objects:
- 
Start writing objects to memory
Spent time on writing (ms): 26713  => 27s
Last writePosition: 29777780  => 30 MB
Spent time on reading (ms): 11892  => 12s

- 10.000.000 objects:
Start writing objects to memory
Spent time on writing (ms): 112375  => 113s
Last writePosition: 317777780  => 318 MB
Spent time on reading (ms): 115155  => 116s

So write/read 100.000 objects (2.8 MegaBytes) only takes 1s! Even with 10.000.000 objects (318 MB), it takes only 113s for writing and 116s for reading.

Feel free to comment! Stay hungry, stay foolish!
