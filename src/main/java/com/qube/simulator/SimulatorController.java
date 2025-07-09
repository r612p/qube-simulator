// Fixed version of SimulatorController.java
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
    @PostMapping("/execute")
public String executeQubit(@RequestBody Map<String, String> input) {
    try {
        String id = input.get("id");
        Qubit qubit = qubits.get(id);

        if (qubit == null) {
            return "Qubit with ID '" + id + "' not found.";
        }

        String result = qubit.executeSingle();
        return "Execution result of Qubit '" + id + "': " + result + " (collapsed = " + qubit.isCollapsed() + ")";
    } catch (Exception e) {
        return "Error executing qubit: " + e.getMessage();
    }
}


    @GetMapping("/qubit/{id}")
    public String getQubit(@PathVariable String id) {
        Qubit qubit = qubits.get(id);
        if (qubit == null) {
            return "Qubit '" + id + "' not found.";
        }
        return qubit.toString();
    }

    @PostMapping("/execute-circuit")
    public String executeCircuit(@RequestBody Map<String, Object> input) {
        try {
            List<String> qubitNames = (List<String>) input.get("qubits");
            List<List<String>> gates = (List<List<String>>) input.get("gates");

            int numQubits = qubitNames.size();

            MultiQubit circuit = new MultiQubit(numQubits);

            // Apply gates to workspace properly
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
}

// Also fix your Qubit class to have a constructor that takes four doubles (aReal, aImag, bReal, bImag):
// Add this to Qubit.java:

/*
    public Qubit(double aReal, double aImag, double bReal, double bImag) {
        this.a = new complexNumber(aReal, aImag);
        this.b = new complexNumber(bReal, bImag);
    }
*/
