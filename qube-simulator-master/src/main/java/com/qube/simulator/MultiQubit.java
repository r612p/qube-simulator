package com.qube.simulator;
import java.util.ArrayList;

class MultiQubit {

    public ArrayList<Qubit> entangled = new ArrayList<>();
    public ArrayList<String> entangledOutputs = new ArrayList<>();
    private int numQubits;
    public ArrayList<ArrayList<String>> workspace;

    public MultiQubit(int num) {
        numQubits = num;

        // Initialize qubits
        for (int i = 0; i < num; i++) {
            entangled.add(new Qubit());
        }

        // Generate all binary output strings
        for (int i = 0; i < (int) Math.pow(2, numQubits); i++) {
            String binary = Integer.toBinaryString(i);
            while (binary.length() < numQubits) {
                binary = "0" + binary;
            }
            entangledOutputs.add(binary);
        }


        workspace = new ArrayList<>();
        for (int i = 0; i < numQubits; i++) {
            workspace.add(new ArrayList<>());
        }
    }


   public String executeMulti() {
        StringBuilder answer = new StringBuilder();
        for (Qubit qz: entangled) {
            answer.append(qz.execute()).append(" ");
        }
        return answer.toString().trim();
    }






     public String executeCircut() {
    // finds max layers
    int maxLayers = 0;
    for (ArrayList<String> row : workspace) {
        maxLayers = Math.max(maxLayers, row.size());
    }

    // outter columns
    for (int c = 0; c < maxLayers; c++) {


        //inner
        for (int r = 0; r < workspace.size(); r++) {
            ArrayList<String> row = workspace.get(r);


            if (c >= row.size()) continue;

            String gate = row.get(c);
            if (gate == null) continue;

            if (gate.equals("X")) {
                getQubitFromMulti(r).XGate();
            }

            if (gate.equals("Y")) {
                getQubitFromMulti(r).YGate();
            }

            if (gate.equals("Z")) {
                getQubitFromMulti(r).ZGate();
            }

            if (gate.equals("S")) {
                getQubitFromMulti(r).SGate();
            }

            if (gate.equals("T")) {
                getQubitFromMulti(r).TGate();
            }

            if (gate.equals("SDagger")) {
                getQubitFromMulti(r).SDaggerGate();
            }

            if (gate.equals("TDagger")) {
                getQubitFromMulti(r).TDaggerGate();
            }

            if (gate.equals("Hadamard")) {              //gotta be a way to make this shorter lol instead of spamming if statements
                getQubitFromMulti(r).HGate();
            }
        }
    }

    return executeMulti();
}



public void addGate(String gate, int qube, int layer) {
        ArrayList<String> row = workspace.get(qube);
        while (row.size() <= layer) {
            row.add(null); 
        }
        row.set(layer, gate);
    }




public int getNumQubits(){
        return numQubits;
}

public Qubit getQubitFromMulti(int selection){
        return entangled.get(selection);
}

}
