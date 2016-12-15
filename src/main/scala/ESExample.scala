import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.elasticsearch.spark._ 

case class Item(dt: Long, server: String, content: String)

object ESExample {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ESExample");
    conf.set("es.index.auto.create", "true");

    val sc = new SparkContext(conf)

    val lines = sc.textFile("nasa_19950801.csv")

    val r = lines.map( line => {
      val words = line.split(",")
      Item(words(2).trim.toLong, words(0).trim.toString, words(4).trim.toString)
    })

    r.saveToEs("nasa_complete/19950801")
  }
}
