# WebBookApp — Book Store (Servlet + JDBC)

A beginner‑friendly CRUD web app to register, list, edit, and delete books. Built with **Jakarta Servlet**, **JDBC (MySQL)**, and **Bootstrap 5**.

## ✨ Features
- Add a book (name, edition, price) via a simple form
- List all books with **Edit** and **Delete** actions
- Update a book in place
- Clean Bootstrap UI

## 🧩 Tech Stack
- Java (Jakarta Servlet API 6, compatible with Tomcat 11)
- MySQL 8.x (JDBC)
- Bootstrap 5 for styling
- Runs great from **Eclipse + Tomcat 11**

## 📁 Project structure (suggested)
Your folders may differ slightly depending on how you created the project (Dynamic Web Project vs Maven). This is a *typical* layout:
```
WebBookApp/
├─ src/main/java/com/idiot/servlet/
│  ├─ RegisterServlet.java
│  ├─ EditScreenServlet.java
│  ├─ DeleteScreenServlet.java
│  └─ Booklist.java
├─ src/main/webapp/
│  ├─ home.html
│  └─ WEB-INF/
│     └─ web.xml   (optional if you use @WebServlet annotations)
├─ pom.xml         (only if you use Maven)
└─ README.md

## 🗄️ Database
Create the database and table before running the app.

```sql
-- schema.sql
CREATE DATABASE IF NOT EXISTS books;
USE books;

CREATE TABLE IF NOT EXISTS booksdata (
  id INT PRIMARY KEY AUTO_INCREMENT,
  bookname   VARCHAR(100) NOT NULL,
  bookedition VARCHAR(50) NOT NULL,
  bookprice  DECIMAL(10,2) NOT NULL
);
```

## 🔐 Configure DB credentials (important)
Right now the servlets use hard‑coded JDBC values. **Do not commit real passwords.**  
Two options:

### Quick & dirty (local only)
Edit the `JDBC_URL`, `JDBC_USER`, `JDBC_PASS` constants inside your servlets before you run locally.

### Recommended (GitHub‑safe)
1. Commit a sample file called **`db.example.properties`** (provided in this repo).
2. On your machine, create **`db.properties`** with your real credentials (this file is **ignored** by Git).
3. Load the properties in your app (e.g., from `ServletContext` on startup).

`db.example.properties` (committed):
```properties
jdbc.url=jdbc:mysql://localhost:3306/books
jdbc.user=root
jdbc.pass=REPLACE_ME
```

`db.properties` (not committed):
```properties
jdbc.url=jdbc:mysql://localhost:3306/books
jdbc.user=your_user
jdbc.pass=your_password
```

## ▶️ Run (Eclipse + Tomcat 11)
1. **Install** Java 21, Tomcat 11, and MySQL 8.x.
2. **Import** the project into Eclipse:
   - *File → Import → Existing Projects into Workspace* (or *Maven → Existing Maven Projects* if you have a `pom.xml`).
3. **Add Tomcat** in Eclipse (*Window → Preferences → Server → Runtime Environments*), choose **Tomcat v11.0**.
4. **Add your project** to the Tomcat server: *Servers* view → right‑click Tomcat → *Add and Remove…* → move **WebBookApp** to Configured.
5. **Start** the server.
6. **Open** in browser:  
   `http://localhost:8080/WebBookApp/home.html`  
   (Context path may be your project name; adjust if needed.)

## 🧪 Endpoints quick test
- Create: submit form at `/home.html` (posts to `/register`)
- List: visit `/bookList`
- Edit: click **Edit** on any row (GET `/editScreen?id=...` then submit the form)
- Delete: click **Delete** on any row (GET `/deleteurl?id=...`)

## 📝 What to commit vs ignore
**Commit:**
- `src/` (all Java source and web files like `home.html`)
- `pom.xml` (if using Maven) / `build.gradle` (if using Gradle)
- `README.md`, `schema.sql`, `db.example.properties`

**Ignore (see .gitignore in this repo):**
- `db.properties` (your real secrets)
- build outputs: `/target/`, `/bin/`, `/build/`
- IDE files: `.settings/`, `.classpath`, `.project`, `.idea/`, `.vscode/`, `*.iml`
- OS junk: `.DS_Store`, `Thumbs.db`

## 🚀 Push to GitHub (existing repo)
If your remote repo is **Book-Shop-Web-Application**:
```bash
# from your project root (the folder that contains README.md)
git init
git add .
git commit -m "WebBookApp: initial commit"
git branch -M main

# replace the URL with your repo's URL (HTTPS or SSH)
git remote add origin https://github.com/<your-username>/Book-Shop-Web-Application.git
git push -u origin main
```

### Push using Eclipse (EGit)
1. Right‑click project → **Team → Share Project…** → Git → *Create* repository.
2. Right‑click project → **Team → Commit…** → select files → write message → **Commit and Push**.
3. Enter remote URL (your GitHub repo), **Next → Finish**.

---

### ✅ To‑do / Ideas
- Move JDBC constants to `db.properties` and load at startup
- Add server‑side validation and nicer error pages
- Paginate the book list
- Add search & sorting

Happy coding! 🎉
