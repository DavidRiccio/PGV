package com.docencia.aed.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.docencia.aed.domain.EventCreateRequest;
import com.docencia.aed.domain.EventPatchRequest;
import com.docencia.aed.entity.Event;
import com.docencia.aed.entity.EventStatus;
import com.docencia.aed.exception.BadRequestException;
import com.docencia.aed.exception.ForbiddenException;
import com.docencia.aed.exception.ResourceNotFoundException;
import com.docencia.aed.infrastructure.security.AppSecurityProperties;
import com.docencia.aed.infrastructure.security.AppSecurityProperties.RolePermissions;
import com.docencia.aed.repository.EventRepository;
import com.docencia.aed.service.EventService;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository repo;
    private final AppSecurityProperties securityProps;

    public EventServiceImpl(EventRepository repo, AppSecurityProperties securityProps) {
        this.repo = repo;
        this.securityProps = securityProps;
    }

    @Override
    public List<Event> listPublicApproved() {
        return repo.findByStatus(EventStatus.APPROVED);
    }

    @Override
    public Event getPublicApprovedById(Long id) {
        Event evento = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el evento"));
        if (evento.getStatus() != EventStatus.APPROVED) {
            throw new ResourceNotFoundException("No se encontro el evento");
        }
        return evento;
    }

    @Override
    public List<Event> listV2(String requestingUser, boolean isAdmin, EventStatus statusFilterOrNull) {
        RolePermissions permisos = permisosEfectivos(isAdmin);
        if (isAdmin) {
            return repo.findByStatus(statusFilterOrNull);
        }
        return repo.findByCreatedByAndStatus(requestingUser, statusFilterOrNull);
    }

    @Override
    public Event getV2ById(String requestingUser, boolean isAdmin, Long id) {
        Event evento = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el evento"));
        if (!isAdmin && !evento.getCreatedBy().equals(requestingUser)) {
            throw new ForbiddenException("No tienes permiso para ver este evento");
        }
        return evento;
    }

    @Override
    public Event create(String requestingUser, boolean isAdmin, EventCreateRequest req) {
        RolePermissions permisos = permisosEfectivos(isAdmin);
        if (!permisos.isCanCreate()) {
            throw new ForbiddenException("No tienes permiso para crear eventos");
        }
        validarDatosEvento(req.getTitle(), req.getStartAt(), req.getEndAt());

        Event evento = new Event();
        evento.setTitle(req.getTitle());
        evento.setDescription(req.getDescription());
        evento.setStartAt(req.getStartAt());
        evento.setEndAt(req.getEndAt());
        evento.setCreatedBy(requestingUser);
        evento.setStatus(EventStatus.DRAFT);
        evento.setCreatedAt(Instant.now());

        return repo.save(evento);
    }

    @Override
    public Event patch(String requestingUser, boolean isAdmin, Long id, EventPatchRequest req) {
        Event evento = getV2ById(requestingUser, isAdmin, id);
        if (!puedeEditar(evento, requestingUser, isAdmin)) {
            throw new ForbiddenException("No tienes permiso para editar este evento");
        }
        if (req.getTitle() != null && !req.getTitle().isBlank()) {
            evento.setTitle(req.getTitle());
        }
        if (req.getDescription() != null) {
            evento.setDescription(req.getDescription());
        }
        if (req.getStartAt() != null && req.getEndAt() != null) {
            if (req.getStartAt().isAfter(req.getEndAt())) {
                throw new BadRequestException("La fecha de inicio no puede ser posterior a la fecha de fin");
            }
            evento.setStartAt(req.getStartAt());
            evento.setEndAt(req.getEndAt());
        }
        return repo.save(evento);
    }

    @Override
    public Event submitForApproval(String requestingUser, boolean isAdmin, Long id) {
        Event evento = getV2ById(requestingUser, isAdmin, id);

        if (!puedeSubmit(evento, requestingUser, isAdmin)) {
            throw new ForbiddenException("No tienes permiso para enviar a aprobación este evento");
        }
        validarEstadoTransicion(evento, EventStatus.DRAFT, EventStatus.REJECTED, "submit");
        evento.setStatus(EventStatus.PENDING_APPROVAL);
        return repo.save(evento);
    }

    @Override
    public Event approve(String requestingUser, boolean isAdmin, Long id) {
        Event evento = getV2ById(requestingUser, isAdmin, id);
        RolePermissions permisos = permisosEfectivos(isAdmin);
        if (!permisos.isCanApprove()) {
            throw new ForbiddenException("No tienes permiso para aprobar eventos");
        }
        validarEstadoTransicion(evento, EventStatus.PENDING_APPROVAL, null, "aprobar");
        evento.setStatus(EventStatus.APPROVED);
        return repo.save(evento);
    }

    @Override
    public Event reject(String requestingUser, boolean isAdmin, Long id, String reason) {
        Event evento = getV2ById(requestingUser, isAdmin, id);
        RolePermissions permisos = permisosEfectivos(isAdmin);
        if (!permisos.isCanReject()) {
            throw new ForbiddenException("No tienes permiso para reprobar eventos");
        }
        validarEstadoTransicion(evento, EventStatus.PENDING_APPROVAL, null, "rechazar");

        evento.setStatus(EventStatus.REJECTED);
        evento.setRejectionReason(reason);
        return repo.save(evento);

    }

    @Override
    public void delete(String requestingUser, boolean isAdmin, Long id) {
        Event evento = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el evento con id " + id));
        RolePermissions permisos = permisosEfectivos(isAdmin);
        if (!permisos.isCanDelete()) {
            throw new ForbiddenException("No tienes permiso para eliminar eventos");
        }
        repo.deleteById(id);
    }

    private RolePermissions permisosEfectivos(boolean isAdmin) {
        if (isAdmin) {
            return securityProps.getPermissions().getAdmin();
        }
        return securityProps.getPermissions().getCollaborator();
    }

    private boolean puedeEditar(Event evento, String requestingUser, boolean isAdmin) {
        RolePermissions permisos = permisosEfectivos(isAdmin);
        if (isAdmin) {
            return permisos.isCanEditAny();
        }
        return evento.getCreatedBy().equals(requestingUser)
                && (evento.getStatus() == EventStatus.DRAFT || evento.getStatus() == EventStatus.REJECTED)
                && permisos.isCanEditOwnDraftOrRejected();
    }

    private boolean puedeSubmit(Event evento, String requestingUser, boolean isAdmin) {
        RolePermissions permisos = permisosEfectivos(isAdmin);

        if (isAdmin) {
            return permisos.isCanSubmitForApproval()
                    && (evento.getStatus() == EventStatus.DRAFT || evento.getStatus() == EventStatus.REJECTED);
        }

        return evento.getCreatedBy().equals(requestingUser)
                && (evento.getStatus() == EventStatus.DRAFT || evento.getStatus() == EventStatus.REJECTED)
                && permisos.isCanSubmitForApproval();
    }

    private void validarEstadoTransicion(Event evento, EventStatus requiredStatus, EventStatus alternativeStatus, String accion) {
        EventStatus estadoActual = evento.getStatus();
        boolean estadoValido = false;

        if (estadoActual == requiredStatus) {
            estadoValido = true;
        } else if (alternativeStatus != null && estadoActual == alternativeStatus) {
            estadoValido = true;
        }

        if (!estadoValido) {
            String mensaje = "Solo se puede " + accion + " eventos en estado " + requiredStatus;
            if (alternativeStatus != null) {
                mensaje += " o " + alternativeStatus;
            }
            throw new ForbiddenException(mensaje);
        }
    }

    private void validarDatosEvento(String title, Instant startAt, Instant endAt) {
        if (title == null || title.isBlank()) {
            throw new BadRequestException("El título es obligatorio");
        }
        if (startAt != null && endAt != null && startAt.isAfter(endAt)) {
            throw new BadRequestException("La fecha de inicio no puede ser posterior a la de fin");
        }
    }
}