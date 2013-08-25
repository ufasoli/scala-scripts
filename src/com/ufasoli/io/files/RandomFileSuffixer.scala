package com.ufasoli.io.files

import java.io.File
import scala.util.Random


object RandomFileSuffixer {
  /**
   *
   * User: ufasoli
   * Date: 25/08/13
   * Time: 11:14
   * Project : scala-scripts
   */

  def main(args: Array[String]) {


    var basePath = System.getProperty("user.dir")

    // override default basePath with the first argument
    if (!args.isEmpty) {
      basePath = args(0)
    }



    var proceed = false

    // before executing the renaming script ask for confirmation from the user
    while (!proceed) {
      println(s"The files in folder $basePath will be processed")
      println("Ok to proceed?")
      proceed = io.Source.stdin.getLines().contains("y")

    }

    // shuffle all the files in from the base path but exclude the folders
    val files = Random.shuffle(new File(basePath).listFiles().filter(!_.isDirectory).toList)


    for (f <- files) {

      // generate the final name for the file
      val finalName = prefixFile(removePrefix("_", f.getName), basePath)
      f.renameTo(new File(finalName))
    }


  }

  /**
   * Removes the 'prefix' part of a string.
   * In other words it will return everything in the string right from
   * the prefix
   * @param prefix the reference prefix
   * @param fileName the fileName to process
   * @return everything in the fileName right of the prefix
   */
  def removePrefix(prefix: String, fileName: String): String = {

    if (fileName.contains(prefix)) {
      fileName.substring(fileName.indexOf(prefix))
    }
    else {
      fileName
    }

  }


  /**
   * Adds a random prefix(Random.nextInt() to a given file.
   * the prefix and the original filename are
   * separated using "_"
   * @param fileName the original name of the file
   * @param path     where the file should be stored
   * @return a prefixed file
   * @see Random
   *
   */
  def prefixFile(fileName: String, path: String): String = {

    s"$path/${Random.nextInt()}_$fileName"
  }


}