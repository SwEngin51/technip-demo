# 🧠 Carbon Emissions Dashboard (Copilot Agent Mode Demo)

This is a full-stack demo application that showcases how GitHub Copilot Agent Mode can accelerate real software development using structured product requirements (PRDs) and GitHub issues.

The app visualizes carbon emissions by project, activity category, and over time — with a Spring Boot backend and a React frontend served as a single deployable unit.

---

## ⚙️ Tech Stack

- 📦 Backend: Spring Boot (Java 17+)
- 🖼 Frontend: React 18 + Chart.js (served by Spring)
- 📁 Data: In-memory JSON (no database)
- 🤖 Copilot: PRD + Issue-based feature additions (Agent Mode)

---

## 🚀 How to Run the App

### 🔧 1. Build the Frontend

```bash
cd frontend
npm install
npm run build
```

This compiles the React frontend into static assets.

### 📂 2. Copy Frontend to Spring Boot (if not already there)
```bash
cp -r frontend/build/* src/main/resources/static/
```
### ☕ 3. Run the Spring Boot App
From the project root:

```bash
./gradlew bootRun
```

Then open:

```
http://localhost:8080
```
You’ll see a full dashboard with charts.

### ✨ Live Features in Demo
1. Loading Spinner – Added via feature defined under prd directory.
2. CSV export was implemented from a github issue.

## 📂 Project Structure
```
frontend/        → React UI (Chart.js)
├── src/        → Springboot backend
│   └── main/
│       ├── java/com/github/
│       │   ├── controller/       # REST APIs
│       │   ├── model/            # Project, Activity EmissionRecord
│       │   ├── repository/       # In-memory JSON loaders
│       │   ├── service/          # EmissionCalculatorService
│       └── resources/
│           ├── static/           # Frontend build output
│           └── data/             # JSON data files
prd/            → Features to be used in Copilot Agent Mode
```
## 💡 Want to Try Copilot Agent Mode?
Use VS Code with the Copilot extension enabled.

Try:

- Copilot: Implement from feature definition → Choose from prd/ directory
- Copilot + [Github MCP Server](https://github.com/modelcontextprotocol/servers/tree/main/src/github) : Implement GitHub Issue → Use the issue in the GitHub repo



