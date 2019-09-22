# PersonNameRecognizer4j

This is a small library that can be used to check if a string contains a human's name. It uses US Census data from 1880 - 2014 for it's dataset which is a fair representation of common names from around the world.

**Author**

* Shikhir Singh

**Dependencies**

* Java 8+ 

**How to Install**

Maven - be sure to check for latest version in Maven:

```
<dependency>
  <groupId>com.shikhir</groupId>
  <artifactId>person-name-recognizer</artifactId>
  <version>1.0.1</version>
</dependency>
```

**Just get me started!**

* Check if Mary is a first name
```
boolean caseSensitive = true; // Mary = yes; MARY = false; mary = false
System.out.println(FirstName.isFirstName("Mary", true)); // true! Mary is a first name!
System.out.println(FirstName.isFirstName("JoHn")); // true! - not tested for case sensitivity
```

**RUNNING EXAMPLES**

* You can find working examples in the test code located at /src/test/java/

**LICENSE**
* Apache 2.0

**Version History**

* 1.0.0 - Initial Release
* 1.0.2 - Classpath bug fix


**Roadmap Features**
* Last Name 