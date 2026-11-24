# BookShare – Book Donation and Management System

**Share Books, Share Knowledge!**

## Overview
BookShare is a lightweight desktop application that lets students **donate** and **request** books, while providing an admin interface to monitor all activity. The whole system runs locally with a simple file-based persistence mechanism, making it easy to run without setting up a database.

## Problem Statement
Many campuses and community groups lack a simple way to manage book donations. Existing solutions often require a server, a database, or a complex web stack. BookShare solves this by offering a **pure Java Swing** desktop client that anyone with Java 17 can run, storing data locally in serialized files.

## Features
- **Student portal**
  - Register a new student account
  - Log in with ID & password
  - Donate books (title, author, quantity)
  - Request available books
  - View personal profile and activity history
  - Browse all available books
- **Admin portal**
  - View all registered students
  - See donated books and inventory
  - Browse activity history
  - Log out back to the welcome screen
- **Persistence**
  - All data (students.dat, ooks.dat, ctivities.dat) are saved automatically when the window closes or after each operation.
- **Responsive UI**
  - Custom styled Swing components with a consistent green-gold-cream color palette.

## Technologies Used
| Category | Technology |
|----------|------------|
| Language | Java 17 |
| UI | Swing & AWT |
| Collections | ArrayList |
| Persistence | Java object serialization to flat files (*.dat) |
| Build / Runtime | Plain javac / java (no external build tools required) |

## Project Architecture
`
BookShare/
¦
+- BookDonationSystem.java        ? Main source file (contains UI, models, and logic)
+- *.class                       ? compiled byte-code (generated, ignored by Git)
+- *.dat                         ? runtime data files (ignored by Git)
+- README.md                     ? this documentation
`

### Core Classes (defined inside BookDonationSystem.java)
| Class | Responsibility |
|-------|----------------|
| Student | Serializable holder for student information (name, id, password, email, branch). |
| Book    | Serializable holder for a donated book (title, author, donor id, quantity). |
| BookDonationSystem | Extends JFrame; orchestrates the GUI, loads/saves data, and implements all portal actions. |

## Prerequisites
- **Java 17** (or any later JDK) installed and java/javac available on the system PATH.
- No external libraries or database servers are required.

## Installation & Execution
1. **Clone the repository (once it is on GitHub)**
   `ash
   git clone <repo-url>
   cd BookShare
   `
2. **Compile the source**
   `ash
   javac BookDonationSystem.java
   `
3. **Run the application**
   `ash
   java BookDonationSystem
   `
The first run will create three data files in the project directory (students.dat, ooks.dat, ctivities.dat). They are automatically loaded on subsequent launches.

## Screenshots
*(No screenshots are included in the current source tree.)*

## Future Enhancements (Ideas)
- Export / import data in CSV or JSON format.
- Add search/filter capabilities for books and students.
- Introduce role-based access control with multiple admin accounts.
- Persist data to an embedded database (e.g., SQLite) for larger deployments.
- Provide a Maven/Gradle build script for easier dependency management and packaging.

## Author / Contributors
The project was created by the original developer(s) of the source code. No additional contributors are listed here to avoid inventing names.

---
**© 2026 BookShare Project**
*Share Books, Share Knowledge!*
