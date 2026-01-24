package com.docencia.aed.service.impl;

import com.docencia.aed.domain.EventCreateRequest;
import com.docencia.aed.domain.EventPatchRequest;
import com.docencia.aed.entity.Event;
import com.docencia.aed.entity.EventStatus;
import com.docencia.aed.repository.EventRepository;
import com.docencia.aed.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repo;

    public EventServiceImpl(EventRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Event> listPublicApproved() {
        return repo.findByStatus(EventStatus.APPROVED);
    }

    @Override
    public Event getPublicApprovedById(Long id) {
        return repo.findById(id)
                .filter(e -> e.getStatus() == EventStatus.APPROVED)
                .orElseThrow(() -> new RuntimeException("Event not found or not approved"));
    }

    @Override
    public List<Event> listV2(String requestingUser, boolean isAdmin, EventStatus statusFilterOrNull) {
        if (isAdmin) {
            return (statusFilterOrNull != null) ? repo.findByStatus(statusFilterOrNull) : repo.findAll();
        }
        return (statusFilterOrNull != null) 
                ? repo.findByCreatedByAndStatus(requestingUser, statusFilterOrNull) 
                : repo.findByCreatedBy(requestingUser);
    }

    @Override
    public Event getV2ById(String requestingUser, boolean isAdmin, Long id) {
        Event event = repo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        if (!isAdmin && !event.getCreatedBy().equals(requestingUser)) {
            throw new RuntimeException("Access denied");
        }
        return event;
    }

    @Override
    public Event create(String requestingUser, boolean isAdmin, EventCreateRequest req) {
        Event event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setStatus(EventStatus.DRAFT);
        event.setCreatedBy(requestingUser);
        return repo.save(event);
    }

    @Override
    public Event patch(String requestingUser, boolean isAdmin, Long id, EventPatchRequest req) {
        Event event = getV2ById(requestingUser, isAdmin, id);
        if (req.getTitle() != null) event.setTitle(req.getTitle());
        if (req.getDescription() != null) event.setDescription(req.getDescription());
        return repo.save(event);
    }

    @Override
    public Event submitForApproval(String requestingUser, boolean isAdmin, Long id) {
        Event event = getV2ById(requestingUser, isAdmin, id);
        event.setStatus(EventStatus.PENDING_APPROVAL);
        return repo.save(event);
    }

    @Override
    public Event approve(String requestingUser, boolean isAdmin, Long id) {
        if (!isAdmin) throw new RuntimeException("Only admins can approve");
        Event event = repo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        event.setStatus(EventStatus.APPROVED);
        return repo.save(event);
    }

    @Override
    public Event reject(String requestingUser, boolean isAdmin, Long id, String reason) {
        if (!isAdmin) throw new RuntimeException("Only admins can reject");
        Event event = repo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        event.setStatus(EventStatus.REJECTED);
        // El motivo de rechazo podría guardarse en un campo de la entidad si existiera
        return repo.save(event);
    }

    @Override
    public void delete(String requestingUser, boolean isAdmin, Long id) {
        Event event = getV2ById(requestingUser, isAdmin, id);
        repo.deleteById(event.getId());
    }
}