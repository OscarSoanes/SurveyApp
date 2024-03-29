package com.example.surveyapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException
import java.util.*
import kotlin.collections.ArrayList

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
    val Column_QuestionId = "QuestionId"
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


    fun getSurvey(eID: Int) : Survey {
        var db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $SurveyTableName WHERE $surveyColumnID = $eID"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst()) {
            db.close()
            return Survey(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
            )
        } else {
            db.close()
            return Survey(0, 0, "", "", "")
        }
    }

    // Adds an admin to table, returns -1 if error occurred
    fun addAdmin(admin: Admin) : Long {
        val loginExists = checkAdmin(admin)
        if (loginExists.toInt() < 0)
            return loginExists

        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_AdminLogin, admin.loginName.lowercase())
        cv.put(Column_AdminPassword, admin.password)

        val id = db.insert(AdminTableName, null, cv)
        db.close()

        if (id.toInt() == -1) return id
        else return id
    }

    /**
     * Checks admins username for any matches
     * -2 = Error connecting to database
     * -3 = Username already exists
     * 0 = User not found (check passed)
     */
    private fun checkAdmin(admin: Admin): Long {
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
    fun addStudent(student: Student) : Long {
        val loginExists = checkStudent(student)
        if (loginExists.toInt() < 0)
            return loginExists

        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_StudentLogin, student.loginName.lowercase())
        cv.put(Column_StudentPassword, student.password)

        val id = db.insert(StudentTableName, null, cv)
        db.close()

        if (id.toInt() == -1) return id
        else return id
    }

    /**
     * Checks admins username for any matches
     * -2 = Error connecting to database
     * -3 = Username already exists
     * 0 = User not found (check passed)
     */
    private fun checkStudent(student: Student): Long {
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

    // Adds an survey to table, returns -1 if error occurred, also returns ID of new survey
    fun addSurvey(survey: Survey) : Long {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(Column_AdminId, survey.adminId)
        cv.put(Column_Module, survey.module)
        cv.put(Column_StartDate, survey.startDate)
        cv.put(Column_EndDate, survey.endDate)

        val id = db.insert(SurveyTableName, null, cv)
        db.close()

        return if (id.toInt() == -1) id
        else id
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

    fun updateSurvey(survey: Survey): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_Module, survey.module)
        cv.put(Column_StartDate, survey.startDate)
        cv.put(Column_EndDate, survey.endDate)

        val result = db.update(SurveyTableName, cv, "$surveyColumnID = ${survey.surveyId}",
        null) == 1
        db.close()
        return result
    }

    fun deleteSurvey(survey: Survey) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(SurveyTableName, "$surveyColumnID = ${survey.surveyId}", null) == 1

        db.close()
        return result
    }

    fun deleteQuestionBySurveyId(eID: Int) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(QuestionTableName, "$Column_SurveyId = $eID", null) == 1

        db.close()
        return result
    }

    fun deleteResponseBySurveyId(eID: Int) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(StudentSurveyResponseTableName, "$Column_SurveyId = $eID", null) == 1

        db.close()
        return result
    }

    fun getAllSurveysToBeCompleted(eID: Int) : ArrayList<Survey> {
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
                if (hasSurveyBeenCompletedByStudent(eID, survey)) {
                    surveyList.add(survey)
                }
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyList
    }

    private fun hasSurveyBeenCompletedByStudent(eID: Int, survey: Survey) : Boolean {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentSurveyResponseTableName WHERE $Column_StudentId = $eID AND $Column_SurveyId = ${survey.surveyId}"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (!cursor.moveToFirst()) {
            db.close()
            cursor.close()

            // ensuring end date has not passed
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.UK)
            val ed = sdf.parse(survey.endDate)
            val sd = sdf.parse(survey.startDate)
            val current = sdf.parse(sdf.format(Date()))

            if (ed.after(current) && current.after(sd) || current.equals(sd)) {
                return true
            }
        }
        return false
    }

    fun getAllQuestionsBySurveyID(eID: Int) : ArrayList<Question> {
        val questionList = ArrayList<Question>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $QuestionTableName WHERE $Column_SurveyId = $eID"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst()) {
            do {
                val id: Int = cursor.getInt(0)
                val surveyId: Int = cursor.getInt(1)
                val questionText: String = cursor.getString(2)

                val question = Question(id, surveyId, questionText)
                questionList.add(question)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return questionList
    }

    fun getAllDataBySurveyAndQuestionID(surveyId: Int, questionId: Int) : DataList {
        val dataList = DataList()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentSurveyResponseTableName WHERE $Column_SurveyId = $surveyId AND $Column_QuestionId = $questionId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst()) {
            do {
                when (cursor.getInt(4)) {
                    5 -> dataList.addOneStrongAgree()
                    4 -> dataList.addOneAgree()
                    3 -> dataList.addOneNeutral()
                    2 -> dataList.addOneDisagree()
                    1 -> dataList.addOneStrongDisagree()
                }
            } while (cursor.moveToNext())
        }

        return dataList
    }

    fun getResponseGreaterThanOne(surveyId: Int) : Boolean {
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $StudentSurveyResponseTableName WHERE $Column_SurveyId = $surveyId"

        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst()) {
            cursor.close()
            db.close()
            return true
        }
        return false
    }
}