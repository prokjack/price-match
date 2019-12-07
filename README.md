# price-match
Demo console application to find match, closest to given price in list of items


## Preconditions

Need to be installed:

[Java 11 sdk](https://openjdk.java.net/install/)  
Was tested with java 11, but should run also with java 8. For this need to change java.version property in [pom.xml](pom.xml))   
  
[Maven](https://maven.apache.org/download.cgi)

### How to use

After cloning:
- run `mvn clean package` inside clonned folder
- run `java -jar target/findpair-1.0-SNAPSHOT.jar`  
### Parameters  
There are two required params:
  
{1} - path to file (if same foler - just `prices.txt`, if other folder - full path `/home/Developer/findpair/prices.txt`)  
{2} - price as integer  

And one optional:  
{3} - depth, how many items should match given price (default value = 2)  

`java -jar target/findpair-1.0-SNAPSHOT.jar prices.txt 2500 2`

### Example result
```
INFO: Candy Bar 500, Earmuffs 2000
```
