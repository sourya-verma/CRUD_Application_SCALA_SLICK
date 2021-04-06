package com.techsophy.repo

import com.techsophy.connection.H2DBComponent
import org.scalatest._
import funsuite._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import java.sql.Date


class UniversitySpec extends AnyFunSuite with UniversityRecord with H2DBComponent with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))


  test("create on university table") {
    val response = create(University(103, "nit","delhi"))
    whenReady(response) { id =>
      assert(id === 1)
    }
  }

  test("read on university table") {
    val response = getAll()
    whenReady(response) { result =>
      assert(result === List(University(101, "hcu","hyderabad"), University(102, "jnu","delhi")))
    }
  }



  test("getById on university table") {
    val response = getById(102)
    whenReady(response) { result =>
      assert(result.get === University(102, "jnu","delhi"))
    }
  }





  test("update on university table") {
    val response = update(University(101, "nit","delhi"))
    whenReady(response) { id =>
      assert(id === 1)
    }
  }

  test("delete on university table") {
    val response = delete(101)
    whenReady(response) { id =>
      assert(id === 1)
    }
  }


}