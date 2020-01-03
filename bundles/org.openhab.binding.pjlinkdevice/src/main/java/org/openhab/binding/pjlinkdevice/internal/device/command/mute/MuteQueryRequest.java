/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
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
package org.openhab.binding.pjlinkdevice.internal.device.command.mute;

import org.openhab.binding.pjlinkdevice.internal.device.command.Request;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The request part of {@link MuteQueryCommand}
 *
 * @author Nils Schnabel - Initial contribution
 */
@NonNullByDefault
public class MuteQueryRequest implements Request {

  @Override
  public String getRequestString() {
    return "%1AVMT ?";
  }

}
