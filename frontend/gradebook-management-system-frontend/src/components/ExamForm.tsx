import axios from "axios";
import { useState } from "react";

const ExamForm = ({ token, courseId }: { token: string; courseId: number }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [examDate, setExamDate] = useState("");

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8085/exams/create-exam",
        {
          title,
          description,
          examDate,
          course: { id: courseId },
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      console.log("Assignment created successfully:", response.data);
    } catch (error) {
      console.error("Error creating assignment:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="justify-content-center">
      <div className="mb-3 d-flex flex-column align-items-center">
        <label htmlFor="title" className="form-label">
          Title:
        </label>
        <input
          type="text"
          id="title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="form-control"
          style={{ width: "50%" }}
          required
        />
      </div>
      <div className="mb-3 d-flex flex-column align-items-center">
        <label htmlFor="description" className="form-label">
          Description:
        </label>
        <textarea
          id="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="form-control"
          style={{ width: "50%" }}
          required
        />
      </div>
      <div className="mb-3 d-flex flex-column align-items-center">
        <label htmlFor="examDate" className="form-label">
          Exam Date:
        </label>
        <input
          type="date"
          id="examDate"
          value={examDate}
          onChange={(e) => setExamDate(e.target.value)}
          className="form-control"
          style={{ width: "50%" }}
          required
        />
      </div>
      <button className="btn btn-secondary" type="submit">
        Submit
      </button>
    </form>
  );
};

export default ExamForm;
