package com.qube.simulator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.*;

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
    public String executeQubit(@RequestBody Map<String, Object> input) {
        try {
            String id = input.get("id").toString();
            boolean noise = Boolean.parseBoolean(input.get("noise").toString());
            Qubit qubit = qubits.get(id);

            if (qubit == null) {
                return "Qubit with ID '" + id + "' not found.";
            }

            String result = qubit.execute(noise);
            return "Execution result of Qubit '" + id + "': " + result;
        } catch (Exception e) {
            return "Error executing qubit: " + e.getMessage();
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

    @GetMapping("/qubit/{id}")
    public String getQubit(@PathVariable String id) {
        Qubit qubit = qubits.get(id);
        if (qubit == null) {
            return "Qubit '" + id + "' not found.";
        }
        return qubit.toString();
    }
}

class Qubit {
    private complexNumber a;
    private complexNumber b;

    public Qubit(complexNumber a, complexNumber b) {
        this.a = a;
        this.b = b;
    }

    public String execute(boolean noise) {
        if (noise) {
            applyBitFlipNoise(0.05);
        }
        return measure();
    }

    private void applyBitFlipNoise(double probability) {
        if (Math.random() < probability) {
            complexNumber temp = a;
            a = b;
            b = temp;
        }
    }

    private String measure() {
        double prob0 = a.absSquared();
        double r = Math.random();
        return r < prob0 ? "0" : "1";
    }

    public String toString() {
        return "|ψ⟩ = " + a + "|0⟩ + " + b + "|1⟩";
    }
}

class complexNumber {
    private double real, imag;

    public complexNumber(double r, double i) {
        this.real = r;
        this.imag = i;
    }

    public double absSquared() {
        return real * real + imag * imag;
    }

    public String toString() {
        return real + (imag >= 0 ? "+" : "") + imag + "i";
    }
}
