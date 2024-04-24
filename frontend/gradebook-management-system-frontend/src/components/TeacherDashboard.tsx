import axios from "axios";
import { useEffect, useState } from "react";

interface Course {
  id: number;
  name: string;
}

interface Student {
  firstName: string;
  lastName: string;
  email: string;
  dateOfBirth: number[];
  formattedDateOfBirth: string;
}

const TeacherDashboard = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [courseClicked, setCourseClicked] = useState(false);
  const [courseId, setCourseId] = useState<number>(0);
  const [studentDetail, setStudentDetail] = useState<Student[]>([]);

  const token = sessionStorage.getItem("jwt");

  const getRegisteredStudents = (courseId: number) => {
    axios
      .get(`http://localhost:8085/courses/get-course/${courseId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        const enrolledStudents = response.data.enrolledStudents;

        const studentData = enrolledStudents.map((student: any) => {
          const dateOfBirth = new Date(student.dateOfBirth);
          const formattedDateOfBirth = dateOfBirth.toLocaleDateString();
          return {
            firstName: student.firstName,
            lastName: student.lastName,
            email: student.email,
            dateOfBirth: student.dateOfBirth,
            formattedDateOfBirth: formattedDateOfBirth,
          };
        });
        setStudentDetail(studentData);
        console.log(studentDetail);
      })
      .catch((error) => {
        console.error("Error fetching registered students:", error);
      });
  };

  useEffect(() => {
    axios
      .get("http://localhost:8085/courses/get-all-courses", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setCourses(response.data);
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

      {courseClicked ? (
        <div className="container">
          <div
            className="fw-bolder text-danger fs-6 d-flex justify-content-end delete-pointer"
            onClick={() => setCourseClicked(false)}
          >
            X
          </div>
          <div className="d-flex flex-column justify-content-center align-items-center">
            {studentDetail.map((student) => {
              return (
                <div
                  key={student.email}
                  className="card m-2 bg-secondary text-white custom-card d-flex flex-column justify-content-start"
                  style={{ width: "60%" }}
                >
                  <div className="card-body">
                    <div className="mb-2">
                      Name: {student.firstName} {student.lastName}
                    </div>
                    <div className="mb-2">{student.email}</div>
                    <div className="mb-2">{student.formattedDateOfBirth}</div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      ) : (
        <div className="d-flex flex-column justify-content-center align-items-center">
          {courses.map((course) => (
            <div
              key={course.id}
              className="card m-2 bg-secondary text-white custom-card"
              onClick={() => {
                setCourseClicked(true);
                setCourseId(course.id);
                console.log(courseId);
                getRegisteredStudents(course.id);
              }}
              style={{ width: "60%", transition: "transform 0.3s" }}
            >
              <div className="card-body">{course.name}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default TeacherDashboard;
