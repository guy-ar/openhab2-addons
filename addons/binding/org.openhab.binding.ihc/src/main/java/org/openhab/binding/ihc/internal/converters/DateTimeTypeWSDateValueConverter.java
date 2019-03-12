/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.ihc.internal.converters;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.openhab.binding.ihc.internal.ws.exeptions.ConversionException;
import org.openhab.binding.ihc.internal.ws.resourcevalues.WSDateValue;
import org.openhab.binding.ihc.internal.ws.resourcevalues.WSTimeValue;

/**
 * DateTimeType <-> WSDateValue converter.
 *
 * @author Pauli Anttila - Initial contribution
 */
public class DateTimeTypeWSDateValueConverter implements Converter<WSDateValue, DateTimeType> {

    @Override
    public DateTimeType convertFromResourceValue(@NonNull WSDateValue from,
            @NonNull ConverterAdditionalInfo convertData) throws ConversionException {
        Calendar cal = dateTimeToCalendar(from, null);
        return new DateTimeType(ZonedDateTime.ofInstant(cal.toInstant(), TimeZone.getDefault().toZoneId()));
    }

    @Override
    public WSDateValue convertFromOHType(@NonNull DateTimeType from, @NonNull WSDateValue value,
            @NonNull ConverterAdditionalInfo convertData) throws ConversionException {
        Calendar cal = GregorianCalendar.from(from.getZonedDateTime());
        short year = (short) cal.get(Calendar.YEAR);
        byte month = (byte) (cal.get(Calendar.MONTH) + 1);
        byte day = (byte) cal.get(Calendar.DAY_OF_MONTH);
        value.setYear(year);
        value.setMonth(month);
        value.setDay(day);
        return value;
    }

    private Calendar dateTimeToCalendar(WSDateValue date, WSTimeValue time) {
        Calendar cal = new GregorianCalendar(2000, 01, 01);
        if (date != null) {
            short year = date.getYear();
            short month = date.getMonth();
            short day = date.getDay();
            cal.set(year, month - 1, day, 0, 0, 0);
        }
        if (time != null) {
            int hour = time.getHours();
            int minute = time.getMinutes();
            int second = time.getSeconds();
            cal.set(2000, 0, 1, hour, minute, second);
        }
        return cal;
    }
}
