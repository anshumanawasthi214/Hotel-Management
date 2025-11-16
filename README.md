# Dream Hotel – Full-Stack Hotel Booking & Management Platform

Dream Hotel is a full-stack web application for hotel booking and management. It provides a seamless experience for users to browse and book rooms while giving admins full control over hotel operations. The project demonstrates full-stack development using Spring Boot, MySQL, JWT authentication, AWS S3, and a modern React.js frontend.

# 🌟 Features
## User Features

✅ Sign up & login with secure authentication (JWT & Spring Security)

✅ User profile with booking history

✅ Browse available rooms with pagination

✅ Filter rooms by type (single, host, studio)

✅ Search for available rooms by date and room type

✅ Book rooms with check-in/check-out dates and guest count

✅ Receive booking confirmation codes

✅ Find existing bookings using confirmation code

## Admin Features

🔑 Admin login with secure credentials

🏨 Manage rooms: add, edit, delete room details (type, price, photo, description)

📋 Manage bookings: view all bookings, filter by booking number, archive bookings

📊 Admin dashboard to monitor hotel operations

## 📽 Demo Video

A comprehensive tutorial showcasing the full project (backend & frontend) and architectural design is available here:
[Watch Demo Video](Demo Video Link)

## 🛠 Tech Stack

### Backend:

Spring Boot, Spring Security, JWT, MySQL

AWS S3 for room image storage

Lombok for boilerplate reduction

Validation for secure and consistent data

### Frontend:

React.js (components, services, protected routes)

API service for backend communication

Responsive pages: Home, Room Search, Room Details, Profile, Admin Pages

Development Tools:

Frontend: VS Code

Backend: Spring Tool Suite (STS)

## ⚙️ Getting Started
### 1. Clone the Repository
git clone https://github.com/yourusername/hotel-management.git
cd hotel-management

### 2. Backend Setup

Open the backend folder in Spring Tool Suite (STS).

Create a MySQL database (e.g., dream_hotel) and update application.properties with your DB details.

Important: Replace the dummy AWS keys with your own in the backend configuration:

aws.accessKey=YOUR_ACCESS_KEY
aws.secretKey=YOUR_SECRET_KEY
aws.region=YOUR_REGION


Run the Spring Boot application (DreamHotelApplication.java).

### 3. Frontend Setup

Open the frontend folder in VS Code.

Install dependencies:

npm install


Start the React development server:

npm start


Open your browser and go to: http://localhost:3000

## ⚠️ Important Notes

AWS Keys: Dummy keys are included in the repo. You must replace them with your own for AWS S3 integration.

Clean Repository: Only required files for frontend and backend are uploaded. node_modules and other unnecessary files are excluded.

### Development Environment:

Frontend: React.js in VS Code

Backend: Spring Boot in Spring Tool Suite (STS)

## 🔧 Project Structure
hotel-management/
├── backend/          # Spring Boot backend
│   ├── src/
│   ├── pom.xml
├── frontend/         # React.js frontend
│   ├── src/
│   ├── package.json
├── README.md

## 🏗 Technical Architecture
### Backend

Entities/Models: User, Room, Booking with proper relationships

DTOs: Transfer only required data between client and server

Repositories: JPA repositories for database operations, including custom queries

Security: Spring Security with JWT authentication & authorization

AWS S3 Integration: Store room images in S3 and reference URLs in the database

Services & Controllers: Handle business logic and REST API endpoints

### Frontend

Organized React components and services

API service for backend interaction

Route guards for authentication

Responsive pages: Home, Room Search & Results, Booking, Profile, Admin Dashboard, Manage Rooms & Bookings

###📌 Future Improvements

Integrate payment gateway

Add advanced search filters

Implement email notifications for bookings

Enhance admin analytics dashboard

### 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request.
