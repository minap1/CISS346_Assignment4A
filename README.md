

CISS 346 – Secure Software Development
Assignment 04 – Part A
Messenger: Simple Message Protocol (SMP)

This assignment is designed to give you help understanding the software
development process, the software development process lifecycle, and common
software development process documents.
Project Description
Miss Pudding, the VP of Engineering for Reality Software, has contracted with Never
Crash Software Services to develop a client-server application that communicates
using a simple communications protocol. The communications protocol this project
develops and utilizes is termed the Simple Message Protocol (SMP). Mr. Pumphrey, a
senior project manager with Never Crash Software Services, has been tasked to
oversee this project. He will direct his team to develop the application according to
the defined project requirements. The project requirements, which have yet to be
formally defined, are defined based on feedback from the project’s stakeholders.
The SMP protocol allows client applications to send messages to a SMP server and
retrieve messages from a SMP server. The server application stores the messages in
a datastore, such as an ASCII text file. The datastore is designed to implement the
behavior of a message queue. A queue is a first-in, first-out data structure.
Messages are sent to the SMP server and inserted into the queue in the order
they’re received. The messages are retrieved from the queue in a first-in, first-out
fashion. In other words, the first message inserted in the queue is the first message
retrieved from the queue. The SMP Message Producer client program is designed to
send messages to a server using TCP/IP and the Sockets API (Application
Programming Interface). The SMP Message Consumer client program is designed to
retrieve messages from a server using TCP/IP and the Sockets API (Application
Programming Interface).

Group Project Roles
This is a group assignment. There should be 2-3 members on each team. Your task
is to assign the following roles to the team members.
• Project Manager
• Software Development Engineer
• Software Test Engineer
A Team Member Roles document is located in Canvas->-Files->Assignments. Please
complete the document and email it to me.
NOTE: A team member can assume multiple roles, or the team members can share
the roles.
Software Project Proposal & Software Project Plan
The typical software development lifecycle begins with a Software Project Proposal.
During this phase, the concepts of the software system are first discussed and
formalized into a definable system. From the Software Project Proposal, the
software project manager creates a Software Project Plan.
Software Requirements Specification
The team’s first task is to analyze the SMP Software Project Proposal and the SMP
Software Project Plan. Based on the Software Project Proposal, a Software
Requirements Specification is generally the next artifact that needs to be
developed. The project manager along with various stakeholders has created the
Software Requirements Specification document.
Software Project Interface Prototype
Based on the Software Requirement Specification, the project manager along with
the project development team has defined a UI (User Interface) protype. Typically,
the UI prototype can be defined as either a CLUI (Command-Line User Interface) or
a GUI (Graphical User Interface). In this case, the team has decided to develop a
GUI prototype.
Software Technical Specification
Based on the Software Requirement Specification and the Software Project Interface
Prototype, a Software Technical Specification is the next artifact that needs to be
developed. The project manager along with the project development team has
defined the Software Requirements Specification document.
Software Project Documents
All software project documents can be found in Canvas->Files->Assignments.
• 00_SMP_Software_Project_Proposal.docx
• 01_SMP_Software_Project_Plan.docx
• 02_SMP_Software_Requirements_Specification.docx
• 03_SMP_Software_Project_Interface_Prototypes.docx
• 04_SMP_Software_Technical_Specification.docx
• 05_SMP_Software_Design_Specification.docx
• 06_SMP_Software_System_Test_Plan.docx
• 07_SMP_Software_Project_Information_File.docx
Software Project Reference Documents
Software development process reference documents can be found in Canvas->Files-
>References.
• Software_Development_Process.docx
• Software_Development_Process_Checklist.docx
• Software_Project_Information_File_Template.docx
Deliverables
For this iteration of the project, the project manager will direct the team to
implement the software application according to the Software Requirements
Specification, the Software Project Interface Prototypes, and the Software Technical
Specification. The following Software Requirements Specification items should be
implemented and tested:
The project manager uploads all source code.
• 2.1 SMP Server Requirements
o 2.1.1 Requirement 1: Application Start
o 2.1.2 Requirement 2: Show Messages
o 2.1.3 Requirement 3: Write SMP PUT Message to File
o 2.1.4 Requirement 4: Read SMP GET Message from File
• 2.2 SMP Client Producer Requirements
o 2.2.1 Requirement 1: Send Message
• 2.3 SMP Client Consumer Requirements
o 2.3.1 Requirement 1: Get Message
• 06_SMP_Software_System_Test_Plan.docx
o The test plan should be stored in a directory named Documents in the
root of the source code directory.
Each team member uploads the following documents:
• Team_Member_Assessment_Form.docx
• Team_Member_Statement_Of_Contribution.docx
NOTE: For reference purposes, example source code examples and prototypes have
been developed. The source code examples and prototypes are uploaded to
Canvas->Files->SourceCode.
Notes
•
Keep in mind that the focus of the course is on the Software Development
Process and the supporting project documents. The focus is not on programming
any particular application domain, in any particular programming language.
• Developing the Software Project Plan and the Software Requirements
Specification is an iterative process. If necessary, you’ll have a chance to update
the document as the course progresses.
• Typically, the application’s UI can be a command-line user interface (CUI), or a
graphical user interface (GUI).
• For this project, the software application implementation language can be either
C# or Java. I’ll introduce the C# programming language, along with the Visual
Studio IDE (Integrated Development Environment) that’s used to implement the
C# application. Visual Studio has a built-in GUI creation tool that works well
when developing C# GUI applications. I’ll also introduce the Java Swing GUI
toolkit for those teams interested in implementing the GUI using Java.
Annotations
