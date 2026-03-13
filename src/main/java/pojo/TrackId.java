package pojo;


public class TrackId {
    private Integer track;

    public TrackId(){}
    public TrackId(Integer track) {
        this.track = track;
    }
    // Геттер возвращает Integer (соответствует типу поля)
    public Integer getTrack() {
        return track;
    }

    // Сеттер принимает Integer (соответствует типу поля)
    public void setTrack(Integer track) {
        this.track = track;
    }
}