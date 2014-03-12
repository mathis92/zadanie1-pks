/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PksZadanie.equip;


import PksZadanie.AnalyserMainCheck;
import java.util.ArrayList;

/**
 *
 * @author Mathis
 */
public class AnalyserDataUpdater {

    public Frame frame;
    public AnalyserMainCheck dataFrame;

    public AnalyserDataUpdater(Frame frame, AnalyserMainCheck dataPanel) {
        this.frame = frame;
        this.dataFrame = dataPanel;

    }

    public String makeString(byte data) {
        StringBuilder dataByte = new StringBuilder();
        dataByte.append(String.format("%02X ", data));
        return dataByte.toString();
    }

    public void doIt() {
        ArrayList stringList = new ArrayList();
        String data = new String();
        frame.getBuffer().rewind();

        for (int i = 0; i < frame.getFrameLength(); i++) {
            stringList.add(makeString(frame.getBuffer().get()));

        }
        for (int i = 1; i < stringList.size()+1; i++) {

            data += stringList.get(i-1).toString();

            if (i % 8 == 0) {
                data += " ";
            }
            if (i % 16 == 0) {
                data += "\n";
            }
        }
        dataFrame.getjDataText().setText(data);

    }

}
