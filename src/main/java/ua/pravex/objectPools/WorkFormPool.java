package ua.pravex.objectPools;


import Domain.Poolable;
import Domain.WorkForm;

import java.util.Map;
import java.util.TreeMap;

public class WorkFormPool {
    private Map<Integer, Poolable> pool = new TreeMap<>();

    public WorkForm add(int id, String title) {
        if (!pool.containsKey(id)) {
            WorkForm workForm = new WorkForm();
            workForm.setId(id);
            workForm.setTitle(title);
            pool.put(id, workForm);
            return workForm;
        }
        return (WorkForm) pool.get(id);
    }

    public WorkForm get(int id) {
        return (WorkForm) pool.get(id);
    }
}
