# Volunteer Management System

The Volunteer Management System is a full-stack application for logging and tracking volunteer information and hours. It consists of a Node.js backend connected to a MongoDB database, a web-based frontend for data submission (volunteer registration & entry submission), and a desktop Java Swing application to manage volunteers and entries.

## 1. Features

- Submit and store volunteer information via the volunteer registration web page
- Submit and store volunteer session information via the entry form web page
- View and approve/deny volunteer sessions
- Approved sessions add to the hours of the associated volunteer in the database, and the total hours of each volunteer are thus tracked consistently.

## 2. Tech Stack

- **Frontend:** HTML, CSS, JavaScript
- **Backend:** Node.js, Express.js
- **Database:** MongoDB (hosted on MongoDB Atlas)
- **Desktop UI:** Java Swing
- **Deployment:** MongoDB Atlas (Database), Render (Backend/API Server)

## 4. How It Works

- The backend exposes RESTful routes under `/entries` and `/volunteers` that interact with the MongoDB database.
- The web pages allow users to submit volunteer registration and volunteer session entries through simple forms and displays.
- The Java Swing application provides an interface for all volunteer and entry management.
- All data is stored in the MongoDB Atlas cluster accessed via a secure connection string stored in `CLOUD_DB_URL` in the '.env' file.

> ⚠️ **Note:** The backend requires a `CLOUD_DB_URL` environment variable in `.env` to connect to MongoDB Atlas. The '.env' file is included in the repository in 'Backend/VMS API'.

## 5. How to Run the Project

Note: Node.js must be installed on the computer

### 📦 Backend (Express Server)

1. Install dependencies:
   Navigate to the 'VMS API' folder in the terminal and run:
   ```bash
   npm install
   ```
2. Start the server:
   ```bash
   node server.js
   ```
3. Submit Volunteer Registration:

   Navigate to the 'Web Pages' Folder and open 'index.html' in each web page folder. Submit volunteer registration first, and then the session entry.

4. Open the desktop application:
   run 'StartScreen.java' in the 'GUI' folder

Buttons and navigation through the interface are intuitive.
