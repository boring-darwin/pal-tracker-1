package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private ConcurrentHashMap<Long, TimeEntry> map= new ConcurrentHashMap<>();
    private long timeEntryId = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntryToCreate) {
        TimeEntry newTimeEntry = new TimeEntry(timeEntryId, timeEntryToCreate.getProjectId(), timeEntryToCreate.getUserId(), timeEntryToCreate.getDate(), timeEntryToCreate.getHours());
        map.put(timeEntryId, newTimeEntry);
        timeEntryId++;

        return newTimeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return map.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {

        List<TimeEntry> list = new ArrayList<>(map.values());
        return list;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (find(id) == null) return null;
        TimeEntry updatedTimeEntry = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        map.replace(id, updatedTimeEntry);

        return map.get(id);
    }

    @Override
    public void delete(long timeEntryId) {
        map.remove(timeEntryId);

    }
}