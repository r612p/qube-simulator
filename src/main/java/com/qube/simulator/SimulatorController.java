package com.qube.simulator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SimulatorController {

    // Thread-safe map to store Qubits by id
    private Map<String, Qubit> qubits = new ConcurrentHashMap<>();

    // Create a new Qubit with two ComplexNumber objects
    @PostMapping("/create-qubit")
    public String createQubit(@RequestBody Map<String, Object> input) {
        try {
            String id = (String) input.get("id");

            double aReal = Double.parseDouble(input.get("aReal").toString());
            double aImag = Double.parseDouble(input.get("aImag").toString());
            double bReal = Double.parseDouble(input.get("bReal").toString());
            double bImag = Double.parseDouble(input.get("bImag").toString());

            complexNumber a = new complexNumber(aReal, aImag);
            complexNumber b = new complexNumber(bReal, bImag);

            Qubit qubit = new Qubit(a, b);
            qubits.put(id, qubit);

            return "Qubit '" + id + "' created successfully.";
        } catch (Exception e) {
            return "Error creating qubit: " + e.getMessage();
        }
    }

@PostMapping("/delete-qubit")
public String deleteQubit(@RequestBody Map<String, String> input) {
    String id = input.get("id");
    if (qubits.remove(id) != null) {
        return "Qubit '" + id + "' deleted successfully.";
    } else {
        return "Qubit '" + id + "' not found.";
    }
}

   @PostMapping("/execute")
public String executeQubit(@RequestBody Map<String, String> input) {
    try {
        String id = input.get("id");
        Qubit qubit = qubits.get(id);

        if (qubit == null) {
            return "Qubit with ID '" + id + "' not found.";
        }

        String result = qubit.execute(); // returns "0" or "1"
        return "Execution result of Qubit '" + id + "': " + result;
    } catch (Exception e) {
        return "Error executing qubit: " + e.getMessage();
    }
}

    // Get the string representation of a Qubit by id
    @GetMapping("/qubit/{id}")
    public String getQubit(@PathVariable String id) {
        Qubit qubit = qubits.get(id);
        if (qubit == null) {
            return "Qubit '" + id + "' not found.";
        }
        return qubit.toString();
    }

    // Add other endpoints (e.g., execute, getProbabilityA, etc.) as needed



@PostMapping("/execute-circuit")
public String executeCircuit(@RequestBody Map<String, Object> input) {
    try {
        // Parse number of qubits
        List<String> qubitNames = (List<String>) input.get("qubits");
        List<List<String>> gates = (List<List<String>>) input.get("gates");

        int numQubits = qubitNames.size();
        int numLayers = gates.get(0).size();

        // Initialize MultiQubit
        MultiQubit circuit = new MultiQubit(numQubits);

        // Resize workspace dynamically to match incoming data
        circuit.workspace = new String[numQubits][numLayers];

        // Apply gates from workspace
        for (int q = 0; q < numQubits; q++) {
            for (int l = 0; l < numLayers; l++) {
                String gate = gates.get(q).get(l);
                if (gate != null && !gate.isEmpty()) {
                    circuit.addGate(gate, q, l);
                }
            }
        }

        // Execute circuit
        return circuit.executeCircut();  // e.g., "0 1 0 1"
    } catch (Exception e) {
        e.printStackTrace();
        return "Error executing circuit: " + e.getMessage();
    }
}



}

