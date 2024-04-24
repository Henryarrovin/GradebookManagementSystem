import axios from "axios";
import { useEffect, useState } from "react";

interface Course {
  id: number;
  name: string;
}

const TeacherDashboard = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const token = sessionStorage.getItem("jwt");

  useEffect(() => {
    axios
      .get("http://localhost:8085/courses/get-all-courses", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setCourses(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error fetching courses:", error);
      });
  }, [token]);
  return (
    <div>
      <nav className="card bg-secondary mb-5 p-3">
        <div className="container-fluid text-center text-white">
          <h1>Teacher dashboard</h1>
        </div>
      </nav>

      <div className="d-flex flex-column justify-content-center align-items-center">
        {courses.map((course) => (
          <div
            className="card m-2 bg-secondary text-white"
            style={{ width: "60%" }}
          >
            <div key={course.id} className="card-body">
              {course.name}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TeacherDashboard;
