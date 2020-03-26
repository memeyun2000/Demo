package com.sec.spark

import breeze.linalg._
import breeze.plot._
object Draw {
  def main(args: Array[String]): Unit = {
    val a = new DenseVector[Int](1 to 3 toArray)
    val b = new DenseMatrix[Int](3, 3, 1 to 9 toArray)

    val f = Figure()  //在一个对话框下画一个图
    val p = f.subplot(0)
    val x = linspace(0.0, 2.0)

    p += plot(x, x * 2.0)
    p += plot(x, x * 3.0, '.')
    p.xlabel = "x axis"
    p.ylabel = "y axis"

    val p2 = f.subplot(2, 1, 1)   //在同一个对话框下画另一个图，参数分别表示：行、列、子图序号
    val g = breeze.stats.distributions.Gaussian(0, 1) //均值是0，标准差是1
    p2 += hist(g.sample(100000), 100)  //hist表示直方图，100000个样本点，100个条状直方
    p2.title = "A normal distribution"
//    f.saveas("d:\\subplots.png")

    val f2 = Figure()
    f2.subplot(0) += image(DenseMatrix.rand(20, 20))
//    f2.saveas("d:\\image.png")
  }
}

