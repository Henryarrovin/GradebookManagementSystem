import axios from "axios";
import { useState } from "react";

const AssignmentForm = ({
  token,
  courseId,
}: {
  token: string;
  courseId: number;
}) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [dueDate, setDueDate] = useState("");

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8085/assignment/create-assignment",
        {
          title,
          description,
          dueDate,
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
        <label htmlFor="dueDate" className="form-label">
          Due Date:
        </label>
        <input
          type="date"
          id="dueDate"
          value={dueDate}
          onChange={(e) => setDueDate(e.target.value)}
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

export default AssignmentForm;
