package ba.telegroup.car_reservation.util.report;

public class ReportData {

    private String name;
    private byte[] data;

    public ReportData() {}

    public ReportData(String name, byte[] content) {
        this.name = name;
        this.data = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return data;
    }

    public void setContent(byte[] content) {
        this.data = content;
    }
}
