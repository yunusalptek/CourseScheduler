/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Yunus
 */

public class MultiTableQueries {
    
    private static Connection connection;
    private static PreparedStatement getAllClassDescription;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescription(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> description = new ArrayList<ClassDescription>();
        try {
            getAllClassDescription = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
            getAllClassDescription.setString(1, semester);
            resultSet = getAllClassDescription.executeQuery();
            while(resultSet.next()) {
                description.add(new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return description;
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> scheduledStudents = new ArrayList<StudentEntry>();
        try
        {
            getScheduledStudentsByClass = connection.prepareStatement("select app.student.studentid, firstname, lastname from app.schedule, app.student where semester = ? and courseCode = ? and status = 's' and app.schedule.studentid = app.student.studentid");
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);
            //getScheduledStudentsByClass.setString(3, "s");
            resultSet = getScheduledStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                scheduledStudents.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudents;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> waitlistedStudents = new ArrayList<StudentEntry>();
        try
        {
            getWaitlistedStudentsByClass = connection.prepareStatement("select app.student.studentid, firstname, lastname from app.schedule, app.student where semester = ? and courseCode = ? and status = 'w' and app.schedule.studentid = app.student.studentid");
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);
            //getWaitlistedStudentsByClass.setString(3, "w");
            resultSet = getWaitlistedStudentsByClass.executeQuery();
            
            while(resultSet.next())
            {
                waitlistedStudents.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudents;
    }
}