package com.qube.simulator;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

}


