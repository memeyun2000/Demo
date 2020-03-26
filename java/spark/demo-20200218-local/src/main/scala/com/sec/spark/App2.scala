package com.sec.spark

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession


object App2 {
  val SPARK_WAREHOUSE_DIR = "F:\\demo\\Demo\\asset\\spark-warehouse";
  val CSV_FILE_PATH = "F:\\demo\\Demo\\asset\\user-data.csv";
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("spark local")
      .master("local")
      .config("spark.sql.warehouse.dir",SPARK_WAREHOUSE_DIR)
      .getOrCreate()


    spark.sql("show databases").show()


    val userDF = spark.read.format("csv")
      .option("delimiter", ",")
      .option("header", "true")
      .option("quote", "'")
      .option("nullValue", "\\N")
      .option("inferSchema", "true")
      .load(CSV_FILE_PATH);


    userDF.filter("username = 'guoqingyun'").show()

    spark.close()
  }
}