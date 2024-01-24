/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.farmaciaislamelmrabetlarhzaoui;

import ConexionBD.FarmaciaDAO;
import ConexionBD.MedicamentDAO;
import ConexionBD.MetgeDAO;
import ConexionBD.PacientDAO;
import ConexionBD.PrescripcioDAO;
import ConexionBD.TractapacientDAO;
import ConexionBD.VenDAO;
import Domain.Adresa;
import Domain.Farmacia;
import Domain.Medicament;
import Domain.Metge;
import Domain.Pacient;
import Domain.Prescriu;
import Domain.Tractapacient;
import Domain.Ven;
import static Manager.FarmaciaManager.insertarFarmaciaYAdresa;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author islam
 */
public class FarmaciaIslamElMrabetLarhzaoui {
    static Scanner sc = new Scanner(System.in);
    
    private static Pacient crearPacient(Scanner sc) {
        sc.nextLine(); 
        System.out.println("Ingrese los datos del paciente:");
        System.out.print("DNI: ");
        String DNI = sc.nextLine();
        System.out.print("Nom: ");
        String Nom = sc.nextLine();
        System.out.print("Cognoms: ");
        String Cognoms = sc.nextLine();

        return new Pacient(DNI, Nom, Cognoms);
    }
    
    private static Metge crearMetge(Scanner sc){
        sc.nextLine();
        System.out.println("Ingrese los datos del medico: ");
        System.out.print("Numero de colegiado: ");
        int numCollegiat = sc.nextInt();
        sc.nextLine();
        System.out.print("Especialidad: ");
        String especialitat = sc.nextLine();
        System.out.print("Nombre: ");
        String nom = sc.nextLine();
        System.out.print("Apellidos: ");
        String cognom = sc.nextLine();
        
        return new Metge(numCollegiat,especialitat,nom,cognom);
    }
    
    private static Tractapacient crearTractament(Scanner sc) throws SQLException{
        sc.nextLine(); 
        System.out.println("Ingrese los detalles del tratamiento:");
        int numColegiatMetge;
        String dniPacient;
        Date fechaTractament;

        do {
            System.out.print("Numero de Colegiado del Medico: ");
            numColegiatMetge = sc.nextInt();
            if (!metgeExisteix(numColegiatMetge)) {
                System.out.println("El numero de colegiado del medico no existe en la base de datos. Intente nuevamente.");
            }
        } while (!metgeExisteix(numColegiatMetge));

        do {
            sc.nextLine(); 
            System.out.print("DNI del Paciente: ");
            dniPacient = sc.nextLine();
            if (!pacientExisteix(dniPacient)) {
                System.out.println("El DNI del paciente no existe en la base de datos. Intente nuevamente.");
            }
        } while (!pacientExisteix(dniPacient));

        System.out.print("Fecha del Tratamiento (YYYY-MM-DD): ");
        String fecha = sc.nextLine();
        fechaTractament = Date.valueOf(fecha);

        return new Tractapacient(dniPacient, numColegiatMetge, fechaTractament);
        
    }
    
    private static Ven crearVenta(Scanner sc) throws SQLException {
        sc.nextLine(); 

        // Solicitar los detalles de la venta
        System.out.println("Ingrese los detalles de la venta:");
        System.out.print("Nombre Comercial del Medicamento: ");
        String nomComercial = sc.nextLine();

        // Comprobar si el medicamento existe
        if (!medicamentExisteix(nomComercial)) {
            System.out.println("El medicamento ingresado no existe. Por favor, inténtelo de nuevo.");
            return crearVenta(sc); 
        }

        System.out.print("CIF de la Farmacia: ");
        String cif = sc.nextLine();

        // Comprobar si la farmacia existe
        if (!farmaciaExisteix(cif)) {
            System.out.println("La farmacia ingresada no existe. Por favor, inténtelo de nuevo.");
            return crearVenta(sc); 
        }

        Date fechaVenta = new Date(System.currentTimeMillis());

        System.out.print("Cantidad Vendida: ");
        String cantidad = sc.nextLine();

        return new Ven(nomComercial, cif, fechaVenta, cantidad);
    }
    
