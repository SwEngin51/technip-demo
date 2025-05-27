import React, { useEffect, useState } from "react";
import axios from "axios";
import { Bar, Pie, Line } from "react-chartjs-2";
import { ClipLoader } from "react-spinners";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    LineElement,
    PointElement,
    ArcElement,
    Tooltip,
    Legend,
} from "chart.js";

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    LineElement,
    PointElement,
    ArcElement,
    Tooltip,
    Legend
);

const Dashboard = () => {
    const [totalByProject, setTotalByProject] = useState([]);
    const [byCategory, setByCategory] = useState([]);
    const [overTime, setOverTime] = useState([]);
    const [activeTab, setActiveTab] = useState("project");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [projectsData, categoryData, timeData] = await Promise.all([
                    axios.get("/api/emissions/projects/total"),
                    axios.get("/api/emissions/by-category"),
                    axios.get("/api/emissions/time-series")
                ]);

                setTotalByProject(projectsData.data);
                setByCategory(categoryData.data);
                setOverTime(timeData.data);
                setLoading(false);
            } catch (error) {
                console.error("Error fetching data:", error);
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    const exportToCSV = () => {
        // Create CSV content
        const headers = ["Project ID", "Project Name", "Total Emissions (kg CO₂)"];
        const csvRows = [
            headers.join(","),
            ...totalByProject.map(project => 
                [
                    project.projectId,
                    `"${project.projectName}"`, // Wrap name in quotes to handle commas
                    project.totalEmissions
                ].join(",")
            )
        ];
        const csvContent = csvRows.join("\n");

        // Create and trigger download
        const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
        const link = document.createElement("a");
        const url = URL.createObjectURL(blob);
        link.setAttribute("href", url);
        link.setAttribute("download", "emissions-by-project.csv");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    const renderProjectChart = () => (
        <div>
            <div style={{ display: "flex", justifyContent: "flex-end", marginBottom: "10px" }}>
                <button
                    onClick={exportToCSV}
                    style={{
                        padding: "8px 16px",
                        backgroundColor: "#4CAF50",
                        color: "white",
                        border: "none",
                        borderRadius: "4px",
                        cursor: "pointer"
                    }}
                >
                    Export CSV
                </button>
            </div>
            <Bar
                data={{
                    labels: totalByProject.map((p) => p.projectName),
                    datasets: [
                        {
                            label: "kg CO₂",
                            data: totalByProject.map((p) => p.totalEmissions),
                            backgroundColor: "rgba(75, 192, 192, 0.7)",
                        },
                    ],
                }}
            />
        </div>
    );

    const renderChart = () => {
        switch (activeTab) {
            case "project":
                return renderProjectChart();
            case "category":
                return (
                    <Pie
                        data={{
                            labels: byCategory.map((item) => item.category),
                            datasets: [
                                {
                                    label: "kg CO₂",
                                    data: byCategory.map((item) => item.total),
                                    backgroundColor: ["#36A2EB", "#FF6384", "#FFCE56"],
                                },
                            ],
                        }}
                    />
                );
            case "time":
                return (
                    <Line
                        data={{
                            labels: overTime.map((pt) => pt.date),
                            datasets: [
                                {
                                    label: "Total Emissions (kg CO₂)",
                                    data: overTime.map((pt) => pt.total),
                                    fill: false,
                                    borderColor: "rgba(153, 102, 255, 1)",
                                    tension: 0.1,
                                },
                            ],
                        }}
                    />
                );
            default:
                return null;
        }
    };

    if (loading) {
        return (
            <div style={{ 
                width: "100%", 
                height: "400px", 
                display: "flex", 
                justifyContent: "center", 
                alignItems: "center",
                flexDirection: "column",
                gap: "20px"
            }}>
                <ClipLoader color="#007bff" size={50} />
                <div>Loading dashboard data...</div>
            </div>
        );
    }

    return (
        <div style={{ width: "90%", margin: "auto", paddingTop: "20px" }}>
            <h1 style={{ textAlign: "center" }}>Carbon Emission Reports</h1>

            <div style={{ display: "flex", justifyContent: "center", marginBottom: "20px" }}>
                <button onClick={() => setActiveTab("project")} style={tabStyle(activeTab === "project")}>
                    🔵 By Project
                </button>
                <button onClick={() => setActiveTab("category")} style={tabStyle(activeTab === "category")}>
                    🔶 By Category
                </button>
                <button onClick={() => setActiveTab("time")} style={tabStyle(activeTab === "time")}>
                    🟢 Over Time
                </button>
            </div>

            <div style={{ maxWidth: "800px", margin: "auto" }}>{renderChart()}</div>
        </div>
    );
};

const tabStyle = (isActive) => ({
    margin: "0 10px",
    padding: "10px 20px",
    border: "none",
    borderBottom: isActive ? "3px solid #007bff" : "1px solid #ccc",
    backgroundColor: isActive ? "#f0f8ff" : "#f9f9f9",
    cursor: "pointer",
    fontWeight: isActive ? "bold" : "normal"
});

export default Dashboard;
