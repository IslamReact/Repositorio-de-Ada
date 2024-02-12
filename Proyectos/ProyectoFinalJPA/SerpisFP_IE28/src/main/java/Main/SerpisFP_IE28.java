/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Main;

//Controller import
import static Controller.GeneralController.handleDeleteAllDataOption;
import static Controller.GroupController.displayGroupList;
import static Controller.ModuleController.askForModule;
import static Controller.ModuleController.displayModuleList;
import static Controller.ModuleController.listEnrolmentsRealtionatedWithModule;
import static Controller.ProjectController.displayProjectList;
import static Controller.StudentController.askForStudent;
import static Controller.StudentController.displayStudentList;
import static Controller.StudentController.listEnrolmentsAndProjectsForStudent;
import static Controller.StudentController.listStudentsRealtionatedWithGroup;
import static Controller.StudentController.listStudentsRealtionatedWithProjects;

//DAO import
import DAO.*;

//Tools import
import static Tools.Menus.*;
import static Tools.Tools.*;

//Entities import
import Entity.ModuleFP;
import Entity.Group;
import Entity.Student;
import Entity.Project;
import Entity.Enrolment;

//Provider import
import static Provider.AppProvider.getStudentDAO;
import static Provider.AppProvider.getEnrolmentDAO;
import static Provider.AppProvider.getGroupDAO;
import static Provider.AppProvider.getModuleDAO;
import static Provider.AppProvider.getProjectDAO;

//Util import
import java.util.*;

//Persistence import
import javax.persistence.*;

//logging import
import org.apache.logging.log4j.*;

/**
 *
 * @author islam
 */
public class SerpisFP_IE28 {

    //General log to be unique for each user.
    //------------------------------------------------------------------------------------------------------------------
    static Logger Log = LogManager.getRootLogger();
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static final Scanner scanner = new Scanner(System.in);

