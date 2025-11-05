import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Student{
   public int stdID;
   public String name;
   public int marks;

    public Student(int stdID, String name, int marks) {
        this.stdID=stdID;
        this.name=name;
        this.marks=marks;
    }

   public String toString(){
      return "Student-> ID: "+stdID+", Name: "+name+", Marks: "+marks;
     }  
}

class StudentManager{
   private ArrayList<Student> students=new ArrayList<>();
   Scanner in=new Scanner(System.in);

   void addStudent(){
        System.out.print("Enter Student ID: ");
        int id = in.nextInt();
        in.nextLine(); // newline left by 
       // Prevent Duplicate ID
      for(Student s : students){
        if(s.stdID == id){
            System.out.println("Student ID already exists! Try again.\n");
            return; // stop method here
        }
       }

        System.out.print("Enter Student Name: ");
        String name = in.nextLine();
        System.out.print("Enter Marks: ");
        int marks = in.nextInt();
      //Input Validation for Marks
       if(marks < 0 || marks > 100){
          System.out.println("⚠ Marks must be between 0 and 100.\n");
          return; // stop method here
        }

        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully!\n");
    }

   void displayStudents(){
      int size=students.size();
      if(size>0){
         System.out.println("=== Student List ===");
          for (int i = 0; i < size; i++) {
          System.out.println(students.get(i).toString());
          }
         System.out.println();
        }
      else{
        System.out.println("No Student Record yet!");
         }
    }
      
    void searchStudent(){
        if (students.isEmpty()){
            System.out.println("No student records to search.\n");
            return;
        }

        System.out.print("Enter Student ID to search: ");
        int search = in.nextInt();
        boolean found = false;

        for (Student s : students) {
            if (s.stdID == search) {
                System.out.println("Record found: " + s.toString());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found!\n");
        }
    }
    
       void deleteStudent(){
        if (students.isEmpty()){
            System.out.println("No student records to delete.\n");
            return;
        }

        System.out.print("Enter Student ID to remove: ");
        int remove = in.nextInt();
        boolean found = false;

        for (int i = 0; i < students.size(); i++){
            if (students.get(i).stdID == remove){
                System.out.println("Removing: " + students.get(i).toString());
                students.remove(i);
                System.out.println("Removed successfully!\n");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found!\n");
        }
    
    }

    void updateStudent(){
         if (students.isEmpty()){
        System.out.println("No student records to update!\n");
        return;
         }

       System.out.print("Enter Student ID to update: ");
       int id = in.nextInt();
       //in.nextLine(); // consume newline
       boolean found = false;

      for (Student s : students){
        if (s.stdID == id) {
            System.out.println("Current record: " + s.toString());
             in.nextLine();
            System.out.print("Enter new name: ");
            s.name = in.nextLine();
            System.out.print("Enter new marks: ");
            s.marks = in.nextInt();
            System.out.println("Record updated successfully!\n");
            found = true;
            break;
        }
     }

      if (!found) {
        System.out.println("Student not found!\n");
       }
     }

      void sortByMarks(){
        if (students.isEmpty()){
              System.out.println("No students to sort!\n");
             return;
         }
      // Sort in descending order (highest marks first)
        students.sort((s1, s2) -> s2.marks - s1.marks);
        System.out.println("Students sorted by marks (High → Low):\n");
       displayStudents(); // reuse your existing function
}

       void showAverageMarks(){
           if (students.isEmpty()) {
              System.out.println("No records to calculate average!\n");
              return;
          }
         double sum = 0;
        for (Student s : students) {
             sum += s.marks;
       }
       double avg = sum / students.size();
        System.out.println("Average Marks of Class = " + avg + "\n");
    }

     void saveToFile(){
           try{
                 FileWriter writer = new FileWriter("students.txt"); // overwrite
             for (Student s : students) {
               writer.write(s.stdID + "," + s.name + "," + s.marks + "\n");
             }
             writer.close();
             System.out.println("Student records saved to students.txt");
            } catch (IOException e){
              System.out.println("Error saving data: " + e.getMessage());
            }
         }

     void loadFromFile(){
          try{
               FileReader fr = new FileReader("students.txt");
             BufferedReader br = new BufferedReader(fr);
               String line;

         while((line = br.readLine()) != null){
            String[] data = line.split(",");
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            int marks = Integer.parseInt(data[2]);
            students.add(new Student(id, name, marks));
         }
             br.close();
           System.out.println("Previous data loaded successfully.\n");
         } catch (IOException e) {
              System.out.println("No saved data found, starting fresh.\n");
           }
       }
  
}

public class StudentManagementSystem {
    public static void main(String[] args) {
         Scanner cin=new Scanner(System.in);
         StudentManager std = new StudentManager();
         std.loadFromFile(); // load stored data
           int choice;

         do {
            System.out.println("===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Show Average Marks");
	    System.out.println("7. Sort Students by Marks");
            System.out.println("8. Save & Exit");
            System.out.print("Enter your choice: ");
            choice = cin.nextInt();

            switch(choice){
                case 1: std.addStudent(); break;
                case 2: std.displayStudents(); break;
                case 3: std.searchStudent(); break;
                case 4: std.updateStudent(); break;
                case 5: std.deleteStudent(); break;
                case 6: std.showAverageMarks(); break;     
                case 7: std.sortByMarks(); break;          
                case 8: std.saveToFile();
                        System.out.println("Exiting... Thank you!");
                         break;
               default: System.out.println("Invalid choice! Try again.\n");
              }
           }while(choice != 8);
        
    }
}
