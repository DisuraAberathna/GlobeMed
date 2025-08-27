package com.disuraaberathna.globemed.model.service.mediator;

import com.disuraaberathna.globemed.model.entity.Appointment;

public interface ScheduleMediator {
    void schedule(Appointment appointment);

    void reschedule(Appointment appointment);

    void cancel(Appointment appointment);
}
