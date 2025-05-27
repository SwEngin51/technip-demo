# Feature: Add Loading Spinner to Dashboard

## Context
Currently, the dashboard briefly shows empty charts before data is loaded, which can be confusing. We need to add a loading indicator.

## User Story
As a user,  
I want to see a loading indicator while data is being fetched,  
So that I know the dashboard is working and not broken.

## Acceptance Criteria
- A full-page spinner appears on initial dashboard load.
- Spinner disappears only after all 3 data sources are fetched:
    - `/projects/total`
    - `/by-category`
    - `/time-series`
- Use minimal, centered spinner or text-based indicator.

## UI Notes
- Spinner must be visually centered in the dashboard area.
- Fallback: `Loading dashboard data...` in case animation isn't supported.
