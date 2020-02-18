package com.sec.spark;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Hello world!
 *
 */
public class App {

    private final static String SPARK_WAREHOUSE_DIR = "F:\\demo\\Demo\\asset\\spark-warehouse";
    private final static String CSV_FILE_PATH = "F:\\demo\\Demo\\asset\\user-data.csv";

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SparkSession spark = SparkSession
            .builder()
            .appName("local spark")
            .master("local")
            .config("spark.sql.warehouse.dir", SPARK_WAREHOUSE_DIR)
            .getOrCreate();


        spark.sql("show databases").show();
        spark.sql("show tables").show();
        Dataset<Row> ds = spark.read().format("csv")
            .option("delimiter", ",")
            .option("header", "true")
            .option("quote", "'")
            .option("nullValue", "\\N")
            .option("inferSchema", "true")
            .load(CSV_FILE_PATH);

        ds.show();
        ds.createOrReplaceTempView("tmp_user_data");

        spark.sql("create table user_data as select * from tmp_user_data");
        
        spark.close();
    }
}