    private static Prescriu crearPrescripcio(Scanner sc){
        // Obtén los datos para la prescripción
        sc.nextLine(); 
        System.out.print("DNI del paciente: ");
        String dniPaciente = sc.nextLine();
        System.out.print("Número de colegiado del médico: ");
        int numColegiadoMedico = sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre comercial del medicamento: ");
        String nomComercialMedicamento = sc.nextLine();

        // Obtiene la fecha y hora actual
        Date fechaPrescripcion = new Date(System.currentTimeMillis());

        System.out.print("Cantidad: ");
        int cantidad = sc.nextInt();

        // Crea un objeto Prescriu con los datos ingresados
        return new Prescriu(dniPaciente, numColegiadoMedico, nomComercialMedicamento, fechaPrescripcion, cantidad);

    }
    
    private static Medicament crearMedicament(Scanner sc){ 
        sc.nextLine(); // Limpia el búfer de nueva línea
        System.out.println("Ingrese los datos del medicamento:");
        System.out.print("Nombre Comercial: ");
        String nombreComercial = sc.nextLine();
        System.out.print("Fórmula: ");
        String formula = sc.nextLine();


        return new Medicament(nombreComercial, formula);
    }
    
    private static boolean pacientExisteix(String dni) throws SQLException {
        List<Pacient> pacientsExistents = PacientDAO.seleccionar();
        for (Pacient paciente : pacientsExistents) {
            if (paciente.getdNI().equals(dni)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean metgeExisteix(int numCollegiat) throws SQLException {
        List<Metge> metgesExistents = MetgeDAO.seleccionar();
        for (Metge metge : metgesExistents) {
            if (metge.getNumCollegiat() == (numCollegiat)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean farmaciaExisteix(String cif) throws SQLException {
    List<Farmacia> farmaciasExistentes = FarmaciaDAO.seleccionar();
    for (Farmacia farmacia : farmaciasExistentes) {
        if (farmacia.getCif().equals(cif)) {
            return true;
        }
    }
    return false;
}
    
    private static boolean medicamentExisteix(String nomComercial) throws SQLException {
        List<Medicament> medicamentExistents = MedicamentDAO.seleccionarMedicaments();
        for (Medicament medicaments : medicamentExistents) {
            if (medicaments.getNomComercial().equals(nomComercial)) {
                return true;
            }
        }
        return false;
    }
    
    public void menu(){
        System.out.println("""
                           
        Menu
        ===============
        1. Insertar
        2. Actualizar
        3. Eliminar
        4. Mostrar
        5. Salir
                                       
        """);
    }
    
    public void menuInstertar(){
        System.out.println(
        """
        
        Menu
        ===============
        1. Insertar un medico
        2. Insertar una farmacia
        3. Insertar un paciente
        4. Insertar un farmaceutico
        5. Insertar un medicamento
        6. Insertar un tratamiento
        7. Insertar una prescripcion
        8. Insertar una venta
                           
        """);
    }
    
    public void menuActualizar(){
        System.out.println(
        """
        
        Menu
        ===============
        1. Actualizar un medico
        2. Actualizar una farmacia
        3. Actualizar un paciente
        4. Actualizar un farmaceutico
                           
        """);
    }
    
    public void menuEliminar(){
        System.out.println(
        """
        
        Menu
        ===============
        1. Eliminar un medico
        2. Eliminar una farmacia
        3. Eliminar un paciente
        4. Eliminar un farmaceutico
        5. Eliminar un medicamento
        6. Eliminar ventas
                           
        """);
    }
    
    public void menuMostrar() {
        System.out.println(
        """
        Menú
        ===============
        1. Mostrar medicos
        2. Mostrar farmacias
        3. Mostrar pacientes
        4. Mostrar farmaceuticos
        5. Mostrar medicamentos
        6. Mostrar Pacientes tratados
        7. Mostrar prescripciones 
        8. Mostrar ventas
        """
        );
    }
    
    public static void main(String[] args) throws SQLException {
        FarmaciaDAO farmaciaDAO = new FarmaciaDAO();
        FarmaciaIslamElMrabetLarhzaoui app = new FarmaciaIslamElMrabetLarhzaoui();
        
        int opcion;
        
        System.out.println("Bienvenido a la base de datos Farmacia, selecciona que desea hacer");
        
        do {
            app.menu();
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            
            switch (opcion) {
                case 1:
                    // Logica para insertar
                    app.menuInstertar();
                    int opcionInsertar = sc.nextInt();
                    switch (opcionInsertar) {  
                        case 1:
                           //Insertar un medico
                           Metge metge = crearMetge(sc);

                            try {
                                MetgeDAO metgeDAO = new MetgeDAO();
                                int registrosInsertados = metgeDAO.insertar(metge);

                                if (registrosInsertados > 0) {
                                    System.out.println("Medico insertado correctamente.");
                                } else {
                                    System.out.println("No se pudo insertar al medico.");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace(System.out);
                            }
                            break;
                        case 2:
                            // Solicitar datos de Adresa
                            System.out.print("Código Postal: ");
                            int codi_Postal = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Provincia: ");
                            String provincia = sc.nextLine();
                            System.out.print("Calle: ");
                            String calle = sc.nextLine();
                            System.out.print("Número de Calle: ");
                            int numCarrer = sc.nextInt();
                            sc.nextLine();
                            // Solicitar el CIF de la farmacia
                            System.out.print("Ingrese el CIF de la farmacia: ");
                            String cif = sc.nextLine();
                            Adresa adresa = new Adresa(codi_Postal,provincia,calle,numCarrer,cif);//Crea un objeto adresa
                            insertarFarmaciaYAdresa(cif,adresa);
                            break;
                        case 3:
                            //Insertar un paciente
                            Pacient pacient = crearPacient(sc);

                            try {
                                int registrosInsertadosPacients = PacientDAO.insertar(pacient);

                                if (registrosInsertadosPacients > 0) {
                                    System.out.println("Paciente insertado correctamente.");
                                } else {
                                    System.out.println("No se pudo insertar al paciente.");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace(System.out);
                            }
                            break;
                        case 4:
                            // Insertar un farmaceutico
                            break;
                        case 5:
                            // Insertar un medicament
                            Medicament medicament = crearMedicament(sc);
                            
                            int registrosInsertadosMedicaments = MedicamentDAO.insertarMedicament(medicament);
                            if (registrosInsertadosMedicaments > 0) {
                                System.out.println("Medicamento insertado correctamente.");
                            } else {
                                System.out.println("No se pudo insertar el medicamento.");
                            }
                            break;
                        case 6:
                            // Insertar un tratamiento
                            Tractapacient tractament = crearTractament(sc);
                            
                            int registrosInsertadosTractaments = TractapacientDAO.insertar(tractament);

                            if (registrosInsertadosTractaments > 0) {
                                System.out.println("Tratamiento insertado correctamente.");
                            } else {
                                System.out.println("No se pudo insertar el tratamiento.");
                            }
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                            
                        case 7:
                            // Insertar una prescripcion 
                            Prescriu prescripcio = crearPrescripcio(sc);
                            int registrosInsertadospreesc = PrescripcioDAO.insertar(prescripcio);
                            
                            if (registrosInsertadospreesc > 0) {
                                System.out.println("Prescripción insertada correctamente.");
                            } else {
                                System.out.println("No se pudo insertar la prescripción.");
                            }
                            break;
                        case 8:
                            // Insertar una venta
                            Ven venta = crearVenta(sc);
                            
                            int registrosVentasRealizadas = VenDAO.insertar(venta);
                            if(registrosVentasRealizadas > 0){
                                System.out.println("Venta realizada correctamente");
                            }else{
                                System.out.println("Venta no realizada");
                            }
                            
                        break;
                    }
                    break;
                case 2:
                    // Logica para actualizar
                    app.menuActualizar();
                    int opcionActualizar = sc.nextInt();
                    switch (opcionActualizar) {
                        case 1:
                            // Actualizar un medico
                            System.out.print("Ingrese el número de colegiado del médico que desea actualizar: ");
                            int numCollegiat = sc.nextInt();
                            
                            if(metgeExisteix(numCollegiat)){
                                sc.nextLine(); 
                                System.out.println("Ingrese los nuevos datos para el médico:");
                                System.out.print("Nombre: ");
                                String nouNomMetge = sc.nextLine();
                                System.out.print("Apellidos: ");
                                String noucognomMetge = sc.nextLine();
                                System.out.print("Especialidad: ");
                                String nouEspecialitat = sc.nextLine();
                                
                                Metge metgeActualitzat = new Metge();
                                
                                int registreActualitzat = MetgeDAO.actualizar(metgeActualitzat);
                                
                                if (registreActualitzat > 0) {
                                    System.out.println("Médico actualizado correctamente.");
                                } else {
                                    System.out.println("No se pudo actualizar el médico.");
                                }
                            } else {
                                System.out.println("El médico con número de colegiado " + numCollegiat + " no fue encontrado.");
                            }
                            break;
                        case 2:
                            // Actualizar una farmacia
                            
                            break;
                        case 3:
                            //Actualiza un paciente
                            sc.nextLine(); 
                            System.out.print("Ingrese el DNI del paciente que desea actualizar: ");
                            String dniActualtizar = sc.nextLine();
                            
                            if (pacientExisteix(dniActualtizar)) {
                                // Solicitar los nuevos datos del paciente
                                System.out.println("Ingrese los nuevos datos del paciente:");
                                System.out.print("Nombre: ");
                                String nouNomPacient = sc.nextLine();
                                System.out.print("Apellidos: ");
                                String nouCognomPacient = sc.nextLine();

                                // Crear un objeto Pacient con los nuevos datos
                                Pacient pacienteActualizado = new Pacient(dniActualtizar, nouNomPacient, nouCognomPacient);

                                // Llamar a la funcion de actualización en PacientDAO
                                int registrosActualizados = PacientDAO.actualizar(pacienteActualizado);

                                if (registrosActualizados > 0) {
                                    System.out.println("Paciente actualizado correctamente.");
                                } else {
                                    System.out.println("No se pudo actualizar el paciente.");
                                }
                            } else {
                                System.out.println("El paciente con DNI " + dniActualtizar + " no se encuentra en la base de datos.");
                            }
                            break;
                        case 4:
                            // Actualizar un farmaceutico
                            
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                    break;
                case 3:
                    // Logica para eliminar
                    app.menuEliminar();
                    int opcionEliminar = sc.nextInt();
                    switch (opcionEliminar) {
                        case 1:
                            // Eliminar un medico
                            System.out.println("Ingrese el numero de colegiado del medico que desea eliminar");
                            sc.nextLine();
                            int numCollegiat = sc.nextInt();
                            
                            if(metgeExisteix(numCollegiat)){
                                int registroEliminadas = MetgeDAO.eliminar(numCollegiat);
                            
                                if(registroEliminadas > 0){
                                    System.out.println("Meidco eliminado correctamente");
                                }else{
                                    System.out.println("Medico no eliminado");

                                }
                            }else{
                                System.out.println("El medico no existe en la base de datos");
                            }
                                  
                            break;
                        case 2:
                            // Eliminar una farmacia
                            System.out.println("Ingrese el cif de la farmacia que desea eliminar");
                            sc.nextLine();
                            String cifFarmacia = sc.nextLine();
                            
                            int registroEliminadas = farmaciaDAO.eliminar(cifFarmacia);
                            
                            if(registroEliminadas > 0){
                                System.out.println("Farmacia eliminada correctamente");
                            }else{
                                System.out.println("Farmacia no eliminada");

                            }
                                  
                            break;
                        case 3:
                           //Eliminar un paciente
                           System.out.print("Ingrese el DNI del paciente que desea eliminar: ");
                           sc.nextLine();
                           String dniEliminar = sc.nextLine();
                           
                           if(pacientExisteix(dniEliminar)){
                                int pacientsEliminat = PacientDAO.eliminar(dniEliminar);
                                if(pacientsEliminat > 0){
                                    System.out.println("Paciente eliminado correctamente");

                                }else {
                                    System.out.println("Paciente no eliminado");
                                }
                            }else{
                              System.out.println("El dni : "+ dniEliminar + " no existe en la base de datos");
                           }
                           break; 
                        case 4:
                            // Eliminar un farmaceutico
                            
                            break;
                        case 5:
                            // Eliminar un medicamento
                            sc.nextLine();
                            System.out.print("Ingrese el nombre comercial del medicamento: ");
                            String nomComercialEliminar = sc.nextLine();
                            
                            if(medicamentExisteix(nomComercialEliminar)){
                                int registremedicamentsEliminats = MedicamentDAO.eliminarMedicament(nomComercialEliminar);
                                if(registremedicamentsEliminats > 0){
                                    System.out.println("Medicamento eliminado correctamente");

                                }else {
                                    System.out.println("Medicamento no eliminado");
                                }
                            }else{
                                System.out.println("El medicamento : "+ nomComercialEliminar + " no existe");
                            }
                            break;
                        case 6:
                            System.out.print("Ingrese el CIF de la farmacia para eliminar sus ventas: ");
                            sc.nextLine();
                            String cifFarmaciaEliminar = sc.nextLine();

                            int registrosEliminados = VenDAO.eliminarMedicamentsVenduts(cifFarmaciaEliminar);

                            if (registrosEliminados > 0) {
                                System.out.println("Se han eliminado " + registrosEliminados + " ventas de la farmacia con CIF " + cifFarmaciaEliminar);
                            } else {
                                System.out.println("No se encontraron ventas para la farmacia con CIF " + cifFarmaciaEliminar);
                            }
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                    break;
                case 4:
                     // Logica para mostrar
                    app.menuMostrar();
                    int opcionMostrar = sc.nextInt();
                    switch (opcionMostrar) {
                        case 1:
                            // Mostrar medicos
                            System.out.println("Metges:");
                            MetgeDAO metgeDAO = new MetgeDAO();
                            List<Metge> metges = MetgeDAO.seleccionar();
                            
                            for (Metge metge : metges) {
                                System.out.println("Número de Colegiado: " + metge.getNumCollegiat());
                                System.out.println("Especialidad: " + metge.getEspecialitat());
                                System.out.println("Nombre: " + metge.getNom());
                                System.out.println("Apellidos: " + metge.getCognoms());
                                System.out.println("--------------------------");
                            }
                            break;
                        case 2:
                            //Mostrar farmacias
                            
                            break;
                        case 3:
                            //Mostrar pacientes 
                            System.out.println("Pacientes:");
                            PacientDAO pacientDAO = new PacientDAO();
                            List<Pacient> pacients = PacientDAO.seleccionar();
                            
                            for (Pacient paciente : pacients) {
                                System.out.println("DNI: " + paciente.getdNI());
                                System.out.println("Nombre: " + paciente.getNom());
                                System.out.println("Apellidos: " + paciente.getCognoms());
                                System.out.println("--------------------------");
                            }
                            break;
                        case 4:
                            // Mostrar farmacéuticos
                           
                            break;
                        case 5:
                            // Mostrar medicamentos
                            List<Medicament> medicaments = MedicamentDAO.seleccionarMedicaments();

                            System.out.println("Lista de Medicamentos:");
                            for (Medicament medicament : medicaments) {
                                System.out.println("Nombre Comercial: " + medicament.getNomComercial());
                                System.out.println("Fórmula: " + medicament.getFormula());
                                System.out.println("-----------------------------");
                            }
                            break;
                        case 6:
                           // Mostrar Pacientes tratados
                            System.out.print("Ingrese el numero de colegiado del medico: ");
                            int numeroColegiat = sc.nextInt();

                            TractapacientDAO tractapacientDAO = new TractapacientDAO();
                            List<Tractapacient> pacientesTratados = tractapacientDAO.obtenerPacientesPorMedico(numeroColegiat);

                            System.out.println("Pacientes tratados por el médico con número de colegiado " + numeroColegiat + ":");
                            for (Tractapacient paciente : pacientesTratados) {
                                System.out.println("DNI del Paciente: " + paciente.getDNI());
                                System.out.println("Fecha del Tratamiento: " + paciente.getDates());
                                System.out.println("--------------------------");
                            }
                            break;
                        case 7:
                            // Mostrar preescripcions
                            PrescripcioDAO prescriuDAO = new PrescripcioDAO();
                            List<Prescriu> prescripcions = prescriuDAO.seleccionar();

                            for (Prescriu prescripcio : prescripcions) {
                                System.out.println("DNI del Paciente: " + prescripcio.getDNI());
                                System.out.println("Número de Colegiado del Médico: " + prescripcio.getNumCollegiat());
                                System.out.println("Nombre Comercial del Medicamento: " + prescripcio.getNomComercial());
                                System.out.println("Fecha de Prescripción: " + prescripcio.getDates());
                                System.out.println("Cantidad: " + prescripcio.getQuantitat());
                                System.out.println("------------------------------------------");
                            }
                            break;
                        case 8:
                            VenDAO venDAO = new VenDAO();
                            List<Ven> ventas = venDAO.seleccionar();

                            if (ventas.isEmpty()) {
                                System.out.println("No hay ventas registradas.");
                            } else {
                                System.out.println("Ventas registradas:");
                                for (Ven venta : ventas) {
                                    System.out.println("Nombre Comercial: " + venta.getNomComercial());
                                    System.out.println("CIF de la Farmacia: " + venta.getCif());
                                    System.out.println("Fecha de la Venta: " + venta.getDates());
                                    System.out.println("Cantidad Vendida: " + venta.getQuantitat());
                                    System.out.println("--------------------------");
                                }
                            }
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                        }
                    break;
                case 5:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            
        } while (opcion != 5);
    }
    
}
