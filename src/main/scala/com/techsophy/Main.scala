package com.techsophy

import com.techsophy.repo._

import java.sql.Date
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

object Main extends App {



//  val studentId = StudentRecord.create(Student("dharmendra", "s@abc.com", 103, Date.valueOf("1996-06-17"), Some(10)))
//  studentId.onComplete {
//      case Success(d) => println(d)
//      case Failure(err) => err.printStackTrace()
//    }
  val ans = StudentRecord.getUniversityStudentCount()
  ans.onComplete {
    case Success(d) => println(d)
    case Failure(err) => err.printStackTrace()
  }
//  val students = UniversityRecord.getById(101)
//  students.onComplete {
//    case Success(d) => println(d)
//    case Failure(err) => err.printStackTrace()
//  }

//  val tmp = StudentRecord.update(Student("yash", "a@abc.com", 102, Date.valueOf("1996-06-17"), Some(3)))
//  tmp.onComplete{
//    case Success(id) => println(id)
//    case Failure(err)=>err.printStackTrace()
//  }

  Thread.sleep(5 * 1000)

}

