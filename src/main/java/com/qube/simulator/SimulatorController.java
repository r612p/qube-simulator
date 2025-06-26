package com.qube.simulator; // ✅ Replace this with your actual package name

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

// ✅ Assuming you have these classes in your project
import com.example.simulator.model.Qubit;
import com.example.simulator.model.ComplexNumber;

@RestController
public class SimulatorController {

    private Map<String, Qubit> qubits = new HashMap<>();

    @PostMapping("/create-qubit")
    public String createQubit(@RequestBody Map<String, Object> payload) {
        String id = (String) payload.get("id");
        if (qubits.containsKey(id)) {
            return "Qubit with ID '" + id + "' already exists.";
        }

        try {
            double aReal = Double.parseDouble(payload.get("aReal").toString());
            double bReal = Double.parseDouble(payload.get("bReal").toString());

            // Auto-generate imaginary parts
            double aImag = Math.random();
            double bImag = Math.random();

            ComplexNumber alpha = new ComplexNumber(aReal, aImag);
            ComplexNumber beta = new ComplexNumber(bReal, bImag);

            Qubit qubit = new Qubit(alpha, beta);
            qubits.put(id, qubit);

            return "Qubit '" + id + "' created successfully.";
        } catch (Exception e) {
            return "Error parsing qubit parameters.";
        }
    }

    @PostMapping("/delete-qubit")
    public String deleteQubit(@RequestBody Map<String, Object> payload) {
        String id = (String) payload.get("id");
        if (qubits.remove(id) != null) {
            return "Qubit '" + id + "' deleted successfully.";
        } else {
            return "Qubit not found.";
        }
    }

    @PostMapping("/execute")
    public String executeQubit(@RequestBody Map<String, Object> payload) {
        String id = (String) payload.get("id");
        Qubit qubit = qubits.get(id);
        if (qubit == null) {
            return "Qubit not found.";
        }

        // Placeholder for actual quantum logic
        return "Executed qubit '" + id + "': " + qubit.toString();
    }
}
