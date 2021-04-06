package com.techsophy.repo
import com.techsophy.connection.{DBComponent, MySqlDBComponent}

import java.sql.Date
import scala.concurrent.Future
import scala.util.Success

trait StudentRecord extends StudentTable {
  this: DBComponent =>

  import driver.api._

  def create(student: Student): Future[Option[Int]] = db.run {
    (studentTableQuery returning studentTableQuery.map(_.id)) += student
  }


  def update(student: Student): Future[Int] = db.run {
    studentTableQuery.filter(_.id === student.id.get).update(student)
  }

  def getById(id: Int): Future[Option[Student]] = db.run {
    studentTableQuery.filter(_.id === id).result.headOption
  }


  def getAll(): Future[List[Student]] = db.run {
    studentTableQuery.to[List].result
  }


    def delete(id: Int): Future[Int] = db.run {
    studentTableQuery.filter(_.id === id).delete
  }

  def getUniversityStudentCount() = {
    val ans = (for {
      (s, u) <- studentTableQuery join universityTableQuery on (_.universityId === _.id)
    } yield (s, u)).groupBy(_._2.universityName).map {
      case (uni, data) => (uni, data.map(_._1.id).length)
    }
    db.run(ans.to[List].result)

//    db.run(ans.result).onComplete{
//      case Success(value) => println(value)
//    }


  }
  def getStudentUniversityName() = {
    val ans = (for {
    (student, university) <- studentTableQuery join universityTableQuery on (_.universityId === _.id)
  } yield (student.name, university.universityName)).to[List]

    db.run(ans.to[List].result)


  }



}

private[repo] trait StudentTable extends UniversityTable {
  this: DBComponent =>

  import driver.api._

  protected val studentTableQuery = TableQuery[StudentTable]

//  protected def bankTableAutoInc = bankTableQuery returning bankTableQuery.map(_.id)

  private[StudentTable] class StudentTable(tag: Tag) extends Table[Student](tag, "student") {
    val id = column[Option[Int]]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")
    val email = column[String]("email")
    val universityId = column[Int]("university_id")
    val dob = column[Date]("date_of_birth")

    def * = (name, email, universityId, dob, id).mapTo[Student]

  }

}

object StudentRecord extends StudentRecord with MySqlDBComponent

case class Student(name : String, email : String, universityId: Int, Dob : Date, id: Option[Int] = None)


