# Sunday School Attendance Application

#### *A simple way to keep track of students' and teachers' attendance in a sunday school class*

This sunday school application will enable superintendents of sunday schools to keep track of students' and teachers' 
attendance in a sunday school class. 

Users will be able to:
 - Create a class
 - View the people in class
 - Add or remove both teachers and students
 - Take attendance of everyone in the class

***Note:*** students' names and attendance in class will be taken only *after* individual or parental consent has been
 given.

#### Relevance of project to developer

The developer was raised in a small Church that continues to utilize outdated methods of data collection. 
Attendance is recorded on paper and then student's information are transferred to graphical
software to determine trends in attendance fluctuations (amongst other things). Also, the process of grouping sunday 
school students and volunteer drivers, continues to be done inefficiently. It is the developer's hope to utilize this
application to optimize this attendance collection process, such that eventually, the 
application could be extended to provide various graphical depictions of the sunday school class' progress, as well 
as to create efficient carpool groups which minimize the distance that each driver has to drive.
 
 #### User Stories
- As a user, I want to be able to create a sunday school class with space for a list of teachers and a list of students 
(no fixed size)
- As a user, I want to be able to add/remove a student to/from a class
- As a user, I want to be able to add/remove a teacher to/from a class
- As a user, I want to be able to be able to view all the people (teacher and students) that are present in class
- As a user, I want to be able to take attendance (i.e., count the number) of the students and teachers in class 
- As a user, I want to be able to clear a class, as in to remove all teachers and students that are part of a class
- As a user, I want to be able to save a sunday school class 
- As a user, I want to be able to be able to load a previous sunday school class from file 


 **Phase 4: Task 2**
 
 Tested and designed my Sunday School Class in the model package to be robust. 
 Made the removeStudent and removeTeachers methods robust.
  
  
 **Phase 4: Task 3**   
 
- I would also refactor the code in AttendanceApp concerning WorkRoom and Json into a class called HandleWorkRoom
    - This new class would contain single fields to WorkRoom, JsonReader and JsonWriter.
    - Then AttendanceApp would only have fields to Scanner and HandleAudio and HandleWorkroom
- Currently, there is a bit of coupling in the program where AttendanceApp has a SundaySchoolClass field
and a WorkRoom field (that has a list of classes), but I would like to leave it like this, because AttendanceApp 
uses a single class/the current class through out the program, and also passes that class in to the ui.tree.HandleJTree 
class, so I would need to know exactly which instance of the class we are currently editing.
    - With the change in the first bullet point, the coupling present would still exist. The only difference the field
     of type WorkRoom in AttendanceApp, would be replace by a field of type HandleWorkRoom. However, AttendanceApp would 
     still store a seperate reference to the current class (may be new or a loaded one) for ease of access to classes
      contents.
- I would change the implementation of loadClass in AttendanceApp to display the saved classes
and ask user which of the classes they would like load. 
    - The chosen class would then be stored in the myClass field currently available in AttendanceApp. 
    Then if the user does not want to load a previous class, but chooses to create an new class, the myClass variable 
    will be assigned to a new SundaySchoolClass
        - This will allow any chosen class to be called up, displayed on the GUI, edited and saved back to the workroom 



