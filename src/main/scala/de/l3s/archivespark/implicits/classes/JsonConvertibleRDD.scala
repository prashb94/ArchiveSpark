/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Helge Holzmann (L3S) and Vinay Goel (Internet Archive)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 */

package de.l3s.archivespark.implicits.classes

import de.l3s.archivespark.utils.JsonConvertible
import org.apache.hadoop.io.compress.GzipCodec
import org.apache.spark.rdd.RDD

class JsonConvertibleRDD[Record <: JsonConvertible](rdd: RDD[Record]) {
  def toJson = rdd.map(r => r.toJson)

  def toJsonStrings = rdd.map(r => r.toJsonString)

  def saveAsJson(path: String) = if (path.endsWith(".gz")) toJsonStrings.saveAsTextFile(path, classOf[GzipCodec]) else toJsonStrings.saveAsTextFile(path)
}