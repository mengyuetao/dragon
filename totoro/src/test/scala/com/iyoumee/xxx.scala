package progscala2.implicitis {

  package database_api {

    case class InvalidColumnName(name: String)
      extends RuntimeException(s"Invalid column name $name")

    trait Row {
      def getInt(colName: String): Int

      def getDouble(colName: String): Double

      def getText(colName: String): String

    }

  }

  package javadb {
    import database_api._

    case class JRow(rep: Map[String,Any]) extends Row {

      private def get(colName:String):Any = rep.getOrElse(colName,throw InvalidColumnName(colName))
      override def getDouble(colName: String): Double = get(colName).asInstanceOf[Double ]

      override def getInt(colName: String): Int =  get(colName).asInstanceOf[Int ]

      override def getText(colName: String): String =  get(colName).asInstanceOf[String ]
    }

    object JRow {
      def apply(pairs:(String,Any)*): JRow = new JRow(Map(pairs : _*))
    }
  }

  package scaladb {

    object implicits {
      import  javadb.JRow

      implicit class SRow(jrow : JRow ){
          def get[T] (colName: String )(implicit toT: (JRow,String) => T) :T =
            toT(jrow,colName)
      }

      implicit val jrowToInt:(JRow,String) => Int =
        (jrow:JRow , colName:String) => jrow.getInt(colName)

      implicit val jrowToD:(JRow,String) => Double =
        (jrow:JRow , colName:String) => jrow.getDouble(colName)

      implicit val jrowToS:(JRow,String) => String =
        (jrow:JRow , colName:String) => jrow.getText(colName)

    }

    object DB {
      import implicits._
      def main(args: Array[String]) = {
        val row = javadb.JRow("one"->1,"two"->2.2,"three"->"THREE!")
        val one1: Int = row.get("one");
        val two1:Double = row.get("two")
        val three1:String = row.get("three")

        print(s"one1  $one1")
        print(s"two1  $two1")
        print(s"three1  $three1")

      }
    }
  }

}