    //------------------------------------------------------------------------------------------------------------------
    /**
     * Main method to run the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        //OPTION GENERAL MENU
        //------------------------------------------------------------------------------------------------------------------
        try (scanner) {
            //OPTION GENERAL MENU
            //------------------------------------------------------------------------------------------------------------------
            int option;

            //PERSISTENCE INSTANCING
            //------------------------------------------------------------------------------------------------------------------
            emf = Persistence.createEntityManagerFactory("serpisFpPU");
            em = emf.createEntityManager();

            //ENTITIES INSTANCING
            //------------------------------------------------------------------------------------------------------------------
            StudentDAO studentDAO = getStudentDAO(em);
            EnrolmentDAO enrolmentDAO = getEnrolmentDAO(em);
            ProjectDAO projectDAO = getProjectDAO(em);
            GroupDAO groupDAO = getGroupDAO(em);
            ModuleDAO moduleDAO = getModuleDAO(em);

            //SIMULATION INSTANCING
            //------------------------------------------------------------------------------------------------------------------
            simulateConexionwaitTimeUser();

            do {

                displayElementCounts(groupDAO.countAllGroups(), studentDAO.getStudentCount(), enrolmentDAO.getEnrolmentCount(), projectDAO.countAllProjects(), moduleDAO.countAllModules());

                showMainMenu();

                option = getIntInput(scanner);

                switch (option) {
                    case 1:
                        handleDeleteAllDataOption(studentDAO, groupDAO, moduleDAO, projectDAO, scanner);
                        pressAnyKeyToContinue();
                        break;
                    case 2:
                        // Add New Elements
                        int tableOption;

                        showTableMenu();
                        tableOption = getIntInput(scanner);

                        switch (tableOption) {
                            case 1:
                                //Add Alumno table
                                if (groupDAO.countAllGroups() == 0) {
                                    System.out.println("\nAdd groups first and then you will be able to add students.");
                                } else {
                                    System.out.println("Adding new elements to student table...");

                                    while (true) {
                                        Student student = askDataStudent(em, scanner);
                                        if (student == null) {
                                            break;
                                        }

                                        Student existingStudent = studentDAO.getStudentById(student.getNia());

                                        if (existingStudent != null) {
                                            System.out.println("\n[❌]  Student with NIA " + student.getNia() + " already exists.");
                                        } else {
                                            studentDAO.createStudent(student);
                                            System.out.println("\n[✅]  Student added successfully.");
                                        }
                                    }
                                }

                                break;
                            case 2:
                                if (studentDAO.getStudentCount() == 0 || moduleDAO.countAllModules() == 0) {
                                    System.out.println("\n[❌]  Please, add at least one student and a module to make an enrollment");
                                } else {
                                    System.out.println("Adding new elements to enrolment table...");

                                    // Get student and module information
                                    Student student = askForStudent(em, scanner);
                                    ModuleFP module = askForModule(em, scanner);

                                    if (module == null || student == null) {
                                        System.out.println("\n[❌]  Please, check if this student exists or module exists.");
                                    } else {

                                        // Check if the enrolment already exists
                                        Enrolment existingEnrolment = enrolmentDAO.getEnrolmentById(student, module);
                                        if (existingEnrolment != null) {
                                            System.out.println("\n[❌]  Enrolment already exists for the given student and module.");
                                        } else {
                                            // Create a new enrolment and persist it
                                            Enrolment newEnrolment = new Enrolment(student, module);
                                            enrolmentDAO.createEnrolment(newEnrolment);
                                            System.out.println("\n[✅]  Enrolment added successfully.");
                                        }
                                    }
                                }
                                break;
                            case 3:
                                // Add Modulo table
                                System.out.println("Adding new elements to module table...");

                                while (true) {
                                    ModuleFP moduleToAdd = askForModuleData(scanner);
                                    if (moduleToAdd == null) {
                                        break;
                                    } else {
                                        moduleDAO.insertModule(Log, moduleToAdd);
                                        System.out.println("\n[✅]  Module added successfully.");
                                    }
                                }

                                break;
                            case 4:
                                // Add Proyecto table
                                if (studentDAO.getStudentCount() == projectDAO.countAllProjects()) {
                                    System.out.println("Add students first and then you will be able to add projects.");
                                } else {
                                    System.out.println("Adding new elements to project table...");
                                    while (true) {
                                        Project projectToAdd = askForProjectData(em, scanner, studentDAO, projectDAO);
                                        if (projectToAdd == null) {
                                            break;
                                        }

                                        projectDAO.insertProject(projectToAdd);
                                        System.out.println("\n[✅]  Project added succesfully");
                                    }
                                }

                                break;

                            case 5:
                                // Add Grupo table
                                System.out.println("Adding new elements to group table...");
                                while (true) {
                                    Group groupToAdd = askForGroupData(scanner);
                                    if (groupToAdd == null) {
                                        break;
                                    }

                                    groupDAO.insertGroup(Log, groupToAdd);
                                    System.out.println("\n[✅]  Group added successfully.");
                                }

                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("\n[❌]  Invalid table option.");
                        }

                        pressAnyKeyToContinue();

                        break;
                    case 3:
                        // List Elements
                        int listTableOption;

                        showTableMenu();
                        listTableOption = getIntInput(scanner);

                        switch (listTableOption) {
                            case 1:

                                selectElementsListOption();
                                int listStudentOption = getIntInput(scanner);

                                switch (listStudentOption) {
                                    case 1:
                                        ArrayList<Integer> studentIds = askAndStoreIDInArray(scanner, true);

                                        for (int studentId : studentIds) {
                                            Student individualStudent = studentDAO.getStudentById(studentId);

                                            if (individualStudent == null) {
                                                System.out.println("\n[❌]  Student with ID " + studentId + " doesn't exist or something went wrong!");
                                            } else {
                                                studentHeader();
                                                System.out.println(individualStudent.toString());

                                                List<Enrolment> enrolments = individualStudent.getEnrolments();
                                                listEnrolmentsAndProjectsForStudent(individualStudent, enrolments);
                                                System.out.println("+-------------+--------------+-----------------------+----------------------+");
                                            }
                                        }

                                        break;
                                    case 2:
                                        System.out.println("Listing elements from student table...");
                                        List<Student> studentList = studentDAO.getAllStudents();

                                        if (studentList.isEmpty()) {
                                            System.err.println("\n[❌]  There are no students. Add new students");
                                        } else {
                                            studentHeader();
                                            for (Student student : studentList) {
                                                System.out.println(student.toString());

                                                List<Enrolment> enrolments = student.getEnrolments();
                                                listEnrolmentsAndProjectsForStudent(student, enrolments);
                                                System.out.println("+-------------+--------------+-----------------------+----------------------+");
                                            }
                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid list option.");

                                }
                                break;
                            case 2:
                                // List elements from Matricula table

                                selectElementsListOption();
                                int listEnrolmentOption = getIntInput(scanner);

                                switch (listEnrolmentOption) {
                                    case 1:
                                        ArrayList<Integer> enrolmentIds = askAndStoreIDInArray(scanner, true);

                                        for (int enrolmentId : enrolmentIds) {
                                            Enrolment individualEnrolment = enrolmentDAO.getEnrolmentsByStudentId(enrolmentId);

                                            if (individualEnrolment == null) {
                                                System.out.println("\n[❌]  Enrolment " + enrolmentId + " doesn't exist or something went wrong!");
                                            } else {
                                                enrolmentHeader();
                                                System.out.println(individualEnrolment.toString());
                                            }
                                        }

                                        break;
                                    case 2:
                                        System.out.println("Listing elements from enrolment table...");
                                        List<Enrolment> enrolmentList = enrolmentDAO.getAllEnrolments();

                                        if (enrolmentList.isEmpty()) {
                                            System.err.println("\n[❌]  There are no enrolments. Add new enrolments.");
                                        } else {
                                            enrolmentHeader();
                                            for (Enrolment enrolment : enrolmentList) {
                                                System.out.println(enrolment.toString());
                                            }
                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid list option.");

                                }
                                break;
                            case 3:
                                // List elements from Modulo table

                                selectElementsListOption();
                                int listModuleOption = getIntInput(scanner);

                                switch (listModuleOption) {
                                    case 1:
                                        ArrayList<Integer> moduleIds = askAndStoreIDInArray(scanner, false);

                                        for (int moduleId : moduleIds) {
                                            ModuleFP individualModule = moduleDAO.getModuleById(moduleId);

                                            if (individualModule == null) {
                                                System.out.println("\n[❌]  Module " + moduleId + " doesn't exist or something went wrong!");
                                            } else {
                                                moduleHeader();
                                                System.out.println(individualModule);

                                                // List enrolments for the current module
                                                List<Enrolment> enrolments = individualModule.getEnrolments();
                                                listEnrolmentsRealtionatedWithModule(individualModule, enrolments);
                                            }
                                        }

                                        break;
                                    case 2:
                                        System.out.println("Listing elements from module table...");
                                        List<ModuleFP> moduleList = moduleDAO.selectAll();

                                        if (moduleList.isEmpty()) {
                                            System.err.println("\n[❌]  There are no modules. Add new modules");
                                        } else {
                                            moduleHeader();
                                            for (ModuleFP module : moduleList) {
                                                System.out.println(module.toString());

                                                // List enrolments for the current module
                                                List<Enrolment> enrolments = module.getEnrolments();
                                                listEnrolmentsRealtionatedWithModule(module, enrolments);
                                            }
                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid list option.");

                                }
                                break;
                            case 4:
                                // List elements from Proyecto table

                                selectElementsListOption();
                                int listProjectOption = getIntInput(scanner);

                                switch (listProjectOption) {
                                    case 1:
                                        ArrayList<Integer> projectIds = askAndStoreIDInArray(scanner, false);

                                        for (int projectId : projectIds) {
                                            Project individualProject = projectDAO.selectProjectById(projectId);

                                            if (individualProject == null) {
                                                System.out.println("\n[❌]  Project " + projectId + " doesn't exist or something went wrong!");
                                            } else {
                                                projectHeader();
                                                System.out.println(individualProject);

                                                Student student = individualProject.getStudent();
                                                listStudentsRealtionatedWithProjects(individualProject, student);
                                            }
                                        }

                                        break;
                                    case 2:
                                        System.out.println("Listing elements from project table...");
                                        List<Project> projectList = projectDAO.selectAll();

                                        if (projectList.isEmpty()) {
                                            System.err.println("\n[❌]  No projects. Add new projects.");
                                        } else {
                                            projectHeader();
                                            for (Project project : projectList) {
                                                System.out.println(project.toString());

                                                Student student = project.getStudent();
                                                listStudentsRealtionatedWithProjects(project, student);
                                            }

                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid list option.");

                                }

                                break;
                            case 5:
                                // List elements from Group table

                                selectElementsListOption();
                                int selectedGroupOption = getIntInput(scanner);

                                switch (selectedGroupOption) {
                                    case 1:
                                        ArrayList<Integer> groupIds = askAndStoreIDInArray(scanner, false);

                                        for (int groupId : groupIds) {
                                            Group individualGroup = groupDAO.selectGroupById(groupId);

                                            if (individualGroup == null) {
                                                System.out.println("\n[❌]  Group " + groupId + " doesn't exist or something went wrong!");
                                            } else {
                                                groupHeader();
                                                System.out.println(individualGroup);

                                                List<Student> students = individualGroup.getStudents();
                                                listStudentsRealtionatedWithGroup(individualGroup, students);
                                            }
                                        }

                                        break;

                                    case 2:
                                        List<Group> groupList = groupDAO.selectAll();

                                        if (groupList.isEmpty()) {
                                            System.err.println("\n[❌]  There are no groups. Add new Groups");
                                        } else {
                                            groupHeader();
                                            for (Group group : groupList) {
                                                System.out.println(group.toString());

                                                // Print students for the current group
                                                List<Student> students = group.getStudents();
                                                listStudentsRealtionatedWithGroup(group, students);
                                            }
                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid list option.");
                                }

                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("\n[❌]  Invalid list option.");
                        }

                        pressAnyKeyToContinue();

                        break;
                    case 4:
                        // Delete Items
                        int deleteTableOption;

                        showTableMenu();
                        deleteTableOption = getIntInput(scanner);

                        switch (deleteTableOption) {
                            case 1:
                                // Delete items from Alumno table
                                getDeleteOption(false);
                                int deleteStudentOption = getIntInput(scanner);

                                switch (deleteStudentOption) {
                                    case 1:
                                        List<Student> allStudents = studentDAO.getAllStudents();
                                        System.out.println("List of Students: ⬇️");
                                        displayStudentList(allStudents);

                                        int deletedStudents = 0;
                                        ArrayList<Integer> studentsToDelete = askAndStoreGroupIdsInArray(scanner, true);

                                        for (int studentNIA : studentsToDelete) {
                                            if (studentDAO.deleteStudent(studentNIA)) {
                                                System.out.println("[✅] Student " + studentNIA + " successfully deleted.");
                                                deletedStudents++;
                                            }
                                        }

                                        if (deletedStudents == studentsToDelete.size() && deletedStudents != 0) {
                                            System.out.println("\n[✅]  All Students deleted successfully.");
                                        } else {
                                            System.out.println("\n[❌]  Not All Students were deleted.");

                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid delete option.");
                                }

                                break;
                            case 2:
                                System.out.println("\nYou are going to unenroll a student: ");

                                Student studentToDelete = askForStudent(em, scanner);
                                ModuleFP moduleToDelete = (studentToDelete != null) ? askForModule(em, scanner) : null;

                                if (moduleToDelete == null || studentToDelete == null) {
                                    System.out.println("\n[❌]  Please, check if this student exists or module exists.");
                                } else {
                                    Enrolment enrolmentToDelete = enrolmentDAO.getEnrolmentById(studentToDelete, moduleToDelete);

                                    if (enrolmentToDelete != null) {
                                        enrolmentDAO.deleteEnrolment(enrolmentToDelete);
                                        System.out.println("[✅]  Enrolment deleted successfully.");
                                    } else {
                                        System.out.println("[❌]  Enrolment not found or something went wrong.");
                                    }
                                }

                                break;
                            case 3:
                                // Delete items from Modulo table
                                getDeleteOption(true);
                                int deleteModuleOption = getIntInput(scanner);

                                switch (deleteModuleOption) {
                                    case 1:

                                        List<ModuleFP> allModules = moduleDAO.selectAll();
                                        System.out.println("List of Modules: ⬇️");
                                        displayModuleList(allModules);

                                        int deletedModules = 0;
                                        ArrayList<Integer> modulesToDelete = askAndStoreGroupIdsInArray(scanner, false);

                                        for (int moduleId : modulesToDelete) {
                                            if (moduleDAO.deleteModule(moduleId)) {
                                                System.out.println("[✅]  Module " + moduleId + " successfully deleted.");
                                                deletedModules++;
                                            } else {
                                                System.out.println("\n[❌]  Error. Module " + moduleId + " doesn't exist or something went wrong.");
                                            }
                                        }

                                        if (deletedModules == modulesToDelete.size() && deletedModules != 0 ) {
                                            System.out.println("\n[✅]  All modules deleted successfully.");
                                        } else if(deletedModules != 0 ) {
                                            System.out.println("\n[❌]  Oops! There was an error while trying to delete some modules.");
                                        }

                                        break;

                                    case 2:
                                        // Delete all modules
                                        int allDeletedModules = moduleDAO.deleteAllModules();

                                        if (allDeletedModules == 0) {
                                            System.out.println("\n[❌]  No module was deleted");
                                        } else {
                                            System.out.println("\n[✅]  " + allDeletedModules + " modules have been deleted");
                                        }
                                        break;
                                    case 0:

                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid delete option.");
                                }

                                break;
                            case 4:
                                // Delete items from Proyecto table
                                System.out.println("Deleting items from project table...");
                                getDeleteOption(true);
                                int deleteProjectOption = getIntInput(scanner);

                                switch (deleteProjectOption) {
                                    case 1:

                                        List<Project> allProjects = projectDAO.selectAll();
                                        System.out.println("List of Projects: ⬇️");
                                        displayProjectList(allProjects);

                                        int deletedProjects = 0;
                                        ArrayList<Integer> projectsToDelete = askAndStoreGroupIdsInArray(scanner, false);

                                        for (int projectId : projectsToDelete) {
                                            if (projectDAO.deleteProject(projectId)) {
                                                System.out.println("\n[✅]  Project " + projectId + " deleted succesfully.");
                                                deletedProjects++;
                                            } else {
                                                System.out.println("\n[❌]  Error. The project " + projectId + " doesn't exist or something went wrong.");
                                            }
                                        }

                                        if (deletedProjects == projectsToDelete.size() && deletedProjects != 0 ) {
                                            System.out.println("\n[✅]  All projects deleted succesfully.");
                                        }else if (deletedProjects != 0){
                                            System.out.println("\n[❌]  Oops! There was an error while trying to delete some projects.");
                                        }

                                        break;

                                    case 2:
                                        // Delete all projects
                                        int allDeletedProjects = projectDAO.deleteAllProjects();

                                        if (allDeletedProjects == 0) {
                                            System.out.println("\n[❌]  No project has been deleted");
                                        } else {
                                            System.out.println("\n[✅]  " + allDeletedProjects + " projects has been deleted.");
                                        }
                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid table option.");
                                }

                                break;
                            case 5:
                                // Delete items from Grupo table
                                getDeleteOption(false);
                                int deleteGroupOption = getIntInput(scanner);

                                switch (deleteGroupOption) {
                                    case 1:

                                        List<Group> allGroups = groupDAO.selectAll();
                                        System.out.println("List of Groups: ⬇️");
                                        displayGroupList(allGroups);

                                        int deletedGroups = 0;
                                        ArrayList<Integer> groupsToDelete = askAndStoreGroupIdsInArray(scanner, false);

                                        for (int groupId : groupsToDelete) {
                                            if (groupDAO.deleteGroup(groupId)) {
                                                System.out.println("\n[✅]  Group " + groupId + " successfully deleted.");
                                                deletedGroups++;
                                            } else {
                                                System.out.println("\n[❌]  Error. Group " + groupId + " doesn't exist or something went wrong.");
                                            }
                                        }

                                        if (deletedGroups == groupsToDelete.size() && deletedGroups != 0) {
                                            System.out.println("\n[✅]  All groups deleted successfully.");
                                        }else if(deletedGroups != 0){
                                            System.out.println("\n[❌]  Oops! There was an error while trying to delete some groups.");
                                        }

                                        break;
                                    case 0:
                                        break;
                                    default:
                                        System.out.println("\n[❌]  Invalid delete option.");
                                }

                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("\n[❌]  Invalid table option.");
                        }

                        pressAnyKeyToContinue();

                        break;
                    case 0:
                        printGoodbyeArt();
                        break;
                    default:
                        System.out.println("\n[❌]  Invalid option. Please choose a valid option.");
                }

            } while (option != 0);
        }
    }
}
