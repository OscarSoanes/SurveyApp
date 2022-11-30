package com.example.surveyapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException

// config
private val databaseName = "Survey.db"
private val ver: Int = 1

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, databaseName, null, ver) {

    /** Admin Table */
    val AdminTableName = "Admin"
    val adminColumnID = "Id"
    val Column_AdminLogin = "LoginName"
    val Column_AdminPassword = "Password"

    /** Student Table */
    val StudentTableName = "Student"
    val studentColumnID = "Id"
    val Column_StudentLogin = "LoginName"
    val Column_StudentPassword = "Password"

    /** Survey Table */
    val SurveyTableName = "Survey"
    val surveyColumnID = "Id"
    val Column_AdminId = "AdminId"
    val Column_Module = "Module"
    val Column_StartDate = "StartDate"
    val Column_EndDate = "EndDate"

    /** Question Table */
    val QuestionTableName = "Question"
    val questionColumnID = "Id"
    val Column_QuestionSurveyId = "SurveyId"
    val Column_QuestionText = "QuestionText"

    /** StudentSurveyResponse Table */
    val StudentSurveyResponseTableName = "StudentSurveyResponse"
    val studentSurveyResponse_ID = "Id"
    val Column_StudentId = "StudentId"
    val Column_SurveyId = "SurveyId"
    val Column_QuestionId = "QuesitonId"
    val Column_Answer = "Answer"


    // Creates a new database on first time
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            var sqlCreateStatement: String = "CREATE TABLE $AdminTableName ( " +
                    "$adminColumnID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$Column_AdminLogin TEXT NOT NULL, $Column_AdminPassword TEXT NOT NULL )"
            db?.execSQL(sqlCreateStatement)

            var sqlCreateStatement2: String = "CREATE TABLE $StudentTableName ( " +
                    "$studentColumnID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$Column_StudentLogin TEXT NOT NULL, $Column_StudentPassword TEXT NOT NULL )"
            db?.execSQL(sqlCreateStatement2)

            var sqlCreateStatement3: String = "CREATE TABLE $SurveyTableName ( " +
                    "$surveyColumnID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$Column_AdminId INTEGER NOT NULL, $Column_Module TEXT NOT NULL, " +
                    "$Column_StartDate TEXT NOT NULL, $Column_EndDate TEXT NOT NULL," +
                    "FOREIGN KEY($Column_AdminId) REFERENCES $AdminTableName($adminColumnID))"
            db?.execSQL(sqlCreateStatement3)

            var sqlCreateStatement4: String = "CREATE TABLE $QuestionTableName (" +
                    "$questionColumnID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$Column_QuestionSurveyId INTEGER NOT NULL, " +
                    "$Column_QuestionText INTEGER NOT NULL, " +
                    "FOREIGN KEY($Column_QuestionSurveyId) REFERENCES $SurveyTableName($surveyColumnID))"
            db?.execSQL(sqlCreateStatement4)

            var sqlCreateStatement5: String = "CREATE TABLE $StudentSurveyResponseTableName (" +
                    "$studentSurveyResponse_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$Column_StudentId INTEGER NOT NULL, $Column_SurveyId INTEGER NOT NULL, " +
                    "$Column_QuestionId INTEGER NOT NULL, $Column_Answer INTEGER NOT NULL, " +
                    "FOREIGN KEY($Column_StudentId) REFERENCES $StudentTableName($studentColumnID), " +
                    "FOREIGN KEY($Column_SurveyId) REFERENCES $SurveyTableName($surveyColumnID), " +
                    "FOREIGN KEY($Column_QuestionId) REFERENCES $QuestionTableName($questionColumnID))"
            db?.execSQL(sqlCreateStatement5)
        } catch (e : SQLException) {

        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun getAdmin(eID: Int) : Admin {
        var db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $adminColumnID = $eID"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst()) {
            db.close()
            return Admin(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            )
        } else {
            db.close()
            return Admin(0, "", "")
        }
    }

    fun getStudent(eID: Int) : Student {
        var db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $studentColumnID = $eID"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst()) {
            db.close()
            return Student(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            )
        } else {
            db.close()
            return Student(0, "", "")
        }
    }


    // returns all admins in admin table
    fun getAllAdmins(): ArrayList<Admin> {
        val adminList = ArrayList<Admin>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $AdminTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val login: String = cursor.getString(1)
                val password: String = cursor.getString(2)

                val admin = Admin(id, login, password)
                adminList.add(admin)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return adminList
    }

    // returns all students in students table
    fun getAllStudents(): ArrayList<Student> {
        val studentList = ArrayList<Student>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val login: String = cursor.getString(1)
                val password: String = cursor.getString(2)

                val student = Student(id, login, password)
                studentList.add(student)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return studentList
    }

    // returns all responces in StudentSurveyResponse table
    fun getAllStudentSurveyResponces(): ArrayList<StudentSurveyResponse> {
        val ssrList = ArrayList<StudentSurveyResponse>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentSurveyResponseTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val studentId: Int = cursor.getInt(1)
                val surveyId: Int = cursor.getInt(2)
                val questionId: Int = cursor.getInt(3)
                val answer: Int = cursor.getInt(4)

                val ssr = StudentSurveyResponse(id, studentId, surveyId, questionId, answer)
                ssrList.add(ssr)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return ssrList
    }

    // returns all surveys in survey table
    fun getAllSurveys(): ArrayList<Survey> {
        val surveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val adminId: Int = cursor.getInt(1)
                val module: String = cursor.getString(2)
                var startDate: String = cursor.getString(3)
                var endDate: String = cursor.getString(4)

                val survey = Survey(id, adminId, module, startDate, endDate)
                surveyList.add(survey)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyList
    }

    // returns all questions in question table
    fun getAllQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $QuestionTableName"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val surveyId: Int = cursor.getInt(1)
                val text: String = cursor.getString(2)

                val question = Question(id, surveyId, text)
                questionList.add(question)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return questionList
    }

    // Adds an admin to table, returns -1 if error occurred
    fun addAdmin(admin: Admin) : Int {
        val loginExists = checkAdmin(admin)
        if (loginExists < 0)
            return loginExists

        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_AdminLogin, admin.loginName.lowercase())
        cv.put(Column_AdminPassword, admin.password)

        val success = db.insert(AdminTableName, null, cv)
        db.close()

        if (success.toInt() == -1) return success.toInt()
        else return 1
    }

    /**
     * Checks admins username for any matches
     * -2 = Error connecting to database
     * -3 = Username already exists
     * 0 = User not found (check passed)
     */
    private fun checkAdmin(admin: Admin): Int {
        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2 // SQLITE ERROR
        }

        val username = admin.loginName.lowercase()

        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $Column_AdminLogin = ?"
        var param = arrayOf(username)
        val cursor: Cursor = db.rawQuery(sqlStatement, param)

        if (cursor.moveToFirst()) {
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // LOGIN ALREADY EXISTS
        }

        cursor.close()
        db.close()
        return 0
    }

    fun getAdminFromLogin(login: String): Int {
        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            return -2
        }

        val sqlStatement = "SELECT * FROM $AdminTableName WHERE $Column_AdminLogin = ?"
        var param = arrayOf(login.lowercase())
        var cursor: Cursor = db.rawQuery(sqlStatement, param)

        if (cursor.moveToFirst()) {
            var id = cursor.getInt(0)
            cursor.close()
            db.close()
            return id
        }
        return -1 // RECORD NOT FOUND
    }

    fun getStudentFromLogin(login: String): Int {
        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        } catch (e: SQLiteException) {
            return -2
        }

        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentLogin = ?"
        var param = arrayOf(login.lowercase())
        var cursor: Cursor = db.rawQuery(sqlStatement, param)

        if (cursor.moveToFirst()) {
            var id = cursor.getInt(0)
            cursor.close()
            db.close()
            return id
        }
        return -1 // RECORD NOT FOUND
    }

    // Adds an student to table, returns -1 if error occurred
    fun addStudent(student: Student) : Int {
        val loginExists = checkStudent(student)
        if (loginExists < 0)
            return loginExists

        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_StudentLogin, student.loginName.lowercase())
        cv.put(Column_StudentPassword, student.password)

        val success = db.insert(StudentTableName, null, cv)
        db.close()

        if (success.toInt() == -1) return success.toInt()
        else return 1
    }

    /**
     * Checks admins username for any matches
     * -2 = Error connecting to database
     * -3 = Username already exists
     * 0 = User not found (check passed)
     */
    private fun checkStudent(student: Student): Int {
        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2 // SQLITE ERROR
        }

        val username = student.loginName.lowercase()

        val sqlStatement = "SELECT * FROM $StudentTableName WHERE $Column_StudentLogin = ?"
        var param = arrayOf(username)
        val cursor: Cursor = db.rawQuery(sqlStatement, param)

        if (cursor.moveToFirst()) {
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // LOGIN ALREADY EXISTS
        }

        cursor.close()
        db.close()
        return 0
    }

    // Adds an survey to table, returns -1 if error occurred
    fun addSurvey(survey: Survey) : Int {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_AdminId, survey.adminId)
        cv.put(Column_Module, survey.module)
        cv.put(Column_StartDate, survey.startDate)
        cv.put(Column_EndDate, survey.endDate)

        val success = db.insert(SurveyTableName, null, cv)
        db.close()

        if (success.toInt() == -1) return success.toInt()
        else return 1
    }

    // Adds an question to table, returns -1 if error occurred
    fun addQuestion(question: Question) : Int {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_QuestionSurveyId, question.surveyId)
        cv.put(Column_QuestionText, question.questionText)

        val success = db.insert(QuestionTableName, null, cv)
        db.close()

        if (success.toInt() == -1) return success.toInt()
        else return 1
    }


    // Adds a response to table, returns -1 if error occurred
    fun addResponse(ssr: StudentSurveyResponse) : Int {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_StudentId, ssr.studentId)
        cv.put(Column_SurveyId, ssr.surveyId)
        cv.put(Column_QuestionId, ssr.questionId)
        cv.put(Column_Answer, ssr.answer)

        val success = db.insert(StudentSurveyResponseTableName, null, cv)
        db.close()

        if (success.toInt() == -1) return success.toInt()
        else return 1
    }

    fun getAllSurveysByAdminId(eID: Int) : ArrayList<Survey> {
        val surveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTableName WHERE $Column_AdminId = $eID"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val adminId: Int = cursor.getInt(1)
                val module: String = cursor.getString(2)
                val startDate: String = cursor.getString(3)
                val endDate: String = cursor.getString(4)

                val survey = Survey(id, adminId, module, startDate, endDate)
                surveyList.add(survey)
            } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return surveyList
    }

}