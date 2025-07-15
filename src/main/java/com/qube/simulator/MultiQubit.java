package com.qube.simulator;

import java.util.ArrayList;

class MultiQubit {

    public ArrayList<Qubit> entangled = new ArrayList<>();
    public ArrayList<String> entangledOutputs = new ArrayList<>();
    private int numQubits;
    public ArrayList<ArrayList<String>> workspace;

    public MultiQubit(List<Qubit> userQubits) {
    this.numQubits = userQubits.size();
    this.entangled = new ArrayList<>(userQubits);

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

        for (int i = 1; i < entangled.size(); i++) {
            Qubit qz = entangled.get(i);

            if (qz.isCollapsed()) {
                return "Error: Qubit " + i + " is already collapsed.";
            }

            answer.append(qz.executeSingle()).append(" ");
        }

        return answer.toString().trim();
    }

    public String executeCircut() {

        for (Qubit qz : entangled) {
            if (qz.isCollapsed()) {
                return "Error: Qubit was already collapsed";
            }
        }

        int maxLayers = 0;
        for (ArrayList<String> row : workspace) {
            maxLayers = Math.max(maxLayers, row.size());
        }

        for (int c = 0; c < maxLayers; c++) {
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

                if (gate.equals("Hadamard")) {
                    getQubitFromMulti(r).HGate();
                }

                if (gate.equals("CXGateTarget")) {
                    int controlQubitI = -1;
                    for (int i = 0; i < workspace.size(); i++) {
                        if (i == r) continue;
                        ArrayList<String> otherRow = workspace.get(i);
                        if (c < otherRow.size()) {
                            String otherGate = otherRow.get(c);
                            if ("CXGateControl".equals(otherGate)) {
                                controlQubitI = i;
                                break;
                            }
                        }
                    }
                    if (controlQubitI != -1) {
                        CXGate(r, controlQubitI, c);
                    }
                }

                if (gate.equals("CYGateTarget")) {
                    int controlIndex = -1;
                    for (int i = 0; i < workspace.size(); i++) {
                        if (i == r) continue;
                        ArrayList<String> otherRow = workspace.get(i);
                        if (c < otherRow.size()) {
                            String otherGate = otherRow.get(c);
                            if ("CYGateControl".equals(otherGate)) {
                                controlIndex = i;
                                break;
                            }
                        }
                    }
                    if (controlIndex != -1) {
                        CYGate(getQubitFromMulti(r), getQubitFromMulti(controlIndex));
                    }
                }

                if (gate.equals("CZGateTarget")) {
                    int controlIndex = -1;
                    for (int i = 0; i < workspace.size(); i++) {
                        if (i == r) continue;
                        ArrayList<String> otherRow = workspace.get(i);
                        if (c < otherRow.size()) {
                            String otherGate = otherRow.get(c);
                            if ("CZGateControl".equals(otherGate)) {
                                controlIndex = i;
                                break;
                            }
                        }
                    }
                    if (controlIndex != -1) {
                        CZGate(getQubitFromMulti(r), getQubitFromMulti(controlIndex));
                    }
                }

                if (gate.equals("ToffoliGateTarget")) {
                    ArrayList<Integer> controlIndices = new ArrayList<>();
                    for (int i = 0; i < workspace.size(); i++) {
                        if (i == r) continue;
                        ArrayList<String> otherRow = workspace.get(i);
                        if (c < otherRow.size()) {
                            String otherGate = otherRow.get(c);
                            if ("ToffoliGateControl".equals(otherGate)) {
                                controlIndices.add(i);
                            }
                        }
                    }
                    if (controlIndices.size() == 2) {
                        Qubit control1 = getQubitFromMulti(controlIndices.get(0));
                        Qubit control2 = getQubitFromMulti(controlIndices.get(1));
                        Qubit target = getQubitFromMulti(r);
                        ToffoliGate(target, control1, control2);
                    }
                }

                if (gate.equals("SWAPGateTarget")) {
                    int otherQubitIndex = -1;
                    for (int i = 0; i < workspace.size(); i++) {
                        if (i == r) continue;
                        ArrayList<String> otherRow = workspace.get(i);
                        if (c < otherRow.size()) {
                            String otherGate = otherRow.get(c);
                            if ("SWAPGateControl".equals(otherGate)) {
                                otherQubitIndex = i;
                                break;
                            }
                        }
                    }
                    if (otherQubitIndex != -1) {
                        SWAPGate(getQubitFromMulti(r), getQubitFromMulti(otherQubitIndex));
                    }
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

    public void addAdvancedGate(String gate, int targetQube, int controlQube, int layer) {
        ArrayList<String> rowTarget = workspace.get(targetQube);
        while (rowTarget.size() <= layer) {
            rowTarget.add(null);
        }
        rowTarget.set(layer, gate + "Target");

        ArrayList<String> rowControl = workspace.get(controlQube);
        while (rowControl.size() <= layer) {
            rowControl.add(null);
        }
        rowControl.set(layer, gate + "Control");
    }

    public void CXGate(int targetPos, int controlPos) {
        Qubit control = getQubitFromMulti(controlPos);
        Qubit target = getQubitFromMulti(targetPos);
        if (control.getProbabilityB() > 0.5) target.XGate();
    }

    public void ToffoliGate(Qubit target, Qubit control1, Qubit control2) {
        if (control1.getProbabilityB() > 0.5 && control2.getProbabilityB() > 0.5) target.XGate();
    }

    public void CZGate(Qubit target, Qubit control) {
        if (control.getProbabilityB() > 0.5) target.ZGate();
    }

    public void CYGate(Qubit target, Qubit control) {
        if (control.getProbabilityB() > 0.5) target.YGate();
    }

    public void SWAPGate(Qubit qube1, Qubit qube2) {
        complexNumber tempA = qube1.getA();
        qube1.setA(qube2.getA());
        qube2.setA(tempA);
        complexNumber tempB = qube1.getB();
        qube1.setB(qube2.getB());
        qube2.setB(tempB);
        qube1.resetCollapse();
        qube2.resetCollapse();
    }

    public int getNumQubits() {
        return numQubits;
    }

    public Qubit getQubitFromMulti(int selection) {
        return entangled.get(selection);
    }
}
