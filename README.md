# GlobeMed - Hospital Management System

A comprehensive Java Swing-based hospital management system that implements various design patterns for billing, insurance claims, and medical reports.

## 🏥 Project Overview

GlobeMed is a desktop application built with Java Swing and Hibernate ORM that provides a complete hospital management solution. The system features a modern UI with FlatLaf theme and implements several design patterns to ensure maintainable and extensible code.

## ✨ Features

### Core Functionality
- **Patient Management** - Add, edit, and manage patient records
- **Appointment Scheduling** - Schedule and manage doctor appointments
- **Staff Management** - Manage doctors, nurses, and other staff members
- **Billing System** - Generate and process bills with insurance support
- **Medical Reports** - Create and view detailed medical reports
- **Financial Reports** - Generate comprehensive financial summaries

### Design Patterns Implemented

#### 1. Bridge Pattern (Billing & Insurance)
- **Purpose**: Separates billing abstraction from implementation
- **Components**:
  - `BillingSystem` (Abstraction)
  - `StandardBillingSystem` (Refined Abstraction)
  - `BillingImplementor` (Implementation)
  - `DirectBilling` & `InsuranceBilling` (Concrete Implementations)

#### 2. Visitor Pattern (Medical Reports)
- **Purpose**: Allows adding new operations to existing objects without modifying their structure
- **Components**:
  - `ReportVisitor` (Visitor Interface)
  - `TreatmentSummaryVisitor` (Concrete Visitor)
  - `FinancialReportVisitor` (Concrete Visitor)
  - `ReportElement` (Element Interface)

#### 3. Additional Patterns
- **Composite Pattern** - Staff management hierarchy
- **Decorator Pattern** - Sign-in functionality with logging and encryption
- **Mediator Pattern** - Appointment scheduling coordination
- **Memento Pattern** - Patient state management
- **Factory Pattern** - Object creation

## 🛠️ Technology Stack

- **Java 17** - Core programming language
- **Java Swing** - GUI framework
- **Hibernate ORM 6.4.4** - Database persistence
- **MySQL 8.0** - Database management system
- **FlatLaf 3.6.1** - Modern UI theme
- **Maven** - Build and dependency management
- **SLF4J** - Logging framework

## 📋 Prerequisites

Before running the application, ensure you have:

- **Java JDK 17** or higher
- **MySQL 8.0** or higher
- **Maven 3.6** or higher
- **Git** (for cloning the repository)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd GlobeMed
```

### 2. Database Setup
1. Create a MySQL database:
```sql
CREATE DATABASE globemed_db;
```

2. Update database configuration in `src/main/resources/hibernate.cfg.xml`:
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/globemed_db?useSSL=false</property>
<property name="hibernate.connection.username">your_username</property>
<property name="hibernate.connection.password">your_password</property>
```

### 3. Build the Project
```bash
mvn clean compile
```

### 4. Run the Application
```bash
mvn exec:java -Dexec.mainClass=com.disuraaberathna.globemed.App
```

## 🎯 Usage Guide

### Initial Setup
1. **Launch Application**: Run the main class to start GlobeMed
2. **Sign In**: Use default credentials or create new user accounts
3. **Database Initialization**: Tables will be created automatically on first run

### Key Features Usage

#### Billing System
1. **Navigate to Billing**: Click "Billing" from the main dashboard
2. **Create Bill**:
   - Select a patient from the dropdown
   - Choose an appointment
   - Select payment type (Direct/Insurance)
   - Enter amount
   - Click "Generate Bill"
3. **Process Payment**:
   - Select a bill from the table
   - Choose payment type
   - Click "Process Bill"

#### Medical Reports
1. **Navigate to Reports**: Click "Reports" from the main dashboard
2. **Generate Reports**:
   - Select a patient
   - Choose report type (Treatment Summary/Financial Report)
   - Click "Generate Report"
3. **Create Medical Report**:
   - Click "Create Medical Report" button
   - Fill in patient, doctor, diagnosis, treatment, and price
   - Click "Create Report"

#### Patient Management
1. **Add Patient**: Fill in patient details and save
2. **Edit Patient**: Select patient and modify details
3. **View Records**: Browse patient history and appointments

#### Appointment Scheduling
1. **Schedule Appointment**: Select doctor, patient, date, and time
2. **Manage Appointments**: View, edit, or cancel existing appointments

## 🏗️ Project Structure

```
src/main/java/com/disuraaberathna/globemed/
├── App.java                          # Main application entry point
├── controller/                       # MVC Controllers
│   ├── BillingController.java       # Billing system controller
│   ├── ReportsController.java       # Reports generation controller
│   ├── MedicalReportController.java # Medical report management
│   └── ...
├── model/
│   ├── dao/                         # Data Access Objects
│   │   ├── BillDAO.java
│   │   ├── PatientDAO.java
│   │   └── ...
│   ├── entity/                      # JPA Entities
│   │   ├── Bill.java
│   │   ├── Patient.java
│   │   └── ...
│   └── service/                     # Business Logic & Design Patterns
│       ├── brigde/                  # Bridge Pattern Implementation
│       ├── visitor/                 # Visitor Pattern Implementation
│       ├── composite/               # Composite Pattern
│       ├── decorator/               # Decorator Pattern
│       └── ...
├── view/                            # Swing UI Components
│   ├── BillingView.java
│   ├── ReportsView.java
│   ├── MedicalReportView.java
│   └── ...
└── util/                            # Utility Classes
    ├── HibernateUtil.java
    └── LoggerUtil.java
```

## 🔧 Configuration

### Database Configuration
Edit `src/main/resources/hibernate.cfg.xml`:
```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/globemed_db</property>
<property name="hibernate.connection.username">your_username</property>
<property name="hibernate.connection.password">your_password</property>
```

### Logging Configuration
Logs are written to `globemed.log` in the project root directory.

## 🎨 UI Theme

The application uses **FlatLaf** theme for a modern, professional appearance:
- Consistent color scheme (blue, gray, white)
- Comic Sans MS font throughout
- Professional layout with proper spacing
- Responsive design elements

## 🔒 Security Features

- **User Authentication**: Role-based access control
- **Password Encryption**: Secure password handling
- **Session Management**: Proper user session handling
- **Input Validation**: Comprehensive form validation

## 📊 Database Schema

### Key Tables
- `users` - Staff and user accounts
- `patients` - Patient information
- `appointments` - Appointment scheduling
- `bills` - Billing records
- `medical_reports` - Medical report data
- `insurance` - Insurance provider information

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `hibernate.cfg.xml`
   - Ensure database `globemed_db` exists

2. **Compilation Errors**
   - Ensure Java 17+ is installed
   - Run `mvn clean compile`
   - Check Maven dependencies

3. **UI Display Issues**
   - Verify FlatLaf dependency is included
   - Check Java version compatibility

### Logs
Check `globemed.log` for detailed error information and debugging.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Disura Aberathna** - Initial work

## 🙏 Acknowledgments

- **FlatLaf** team for the modern UI theme
- **Hibernate** team for the ORM framework
- **MySQL** team for the database system

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the logs for error details

---

**GlobeMed** - Modern Hospital Management System with Design Patterns
