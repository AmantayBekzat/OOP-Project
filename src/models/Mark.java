package models;

public class Mark {
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private double totalMark;
    private double points;

    public Mark() {}

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
        calculateTotal();
    }

    public double calculateTotal() {
        totalMark = firstAttestation + secondAttestation + finalExam;
        if      (totalMark >= 95) points = 4.0;
        else if (totalMark >= 90) points = 4.0;
        else if (totalMark >= 85) points = 3.67;
        else if (totalMark >= 80) points = 3.33;
        else if (totalMark >= 75) points = 3.0;
        else if (totalMark >= 70) points = 2.67;
        else if (totalMark >= 65) points = 2.33;
        else if (totalMark >= 60) points = 2.0;
        else if (totalMark >= 55) points = 1.67;
        else if (totalMark >= 50) points = 1.0;
        else                      points = 0.0;
        return totalMark;
    }

    public String getLetterGrade() {
        if      (totalMark >= 95) return "A+";
        else if (totalMark >= 90) return "A";
        else if (totalMark >= 85) return "A-";
        else if (totalMark >= 80) return "B+";
        else if (totalMark >= 75) return "B";
        else if (totalMark >= 70) return "B-";
        else if (totalMark >= 65) return "C+";
        else if (totalMark >= 60) return "C";
        else if (totalMark >= 55) return "C-";
        else if (totalMark >= 50) return "D+";
        else return "F";
    }

    public boolean isPassed() {
        return totalMark >= 50;
    }

    public double getFirstAttestation() { return firstAttestation; }
    public void setFirstAttestation(double firstAttestation) { this.firstAttestation = firstAttestation; }

    public double getSecondAttestation() { return secondAttestation; }
    public void setSecondAttestation(double secondAttestation) { this.secondAttestation = secondAttestation; }

    public double getFinalExam() { return finalExam; }
    public void setFinalExam(double finalExam) { this.finalExam = finalExam; }

    public double getTotalMark() { return totalMark; }

    public double getPoints() { return points; }
}
