import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.spark.input.PortableDataStream
import scala.util.Try
import java.nio.charset._

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import org.elasticsearch.spark._ 

case class Item(content: String)

object ESExample {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ESExample");
    conf.set("es.index.auto.create", "true");
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)

    val src = args(0)
    val dst = args(1)
    val rdd = sc.textFile(src)
    rdd.saveJsonToEs(dst)
  }


/*  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("ESExample");
    conf.set("es.index.auto.create", "true");

    val sc = new SparkContext(conf)

    val src = args(0)

    val t = sc.binaryFiles(src).flatMap(x => extractFiles(x._2).toOption).flatMap(_.map(decode()))
    //val tt = t.coalesce(2, shuffle=true)

    //val files = sc.wholeTextFiles("health-websites/", 32)

    val r = t.map(f => Item(f.trim.toString))

    r.saveToEs("480-health-websites/480")
  }

  def extractFiles(ps: PortableDataStream, n: Int = 1024) = Try {
    val tar = new TarArchiveInputStream(ps.open)
    Stream.continually(Option(tar.getNextTarEntry))
      // Read until next exntry is null
      .takeWhile(_.isDefined)
      // flatten
      .flatMap(x => x)
      // Drop directories
      .filter(!_.isDirectory)
      .map(e => {
        Stream.continually {
          // Read n bytes
          val buffer = Array.fill[Byte](n)(-1)
          val i = tar.read(buffer, 0, n)
          (i, buffer.take(i))}
        // Take as long as we've read something
        .takeWhile(_._1 > 0)
        .map(_._2)
        .flatten
        .toArray})
      .toArray
  }

  def decode(charset: Charset = StandardCharsets.UTF_8)(bytes: Array[Byte]) = 
    new String(bytes, StandardCharsets.UTF_8)
*/
}
