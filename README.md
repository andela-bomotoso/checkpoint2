# checkpoint2
Git Repository for checkpoint2

This project uses the concept of Multithreading. Two major tasks were achieved in this project;

1. Read a value pair file at once, store the file in a buffer and pass it at once to the database
2. Read a value pair file line by line using a thread, another thread writes the parsed line into a database, while a third thread logs the threads' activities


### Description of Packages and Classes used

**Db Package**
The db package contains clases that manage activities related to a database. It has the following classes;

1. Database Manager: This class manages connection to the database, creation of database and table creation.
2. DbWriter: This class manages data insertion into the database table.

**Parser Package**
The parser package contains clases that manage file parsing activities. It has the following classes;

1. AttributeValueFile: This class depicts an actual attribute value file like the one used in this project.
2. KeyValuePair: This class is a data structure that works easily with an attribute value file
3. FileParser: This class handles the file parsing activity

**Thread Package**
The thread package contains classes that manage thread activities. It has the following classes;

1. FileParserThread: This class depicts how a thread can read an attribute value file
2. DbWriterThread: This class depicts how a thread can write from a buffer to a database
3. LogWriterThread: This class depicts how FileParserThread and DbWriterThread activities can be logged.
4. Buffer: This is a shared location accessible to the FileParserThread and the DbWriterThread. The FileParser passes writes to the buffe and the Dbwriter reads from the buffer before writing into the database
5. LogBuffer: This is a shared loation between the FileParseThread, DbWriterThread and the LogWriterThread. FileParserThread and DbWriterThread write into the log buffer and the LogWriterThread reads from the logbuffer and writes to a text file




