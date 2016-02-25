package Domain;

/**
 * Created by Kryvenko on 08.01.2016.
 */
public class WorkTitle implements Poolable {
    private int id;
    private String title;
    private WorkType workType;

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    @Override
    public int getId() {
        return id;
    }
}
