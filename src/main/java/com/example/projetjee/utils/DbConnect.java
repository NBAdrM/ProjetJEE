package com.example.projetjee.utils;


import java.sql.*;import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.projetjee.models.*;
import org.mindrot.jbcrypt.BCrypt;
public class DbConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/projetjeedb";
    private static final String USER = "root";
    private static final String PASSWORD = "cytech0001";

    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        // Charge le driver JDBC
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Crée et retourne la connexion à la base de données
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static int addPerson(String firstName, String lastName, String email, String adress, String username, String password, Boolean active) throws SQLException, ClassNotFoundException {
        String hashedPassword = hashPassword(password);
        Connection conn = initializeDatabase();

        String query = "INSERT INTO Person (first_name, last_name, email, address, username, password,active) VALUES (?, ?, ?, ?, ?, ?,?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, email);
        stmt.setString(4, adress);
        stmt.setString(5, username);
        stmt.setString(6, hashedPassword);
        stmt.setBoolean(7, active);

        int rowsAffected = stmt.executeUpdate();

        // Récupérer l'ID généré automatiquement
        int generatedId = -1;
        if (rowsAffected > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        }

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addStudent(String firstName, String lastName, String email, String adress, String username, String password, Boolean active) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName, lastName, email, adress, username, password, active);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Student (id) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addStudent(String firstName, String lastName, String email, String adress, String username, String password, Boolean active, String report) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName, lastName, email, adress, username, password, active);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Student (id,report) VALUES (?,?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);
        stmt.setString(2, report);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addTeacher(String firstName, String lastName, String email, String adress, String username, String password, Boolean active) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName, lastName, email, adress, username, password, active);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Teacher (id) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addAdmin(String firstName, String lastName, String email, String adress, String username, String password, Boolean active) throws SQLException, ClassNotFoundException {

        int generatedId = addPerson(firstName, lastName, email, adress, username, password, active);

        Connection conn = initializeDatabase();

        String query = "INSERT INTO Admin (id) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, generatedId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return generatedId;
    }


    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private static boolean checkNoneHashedPassword(String plaintextPassword, String hashedPassword) {
        return BCrypt.checkpw(plaintextPassword, hashedPassword);
    }

    public static boolean checkPassword(int id, String password) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();
        String query = "SELECT password FROM Person WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("password");
            rs.close();
            stmt.close();
            conn.close();
            return checkNoneHashedPassword(password, hashedPassword);
        }
        rs.close();
        stmt.close();
        conn.close();
        return false;
    }

    public static boolean alreadyExisteUsername(String username) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT COUNT(*) FROM Person WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            boolean out = rs.getInt(1) > 0;
            rs.close();
            stmt.close();
            conn.close();
            return out;
        }
        rs.close();
        stmt.close();
        conn.close();
        return false;
    }

    public static void addReportStudent(int id, String report) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "UPDATE Student SET report = ? WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, report);
        stmt.setInt(2, id);
        stmt.executeQuery();
        stmt.close();
        conn.close();
    }

    public static void updateTeacher(int id, String firstName, String lastName, String email, String adress, Boolean active) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "UPDATE Person SET first_name=? , last_name=? , email=? , address=? , active=? WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, email);
        stmt.setString(4, adress);
        stmt.setBoolean(5, active);
        stmt.setInt(6, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public static void updateStudent(int id, String firstName, String lastName, String email, String adress, Boolean active, String report) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "UPDATE Person p INNER JOIN Student s ON s.id = p.id SET p.first_name=? , p.last_name=? , p.email=? , p.address=? , p.active=? ,s.report = ? WHERE p.id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, email);
        stmt.setString(4, adress);
        stmt.setBoolean(5, active);
        stmt.setString(6, report);
        stmt.setInt(7, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public static void deletePerson(int id) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "UPDATE person SET active = ? WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setBoolean(1, false);
        stmt.setInt(2, id);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public static List<Student> getStudents() throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Student AS s INNER JOIN Person AS p ON s.id=p.id";

        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();
        List<Student> students = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean active = rs.getBoolean("active");
            String report = rs.getString("report");

            students.add(new Student(id, firstname, lastname, email, address, username, password, active, report));
        }

        stmt.close();
        conn.close();
        return students;
    }

    public static List<Teacher> getTeachers() throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Teacher AS t INNER JOIN Person AS p ON t.id=p.id";

        PreparedStatement stmt = conn.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();
        List<Teacher> teachers = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean active = rs.getBoolean("active");

            teachers.add(new Teacher(id, firstname, lastname, email, address, username, password, active));
        }

        stmt.close();
        conn.close();
        return teachers;
    }

    public static Student getStudent(int id) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Student AS s INNER JOIN Person AS p ON s.id=p.id WHERE p.id=?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean active = rs.getBoolean("active");
            String report = rs.getString("report");

            return new Student(id, firstname, lastname, email, address, username, password, active, report);
        }
        return null;
    }

    public static Teacher getTeacher(int id) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Teacher AS t INNER JOIN Person AS p ON t.id=p.id WHERE p.id=?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean active = rs.getBoolean("active");

            // Crée une instance de Teacher et la retourne
            return new Teacher(id, firstname, lastname, email, address, username, password, active);
        }

        stmt.close();
        conn.close();
        return null;
    }


    public static Integer getUserIdByUsername(String username) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT id FROM Person WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        Integer userId = null;
        if (rs.next()) {
            userId = rs.getInt("id");
        }

        rs.close();
        stmt.close();
        conn.close();

        return userId;
    }

    public static String getRoleById(int id) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String role = null;

        try {
            // Vérifier si la personne est active
            String personQuery = "SELECT active FROM Person WHERE id = ?";
            PreparedStatement personStmt = conn.prepareStatement(personQuery);
            personStmt.setInt(1, id);

            ResultSet personRs = personStmt.executeQuery();

            if (personRs.next() && personRs.getBoolean("active")) {
                // Vérifier si c'est un étudiant
                String studentQuery = "SELECT report FROM Student WHERE id = ?";
                PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
                studentStmt.setInt(1, id);

                ResultSet studentRs = studentStmt.executeQuery();

                if (studentRs.next()) {
                    role = "student";
                } else {
                    // Vérifier si c'est un enseignant
                    String teacherQuery = "SELECT id FROM Teacher WHERE id = ?";
                    PreparedStatement teacherStmt = conn.prepareStatement(teacherQuery);
                    teacherStmt.setInt(1, id);

                    ResultSet teacherRs = teacherStmt.executeQuery();

                    if (teacherRs.next()) {
                        role = "teacher";
                    } else {
                        // Vérifier si c'est un administrateur
                        String adminQuery = "SELECT iid FROM Admin WHERE iid = ?";
                        PreparedStatement adminStmt = conn.prepareStatement(adminQuery);
                        adminStmt.setInt(1, id);

                        ResultSet adminRs = adminStmt.executeQuery();

                        if (adminRs.next()) {
                            role = "admin";
                        }
                        adminRs.close();
                        adminStmt.close();
                    }
                    teacherRs.close();
                    teacherStmt.close();
                }
                studentRs.close();
                studentStmt.close();
            }

            personRs.close();
            personStmt.close();
        } finally {
            conn.close();
        }

        return role;
    }

    public static int addCourse(String name, int year, int teacherId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "INSERT INTO Course (name,year,Teacher_Person_id) VALUES (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, name);
        stmt.setInt(2, year);
        stmt.setInt(3, teacherId);

        int rowsAffected = stmt.executeUpdate();

        int generatedId = -1;
        if (rowsAffected > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        }

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static int addDateCourse(String date, String startTime, String endTime, String classroom, int courseId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "INSERT INTO Date_Course (date,start_time,end_time,classroom,Course_id) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, date);
        stmt.setString(2, startTime);
        stmt.setString(3, endTime);
        stmt.setString(4, classroom);
        stmt.setInt(5, courseId);

        int rowsAffected = stmt.executeUpdate();

        int generatedId = -1;
        if (rowsAffected > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        }

        stmt.close();
        conn.close();

        return generatedId;
    }

    public static List<Student> getStudentsByName(String lastName, String firstName) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Student AS s INNER JOIN Person AS p ON s.id=p.id WHERE p.last_name LIKE ? OR p.first_name LIKE ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, lastName);
        stmt.setString(2, firstName);

        ResultSet rs = stmt.executeQuery();
        List<Student> students = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean active = rs.getBoolean("active");
            String report = rs.getString("report");

            students.add(new Student(id, firstname, lastname, email, address, null, null, active, report));
        }

        stmt.close();
        conn.close();
        return students;
    }

    public static List<Teacher> getTeachersByName(String lastName, String firstName) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Teacher AS t INNER JOIN Person AS p ON t.id=p.id WHERE p.last_name LIKE ? OR p.first_name LIKE ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, lastName);
        stmt.setString(2, firstName);

        ResultSet rs = stmt.executeQuery();
        List<Teacher> teachers = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean active = rs.getBoolean("active");

            teachers.add(new Teacher(id, firstname, lastname, email, address, null, null, active));
        }

        stmt.close();
        conn.close();
        return teachers;
    }

    public static List<Course> getCoursesByTeacher(int teacherId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Course WHERE Teacher_Person_id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, teacherId);

        ResultSet rs = stmt.executeQuery();
        List<Course> courses = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int year = rs.getInt("year");
            int degree = rs.getInt("degree");

            courses.add(new Course(id, name, year, teacherId));
        }

        stmt.close();
        conn.close();
        return courses;
    }

    public static List<Course> getCoursesByStudent(int studentId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Course AS c INNER JOIN student_has_course as s_a_c ON s_a_c.course_id = c.id WHERE Student_id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, studentId);

        ResultSet rs = stmt.executeQuery();
        List<Course> courses = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int year = rs.getInt("year");
            int teacherId = rs.getInt("Teacher_Person_id");

            courses.add(new Course(id, name, year, teacherId));
        }

        stmt.close();
        conn.close();
        return courses;
    }

    public static void addStudentFollowCourse(int studentId, int courseId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "INSERT INTO Student_has_Course (Student_id,course_id) VALUES (?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, studentId);
        stmt.setInt(2, courseId);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public static void addGrade(int studentId, int courseId, float grade, String date) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "INSERT INTO Garde_of_Student (student_course_id_course,student_course_id_student,grade,date) VALUES (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, courseId);
        stmt.setInt(2, studentId);
        stmt.setFloat(3, grade);
        stmt.setString(4, date);

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public static List<Grade> getGrades(int studentId, int courseId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Garde_of_Student WHERE student_course_id_student = ? AND student_course_id_course = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, studentId);
        stmt.setInt(2, courseId);

        ResultSet rs = stmt.executeQuery();
        List<Grade> grades = new ArrayList<>();

        while (rs.next()) {
            int grade = rs.getInt("grade");
            String date = rs.getString("date");

            grades.add(new Grade(grade, date));
        }

        stmt.close();
        conn.close();
        return grades;
    }

    public static List<Course> getCourses() throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();
        String query = "SELECT * FROM Course";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int year = rs.getInt("year");
            int teacherId = rs.getInt("Teacher_Person_id");
            courses.add(new Course(id, name, year, teacherId));
        }
        stmt.close();
        conn.close();
        return courses;
    }

    public static List<StudentCourse> getStudentCourses(int studentId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Student_has_Course WHERE Student_id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, studentId);

        ResultSet rs = stmt.executeQuery();
        List<StudentCourse> studentCourses = new ArrayList<>();

        while (rs.next()) {
            int courseId = rs.getInt("course_id");

            List<Grade> grades = getGrades(studentId, courseId);

            studentCourses.add(new StudentCourse(courseId, studentId, grades));
        }

        stmt.close();
        conn.close();
        return studentCourses;
    }

    public static List<Student> getStudentsByCourse(int courseId) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Person AS p INNER JOIN Student as s ON p.id = s.id INNER JOIN Student_has_course AS s_a_c ON p.id=s_a_c.Student_id INNER JOIN course as c ON c.id=s_a_c.course_id WHERE s_a_c.course_id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, courseId);

        ResultSet rs = stmt.executeQuery();
        List<Student> students = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean active = rs.getBoolean("active");
            String report = rs.getString("report");

            students.add(new Student(id, firstname, lastname, email, address, null, null, active, report));
        }

        stmt.close();
        conn.close();
        return students;
    }

    public static Course getCourseById(int id) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Course WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        Course course = null;
        if (rs.next()) {
            String name = rs.getString("name");
            int year = rs.getInt("year");
            int degree = rs.getInt("degree");
            int teacherId = rs.getInt("Teacher_Person_id");

            course = new Course(id, name, year, teacherId);
        }

        rs.close();
        stmt.close();
        conn.close();

        return course;
    }

    public static List<Grade> getGradesByYear(int studentId, int courseId,int year) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Garde_of_Student WHERE student_course_id_student = ? AND student_course_id_course = ? AND date < ? AND date > ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, studentId);
        stmt.setInt(2, courseId);
        stmt.setString(3, (year+1)+"-08-31");
        stmt.setString(4, year+"-09-01");

        ResultSet rs = stmt.executeQuery();
        List<Grade> grades = new ArrayList<>();

        while (rs.next()) {
            int grade = rs.getInt("grade");
            String date = rs.getString("date");

            grades.add(new Grade(grade, date));
        }

        stmt.close();
        conn.close();
        return grades;
    }

    public static List<StudentCourse> getStudentCoursesByYear(int studentId,int year) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Student_has_Course AS shc INNER JOIN Course AS c ON c.id=shc.course_id AND c.year=? WHERE Student_id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, year);
        stmt.setInt(2, studentId);

        ResultSet rs = stmt.executeQuery();
        List<StudentCourse> studentCourses = new ArrayList<>();

        while (rs.next()) {
            int courseId = rs.getInt("course_id");

            List<Grade> grades = null;
            grades = getGradesByYear(studentId, courseId,year);

            studentCourses.add(new StudentCourse(courseId, studentId, grades));
        }

        stmt.close();
        conn.close();
        return studentCourses;
    }

    public static List<DateCourse> getDateCourse(int id) throws SQLException, ClassNotFoundException {
        Connection conn = initializeDatabase();

        String query = "SELECT * FROM Date_Course WHERE Course_id = ?";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        List<DateCourse> dateCourses = new ArrayList<>();

        while (rs.next()) {
            int idd = rs.getInt("id");
            String dateString = rs.getString("date");
            String startTime = rs.getString("start_time");
            String endTime = rs.getString("end_time");
            String classroom = rs.getString("classroom");
            Date date = Date.valueOf(dateString);
            dateCourses.add(new DateCourse(idd, id, date, classroom, startTime, endTime));
        }

        stmt.close();
        conn.close();
        return dateCourses;
    }
}