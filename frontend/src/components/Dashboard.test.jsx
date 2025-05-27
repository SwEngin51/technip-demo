import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import Dashboard from './Dashboard';
import axios from 'axios';

// Mock axios
jest.mock('axios');

// Mock URL.createObjectURL
const mockCreateObjectURL = jest.fn();
window.URL.createObjectURL = mockCreateObjectURL;
window.URL.revokeObjectURL = jest.fn();

describe('Dashboard Component CSV Export', () => {
    const mockProjectData = [
        { projectId: "P1", projectName: "Project 1", totalEmissions: 100 },
        { projectId: "P2", projectName: "Project 2, with comma", totalEmissions: 200 }
    ];

    beforeEach(() => {
        // Reset mocks
        jest.clearAllMocks();
        
        // Setup axios mock responses
        axios.get.mockImplementation((url) => {
            switch (url) {
                case '/api/emissions/projects/total':
                    return Promise.resolve({ data: mockProjectData });
                case '/api/emissions/by-category':
                    return Promise.resolve({ data: [] });
                case '/api/emissions/time-series':
                    return Promise.resolve({ data: [] });
                default:
                    return Promise.reject(new Error('not found'));
            }
        });

        // Mock document.createElement to track link creation
        const mockLink = {
            click: jest.fn(),
            setAttribute: jest.fn(),
        };
        jest.spyOn(document, 'createElement').mockImplementation((tag) => {
            if (tag === 'a') return mockLink;
            return document.createElement(tag);
        });
    });

    test('renders Export CSV button when project tab is active', async () => {
        render(<Dashboard />);
        
        // Wait for data to load
        await waitFor(() => {
            expect(screen.queryByText('Loading dashboard data...')).not.toBeInTheDocument();
        });

        // Verify button is present
        expect(screen.getByText('Export CSV')).toBeInTheDocument();
    });

    test('exports correct CSV data when Export CSV button is clicked', async () => {
        render(<Dashboard />);
        
        // Wait for data to load
        await waitFor(() => {
            expect(screen.queryByText('Loading dashboard data...')).not.toBeInTheDocument();
        });

        // Click export button
        fireEvent.click(screen.getByText('Export CSV'));

        // Check if Blob was created with correct content
        const expectedCSV = 'Project ID,Project Name,Total Emissions (kg CO₂)\n' +
            'P1,"Project 1",100\n' +
            'P2,"Project 2, with comma",200';

        // Get the last call to createObjectURL and verify Blob content
        const blob = mockCreateObjectURL.mock.calls[0][0];
        const reader = new FileReader();
        
        reader.onload = () => {
            expect(reader.result).toBe(expectedCSV);
        };
        
        reader.readAsText(blob);

        // Verify link was created with correct attributes
        expect(document.createElement).toHaveBeenCalledWith('a');
        const mockLink = document.createElement('a');
        expect(mockLink.setAttribute).toHaveBeenCalledWith('download', 'emissions-by-project.csv');
        expect(mockLink.click).toHaveBeenCalled();
    });

    test('handles empty project data gracefully', async () => {
        // Mock empty project data
        axios.get.mockImplementation((url) => {
            if (url === '/api/emissions/projects/total') {
                return Promise.resolve({ data: [] });
            }
            return Promise.resolve({ data: [] });
        });

        render(<Dashboard />);
        
        // Wait for data to load
        await waitFor(() => {
            expect(screen.queryByText('Loading dashboard data...')).not.toBeInTheDocument();
        });

        // Click export button
        fireEvent.click(screen.getByText('Export CSV'));

        // Check if Blob was created with just headers
        const expectedCSV = 'Project ID,Project Name,Total Emissions (kg CO₂)';

        const blob = mockCreateObjectURL.mock.calls[0][0];
        const reader = new FileReader();
        
        reader.onload = () => {
            expect(reader.result).toBe(expectedCSV);
        };
        
        reader.readAsText(blob);
    });
});