package ua.pravex.objectPools;

import Domain.Poolable;
import Domain.WorkType;

import java.util.Map;
import java.util.TreeMap;


public class WorkTypePool  {
    private Map<Integer, Poolable> pool = new TreeMap<>();

    public WorkType add (int id, String title) {
        if (!pool.containsKey(id)) {
            WorkType workType = new WorkType();
            workType.setId(id);
            workType.setTitle(title);
            pool.put(id, workType);
            return workType;
        }
        return (WorkType)pool.get(id);
    }

    public WorkType get (int id) {
        return (WorkType)pool.get(id);
    }
}
