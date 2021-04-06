package com.techsophy.repo
import com.techsophy.connection.{DBComponent, MySqlDBComponent}
import java.sql.Date
import scala.concurrent.Future

trait UniversityRecord extends UniversityTable {
  this: DBComponent =>

  import driver.api._

  def create(university: University): Future[Int] = db.run {
    universityTableQuery += university
  }
  def update(university: University): Future[Int] = db.run {
    universityTableQuery.filter(_.id === university.id).update(university)
  }
  def getById(id: Int): Future[Option[University]] = db.run {
    universityTableQuery.filter(_.id === id).result.headOption
  }
  def getAll(): Future[List[University]] = db.run {
    universityTableQuery.to[List].result
  }

  def delete(id: Int): Future[Int] = db.run {
    universityTableQuery.filter(_.id === id).delete
  }

}

private[repo] trait UniversityTable {
  this: DBComponent =>

  import driver.api._

  protected val universityTableQuery = TableQuery[UniversityTable]

  //  protected def bankTableAutoInc = bankTableQuery returning bankTableQuery.map(_.id)

  private[UniversityTable] class UniversityTable(tag: Tag) extends Table[University](tag, "university") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val universityName = column[String]("university_name")
    val location = column[String]("location")

    def * = (id, universityName, location).mapTo[University]

  }

}

object UniversityRecord extends UniversityRecord with MySqlDBComponent

case class University(id : Int, universityName : String, location: String)


