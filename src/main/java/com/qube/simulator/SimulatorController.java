package com.qube.simulator;



import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@CrossOrigin(origins = "https://r612p.github.io")
public class SimulatorController {

    private Map<String, Qubit> qubits = new ConcurrentHashMap<>();

    @PostMapping("/create-qubit")
    public String createQubit(@RequestBody Map<String, Object> input) {
        try {
            String id = (String) input.get("id");

            double aReal = Double.parseDouble(input.get("aReal").toString());
            double aImag = Double.parseDouble(input.get("aImag").toString());
            double bReal = Double.parseDouble(input.get("bReal").toString());
            double bImag = Double.parseDouble(input.get("bImag").toString());

            Qubit qubit = new Qubit(aReal, aImag, bReal, bImag);
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


   @PostMapping("/uncollapse-qubit")
   public String uncollapseQubit(@RequestBody Map<String, String> input) {
      String id = input.get("id");
      Qubit qubit = qubits.get(id);
    if (qubit != null) {
        qubit.resetCollapse();
        return "Qubit '" + id + "' uncollapsed.";
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

        // Check if already collapsed
        if (qubit.isCollapsed()) {
            return "Error: Qubit '" + id + "' has already collapsed.";
        }

        String result = qubit.executeSingle();
        return result;

    } catch (Exception e) {
        return "Error executing qubit: " + e.getMessage();
    }
}


@GetMapping("/qubit/{id}")
public Map<String, Object> getQubit(@PathVariable String id) {
    Qubit qubit = qubits.get(id);
    Map<String, Object> response = new HashMap<>();
    if (qubit == null) {
        response.put("error", "Qubit '" + id + "' not found.");
        return response;
    }

    response.put("state", qubit.toString());
    response.put("collapsed", qubit.isCollapsed());
    response.put("measuredValue", qubit.isCollapsed() ? qubit.executeSingle() : null);
    return response;
}


    @PostMapping("/execute-circuit")
public String executeCircuit(@RequestBody Map<String, Object> input) {
    try {
        List<String> qubitNames = (List<String>) input.get("qubits");
        List<List<String>> gates = (List<List<String>>) input.get("gates");

        // Uncollapse qubits before execution
        for (String id : qubitNames) {
            Qubit q = qubits.get(id);
            if (q != null) {
                q.uncollapse();
            }
        }

        int numQubits = qubitNames.size();
        MultiQubit circuit = new MultiQubit(numQubits);

        // Apply gates
        for (int q = 0; q < numQubits; q++) {
            for (int l = 0; l < gates.get(q).size(); l++) {
                String gate = gates.get(q).get(l);
                if (gate != null && !gate.isEmpty()) {
                    circuit.addGate(gate, q, l);
                }
            }
        }

        return circuit.executeCircut();
    } catch (Exception e) {
        e.printStackTrace();
        return "Error executing circuit: " + e.getMessage();
    }
}
 @PostMapping("/uncollapse-all")
public String uncollapseAllQubits() {
    for (Qubit q : qubits.values()) {
        q.uncollapse(); // This must be a method you define
    }
    return "All qubits uncollapsed.";
}
}

// Also fix your Qubit class to have a constructor that takes four doubles (aReal, aImag, bReal, bImag):
// Add this to Qubit.java:

/*
    public Qubit(double aReal, double aImag, double bReal, double bImag) {
        this.a = new complexNumber(aReal, aImag);
        this.b = new complexNumber(bReal, bImag);
    }
*/
