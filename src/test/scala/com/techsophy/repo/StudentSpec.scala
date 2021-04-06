package com.techsophy.repo

import com.techsophy.connection.H2DBComponent
import org.scalatest._
import funsuite._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import java.sql.Date


class StudentSpec extends AnyFunSuite with StudentRecord with H2DBComponent with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  test("create on student table") {
    val response = create(Student("dharma", "d@abc", 101, Date.valueOf("1998-03-02")))
    whenReady(response) { id =>
      assert(id.get === 4)
    }
  }

  test("read on student table") {
    val response = getAll()
    whenReady(response) { list =>
      assert(list === List(Student("sourya", "s@abc", 101, Date.valueOf("1999-06-17"), Some(1)), Student("aditya", "a@abc", 102, Date.valueOf("1996-06-17"), Some(2)), Student("dharma", "d@abc", 101, Date.valueOf("1998-06-17"), Some(3))))
    }
  }



  test("getById on student table") {
    val response = getById(3)
    whenReady(response) { student =>
      assert(student === Some(Student("dharma", "d@abc", 101, Date.valueOf("1998-06-17"), Some(3))))
    }
  }

  test("update on student table") {
    val response = update(Student("dharma", "d@abc", 101, Date.valueOf("1996-06-17"), Some(2)))
    whenReady(response) { id =>
      assert(id === 1)
    }
  }

  test("delete on student table") {
    val response = delete(1)
    whenReady(response) { id =>
      assert(id === 1)
    }
  }

  test("Student name with university name") {
    val response = getStudentUniversityName()
    whenReady(response) { result =>
      assert(result === List(("sourya","hcu"), ("aditya","jnu"), ("dharma","hcu")) )
    }
  }


  test("university name with student count") {
    val response = getUniversityStudentCount()
    whenReady(response) { id =>
      assert(id === List(("jnu",1), ("hcu",2)))
    }
  }


}