# Task Management System

The Task Manager is a robust web-based application designed to streamline project and task management.  
With a focus on user-friendly interfaces and efficient collaboration, this system incorporates various   
features to enhance project productivity.

**User Registration and Authentication:**
- Users can register and create accounts securely.
- Authentication mechanisms ensure secure access to the system.

**Project Management:**
- Create, Read, Update, and Delete (CRUD) operations for projects.
- Users can initiate new projects, update project details, and delete projects when necessary.

**Task Management:**
- Efficient task management functionalities within the context of specific projects.
- Tasks can be created, viewed, updated, and deleted within the scope of a project.

**Assign and Notify Users About Tasks:**
- Users can assign tasks to team members.
- Automated notifications ensure that assigned users are promptly informed about new tasks.

**Comments and Attachments to Tasks:**
- Users can add comments to tasks, facilitating communication and collaboration.
- Attachments can be added to tasks to provide additional context or supporting documentation.

**Organize Tasks with Labels:**
- Organize tasks systematically using labels.
- Labels help categorize and classify tasks for better organization and retrieval.

**Store Attachments on Dropbox Using Its API:**
- Seamless integration with the Dropbox API for storing task attachments.
- When users upload attachments, the system securely uploads files to Dropbox and stores corresponding metadata locally.

**Sending Email Notifications:**
- Implement email notifications to keep users informed about various events.
- Notifications could include task assignments, upcoming project deadlines, or new comments on assigned tasks.

**Security:**
   - Prioritize secure user authentication mechanisms.
   - Ensure data integrity and confidentiality, especially concerning sensitive user information.

### Controllers

1. **Auth Controller**:
   - POST: /api/auth/register - User registration
   - POST: /api/auth/login - User authentication

2. **Users Controller**:
   - PUT: /users/{id}/role - Update user role
   - GET: /users/me - Get my profile info
   - PUT/PATCH: /users/me - Update profile info

3. **Project Controller**:
   - POST: /api/projects - Create a new project
   - GET: /api/projects - Retrieve user's projects
   - GET: /api/projects/{id} - Retrieve project details
   - PUT: /api/projects/{id} - Update project
   - DELETE: /api/projects/{id} - Delete project

4. **Task Controller**:
   - POST: /api/tasks - Create a new task
   - GET: /api/tasks - Retrieve tasks for a project
   - GET: /api/tasks/{id} - Retrieve task details
   - PUT: /api/tasks/{id} - Update task
   - DELETE: /api/tasks/{id} - Delete task

5. **Comment Controller**:
   - POST: /api/comments - Add a comment to a task
   - GET: /api/comments?taskId={taskId} - Retrieve comments for a task

6. **Attachment Controller**:
   - POST: /api/attachments - Upload an attachment to a task
   - GET: /api/attachments?taskId={taskId} - Retrieve attachments for a task

7. **Label Controller**:
   - POST: /api/labels - Create a new label
   - GET: /api/labels - Retrieve labels
   - PUT: /api/labels/{id} - Update label
   - DELETE: /api/labels/{id} - Delete label

## Running with Docker Compose

To set up the Online Book Store, follow these steps:

1. Clone the repository: `git clone https://github.com/sanmartial/task-management-system.git`.
2. Ensure you have Java and Maven installed.
3. Customize the `docker-compose.yml` file for your environment.
4. Execute the following commands in the project root directory:
   ```bash
   mvn package
   docker-compose build
   docker-compose up

5. Access the Swagger documentation at `http://localhost:8088/swagger-ui/index.html`.  
   To log in as administrator use the following login and password   
   login: admin@gmail.com;  
   password: 12345678.

The Task Manager is a comprehensive solution for effective project and task management, offering a range of features  
to enhance collaboration, organization, and overall productivity. With a focus on security, scalability, and user   
experience, this web-based application is tailored to meet the dynamic needs of project teams.

Feel free to explore the API 🚀

Thank you for watching, and happy exploring!



