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

package de.l3s.archivespark.rdd

import de.l3s.archivespark.ResolvedArchiveRecord
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, TaskContext}

abstract class ResolvedArchiveRDD[Base](parent: RDD[Base]) extends RDD[ResolvedArchiveRecord](parent) {
  protected def record(from: Base): ResolvedArchiveRecord

  override def compute(split: Partition, context: TaskContext): Iterator[ResolvedArchiveRecord] = {
    parent.iterator(split, context).filter(r => r != null).map(r => record(r)).filter(r => r != null)
  }

  override protected def getPartitions: Array[Partition] = parent.partitions
}