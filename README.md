# QUBE

QUBE is an interactive quantum computing learning platform. We made it to help students, teachers, and people who enjoy quantum computing in general explore the fundamentals of quantum mechanics and quantum computing through simulation. QUBE provides both a dashboard for managing all the qubits and a drag-and-drop circuit builder for playing around and experimenting with quantum gates and algorithms. Visit our website to get started: [https://r612p.github.io/qube/](https://r612p.github.io/qube/).


---

## Features

### Qubit Simulator
- **Create Qubits**: Define a qubit with custom amplitudes (a, b) for the |0⟩ and |1⟩ states
- **Manage Qubits**: View, delete, or "uncollapse" qubits after measurement
- **Logging**: Keep track of qubit operations and measurements you made in the past

### Quantum Circuit Builder
- **Drag-and-Drop Interface**: Build circuits using a collection of different quantum gates
- **Gate Support**: Includes common gates like X, Y, Z, H, S, T, and CNOT, with more advanced gates being added in the future
- **Execution**: Apply circuits to qubits and see what happens (the result)

### Backend
- **Spring Boot API**: Keep qubit storage, gate execution, and measurement logic
- **In-Memory Storage**: Qubits managed in a thread-safe `ConcurrentHashMap`.
- **Fully Java Program**: The code can be easily understood if you have a basic knowledge of java or have taken classes such as AP Computer Science A in High School.

### Frontend
- **Dashboard**: Displays all qubits with controls for measurement, uncollapse, and deletion
- **Circuit Workspace**: Drag, drop, and connect gates to qubits to simulate circuits
- **Interactive Feedback**: Real-time updates as qubits are created, manipulated, and measured

---

## Goals
- Provide an accessible way to **learn and experiment** with quantum concepts without needing a real quantum computer (expensive)
- Serve as a teaching aid for outreach events and whoever might need it to facilitate the learning of quantum computing
- Build a program that is capable of letting users implement advanced algorithms like Shor’s and Grover’s in a visually intuitive and educational way

---

## Future Roadmap
- **Algorithm Modules**: Shor’s, Grover’s, Quantum Fourier Transform - Hopefully this will come once we learn linear algebra, that should make this easier
- **Persistence**: We want to implement a feature for you to be able to save your qubit gate map setup and come back to it later, and we are planning on using Google Firebase for it
- **Collaboration**: It would also be pretty cool to have a multi-user support for classrooms or team projects, sort of like a google document sharing kind of system
- **Visualization**: State vector and Bloch sphere displays would vastly help users see what the math looks like for understanding
- **Python Program Version**: Have a python version of the program that can be run instead of java for those who want to learn the code behind the program but might not understand java as much as python.

---




