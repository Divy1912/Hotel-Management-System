# Hotel Reservation System 🏨

A simple **Java console application** that uses **MySQL and JDBC** to manage hotel room reservations. Built for learning and practicing Java + database integration.

---

## 📌 Features

- ✅ Reserve a room
- 👀 View all reservations
- 🔍 Get room number by reservation ID and guest name
- ✏️ Update reservation details
- ❌ Delete a reservation
- 🗓️ Reservation date auto-recorded

---

## 🛠 Tech Stack

- **Java** (JDK 8+)
- **JDBC** for database interaction
- **MySQL** as the database
- **Console-based UI**

---

## ⚙️ Database Setup

1. Create a MySQL database called `hotel_db`
2. Run this SQL command to create the required table:

```sql
CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(255) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(10) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
---

## 🚀 How to Run
1. Clone or Download the Repo
git clone https://github.com/YOUR_USERNAME/HotelReservationSystem.git
cd HotelReservationSystem
2. Compile the Java code
javac src/Main.java
3. Run the Application
java -cp src Main

Make sure MySQL is running and your connection string in the code is correct:  

1. private static final String url = "jdbc:mysql://localhost:3306/hotel_db";  

2. private static final String id = "root";  

3. private static final String pass = "your_mysql_password";  
---
## 📁 Project Structure
HotelReservationSystem/  

├── src/  

│   └── Main.java  

├── .gitignore  

└── README.md  

---
## 👨‍💻 Author  

Divyansh Goyal  

B.Tech CSE @ Medi-Caps University  

🚀 Aspiring Full Stack Developer  

📫 LinkedIn  
https://www.linkedin.com/in/divyansh-goyal-profile/  

---
## 📄 License
This project is licensed under the MIT License — feel free to use and modify it.
---
---
## 🌟 Star the repo if you found it helpful!

---
