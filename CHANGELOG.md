# Changelog

All notable changes to this project will be documented in this file.

## [1.0.1] - 2025-05-27
### Added
- Loading spinner to dashboard while data is being fetched
- Concurrent data loading for improved performance
- Fallback text message if spinner animation fails
- Added react-spinners package as a dependency

### Changed
- Improved data fetching with Promise.all for better performance
- Dashboard now shows loading state until all data sources are loaded

### Fixed
- Fixed issue where empty charts were briefly visible before data loaded

## [1.1.0] - 2025-05-27
### Added
- CSV export functionality for "Total Emissions by Project" data
  - Added "Export CSV" button above the project emissions chart
  - Downloads include Project ID, Project Name, and Total Emissions columns
  - File downloads as "emissions-by-project.csv"