package Domain;

public class WorkType implements Poolable {
    private int id;
    private String title;

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getId() {
        return id;
    }
}
