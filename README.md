# PersonNameRecognizer4j

This is a small library that can be used to check if a string contains a human's name. It uses [baby names for the USA Social Security card](https://catalog.data.gov/dataset/baby-names-from-social-security-card-applications-national-level-data) applications from from 1880 - 2018 for it's dataset. Given the ethnic diversity in the USA, there are a substantial amount of non-western names in this list (although they all use the English alphabet). 

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
  <version>1.2.0</version>
</dependency>
```

**Just get me started!**

* Check if Mary is a first name
```
System.out.println(FirstName.isFirstName("JoHn")); // true! - not tested for case sensitivity

boolean caseSensitive = true; // Mary = yes; MARY = false; mary = false
System.out.println(FirstName.isFirstName("Mary", true)); // true! Mary is a first name!

FirstName.close() // frees up memory by closing the firstname dataset - calling isFirstName again will open it again
```

**RUNNING EXAMPLES**

* You can find working examples in the test code located at /src/test/java/

**LICENSE**
* Apache 2.0

**Version History**

* 1.0.0 - Initial Release
* 1.0.2 - Classpath bug fix
* 1.1.2 - removed human names that are common words (eg "Will", "See", "Tiny") 
* 1.1.3 - removed human names that are more common words - ("Jan", "Summer", "Hope", "Code", etc)
* 1.1.6 - Updated for more recent version of dataset avail, added close function  
* 1.1.8 - minor fixes
* 1.2.0 - added new function FirstName.findNamesInString to find a list of first names in a string

**Roadmap Features**
* Last Name - dataset surname.csv is not yet used 
