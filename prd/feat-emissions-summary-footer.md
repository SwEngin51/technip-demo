# Feature: Display Emissions Summary Footer

## Context
To help users quickly assess overall carbon impact, the dashboard should include a persistent summary of total emissions across all projects.

## User Story
As an environmental analyst,  
I want to see a total emissions value across all projects at the bottom of the dashboard,  
So that I can immediately understand the organization-wide carbon footprint.

## Acceptance Criteria
- A footer appears at the bottom of the dashboard layout.
- The footer shows: `Total Emissions: X kg CO₂`, where X is the total from `/api/emissions/projects/total`.
- Value updates dynamically once data is fetched.
- If the API fails, show fallback: `Total Emissions: -- kg CO₂`.

## UI Notes
- Background: light gray (`#f9f9f9`)
- Font: bold, right-aligned inside a padded box
- Responsive and visually unobtrusive
