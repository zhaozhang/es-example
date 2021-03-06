ElasticSearch Example with Spark
=====
This is the instruction of using Spark to ingest indices to ElasticSearch on TACC Wrangler.

### 1. Uplaod the data file to HDFS

Make sure you are on a compute node within an HDFS reservation. Run

   $ hadoop fs -put data/nasa_19950801_50.csv ./nasa_19950801.csv

This small input file only contains 50 lines of apache logs.

### 1. Build the application
Make sure you are on a compute node, go to the directory of this example, run

   $ mvn package

This will build the application, the input data path is hard-coded in the source code.

### 2. Submit the application

In the same directory, run

   $ spark-submit --jars /home/00946/zzhang/.m2/repository/org/elasticsearch/elasticsearch-spark-13_2.10/5.0.0-beta1/elasticsearch-spark-13_2.10-5.0.0-beta1.jar --class "ESExample" --master yarn --deploy-mode client target/ESExample-1.0-SNAPSHOT.jar

Please note: the elasticsearch-spark-13_2.10-5.0.0-beta1.jar is automatically downloaded to your home directory if the compilation succeeded. Change the path to point to your local copy.

### 3.Play With Data

Open Kibana web interface, load index "nasa_complete*", then you will see the data is imported, and you can play with it.
