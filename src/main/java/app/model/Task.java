package app.model;

public class Task {
    private Integer id;
    private Integer arrivalTime;
    private Integer serviceTime;
    private Integer waitingTime;

    public Task (Integer id, Integer arrivalTime, Integer serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.waitingTime = 0;
    }

    @Override
    public String toString() {
        String taskString = new String("(" + id + ", " + arrivalTime + ", " + serviceTime + ")");
        return taskString;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }
}
