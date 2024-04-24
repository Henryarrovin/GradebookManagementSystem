import axios from "axios";
import { useEffect, useState } from "react";
import AssignmentForm from "./AssignmentForm";
import ExamForm from "./ExamForm";

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

interface Assignment {
  id: number;
  title: string;
  description: string;
  dueDate: number[];
  formattedDueDate: string;
}

interface Exam {
  id: number;
  title: string;
  description: string;
  dueDate: number[];
  formattedDueDate: string;
}

const TeacherDashboard = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [courseClicked, setCourseClicked] = useState(false);
  const [studentClicked, setStudentClicked] = useState(false);
  const [navbar, setNavbar] = useState("");
  const [courseId, setCourseId] = useState<number>(0);
  const [studentDetail, setStudentDetail] = useState<Student[]>([]);
  const [assignmentDetail, setAssignmentDetail] = useState<Assignment[]>([]);
  const [examDetail, setExamDetail] = useState<Exam[]>([]);

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
        console.log(studentData);
      })
      .catch((error) => {
        console.error("Error fetching registered students:", error);
      });
  };

  const getAssignment = (courseId: number) => {
    axios
      .get("http://localhost:8085/assignment/get-all-assignments", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        const assignments = response.data.filter((assignment: any) => {
          return assignment.course.id === courseId;
        });

        const assignmentData = assignments.map((assignment: any) => {
          const dueDate = new Date(assignment.dueDate);
          const formattedDueDate = dueDate.toLocaleDateString();
          return {
            id: assignment.id,
            title: assignment.title,
            description: assignment.description,
            dueDate: assignment.dueDate,
            formattedDueDate: formattedDueDate,
          };
        });
        setAssignmentDetail(assignmentData);
        console.log(assignmentData);
      })
      .catch((error) => {
        console.error("Error fetching assignments:", error);
      });
  };

  const getExam = (courseId: number) => {
    axios
      .get("http://localhost:8085/exams/get-all-exams", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        const exams = response.data.filter((exam: any) => {
          return exam.course.id === courseId;
        });

        const examData = exams.map((exam: any) => {
          const dueDate = new Date(exam.dueDate);
          const formattedDueDate = dueDate.toLocaleDateString();
          return {
            id: exam.id,
            title: exam.title,
            description: exam.description,
            dueDate: exam.dueDate,
            formattedDueDate: formattedDueDate,
          };
        });
        setExamDetail(examData);
        console.log(examData);
      })
      .catch((error) => {
        console.error("Error fetching exams:", error);
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
      <nav className="card bg-secondary p-3">
        <div className="container-fluid text-center text-white">
          <h1>Teacher dashboard</h1>
        </div>
      </nav>

      {courseClicked ? (
        studentClicked ? (
          <div>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
              <div className="container">
                <div className="collapse navbar-collapse" id="navbarNav">
                  <div className="d-flex justify-content-between w-100">
                    <ul className="navbar-nav">
                      <li className="nav-item link-pointer">
                        <a
                          className="nav-link"
                          onClick={() => {
                            getAssignment(courseId);
                            setNavbar("assignment");
                          }}
                        >
                          Assignment
                        </a>
                      </li>
                      <li className="nav-item link-pointer">
                        <a
                          className="nav-link"
                          onClick={() => {
                            getExam(courseId);
                            setNavbar("exam");
                          }}
                        >
                          Exam
                        </a>
                      </li>
                    </ul>
                    <ul className="navbar-nav">
                      <li className="nav-item">
                        <button
                          className="btn btn-outline-danger"
                          onClick={() => {
                            setStudentClicked(false);
                            setNavbar("");
                          }}
                        >
                          Close
                        </button>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </nav>
            {navbar === "assignment" && (
              <div>
                <div className="d-flex flex-column align-items-center">
                  {assignmentDetail.map((assignment) => (
                    <div
                      key={assignment.id}
                      className="card m-2 bg-secondary text-white custom-card"
                      style={{ width: "60%" }}
                    >
                      <div className="card-body">
                        <div className="mb-2">Title: {assignment.title}</div>
                        <div className="mb-2">
                          Description: {assignment.description}
                        </div>
                        <div className="mb-2">
                          Due date: {assignment.formattedDueDate}
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
                <AssignmentForm token={token ?? ""} courseId={courseId} />
              </div>
            )}
            {navbar === "exam" && (
              <div>
                <div className="d-flex flex-column align-items-center">
                  {examDetail.map((exam) => (
                    <div
                      key={exam.id}
                      className="card m-2 bg-secondary text-white custom-card"
                      style={{ width: "60%" }}
                    >
                      <div className="card-body">
                        <div className="mb-2">Title: {exam.title}</div>
                        <div className="mb-2">
                          Description: {exam.description}
                        </div>
                        <div className="mb-2">
                          Due date: {exam.formattedDueDate}
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
                <ExamForm token={token ?? ""} courseId={courseId} />
              </div>
            )}
          </div>
        ) : (
          <div className="container mt-5">
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
                    onClick={() => setStudentClicked(true)}
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
        )
      ) : (
        <div className="d-flex flex-column justify-content-center align-items-center mt-5">
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
