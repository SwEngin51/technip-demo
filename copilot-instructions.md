# GitHub Copilot Project Instructions

## Project Overview
This is a full-stack Java + React demo application for carbon emission tracking and visualization. The frontend is built using React and served via Spring Boot. The backend is a Spring Boot REST API that reads JSON data at startup and performs in-memory computation of emission statistics.

## Tech Stack
- Spring Boot 3.x (Java 17+)
- React 18 (JSX)
- Chart.js via react-chartjs-2
- Axios for HTTP calls
- JSON files as in-memory data source (no database)

## Backend Project Structure
```
src/
├── main/
│ ├── java/com/example/emissions/
│ │ ├── controller/ # REST API endpoints
│ │ ├── model/ # Domain models (Project, Activity, EmissionRecord)
│ │ ├── repository/ # In-memory data loaders from JSON
│ │ ├── service/ # EmissionCalculatorService (main logic)
│ └── resources/data/ # Static JSON input files
```
## Frontend Structure
```
frontend/src/
├── App.jsx # Main app shell with tab layout
├── Dashboard.jsx # Loads and renders charts
├── components/ # (optional) visual components
├── public/ # React build artifacts served by Spring
```

## Conventions

### Backend
- All logic is in `EmissionCalculatorService`, which operates on `List<T>` loaded from JSON.
- Repositories return `List<T>` or allow simple filtering by `id`.
- No lateral service calls; architecture flows downward (controller → service → repo).
- Models use Lombok where needed; JSON dates are handled via `JavaTimeModule`.

### Frontend
- Axios calls fetch from `/api/emissions/*`.
- State is managed via `useState` and `useEffect` (no Redux).
- React component layout is tab-based for simplicity.
- Charts use Chart.js (bar, pie, line).
- UI elements like buttons or text use minimal styling, can use inline or utility-first.

## Change Logging
- Each time you generate code or make changes, log the change in the `CHANGELOG.md` file.
- Follow semantic versioning guidelines
- Use the format: `- [Date] - [Description of change]`.

## Preferred AI Behavior

- When implementing a feature, modify the appropriate React or Java file only.
- Avoid creating new layers unless explicitly specified.
- Follow clean naming (e.g., `getTotalEmissionsByProject`, `totalEmissions`, `Dashboard.jsx`).
- For PRDs or issues involving frontend/backend, make both changes in one go.
 